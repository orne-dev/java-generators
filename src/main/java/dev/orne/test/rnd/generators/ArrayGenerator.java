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

import java.lang.reflect.Array;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.RandomUtils;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.AbstractGenerator;
import dev.orne.test.rnd.GenerationException;
import dev.orne.test.rnd.Generator;
import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;

/**
 * Generator of array values.
 * Requires supported component type generation in {@code Generators}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
@Priority(Priority.GENERIC_GENERATORS)
public class ArrayGenerator
extends AbstractGenerator {

    /** The minimum generated array size. */
    public static final int MIN_SIZE = 1;
    /** The maximum generated array size. */
    public static final int MAX_SIZE = 10;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(
            final @NotNull Class<?> type) {
        return type.isArray() &&
                Generators.supports(type.getComponentType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> @NotNull T defaultValue(
            final @NotNull Class<T> type) {
        assertSupported(type);
        return type.cast(Array.newInstance(type.getComponentType(), 0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> @NotNull T randomValue(
            final @NotNull Class<T> type) {
        assertSupported(type);
        final Class<?> componentType = type.getComponentType();
        final Generator generator = Generators.getGenerator(componentType);
        return type.cast(randomArray(componentType, generator));
    }

    /**
     * Returns a random sized array of the specified component type.
     * 
     * @param componentType The requested component type.
     * @param generator The generator to use to generate the array values.
     * @return A random array for the specified component type.
     * @throws GenerationException If an error occurs generating the value
     */
    protected @NotNull Object randomArray(
            final @NotNull Class<?> componentType,
            final @NotNull Generator generator) {
        final int size = RandomUtils.nextInt(MIN_SIZE, MAX_SIZE);
        final Object result = Array.newInstance(componentType, size);
        for (int i = 0; i < size; i++) {
            Array.set(result, i, generator.randomValue(componentType));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T nullableRandomValue(
            final @NotNull Class<T> type) {
        assertSupported(type);
        final T value;
        if (randomNull(type)) {
            value = null;
        } else {
            final Class<?> componentType = type.getComponentType();
            final Generator generator = Generators.getGenerator(componentType);
            value = type.cast(randomNullablesArray(componentType, generator));
        }
        return value;
    }

    /**
     * Returns a random sized array of the specified component type allowing
     * {@code null} component values.
     * 
     * @param <T> The requested component type.
     * @param type The requested component type.
     * @return A random array of the specified component type.
     * @throws GenerationException If an error occurs generating the value
     */
    protected Object randomNullablesArray(
            final @NotNull Class<?> componentType,
            final @NotNull Generator generator) {
        final int size = RandomUtils.nextInt(MIN_SIZE, MAX_SIZE);
        final Object result = Array.newInstance(componentType, size);
        for (int i = 0; i < size; i++) {
            Array.set(result, i, generator.nullableRandomValue(componentType));
        }
        return result;
    }
}
