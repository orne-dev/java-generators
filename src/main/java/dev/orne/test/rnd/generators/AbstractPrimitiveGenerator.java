package dev.orne.test.rnd.generators;

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

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.ClassUtils;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.AbstractTypedGenerator;

/**
 * Abstract generator implementation for primitive types.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @param <T> The primitive wrapper type
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
public abstract class AbstractPrimitiveGenerator<T>
extends AbstractTypedGenerator<T> {

    /**
     * Crates a new instance.
     * <p>
     * Infers generated values type from the generic type arguments of this
     * instance class.
     * The class must extend {@code AbstractPrimitiveGenerator} specifying the
     * generic types.
     * <p>
     * Example:
     * <pre>
     * class MyLongGenerator
     * extends AbstractPrimitiveGenerator{@literal <}Long{@literal >} {
     *     ...
     * }
     * </pre>
     */
    protected AbstractPrimitiveGenerator() {
        super();
    }

    /**
     * Crates a new instance.
     * 
     * @param valuesType The type of generated values
     */
    protected AbstractPrimitiveGenerator(
            final @NotNull Class<T> valueType) {
        super(valueType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(
            final @NotNull Class<?> type) {
        return super.supports(ClassUtils.primitiveToWrapper(type));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <R> R nullableDefaultValue(
            final @NotNull Class<R> type) {
        assertSupported(type);
        return (R) (type.isPrimitive() ? defaultValue() : null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean randomNull(
            final @NotNull Class<?> type) {
        return !type.isPrimitive() && super.randomNull(type);
    }
}
