package dev.orne.test.rnd;

/*-
 * #%L
 * Orne Test Generators
 * %%
 * Copyright (C) 2021 Orne Developments
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

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Abstract implementation of {@code TypedGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2021-03
 * @param <T> The type of generated values
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
public abstract class AbstractTypedGenerator<T>
extends AbstractGenerator
implements TypedGenerator<T> {

    /** The type of generated values. */
    private final @NotNull Class<T> valueType;

    /**
     * Crates a new instance.
     * 
     * @param valuesType The type of generated values
     */
    protected AbstractTypedGenerator(
            final @NotNull Class<T> valueType) {
        super();
        this.valueType = Validate.notNull(valueType);
    }

    /**
     * Crates a new instance.
     * <p>
     * Infers generated values type from the generic type arguments of this
     * instance class.
     * The class must extend {@code AbstractTypedGenerator} specifying the
     * generic types.
     * <p>
     * Example:
     * <pre>
     * class MyGenerator
     * extends AbstractTypedGenerator{@literal <}MyValues{@literal >} {
     *     ...
     * }
     * </pre>
     */
    @SuppressWarnings("unchecked")
    protected AbstractTypedGenerator() {
        super();
        this.valueType = (Class<T>) TypeUtils.unrollVariables(
                TypeUtils.getTypeArguments(getClass(), AbstractTypedGenerator.class),
                AbstractTypedGenerator.class.getTypeParameters()[0]);
        Validate.notNull(
                this.valueType,
                "Cannot infer the type of generated values from class %s. Wrong implementation?",
                getClass());
    }

    /**
     * Returns the type of generated values.
     * 
     * @return The type of generated values
     */
    public Class<T> getValueType() {
        return this.valueType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(
            final @NotNull Class<?> type) {
        return Validate.notNull(type).equals(this.valueType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull <V> V defaultValue(
            final @NotNull Class<V> type) {
        assertSupported(type);
        return type.cast(defaultValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T nullableDefaultValue() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull <V> V randomValue(
            final @NotNull Class<V> type) {
        assertSupported(type);
        return type.cast(randomValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T nullableRandomValue() {
        final T value;
        if (randomNull(this.valueType)) {
            value = null;
        } else {
            value = randomValue();
        }
        return value;
    }
}
