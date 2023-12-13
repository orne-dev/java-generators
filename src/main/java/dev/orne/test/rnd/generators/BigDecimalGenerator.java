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

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.constraints.NotNull;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.AbstractTypedGenerator;
import dev.orne.test.rnd.Priority;

/**
 * Generator of {@code BigDecimal} and {@code Number} values.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
@Priority(Priority.NATIVE_GENERATORS)
public class BigDecimalGenerator
extends AbstractTypedGenerator<BigDecimal> {

    /** The default value. */
    public static final BigDecimal DEFAULT_VALUE = BigDecimal.ZERO;

    /**
     * Creates a new instance.
     */
    public BigDecimalGenerator() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(
            final @NotNull Class<?> type) {
        return super.supports(type) || Number.class.equals(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull BigDecimal defaultValue() {
        return DEFAULT_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull BigDecimal randomValue() {
        return randomBigDecimal();
    }

    /**
     * Generates a random {@code BigDecimal} with unescaled value between
     * -10<sup>32</sup> and 10<sup>32</sup> (both excluded) and scale
     * between -1022 and 1023 (both included).
     * 
     * @return A random {@code BigDecimal} value
     */
    public static BigDecimal randomBigDecimal() {
        final BigInteger value = BigIntegerGenerator.randomBigInteger();
        final int scale = IntegerGenerator.randomInt(Double.MIN_EXPONENT, Double.MAX_EXPONENT);
        return new BigDecimal(value, scale);
    }
}
