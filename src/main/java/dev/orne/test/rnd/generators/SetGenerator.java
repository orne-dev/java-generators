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

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.GenerationException;
import dev.orne.test.rnd.Priority;
import dev.orne.test.rnd.params.AbstractTypedParameterizableGenerator;
import dev.orne.test.rnd.params.CollectionGenerationParameters;

/**
 * Generator of {@code Set} values.
 * Requires supported component type generation in {@code Generators}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
@Priority(Priority.GENERIC_GENERATORS)
public class SetGenerator
extends AbstractTypedParameterizableGenerator<Set<?>, CollectionGenerationParameters> {

    /** The minimum generated set size. */
    public static final int MIN_SIZE = 0;
    /** The maximum generated set size. */
    public static final int MAX_SIZE = 100;

    /**
     * Creates a new instance.
     */
    public SetGenerator() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<?> defaultValue(
            final @NotNull CollectionGenerationParameters parameters) {
        validateParameters(parameters);
        return new HashSet<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<?> randomValue(
            final @NotNull CollectionGenerationParameters parameters) {
        validateParameters(parameters);
        return randomSet(parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T nullableRandomValue(
            final @NotNull Class<T> type,
            final @NotNull CollectionGenerationParameters parameters) {
        assertSupported(type);
        final T value;
        if (parameters.isNullable() && randomNull(type)) {
            value = null;
        } else {
            value = type.cast(randomNullablesSet(parameters));
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<?> nullableRandomValue(
            final @NotNull CollectionGenerationParameters parameters) {
        validateParameters(parameters);
        final Set<?> value;
        if (parameters.isNullable() && randomNull(getValueType())) {
            value = null;
        } else {
            value = randomNullablesSet(parameters);
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
            final @NotNull CollectionGenerationParameters parameters) {
        Validate.notNull(parameters.getType(), "The set component type is required");
    }

    /**
     * Returns a random sized set of the specified component type.
     * 
     * @param parameters The generation parameters.
     * @return A random set for the specified component type.
     * @throws GenerationException If an error occurs generating the value
     */
    @SuppressWarnings("java:S1452")
    protected @NotNull Set<?> randomSet(
            final @NotNull CollectionGenerationParameters parameters) {
        final int size = randomSize(parameters);
        final Set<Object> result = new HashSet<>(size);
        for (int i = 0; i < size; i++) {
            result.add(CollectionGeneratorUtils.randomComponent(
                    parameters.getType()));
        }
        return result;
    }

    /**
     * Returns a random sized set of the specified component type allowing
     * {@code null} component values.
     * 
     * @param parameters The generation parameters.
     * @return A random set of the specified component type.
     * @throws GenerationException If an error occurs generating the value
     */
    @SuppressWarnings("java:S1452")
    protected Set<?> randomNullablesSet(
            final @NotNull CollectionGenerationParameters parameters) {
        final int size = randomSize(parameters);
        final Set<Object> result = new HashSet<>(size);
        for (int i = 0; i < size; i++) {
            result.add(CollectionGeneratorUtils.nullableRandomComponent(
                    parameters.getType()));
        }
        return result;
    }

    /**
     * Returns a random set size.
     * 
     * @param parameters The generation parameters.
     * @return The set size.
     */
    protected int randomSize(
            final @NotNull CollectionGenerationParameters parameters) {
        return RandomUtils.nextInt(
                NumberUtils.max(MIN_SIZE, parameters.getMinSize()),
                NumberUtils.min(MAX_SIZE, parameters.getMaxSize()) + 1);
    }
}
