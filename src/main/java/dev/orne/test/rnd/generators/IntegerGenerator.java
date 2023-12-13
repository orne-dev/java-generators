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
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.Priority;

/**
 * Generator of {@code int} and {@code Integer} values.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
@Priority(Priority.NATIVE_GENERATORS)
public class IntegerGenerator
extends AbstractPrimitiveGenerator<Integer> {

    /** The default value. */
    public static final int DEFAULT_VALUE = 0;

    /**
     * Creates a new instance.
     */
    public IntegerGenerator() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Integer defaultValue() {
        return DEFAULT_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Integer randomValue() {
        return randomInt();
    }

    /**
     * Generates a random {@code int} between {@code Integer.MIN_VALUE}
     * and {@code Integer.MAX_VALUE} both included.
     * 
     * @return A random {@code int} value
     */
    public static int randomInt() {
        int result = RandomUtils.nextInt();
        if (RandomUtils.nextBoolean()) {
            result *= -1;
        }
        return result;
    }

    /**
     * Generates a random {@code int} between {@code min}
     * and {@code max} both included.
     * 
     * @param min The minimal value
     * @param max The maximum value
     * @return A random {@code int} value
     */
    public static int randomInt(
            final int min,
            final int max) {
        return (int) (RandomUtils.nextLong(0, (long) max - min + 1) + min);
    }
}
