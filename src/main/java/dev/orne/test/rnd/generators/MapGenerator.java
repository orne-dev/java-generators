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

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.GenerationException;
import dev.orne.test.rnd.Priority;
import dev.orne.test.rnd.params.AbstractTypedParameterizableGenerator;
import dev.orne.test.rnd.params.MapGenerationParameters;

/**
 * Generator of {@code Map} values.
 * Requires supported component type generation in {@code Generators}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
@Priority(Priority.GENERIC_GENERATORS)
public class MapGenerator
extends AbstractTypedParameterizableGenerator<Map<?, ?>, MapGenerationParameters> {

    /** The minimum generated array size. */
    public static final int MIN_SIZE = 1;
    /** The maximum generated array size. */
    public static final int MAX_SIZE = 100;

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Map<?, ?> defaultValue(
            final @NotNull MapGenerationParameters parameters) {
        validateParameters(parameters);
        return new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Map<?, ?> randomValue(
            final @NotNull MapGenerationParameters parameters) {
        validateParameters(parameters);
        return randomMap(parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T nullableRandomValue(
            final @NotNull Class<T> type,
            final @NotNull MapGenerationParameters parameters) {
        assertSupported(type);
        final T value;
        if (parameters.isNullable() && randomNull(type)) {
            value = null;
        } else {
            value = type.cast(randomNullablesMap(parameters));
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<?, ?> nullableRandomValue(
            final @NotNull MapGenerationParameters parameters) {
        validateParameters(parameters);
        final Map<?, ?> value;
        if (parameters.isNullable() && randomNull(getValueType())) {
            value = null;
        } else {
            value = randomNullablesMap(parameters);
        }
        return value;
    }

    /**
     * Validates that the parameters contain enough information to generate
     * the values.
     * 
     * @param parameters The generation parameters.
     */
    protected void validateParameters(
            final @NotNull MapGenerationParameters parameters) {
        Validate.notNull(parameters.getKeysType(), "The map keys type is required");
        Validate.notNull(parameters.getValuesType(), "The map values type is required");
    }

    /**
     * Returns a random sized map of the specified keys and values types.
     * 
     * @param parameters The generation parameters.
     * @return A random map for the specified keys and values types.
     * @throws GenerationException If an error occurs generating the value
     */
    protected @NotNull Map<?, ?> randomMap(
            final @NotNull MapGenerationParameters parameters) {
        final int size = randomSetSize(parameters);
        final Map<Object, Object> result = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            result.put(
                    CollectionGeneratorUtils.randomComponent(
                        parameters.getKeysType()),
                    CollectionGeneratorUtils.randomComponent(
                        parameters.getValuesType()));
        }
        return result;
    }

    /**
     * Returns a random sized map of the specified keys and values types allowing
     * {@code null} values.
     * 
     * @param parameters The generation parameters.
     * @return A random map of the specified keys and values types.
     * @throws GenerationException If an error occurs generating the value
     */
    protected Map<?, ?> randomNullablesMap(
            final @NotNull MapGenerationParameters parameters) {
        final int size = randomSetSize(parameters);
        final Map<Object, Object> result = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            result.put(
                    CollectionGeneratorUtils.randomComponent(
                        parameters.getKeysType()),
                    CollectionGeneratorUtils.nullableRandomComponent(
                        parameters.getValuesType()));
        }
        return result;
    }

    /**
     * Returns a random map size.
     * 
     * @param parameters The generation parameters.
     * @return The map size.
     */
    protected int randomSetSize(
            final @NotNull MapGenerationParameters parameters) {
        return RandomUtils.nextInt(
                NumberUtils.max(MIN_SIZE, parameters.getMinSize()),
                NumberUtils.min(MAX_SIZE, parameters.getMaxSize()) + 1);
    }
}
