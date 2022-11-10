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
 * Abstract implementation of {@code ParametrizableGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @param <P> The parameters type
 * @since 0.1
 */
public abstract class AbstractParametrizableGenerator<
        P extends AbstractParametrizableGenerator.Parameters>
extends AbstractGenerator
implements ParametrizableGenerator {

    /** The type of generation parameters. */
    private final @NotNull Class<P> parametersType;

    /**
     * Creates a new instance.
     * 
     * @param paramsType The type of parameters
     */
    protected AbstractParametrizableGenerator(
            final @NotNull Class<P> paramsType) {
        super();
        this.parametersType = Validate.notNull(paramsType);
    }

    /**
     * Crates a new instance.
     */
    @SuppressWarnings("unchecked")
    protected AbstractParametrizableGenerator() {
        super();
        this.parametersType = (Class<P>) TypeUtils.unrollVariables(
                TypeUtils.getTypeArguments(getClass(), AbstractParametrizableGenerator.class),
                AbstractParametrizableGenerator.class.getTypeParameters()[0]);
        Validate.notNull(
                this.parametersType,
                "Cannot infer the type of parameters from class %s. Wrong implementation?",
                getClass());
    }

    /**
     * Returns the type of generation parameters.
     * 
     * @return The type of generation parameters
     */
    public @NotNull Class<P> getParametersType() {
        return this.parametersType;
    }

    /**
     * Returns the generation parameters extractor.
     * 
     * @return The generation parameters extractor
     */
    public @NotNull ParametersExtractor<P> getExtractor() {
        return ParametersExtractors.getExtractor(this.parametersType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> @NotNull T defaultValue(
            final @NotNull Class<T> type) {
        return defaultValue(type, createEmptyParams());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> @NotNull T defaultValue(
            final @NotNull Class<T> type,
            final @NotNull Object... sources) {
        return defaultValue(type, createParams(sources));
    }

    /**
     * Returns the default value for the specified type and parameters.
     * 
     * @param <T> The requested value type.
     * @param type The requested value type.
     * @param parameters The generation parameters
     * @return The default value for the specified type.
     * @throws IllegalArgumentException If the specified type is not supported.
     */
    public abstract @NotNull <T> T defaultValue(
            @NotNull Class<T> type,
            @NotNull P parameters);

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T nullableDefaultValue(
            final @NotNull Class<T> type) {
        return nullableDefaultValue(type, createEmptyParams());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T nullableDefaultValue(
            final @NotNull Class<T> type,
            final @NotNull Object... sources) {
        return nullableDefaultValue(type, createParams(sources));
    }

    /**
     * Returns the default value for the specified type and parameters
     * allowing {@code null} values.
     * <p>
     * This method should return {@code null} except for native types when
     * no {@code NotNull} constraint is present.
     * 
     * @param <T> The requested value type.
     * @param type The requested value type.
     * @param parameters The generation parameters
     * @return The nullable default value for the specified type.
     * @throws IllegalArgumentException If the specified type is not supported.
     */
    public <T> T nullableDefaultValue(
            final @NotNull Class<T> type,
            final @NotNull P parameters) {
        assertSupported(type);
        final T value;
        if (parameters.isNullable() && randomNull(type)) {
            value = null;
        } else {
            value = defaultValue(type, parameters);
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> @NotNull T randomValue(
            final @NotNull Class<T> type) {
        return randomValue(type, createEmptyParams());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> @NotNull T randomValue(
            final @NotNull Class<T> type,
            final @NotNull Object... sources) {
        return randomValue(type, createParams(sources));
    }

    /**
     * Returns a random value of the specified type and parameters.
     * 
     * @param <T> The requested value type.
     * @param type The requested value type.
     * @param parameters The generation parameters
     * @return A random value for the specified type.
     * @throws IllegalArgumentException If the specified type is not supported.
     */
    public abstract @NotNull <T> T randomValue(
            @NotNull Class<T> type,
            @NotNull P parameters);

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T nullableRandomValue(
            final @NotNull Class<T> type) {
        return nullableRandomValue(type, createEmptyParams());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T nullableRandomValue(
            final @NotNull Class<T> type,
            final @NotNull Object... sources) {
        return nullableRandomValue(type, createParams(sources));
    }

    /**
     * Returns a random value of the specified type and parameters.
     * <p>
     * The returned value has a probability of be {@code null} except for
     * native types when no {@code NotNull} constraint is present.
     * If not {@code null} behaves as {@code randomValue()}.
     * 
     * @param <T> The requested value type.
     * @param type The requested value type.
     * @param parameters The generation parameters
     * @return A random nullable value for the specified type.
     * @throws IllegalArgumentException If the specified type is not supported.
     * @see #randomValue(Class, Parameters)
     */
    public <T> T nullableRandomValue(
            final @NotNull Class<T> type,
            final @NotNull P parameters) {
        assertSupported(type);
        final T value;
        if (parameters.isNullable() && randomNull(type)) {
            value = null;
        } else {
            value = randomValue(type, parameters);
        }
        return value;
    }

    /**
     * Creates a new empty instance of generation parameters.
     * 
     * @return A new empty instance of generation parameters.
     */
    public @NotNull P createEmptyParams() {
        try {
            return getParametersType().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("The parameters type has not default constructor. Override createEmptyParams().", e);
        }
    }

    /**
     * Creates a new instance of generation parameters with the values
     * extracted from the specified sources.
     * 
     * @param sources The sources to extract the parameters values from
     * @return A new instance of generation parameters.
     */
    public @NotNull P createParams(
            final @NotNull Object... sources) {
        final P result = createEmptyParams();
        getExtractor().extractParameters(result, sources);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(this.parametersType)
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
        final AbstractParametrizableGenerator<?> other = (AbstractParametrizableGenerator<?>) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.parametersType, other.parametersType)
                .build();
    }

    /**
     * Minimum interface for value generation parameters.
     * 
     * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
     * @version 1.0, 2022-11
     * @since AbstractParametrizableGenerator 1.0
     */
    public interface Parameters {

        /**
         * Returns {@code true} if a {@code null} value is accepted.
         * 
         * @return If a {@code null} value is accepted.
         */
        boolean isNullable();
    }
}