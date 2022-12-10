package dev.orne.test.rnd.params;

/*-
 * #%L
 * Orne Test Generators
 * %%
 * Copyright (C) 2022 Orne Developments
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import java.lang.reflect.Method;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.GenerationException;
import dev.orne.test.rnd.Generators;

/**
 * Generator that calls a static factory method with generated parameters to
 * generate values of the target type.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @param <T> The type of generated values
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public class FactoryMethodGenerator<T>
extends ExecutableGenerator<T> {

    /**
     * Creates a new instance.
     * 
     * @param type The type of generated values
     * @param method The factory method to call
     * @param paramGenerators The parameter generators
     */
    protected FactoryMethodGenerator(
            final @NotNull Class<T> type,
            final @NotNull Method method,
            final @NotNull TargetedGenerator<?>[] paramGenerators) {
        super(type, method, paramGenerators);
    }

    /**
     * Creates a new instance that will call the specified factory method.
     * 
     * @param <T> The type of the generated instance
     * @param type The type of generated values
     * @param method The factory method to call
     * @return The created generator
     */
    public static <T> FactoryMethodGenerator<T> of(
            final @NotNull Class<T> type,
            final @NotNull Method method) {
        Validate.notNull(type);
        Validate.notNull(method);
        Validate.isTrue(method.getReturnType().equals(type));
        final TargetedGenerator<?>[] generators = new TargetedGenerator<?>[method.getParameterCount()];
        for (int i = 0; i < generators.length; i++) {
            generators[i] = Generators.forParameter(method, i);
        }
        return new FactoryMethodGenerator<>(type, method, generators);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Method getExecutable() {
        return (Method) super.getExecutable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull T generate(
            final Object[] params) {
        try {
            return getValueType().cast(getExecutable().invoke(null, params));
        } catch (Exception e) {
            throw new GenerationException("Error generating new instance", e);
        }
    }
}
