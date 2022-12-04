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

import java.lang.reflect.Constructor;

import javax.validation.constraints.NotNull;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.GenerationException;
import dev.orne.test.rnd.Generators;

/**
 * Generator that calls a constructor with generated parameters to
 * generate values of the target type.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @param <T> The type of generated values
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public class ConstructorGenerator<T>
extends ExecutableGenerator<T> {

    /**
     * Creates a new instance.
     * 
     * @param constructor The constructor to call
     * @param paramGenerators The parameter generators
     */
    protected ConstructorGenerator(
            final @NotNull Constructor<T> constructor,
            final @NotNull TargetedGenerator<?>[] paramGenerators) {
        super(constructor.getDeclaringClass(), constructor, paramGenerators);
    }

    /**
     * Creates a new instance that will call the specified constructor.
     * 
     * @param <T> The type of the generated instance
     * @param constructor The constructor to call
     * @return The created generator
     */
    public static <T> ConstructorGenerator<T> of(
            final @NotNull Constructor<T> constructor) {
        final TargetedGenerator<?>[] generators = new TargetedGenerator<?>[constructor.getParameterCount()];
        for (int i = 0; i < generators.length; i++) {
            generators[i] = Generators.forParameter(constructor, i);
        }
        return new ConstructorGenerator<>(constructor, generators);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public Constructor<T> getExecutable() {
        return (Constructor<T>) super.getExecutable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull T generate(
            final Object[] params) {
        try {
            return getExecutable().newInstance(params);
        } catch (Exception e) {
            throw new GenerationException("Error generating new instance", e);
        }
    }
}
