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

import java.math.BigInteger;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.AbstractTypedGenerator;
import dev.orne.test.rnd.Priority;

/**
 * Generator of {@code BigInteger} values.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
@Priority(Priority.NATIVE_GENERATORS)
public class BigIntegerGenerator
extends AbstractTypedGenerator<BigInteger> {

    /** The default value. */
    public static final BigInteger DEFAULT_VALUE = BigInteger.ZERO;
    /** The maximum 10 base exponent of generated values. */
    private static final int MAX_EXPONENT = 32;

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull BigInteger defaultValue() {
        return DEFAULT_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull BigInteger randomValue() {
        return randomBigInteger();
    }

    /**
     * Generates a random {@code BigInteger} between -10<sup>32</sup>
     * and 10<sup>32</sup> both excluded.
     * 
     * @return A random {@code BigInteger} value
     */
    public static BigInteger randomBigInteger() {
        final int size = RandomUtils.nextInt(1, MAX_EXPONENT + 1);
        BigInteger result = new BigInteger(RandomStringUtils.randomNumeric(size));
        if (RandomUtils.nextBoolean()) {
            result = result.negate();
        }
        return result;
    }
}
