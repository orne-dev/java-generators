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

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.GeneratorsTestUtils;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code DoubleGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 * @see DoubleGenerator
 */
@Tag("ut")
class DoubleGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(Double.class));
        assertTrue(Generators.supports(double.class));
        assertEquals(DoubleGenerator.class, Generators.getGenerator(Double.class).getClass());
        assertEquals(DoubleGenerator.class, Generators.getGenerator(double.class).getClass());
    }

    /**
     * Unit test for {@link DoubleGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new DoubleGenerator().getPriority());
    }

    /**
     * Unit test for {@link DoubleGenerator#supports()}
     */
    @Test
    void testSupports() {
        final DoubleGenerator generator = new DoubleGenerator();
        assertTrue(generator.supports(Double.class));
        assertTrue(generator.supports(double.class));
        assertFalse(generator.supports(Double[].class));
        assertFalse(generator.supports(double[].class));
    }

    /**
     * Unit test for {@link DoubleGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final DoubleGenerator generator = new DoubleGenerator();
        assertEquals(DoubleGenerator.DEFAULT_VALUE, generator.defaultValue());
    }

    /**
     * Unit test for {@link DoubleGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final DoubleGenerator generator = new DoubleGenerator();
        GeneratorsTestUtils.assertRandomGeneration(generator, 1000, 4);
    }

    /**
     * Unit test for {@link DoubleGenerator#randomFiniteDouble()}
     */
    @Test
    void testRandomFiniteDouble() {
        GeneratorsTestUtils.assertRandomGeneration(
                () -> {
                    final double value = DoubleGenerator.randomFiniteDouble();
                    assertFalse(DoubleGenerator.isInfinity(Double.doubleToLongBits(value)));
                    return value;
                },
                1000,
                4);
    }

    /**
     * Unit test for {@link DoubleGenerator#isNaN(int)}
     */
    @Test
    void testIsNaN() {
        assertFalse(DoubleGenerator.isNaN(Double.doubleToLongBits(RandomUtils.nextDouble())));
        assertFalse(DoubleGenerator.isNaN(Double.doubleToLongBits(Double.MIN_VALUE)));
        assertFalse(DoubleGenerator.isNaN(Double.doubleToLongBits(Double.MIN_NORMAL)));
        assertFalse(DoubleGenerator.isNaN(Double.doubleToLongBits(Double.MAX_VALUE)));
        assertTrue(DoubleGenerator.isNaN(Double.doubleToLongBits(Double.POSITIVE_INFINITY)));
        assertTrue(DoubleGenerator.isNaN(Double.doubleToLongBits(Double.NEGATIVE_INFINITY)));
        assertTrue(DoubleGenerator.isNaN(Double.doubleToLongBits(Double.NaN)));
        assertTrue(DoubleGenerator.isNaN(
                Double.doubleToLongBits(Double.POSITIVE_INFINITY) |
                Double.doubleToLongBits(RandomUtils.nextDouble())
        ));
        assertTrue(DoubleGenerator.isNaN(
                Double.doubleToLongBits(Double.NEGATIVE_INFINITY) |
                Double.doubleToLongBits(RandomUtils.nextDouble())
        ));
    }

    /**
     * Unit test for {@link DoubleGenerator#isInfinity(int)}
     */
    @Test
    void testIsInfinity() {
        assertFalse(DoubleGenerator.isInfinity(Double.doubleToLongBits(RandomUtils.nextFloat())));
        assertFalse(DoubleGenerator.isInfinity(Double.doubleToLongBits(Double.MIN_VALUE)));
        assertFalse(DoubleGenerator.isInfinity(Double.doubleToLongBits(Double.MIN_NORMAL)));
        assertFalse(DoubleGenerator.isInfinity(Double.doubleToLongBits(Double.MAX_VALUE)));
        assertTrue(DoubleGenerator.isInfinity(Double.doubleToLongBits(Double.POSITIVE_INFINITY)));
        assertTrue(DoubleGenerator.isInfinity(Double.doubleToLongBits(Double.NEGATIVE_INFINITY)));
        assertFalse(DoubleGenerator.isInfinity(Double.doubleToLongBits(Double.NaN)));
        assertFalse(DoubleGenerator.isInfinity(
                Double.doubleToLongBits(Double.POSITIVE_INFINITY) |
                Double.doubleToLongBits(RandomUtils.nextDouble())
        ));
        assertFalse(DoubleGenerator.isInfinity(
                Double.doubleToLongBits(Double.NEGATIVE_INFINITY) |
                Double.doubleToLongBits(RandomUtils.nextDouble())
        ));
    }
}
