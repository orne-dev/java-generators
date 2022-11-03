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

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.HashSet;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code IntegerGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 * @see IntegerGenerator
 */
@Tag("ut")
class IntegerGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(Integer.class));
        assertTrue(Generators.supports(int.class));
        assertEquals(IntegerGenerator.class, Generators.getGenerator(Integer.class).getClass());
        assertEquals(IntegerGenerator.class, Generators.getGenerator(int.class).getClass());
    }

    /**
     * Unit test for {@link IntegerGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new IntegerGenerator().getPriority());
    }

    /**
     * Unit test for {@link IntegerGenerator#supports()}
     */
    @Test
    void testSupports() {
        final IntegerGenerator generator = new IntegerGenerator();
        assertTrue(generator.supports(Integer.class));
        assertTrue(generator.supports(int.class));
        assertFalse(generator.supports(Integer[].class));
        assertFalse(generator.supports(int[].class));
    }

    /**
     * Unit test for {@link IntegerGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final IntegerGenerator generator = new IntegerGenerator();
        assertEquals(IntegerGenerator.DEFAULT_VALUE, generator.defaultValue());
    }

    /**
     * Unit test for {@link IntegerGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final IntegerGenerator generator = new IntegerGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<Integer> results = new HashSet<>(); 
            // We just check that there is some result variety
            while (results.size() < 100) {
                results.add(generator.randomValue());
            }
        });
    }

    /**
     * Unit test for {@link IntegerGenerator#randomInt(int, int)}
     */
    @Test
    void testRandomInt_Range() {
        assertTimeoutPreemptively(Duration.ofSeconds(4), () -> {
            final int min = 10;
            final int max = 20;
            final HashSet<Integer> results = new HashSet<>(); 
            // We check that all values are generated
            while (results.size() < max - min) {
                results.add(IntegerGenerator.randomInt(min, max));
            }
        });
    }
}
