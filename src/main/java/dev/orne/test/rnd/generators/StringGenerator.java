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

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.GenerationException;
import dev.orne.test.rnd.Priority;
import dev.orne.test.rnd.params.AbstractTypedParameterizableGenerator;
import dev.orne.test.rnd.params.StringGenerationParameters;

/**
 * Generator of {@code String} and {@code CharSequence} values.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
@Priority(Priority.NATIVE_GENERATORS)
public class StringGenerator
extends AbstractTypedParameterizableGenerator<String, StringGenerationParameters> {

    /** The default value. */
    public static final String DEFAULT_VALUE = "";
    /** The minimum generated string length. */
    public static final int MIN_SIZE = 1;
    /** The maximum generated string length. */
    public static final int MAX_SIZE = 40;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(
            final @NotNull Class<?> type) {
        Validate.notNull(type);
        return String.class.equals(type) ||
                CharSequence.class.equals(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String defaultValue(
            final @NotNull StringGenerationParameters parameters) {
        validateParameters(parameters);
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String randomValue(
            final @NotNull StringGenerationParameters parameters) {
        validateParameters(parameters);
        return randomString(parameters);
    }

    /**
     * Validates that the parameters contain enough information to generate
     * the values.
     * 
     * @param parameters The generation parameters.
     */
    protected void validateParameters(
            final @NotNull StringGenerationParameters parameters) {
        // No restrictions
    }

    /**
     * Returns a random sized string.
     * 
     * @param parameters The generation parameters.
     * @return A random string.
     * @throws GenerationException If an error occurs generating the value
     */
    protected @NotNull String randomString(
            final @NotNull StringGenerationParameters parameters) {
        final int size = randomStringSize(parameters);
        return RandomStringUtils.random(size);
    }

    /**
     * Returns a random string length.
     * 
     * @param parameters The generation parameters.
     * @return The string length.
     */
    protected int randomStringSize(
            final @NotNull StringGenerationParameters parameters) {
        return RandomUtils.nextInt(
                NumberUtils.max(MIN_SIZE, parameters.getMinSize()),
                NumberUtils.min(MAX_SIZE, parameters.getMaxSize()) + 1);
    }

    /**
     * Returns a new instance of generation parameters.
     * 
     * @return The generation parameters.
     * @see StringGenerationParameters
     */
    public static @NotNull StringGenerationParameters createParameters() {
        return new StringGenerationParameters();
    }
}
