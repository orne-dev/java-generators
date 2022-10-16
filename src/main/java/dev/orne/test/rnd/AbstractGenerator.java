package dev.orne.test.rnd;

/*-
 * #%L
 * Orne Test Generators
 * %%
 * Copyright (C) 2021 Orne Developments
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

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.Validate;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Abstract basic implementation of {@code Generator}.
 * Provides basic implementations for common methods.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2021-03
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
public abstract class AbstractGenerator
implements Generator {

    /** The default probability of {@code null} values. */
    public static final float DEFAULT_NULL_PROBABILITY = 0.3f;

    /** The probability of {@code null} values. */
    private float nullProbability = DEFAULT_NULL_PROBABILITY;

    /**
     * Verifies that the specified type is supported by this instance.
     * If test fails throws an {@code IllegalArgumentException} exception
     * as documented in {@link #defaultValue(Class)},
     * {@link #nullableDefaultValue(Class)}, {@link #randomValue(Class)} and
     * {@link #nullableRandomValue(Class)}.
     * 
     * @param type The requested value type.
     * @throws IllegalArgumentException If the specified type is not supported.
     */
    protected void assertSupported(
            final @NotNull Class<?> type) {
        if (!supports(type)) {
            throw new IllegalArgumentException(String.format(
                    "Generators of type %s does not support generation of values of type %s",
                    getClass(),
                    type));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T nullableDefaultValue(
            final @NotNull Class<T> type) {
        assertSupported(type);
        return null;
    }

    /**
     * {@inheritDoc}
     * @see #setNullProbability(float)
     */
    @Override
    public <T> T nullableRandomValue(
            final @NotNull Class<T> type) {
        assertSupported(type);
        final T value;
        if (randomNull()) {
            value = null;
        } else {
            value = randomValue(type);
        }
        return value;
    }

    /**
     * Returns the probability of {@code null} value in
     * {@link #nullableRandomValue(Class)}.
     * 
     * @return The probability of {@code null} values.
     */
    public @Min(0) @Max(1) float getNullProbability() {
        return this.nullProbability;
    }

    /**
     * Sets the probability of {@code null} value in
     * {@link #nullableRandomValue(Class)}.
     * 
     * @param prob The probability of {@code null} values.
     */
    public void setNullProbability(
            final @Min(0) @Max(1) float prob) {
        Validate.inclusiveBetween(0, 1, prob);
        this.nullProbability = prob;
    }

    /**
     * Determines if the value must be {@code null} based on the probability of
     * {@code null} values.
     * 
     * @return If the value must be {@code null}
     * @see #setNullProbability(float)
     */
    public boolean randomNull() {
        return RandomUtils.nextFloat(0, 1) < this.nullProbability;
    }
}
