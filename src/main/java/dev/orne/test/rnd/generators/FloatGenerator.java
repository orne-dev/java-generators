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
 * Generator of {@code float} and {@code Float} values.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
@Priority(Priority.NATIVE_GENERATORS)
public class FloatGenerator
extends AbstractPrimitiveGenerator<Float> {

    /** The default value. */
    public static final float DEFAULT_VALUE = 0;

    /** The bit mask for infinity (NaN) values of float. */
    private static final int FLOAT_INFINITY_MASK =
            Float.floatToIntBits(Float.NEGATIVE_INFINITY);
    /** The bits of float positive infinity. */
    private static final int FLOAT_POSITIVE_INFINITY =
            Float.floatToIntBits(Float.POSITIVE_INFINITY);
    /** The bits of float negative infinity. */
    private static final int FLOAT_NEGATIVE_INFINITY =
            Float.floatToIntBits(Float.NEGATIVE_INFINITY);

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Float defaultValue() {
        return DEFAULT_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Float randomValue() {
        return randomFloat();
    }

    /**
     * Generates a random {@code float} between {@code Float.NEGATIVE_INFINITY}
     * and {@code Float.POSITIVE_INFINITY} both included.
     * 
     * @return A random {@code float} value
     */
    public static float randomFloat() {
        int bits = IntegerGenerator.randomInt();
        // We ignore NaN values
        while (((bits & FLOAT_INFINITY_MASK) == FLOAT_POSITIVE_INFINITY
                    && bits != FLOAT_POSITIVE_INFINITY)
                || ((bits & FLOAT_INFINITY_MASK) == FLOAT_NEGATIVE_INFINITY
                    && bits != FLOAT_NEGATIVE_INFINITY)) {
            bits = IntegerGenerator.randomInt();
        }
        return Float.intBitsToFloat(bits);
    }

    /**
     * Generates a random {@code float} between {@code Float.NEGATIVE_INFINITY}
     * and {@code Float.POSITIVE_INFINITY} both excluded.
     * 
     * @return A random {@code float} value
     */
    public static float randomFiniteFloat() {
        int bits = IntegerGenerator.randomInt();
        // We ignore NaN and infinite values
        while ((bits & FLOAT_INFINITY_MASK) == FLOAT_POSITIVE_INFINITY
                || (bits & FLOAT_INFINITY_MASK) == FLOAT_NEGATIVE_INFINITY) {
            bits = IntegerGenerator.randomInt();
        }
        return Float.intBitsToFloat(bits);
    }
}
