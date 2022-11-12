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

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Interface for random value generators with optional parameters.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public interface ParameterizableGenerator
extends Generator {

    /**
     * Returns the default value for the specified type and parameter sources.
     * 
     * @param <T> The requested value type.
     * @param type The requested value type.
     * @param params The parameter sources to extract the parameters from
     * @return The default value for the specified type.
     * @throws IllegalArgumentException If the specified type is not supported.
     */
    @NotNull <T> T defaultValue(
            @NotNull Class<T> type,
            @NotNull Object... params);

    /**
     * Returns the default value for the specified type and parameter sources
     * allowing {@code null} values.
     * <p>
     * This method should return {@code null} except for native types when
     * no {@code NotNull} constraint is present.
     * 
     * @param <T> The requested value type.
     * @param type The requested value type.
     * @param params The parameter sources to extract the parameters from
     * @return The nullable default value for the specified type.
     * @throws IllegalArgumentException If the specified type is not supported.
     */
    <T> T nullableDefaultValue(
            @NotNull Class<T> type,
            @NotNull Object... params);

    /**
     * Returns a random value of the specified type and parameter sources.
     * 
     * @param <T> The requested value type.
     * @param type The requested value type.
     * @param params The parameter sources to extract the parameters from
     * @return A random value for the specified type.
     * @throws IllegalArgumentException If the specified type is not supported.
     */
    @NotNull <T> T randomValue(
            @NotNull Class<T> type,
            @NotNull Object... params);

    /**
     * Returns a random value of the specified type and parameter sources.
     * <p>
     * The returned value has a probability of be {@code null} except for
     * native types when no {@code NotNull} constraint is present.
     * If not {@code null} behaves as {@code randomValue()}.
     * 
     * @param <T> The requested value type.
     * @param type The requested value type.
     * @param params The parameter sources to extract the parameters from
     * @return A random nullable value for the specified type.
     * @throws IllegalArgumentException If the specified type is not supported.
     * @see #randomValue(Class, Object...)
     */
    <T> T nullableRandomValue(
            @NotNull Class<T> type,
            @NotNull Object... params);
}
