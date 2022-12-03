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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
 * Generator of {@code List} values.
 * Requires supported component type generation in {@code Generators}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
@Priority(Priority.GENERIC_GENERATORS)
public class ListGenerator
extends AbstractTypedParameterizableGenerator<List<?>, CollectionGenerationParameters> {

    /** The minimum generated array size. */
    public static final int MIN_SIZE = 1;
    /** The maximum generated array size. */
    public static final int MAX_SIZE = 10;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(@NotNull Class<?> type) {
        return super.supports(type) || Collection.class.equals(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull List<?> defaultValue(
            final @NotNull CollectionGenerationParameters parameters) {
        validateParameters(parameters);
        return new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull List<?> randomValue(
            final @NotNull CollectionGenerationParameters parameters) {
        validateParameters(parameters);
        return randomList(parameters);
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
            value = type.cast(randomNullablesList(parameters));
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<?> nullableRandomValue(
            final @NotNull CollectionGenerationParameters parameters) {
        validateParameters(parameters);
        final List<?> value;
        if (parameters.isNullable() && randomNull(getValueType())) {
            value = null;
        } else {
            value = randomNullablesList(parameters);
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
        Validate.notNull(parameters.getType(), "The list component type is required");
    }

    /**
     * Returns a random sized list of the specified component type.
     * 
     * @param parameters The generation parameters.
     * @return A random list for the specified component type.
     * @throws GenerationException If an error occurs generating the value
     */
    protected @NotNull List<?> randomList(
            final @NotNull CollectionGenerationParameters parameters) {
        final int size = randomListSize(parameters);
        final List<Object> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(CollectionGeneratorUtils.randomComponent(
                    parameters.getType()));
        }
        return result;
    }

    /**
     * Returns a random sized list of the specified component type allowing
     * {@code null} component values.
     * 
     * @param parameters The generation parameters.
     * @return A random list of the specified component type.
     * @throws GenerationException If an error occurs generating the value
     */
    protected List<?> randomNullablesList(
            final @NotNull CollectionGenerationParameters parameters) {
        final int size = randomListSize(parameters);
        final List<Object> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(CollectionGeneratorUtils.nullableRandomComponent(
                    parameters.getType()));
        }
        return result;
    }

    /**
     * Returns a random list size.
     * 
     * @param parameters The generation parameters.
     * @return The list size.
     */
    protected int randomListSize(
            final @NotNull CollectionGenerationParameters parameters) {
        return RandomUtils.nextInt(
                NumberUtils.max(MIN_SIZE, parameters.getMinSize()),
                NumberUtils.min(MAX_SIZE, parameters.getMaxSize()) + 1);
    }

    /**
     * Returns a new builder of generation parameters that ensures that
     * the required properties are set.
     * 
     * @return The generation parameters builder.
     * @see CollectionGenerationParameters#builder()
     */
    public static @NotNull CollectionGenerationParameters.Builder createParameters() {
        return CollectionGenerationParameters.builder();
    }
}
