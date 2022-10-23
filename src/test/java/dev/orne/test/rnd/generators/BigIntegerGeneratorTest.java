package dev.orne.test.rnd.generators;

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

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.time.Duration;
import java.util.HashSet;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code BigIntegerGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2021-10
 * @since 0.1
 * @see BigIntegerGenerator
 */
@Tag("ut")
class BigIntegerGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(BigInteger.class));
        assertEquals(BigIntegerGenerator.class, Generators.getGenerator(BigInteger.class).getClass());
    }

    /**
     * Unit test for {@link BigIntegerGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new BigIntegerGenerator().getPriority());
    }

    /**
     * Unit test for {@link BigIntegerGenerator#supports()}
     */
    @Test
    void testSupports() {
        final BigIntegerGenerator generator = new BigIntegerGenerator();
        assertTrue(generator.supports(BigInteger.class));
        assertFalse(generator.supports(BigInteger[].class));
    }

    /**
     * Unit test for {@link BigIntegerGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final BigIntegerGenerator generator = new BigIntegerGenerator();
        assertEquals(BigIntegerGenerator.DEFAULT_VALUE, generator.defaultValue());
    }

    /**
     * Unit test for {@link BigIntegerGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final BigIntegerGenerator generator = new BigIntegerGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<BigInteger> results = new HashSet<>(); 
            // We just check that there is some result variety
            while (results.size() < 100) {
                results.add(generator.randomValue());
            }
        });
    }
}
