package dev.orne.test.rnd;

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

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.reflect.TypeUtils;

/**
 * Abstract implementation of {@code TypedParametrizableGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @param <T> The type of generated values
 * @param <P> The parameters type
 * @since 0.1
 */
public abstract class AbstractTypedParametrizableGenerator<
        T,
        P extends AbstractParametrizableGenerator.Parameters>
extends AbstractParametrizableGenerator<P>
implements TypedParametrizableGenerator<T> {

    /** The type of generated values. */
    private final @NotNull Class<T> valueType;

    /**
     * Crates a new instance.
     * 
     * @param valueType The type of generated values
     * @param paramsType The type of parameters
     */
    protected AbstractTypedParametrizableGenerator(
            final @NotNull Class<T> valueType,
            final @NotNull Class<P> paramsType) {
        super(paramsType);
        this.valueType = Validate.notNull(valueType);
    }

    /**
     * Crates a new instance.
     */
    @SuppressWarnings("unchecked")
    protected AbstractTypedParametrizableGenerator() {
        super();
        this.valueType = (Class<T>) TypeUtils.unrollVariables(
                TypeUtils.getTypeArguments(getClass(), AbstractTypedParametrizableGenerator.class),
                AbstractTypedParametrizableGenerator.class.getTypeParameters()[0]);
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
    public @NotNull T defaultValue() {
        return defaultValue(createEmptyParams());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull T defaultValue(
            final @NotNull Object... sources) {
        return defaultValue(createParams(sources));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <V> @NotNull V defaultValue(
            final @NotNull Class<V> type,
            final @NotNull P parameters) {
        assertSupported(type);
        return type.cast(defaultValue(parameters));
    }

    /**
     * Returns the default value of the supported type for the specified
     * parameters.
     * 
     * @param parameters The generation parameters
     * @return The default value.
     */
    public abstract @NotNull T defaultValue(
            final @NotNull P parameters);

    /**
     * {@inheritDoc}
     */
    @Override
    public T nullableDefaultValue() {
        return nullableDefaultValue(createEmptyParams());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T nullableDefaultValue(
            final @NotNull Object... sources) {
        return nullableDefaultValue(createParams(sources));
    }

    /**
     * Returns the default value of the supported type  for the specified
     * parameters allowing {@code null}values.
     * <p>
     * This method should return {@code null} except for native types.
     * 
     * @param parameters The generation parameters
     * @return The nullable default value.
     */
    public T nullableDefaultValue(
            final @NotNull P parameters) {
        final T value;
        if (parameters.isNullable() && randomNull(this.valueType)) {
            value = null;
        } else {
            value = defaultValue(parameters);
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull T randomValue() {
        return randomValue(createEmptyParams());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull T randomValue(
            final @NotNull Object... sources) {
        return randomValue(createParams(sources));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <V> @NotNull V randomValue(
            final @NotNull Class<V> type,
            final @NotNull P parameters) {
        assertSupported(type);
        return type.cast(randomValue(parameters));
    }

    /**
     * Returns a random value of the supported type for the specified
     * parameters.
     * 
     * @param parameters The generation parameters
     * @return A random value.
     */
    public abstract @NotNull T randomValue(
            final @NotNull P parameters);

    /**
     * {@inheritDoc}
     */
    @Override
    public T nullableRandomValue() {
        return nullableRandomValue(createEmptyParams());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T nullableRandomValue(
            final @NotNull Object... sources) {
        return nullableRandomValue(createParams(sources));
    }

    /**
     * Returns a random value of the supported type for the specified
     * parameters allowing {@code null} values.
     * <p>
     * The returned value has a probability of be {@code null} except for
     * native types. If not {@code null} behaves as {@code randomValue()}.
     * 
     * @param parameters The generation parameters
     * @return A random nullable value.
     * @see #randomValue()
     */
    public T nullableRandomValue(
            final @NotNull P parameters) {
        final T value;
        if (parameters.isNullable() && randomNull(this.valueType)) {
            value = null;
        } else {
            value = randomValue(parameters);
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(this.valueType)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) { return false; }
        final AbstractTypedParametrizableGenerator<?, ?> other = (AbstractTypedParametrizableGenerator<?, ?>) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.valueType, other.valueType)
                .build();
    }
}
