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
 * Interface for generation parameters of generic classes with a single
 * type parameter.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public interface SimpleGenericParameters
extends GenerationParameters {

    /**
     * Returns the type parameter.
     * 
     * @return The type parameter
     */
    Type getType();

    /**
     * Sets the type parameter.
     * 
     * @param type the type parameter
     */
    void setType(Type type);

    /**
     * Interface for {@code SimpleGenericParameters} builders.
     * 
     * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
     * @version 1.0, 2022-12
     * @since SimpleGenericParameters 1.0
     */
    public interface Builder {

        /**
         * Creates a new instance of generation parameters with the specified
         * class as list components type.
         * 
         * @param type The list components type.
         * @return The resulting generation parameters.
         */
        @NotNull SimpleGenericParameters withElementsType(
                @NotNull Class<?> type);

        /**
         * Creates a new instance of generation parameters with the specified
         * parameterized type as list components type.
         * 
         * @param type The list components type.
         * @return The resulting generation parameters.
         */
        @NotNull SimpleGenericParameters withElementsType(
                @NotNull ParameterizedType type);

        /**
         * Creates a new instance of generation parameters with the specified
         * generic array type as list components type.
         * 
         * @param type The list components type.
         * @return The resulting generation parameters.
         */
        @NotNull SimpleGenericParameters withElementsType(
                @NotNull GenericArrayType type);
    }
}
