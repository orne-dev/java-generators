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

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Parameters for random {@code Map} generation.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public class MapGenerationParameters
extends GenerationParameters
implements KeyValueGenericParameters, SizeParameters {

    /** The keys type. */
    private Type keysType;
    /** The values type. */
    private Type valuesType;
    /** The minimum size. */
    private int minSize = 0;
    /** The maximum size. */
    private int maxSize = Integer.MAX_VALUE;

    /**
     * Creates a new instance.
     */
    public MapGenerationParameters() {
        super();
    }

    /**
     * Copy constructor.
     * 
     * @param copy The instance to copy.
     */
    public MapGenerationParameters(
            final @NotNull GenerationParameters copy) {
        super(copy);
        if (copy instanceof KeyValueGenericParameters) {
            this.keysType = ((KeyValueGenericParameters) copy).getKeysType();
            this.valuesType = ((KeyValueGenericParameters) copy).getValuesType();
        }
        if (copy instanceof SizeParameters) {
            this.minSize = ((SizeParameters) copy).getMinSize();
            this.maxSize = ((SizeParameters) copy).getMaxSize();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull MapGenerationParameters withNullable(
            final boolean nullable) {
        setNullable(nullable);
        return this;
    }

    /**
     * Returns the keys type.
     * 
     * @return The keys type.
     */
    @Override
    public Type getKeysType() {
        return this.keysType;
    }

    /**
     * Sets the keys type.
     * 
     * @param type The keys type.
     */
    @Override
    public void setKeysType(
            final Type type) {
        this.keysType = type;
    }

    /**
     * Sets the keys type.
     * 
     * @param type The keys type.
     * @return This instance, for method chaining.
     */
    public @NotNull MapGenerationParameters withKeysType(
            final Type type) {
        setKeysType(type);
        return this;
    }

    /**
     * Returns the values type.
     * 
     * @return The values type.
     */
    @Override
    public Type getValuesType() {
        return this.valuesType;
    }

    /**
     * Sets the values type.
     * 
     * @param type The values type.
     */
    @Override
    public void setValuesType(
            final Type type) {
        this.valuesType = type;
    }

    /**
     * Sets the values type.
     * 
     * @param type The values type.
     * @return This instance, for method chaining.
     */
    public @NotNull MapGenerationParameters withValuesType(
            final Type type) {
        setValuesType(type);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMinSize() {
        return this.minSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMinSize(
            final int value) {
        this.minSize = value;
    }

    /**
     * Sets the minimum size.
     * 
     * @param value The minimum size.
     * @return This instance, for method chaining.
     */
    public @NotNull MapGenerationParameters withMinSize(
            final int value) {
        setMinSize(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxSize() {
        return this.maxSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaxSize(
            final int value) {
        this.maxSize = value;
    }

    /**
     * Sets the maximum size.
     * 
     * @param value The maximum size.
     * @return This instance, for method chaining.
     */
    public @NotNull MapGenerationParameters withMaxSize(
            final int value) {
        setMaxSize(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MapGenerationParameters clone() {
        return new MapGenerationParameters(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(this.keysType)
                .append(this.valuesType)
                .append(this.minSize)
                .append(this.maxSize)
                .toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(
            final Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) { return false; }
        final MapGenerationParameters other = (MapGenerationParameters) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.keysType, other.keysType)
                .append(this.valuesType, other.valuesType)
                .append(this.minSize, other.minSize)
                .append(this.maxSize, other.maxSize)
                .build();
    }

    /**
     * Returns a new builder of generation parameters that ensures that
     * the required properties are set.
     * 
     * @return The generation parameters builder.
     */
    public static @NotNull Builder builder() {
        return new BuilderImpl();
    }

    /**
     * Interface for list generation parameters builder.
     * 
     * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
     * @version 1.0, 2022-12
     * @since ListGenerationParameters 1.0
     */
    public interface Builder {

        /**
         * Specifies the type of the map keys.
         * 
         * @param type The type of the map keys.
         * @return The next stage builder.
         */
        @NotNull ValuesBuilder withKeysType(
                @NotNull Class<?> type);

        /**
         * Creates a new instance of generation parameters with the specified
         * parameterized type as list components type.
         * 
         * @param type The type of the map keys.
         * @return The next stage builder.
         */
        @NotNull ValuesBuilder withKeysType(
                @NotNull ParameterizedType type);

        /**
         * Creates a new instance of generation parameters with the specified
         * generic array type as list components type.
         * 
         * @param type The type of the map keys.
         * @return The next stage builder.
         */
        @NotNull ValuesBuilder withKeysType(
                @NotNull GenericArrayType type);
    }

    /**
     * Interface for map generation parameters builder.
     * 
     * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
     * @version 1.0, 2022-12
     * @since ListGenerationParameters 1.0
     */
    public interface ValuesBuilder {

        /**
         * Creates a new instance of generation parameters with the specified
         * keys and values types.
         * 
         * @param type The type of the map values.
         * @return The resulting generation parameters.
         */
        @NotNull MapGenerationParameters withValuesType(
                @NotNull Class<?> type);

        /**
         * Creates a new instance of generation parameters with the specified
         * keys and values types.
         * 
         * @param type The type of the map values.
         * @return The resulting generation parameters.
         */
        @NotNull MapGenerationParameters withValuesType(
                @NotNull ParameterizedType type);

        /**
         * Creates a new instance of generation parameters with the specified
         * keys and values types.
         * 
         * @param type The type of the map values.
         * @return The resulting generation parameters.
         */
        @NotNull MapGenerationParameters withValuesType(
                @NotNull GenericArrayType type);
    }

    /**
     * Internal implementation of {@code Builder}.
     * 
     * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
     * @version 1.0, 2022-11
     * @since ListGenerationParameters 1.0
     */
    protected static class BuilderImpl
    implements Builder, ValuesBuilder {

        private Type keysType;

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull BuilderImpl withKeysType(
                final @NotNull Class<?> type) {
            Validate.notNull(type);
            this.keysType = type;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull BuilderImpl withKeysType(
                final @NotNull ParameterizedType type) {
            Validate.notNull(type);
            this.keysType = type;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull BuilderImpl withKeysType(
                final @NotNull GenericArrayType type) {
            Validate.notNull(type);
            this.keysType = type;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull MapGenerationParameters withValuesType(
                final @NotNull Class<?> type) {
            Validate.notNull(type);
            return new MapGenerationParameters()
                    .withKeysType(this.keysType)
                    .withValuesType(type);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull MapGenerationParameters withValuesType(
                final @NotNull ParameterizedType type) {
            Validate.notNull(type);
            return new MapGenerationParameters()
                    .withKeysType(this.keysType)
                    .withValuesType(type);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull MapGenerationParameters withValuesType(
                final @NotNull GenericArrayType type) {
            Validate.notNull(type);
            return new MapGenerationParameters()
                    .withKeysType(this.keysType)
                    .withValuesType(type);
        }
    }
}
