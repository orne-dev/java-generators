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

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Interface for generation parameters of generic classes with two
 * type parameters for key and values.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public interface KeyValueGenericParameters
extends GenerationParameters {

    /**
     * Returns the keys type parameter.
     * 
     * @return The keys type parameter
     */
    Type getKeysType();

    /**
     * Sets the keys type parameter.
     * 
     * @param type the keys type parameter
     */
    void setKeysType(Type type);

    /**
     * Returns the values type parameter.
     * 
     * @return The values type parameter
     */
    Type getValuesType();

    /**
     * Sets the values type parameter.
     * 
     * @param type the values type parameter
     */
    void setValuesType(Type type);

    /**
     * Interface for {@code KeyValueGenericParameters} keys type builders.
     * 
     * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
     * @version 1.0, 2022-12
     * @since KeyValueGenericParameters 1.0
     */
    public interface KeysTypeBuilder {

        /**
         * Specifies the type of the map keys.
         * 
         * @param type The type of the map keys.
         * @return The next stage builder.
         */
        @NotNull ValuesTypeBuilder withKeysType(
                @NotNull Class<?> type);

        /**
         * Creates a new instance of generation parameters with the specified
         * parameterized type as list components type.
         * 
         * @param type The type of the map keys.
         * @return The next stage builder.
         */
        @NotNull ValuesTypeBuilder withKeysType(
                @NotNull ParameterizedType type);

        /**
         * Creates a new instance of generation parameters with the specified
         * generic array type as list components type.
         * 
         * @param type The type of the map keys.
         * @return The next stage builder.
         */
        @NotNull ValuesTypeBuilder withKeysType(
                @NotNull GenericArrayType type);
    }

    /**
     * Interface for {@code KeyValueGenericParameters} values type builders.
     * 
     * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
     * @version 1.0, 2022-12
     * @since KeyValueGenericParameters 1.0
     */
    public interface ValuesTypeBuilder {

        /**
         * Creates a new instance of generation parameters with the specified
         * keys and values types.
         * 
         * @param type The type of the map values.
         * @return The resulting generation parameters.
         */
        @NotNull KeyValueGenericParameters withValuesType(
                @NotNull Class<?> type);

        /**
         * Creates a new instance of generation parameters with the specified
         * keys and values types.
         * 
         * @param type The type of the map values.
         * @return The resulting generation parameters.
         */
        @NotNull KeyValueGenericParameters withValuesType(
                @NotNull ParameterizedType type);

        /**
         * Creates a new instance of generation parameters with the specified
         * keys and values types.
         * 
         * @param type The type of the map values.
         * @return The resulting generation parameters.
         */
        @NotNull KeyValueGenericParameters withValuesType(
                @NotNull GenericArrayType type);
    }
}
