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

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.Validate;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.AbstractGenerator;
import dev.orne.test.rnd.Priority;

/**
 * Generator of enumeration types values.
 * <p>
 * Requires that the enumeration type contain constants.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
@Priority(Priority.GENERIC_GENERATORS)
public class EnumGenerator
extends AbstractGenerator {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(
            final @NotNull Class<?> type) {
        return type.isEnum() && type.getEnumConstants().length > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> @NotNull T defaultValue(
            final @NotNull Class<T> type) {
        assertSupported(type);
        return type.getEnumConstants()[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> @NotNull T randomValue(
            final @NotNull Class<T> type) {
        assertSupported(type);
        final T[] values = type.getEnumConstants();
        return values[RandomUtils.nextInt(0, values.length)];
    }

    /**
     * Returns a random constant of the specified enumeration.
     * 
     * @param <T> The enumeration type
     * @param type The enumeration type
     * @return A random enumeration constant
     * @throws IllegalArgumentException If the enumeration has no constants
     */
    public static <T extends Enum<T>> @NotNull T randomEnumValue(
            final @NotNull Class<T> type) {
        Validate.notNull(type);
        Validate.isTrue(type.isEnum(), "The specified class is not an enumeration");
        final T[] values = type.getEnumConstants();
        Validate.isTrue(values.length > 0, "The specified enumeration has no values");
        return values[RandomUtils.nextInt(0, values.length)];
    }
}
