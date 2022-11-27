package dev.orne.test.rnd.params;

/*-
 * #%L
 * Orne Test Generators
 * %%
 * Copyright (C) 2021 - 2022 Orne Developments
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
 * Interface for generators that target a property, method or constructor
 * parameter or method return type.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @param <T> The type of the target
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public interface TargetedGenerator<T> {

    /**
     * Returns the generation parameter sources to be used.
     * <p>
     * The specified validation groups will be used to retrieve the constraints
     * to use as sources.
     * 
     * @param groups The validation groups.
     * @return The generation parameter sources.
     */
    @NotNull Object[] getParameterSources(
            @NotNull Class<?>... groups);

    /**
     * Returns the default value of the supported type.
     * <p>
     * If the generator for the type of the target is a
     * {@code ParameterizableGenerator} extracts the parameters from the
     * constraints of the target when applying the specified validation groups.
     * 
     * @param groups The validation groups to use when extracting the
     * constraints of the target.
     * @return The default value.
     */
    @NotNull T defaultValue(
            @NotNull Class<?>... groups);

    /**
     * Returns the default value for the specified type allowing {@code null}
     * values.
     * <p>
     * If the generator for the type of the target is a
     * {@code ParameterizableGenerator} extracts the parameters from the
     * constraints of the target when applying the specified validation groups.
     * <p>
     * This method should return {@code null} except for native types or
     * targets annotated with {@code NotNull}.
     * 
     * @param groups The validation groups to use when extracting the
     * constraints of the target.
     * @return The nullable default value for the specified type.
     */
    T nullableDefaultValue(
            @NotNull Class<?>... groups);

    /**
     * Returns a random value of the supported type.
     * <p>
     * If the generator for the type of the target is a
     * {@code ParameterizableGenerator} extracts the parameters from the
     * constraints of the target when applying the specified validation groups.
     * 
     * @param groups The validation groups to use when extracting the
     * constraints of the target
     * @return A random value.
     */
    @NotNull T randomValue(
            @NotNull Class<?>... groups);

    /**
     * Returns a random value of the specified type.
     * <p>
     * If the generator for the type of the target is a
     * {@code ParameterizableGenerator} extracts the parameters from the
     * constraints of the target when applying the specified validation groups.
     * <p>
     * The returned value has a probability of be {@code null} except for
     * native types or targets annotated with {@code NotNull}.
     * If not {@code null} behaves as {@code randomValue()}.
     * 
     * @param groups The validation groups to use when extracting the
     * constraints of the target.
     * @return A random nullable value.
     * @see #randomValue(Class...)
     */
    T nullableRandomValue(
            @NotNull Class<?>... groups);
}
