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

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.Priority;

/**
 * Generator of {@code double} and {@code Double} values.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
@Priority(Priority.NATIVE_GENERATORS)
public class DoubleGenerator
extends AbstractPrimitiveGenerator<Double> {

    /** The default value. */
    public static final double DEFAULT_VALUE = 0;

    /** The bit mask for infinity (NaN) values of double. */
    private static final long DOUBLE_INFINITY_MASK =
            Double.doubleToLongBits(Double.NEGATIVE_INFINITY);
    /** The bits of double positive infinity. */
    private static final long DOUBLE_POSITIVE_INFINITY =
            Double.doubleToLongBits(Double.POSITIVE_INFINITY);
    /** The bits of double negative infinity. */
    private static final long DOUBLE_NEGATIVE_INFINITY =
            Double.doubleToLongBits(Double.NEGATIVE_INFINITY);

    /**
     * Creates a new instance.
     */
    public DoubleGenerator() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Double defaultValue() {
        return DEFAULT_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Double randomValue() {
        return randomDouble();
    }

    /**
     * Generates a random {@code double} between {@code Double.NEGATIVE_INFINITY}
     * and {@code Double.POSITIVE_INFINITY} both included.
     * 
     * @return A random {@code double} value
     */
    public static double randomDouble() {
        long bits = LongGenerator.randomLong();
        // We ignore NaN values
        while (isNaN(bits) && !isInfinity(bits)) {
            bits = LongGenerator.randomLong();
        }
        return Double.longBitsToDouble(bits);
    }

    /**
     * Generates a random {@code double} between {@code Double.NEGATIVE_INFINITY}
     * and {@code Double.POSITIVE_INFINITY} both excluded.
     * 
     * @return A random {@code double} value
     */
    public static double randomFiniteDouble() {
        long bits = LongGenerator.randomLong();
        // We ignore NaN and infinite values
        while (isNaN(bits)) {
            bits = LongGenerator.randomLong();
        }
        return Double.longBitsToDouble(bits);
    }

    /**
     * Returns true if the bit representation of the double value results in
     * a NaN. This includes both {@code Double.POSITIVE_INFINITY} and
     * {@code Double.NEGATIVE_INFINITY}.
     * 
     * @param bits The bit representation of the double value
     * @return If the bit representation results in a NaN
     * @see Double#longBitsToDouble(long)
     */
    static boolean isNaN(
            final long bits) {
        return (bits & DOUBLE_INFINITY_MASK) == DOUBLE_POSITIVE_INFINITY
                || (bits & DOUBLE_INFINITY_MASK) == DOUBLE_NEGATIVE_INFINITY;
    }

    /**
     * Returns true if the bit representation of the double value results
     * if {@code Double.POSITIVE_INFINITY} or {@code Double.NEGATIVE_INFINITY}.
     * 
     * @param bits The bit representation of the double value
     * @return If the bit representation results in a infintyN
     * @see Double#longBitsToDouble(long)
     */
    static boolean isInfinity(
            final long bits) {
        return bits == DOUBLE_POSITIVE_INFINITY
                || bits == DOUBLE_NEGATIVE_INFINITY;
    }
}
