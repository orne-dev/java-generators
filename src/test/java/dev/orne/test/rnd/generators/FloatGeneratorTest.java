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
 * Unit tests for {@code FloatGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 * @see FloatGenerator
 */
@Tag("ut")
class FloatGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(Float.class));
        assertTrue(Generators.supports(float.class));
        assertEquals(FloatGenerator.class, Generators.getGenerator(Float.class).getClass());
        assertEquals(FloatGenerator.class, Generators.getGenerator(float.class).getClass());
    }

    /**
     * Unit test for {@link FloatGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new FloatGenerator().getPriority());
    }

    /**
     * Unit test for {@link FloatGenerator#supports()}
     */
    @Test
    void testSupports() {
        final FloatGenerator generator = new FloatGenerator();
        assertTrue(generator.supports(Float.class));
        assertTrue(generator.supports(float.class));
        assertFalse(generator.supports(Float[].class));
        assertFalse(generator.supports(float[].class));
    }

    /**
     * Unit test for {@link FloatGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final FloatGenerator generator = new FloatGenerator();
        assertEquals(FloatGenerator.DEFAULT_VALUE, generator.defaultValue());
    }

    /**
     * Unit test for {@link FloatGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final FloatGenerator generator = new FloatGenerator();
        GeneratorsTestUtils.assertRandomGeneration(generator, 1000, 4);
    }

    /**
     * Unit test for {@link FloatGenerator#randomFiniteFloat()}
     */
    @Test
    void testRandomFiniteFloat() {
        GeneratorsTestUtils.assertRandomGeneration(
                () -> {
                    final float value = FloatGenerator.randomFiniteFloat();
                    assertFalse(FloatGenerator.isInfinity(Float.floatToIntBits(value)));
                    return value;
                },
                1000,
                4);
    }

    /**
     * Unit test for {@link FloatGenerator#isNaN(int)}
     */
    @Test
    void testIsNaN() {
        assertFalse(FloatGenerator.isNaN(Float.floatToIntBits(RandomUtils.nextFloat())));
        assertFalse(FloatGenerator.isNaN(Float.floatToIntBits(Float.MIN_VALUE)));
        assertFalse(FloatGenerator.isNaN(Float.floatToIntBits(Float.MIN_NORMAL)));
        assertFalse(FloatGenerator.isNaN(Float.floatToIntBits(Float.MAX_VALUE)));
        assertTrue(FloatGenerator.isNaN(Float.floatToIntBits(Float.POSITIVE_INFINITY)));
        assertTrue(FloatGenerator.isNaN(Float.floatToIntBits(Float.NEGATIVE_INFINITY)));
        assertTrue(FloatGenerator.isNaN(Float.floatToIntBits(Float.NaN)));
        assertTrue(FloatGenerator.isNaN(
                Float.floatToIntBits(Float.POSITIVE_INFINITY) |
                Float.floatToIntBits(RandomUtils.nextFloat())
        ));
        assertTrue(FloatGenerator.isNaN(
                Float.floatToIntBits(Float.NEGATIVE_INFINITY) |
                Float.floatToIntBits(RandomUtils.nextFloat())
        ));
    }

    /**
     * Unit test for {@link FloatGenerator#isInfinity(int)}
     */
    @Test
    void testIsInfinity() {
        assertFalse(FloatGenerator.isInfinity(Float.floatToIntBits(RandomUtils.nextFloat())));
        assertFalse(FloatGenerator.isInfinity(Float.floatToIntBits(Float.MIN_VALUE)));
        assertFalse(FloatGenerator.isInfinity(Float.floatToIntBits(Float.MIN_NORMAL)));
        assertFalse(FloatGenerator.isInfinity(Float.floatToIntBits(Float.MAX_VALUE)));
        assertTrue(FloatGenerator.isInfinity(Float.floatToIntBits(Float.POSITIVE_INFINITY)));
        assertTrue(FloatGenerator.isInfinity(Float.floatToIntBits(Float.NEGATIVE_INFINITY)));
        assertFalse(FloatGenerator.isInfinity(Float.floatToIntBits(Float.NaN)));
        assertFalse(FloatGenerator.isInfinity(
                Float.floatToIntBits(Float.POSITIVE_INFINITY) |
                Float.floatToIntBits(RandomUtils.nextFloat())
        ));
        assertFalse(FloatGenerator.isInfinity(
                Float.floatToIntBits(Float.NEGATIVE_INFINITY) |
                Float.floatToIntBits(RandomUtils.nextFloat())
        ));
    }
}
