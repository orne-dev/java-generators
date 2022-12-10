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

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;

/**
 * Unit tests for primitive value generation.
 * <p>
 * Unboxing occurs in calling method.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 */
@Tag("ut")
class PrimitiveGenerationTest {

    /**
     * Unit test for {@code boolean} values generation,
     */
    @Test
    void testBooleanPrimitives() {
        boolean result = Generators.defaultValue(boolean.class);
        assertEquals(false, result);
        result = Generators.nullableDefaultValue(boolean.class);
        assertEquals(false, result);
        result = Generators.randomValue(boolean.class);
        result = Generators.nullableRandomValue(boolean.class);
        boolean[] arrayResult = Generators.defaultValue(boolean[].class);
        assertArrayEquals(new boolean[0], arrayResult);
        arrayResult = Generators.nullableDefaultValue(boolean[].class);
        assertNull(arrayResult);
        arrayResult = Generators.randomValue(boolean[].class);
        assertNotNull(arrayResult);
        assertTrue(arrayResult.length > 0);
        arrayResult = Generators.nullableRandomValue(boolean[].class);
        if (arrayResult != null) {
            assertTrue(arrayResult.length > 0);
        }
    }

    /**
     * Unit test for {@code byte} values generation,
     */
    @Test
    void testBytePrimitives() {
        byte result = Generators.defaultValue(byte.class);
        assertEquals((byte) 0, result);
        result = Generators.nullableDefaultValue(byte.class);
        assertEquals((byte) 0, result);
        result = Generators.randomValue(byte.class);
        result = Generators.nullableRandomValue(byte.class);
        byte[] arrayResult = Generators.defaultValue(byte[].class);
        assertArrayEquals(new byte[0], arrayResult);
        arrayResult = Generators.nullableDefaultValue(byte[].class);
        assertNull(arrayResult);
        arrayResult = Generators.randomValue(byte[].class);
        assertNotNull(arrayResult);
        assertTrue(arrayResult.length > 0);
        arrayResult = Generators.nullableRandomValue(byte[].class);
        if (arrayResult != null) {
            assertTrue(arrayResult.length > 0);
        }
    }

    /**
     * Unit test for {@code short} values generation,
     */
    @Test
    void testShortPrimitives() {
        short result = Generators.defaultValue(short.class);
        assertEquals((short) 0, result);
        result = Generators.nullableDefaultValue(short.class);
        assertEquals((short) 0, result);
        result = Generators.randomValue(short.class);
        result = Generators.nullableRandomValue(short.class);
        short[] arrayResult = Generators.defaultValue(short[].class);
        assertArrayEquals(new short[0], arrayResult);
        arrayResult = Generators.nullableDefaultValue(short[].class);
        assertNull(arrayResult);
        arrayResult = Generators.randomValue(short[].class);
        assertNotNull(arrayResult);
        assertTrue(arrayResult.length > 0);
        arrayResult = Generators.nullableRandomValue(short[].class);
        if (arrayResult != null) {
            assertTrue(arrayResult.length > 0);
        }
    }

    /**
     * Unit test for {@code int} values generation,
     */
    @Test
    void testIntPrimitives() {
        int result = Generators.defaultValue(int.class);
        assertEquals(0, result);
        result = Generators.nullableDefaultValue(int.class);
        assertEquals(0, result);
        result = Generators.randomValue(int.class);
        result = Generators.nullableRandomValue(int.class);
        int[] arrayResult = Generators.defaultValue(int[].class);
        assertArrayEquals(new int[0], arrayResult);
        arrayResult = Generators.nullableDefaultValue(int[].class);
        assertNull(arrayResult);
        arrayResult = Generators.randomValue(int[].class);
        assertNotNull(arrayResult);
        assertTrue(arrayResult.length > 0);
        arrayResult = Generators.nullableRandomValue(int[].class);
        if (arrayResult != null) {
            assertTrue(arrayResult.length > 0);
        }
    }

    /**
     * Unit test for {@code long} values generation,
     */
    @Test
    void testLongPrimitives() {
        long result = Generators.defaultValue(long.class);
        assertEquals(0l, result);
        result = Generators.nullableDefaultValue(long.class);
        assertEquals(0l, result);
        result = Generators.randomValue(long.class);
        result = Generators.nullableRandomValue(long.class);
        long[] arrayResult = Generators.defaultValue(long[].class);
        assertArrayEquals(new long[0], arrayResult);
        arrayResult = Generators.nullableDefaultValue(long[].class);
        assertNull(arrayResult);
        arrayResult = Generators.randomValue(long[].class);
        assertNotNull(arrayResult);
        assertTrue(arrayResult.length > 0);
        arrayResult = Generators.nullableRandomValue(long[].class);
        if (arrayResult != null) {
            assertTrue(arrayResult.length > 0);
        }
    }

    /**
     * Unit test for {@code float} values generation,
     */
    @Test
    void testFloatPrimitives() {
        float result = Generators.defaultValue(float.class);
        assertEquals(0f, result);
        result = Generators.nullableDefaultValue(float.class);
        assertEquals(0f, result);
        result = Generators.randomValue(float.class);
        result = Generators.nullableRandomValue(float.class);
        float[] arrayResult = Generators.defaultValue(float[].class);
        assertArrayEquals(new float[0], arrayResult);
        arrayResult = Generators.nullableDefaultValue(float[].class);
        assertNull(arrayResult);
        arrayResult = Generators.randomValue(float[].class);
        assertNotNull(arrayResult);
        assertTrue(arrayResult.length > 0);
        arrayResult = Generators.nullableRandomValue(float[].class);
        if (arrayResult != null) {
            assertTrue(arrayResult.length > 0);
        }
    }

    /**
     * Unit test for {@code double} values generation,
     */
    @Test
    void testDoublePrimitives() {
        double result = Generators.defaultValue(double.class);
        assertEquals(0d, result);
        result = Generators.nullableDefaultValue(double.class);
        assertEquals(0d, result);
        result = Generators.randomValue(double.class);
        result = Generators.nullableRandomValue(double.class);
        double[] arrayResult = Generators.defaultValue(double[].class);
        assertArrayEquals(new double[0], arrayResult);
        arrayResult = Generators.nullableDefaultValue(double[].class);
        assertNull(arrayResult);
        arrayResult = Generators.randomValue(double[].class);
        assertNotNull(arrayResult);
        assertTrue(arrayResult.length > 0);
        arrayResult = Generators.nullableRandomValue(double[].class);
        if (arrayResult != null) {
            assertTrue(arrayResult.length > 0);
        }
    }

    /**
     * Unit test for {@code char} values generation,
     */
    @Test
    void testCharPrimitives() {
        char result = Generators.defaultValue(char.class);
        assertEquals((char) 0, result);
        result = Generators.nullableDefaultValue(char.class);
        assertEquals((char) 0, result);
        result = Generators.randomValue(char.class);
        result = Generators.nullableRandomValue(char.class);
        char[] arrayResult = Generators.defaultValue(char[].class);
        assertArrayEquals(new char[0], arrayResult);
        arrayResult = Generators.nullableDefaultValue(char[].class);
        assertNull(arrayResult);
        arrayResult = Generators.randomValue(char[].class);
        assertNotNull(arrayResult);
        assertTrue(arrayResult.length > 0);
        arrayResult = Generators.nullableRandomValue(char[].class);
        if (arrayResult != null) {
            assertTrue(arrayResult.length > 0);
        }
    }
}
