package dev.orne.test.rnd.it;

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

import java.util.Set;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.GeneratorsTestUtils;

/**
 * Integration test for custom generators registration.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 */
@Tag("it")
class CustomGeneratorsIT {

    /**
     * Test for custom enumeration generator.
     * 
     * @see EnumCustomGenerator
     */
    @Test
    void testCustomEnumGeneration() {
        EnumCustomGenerator.Values result;
        result = Generators.defaultValue(EnumCustomGenerator.Values.class);
        assertSame(EnumCustomGenerator.Values.DEFAULT, result);
        result = Generators.nullableDefaultValue(EnumCustomGenerator.Values.class);
        assertNull(result);
        Set<EnumCustomGenerator.Values> results = GeneratorsTestUtils.assertRandomGeneration(
                EnumCustomGenerator.Values.class,
                EnumCustomGenerator.GENERATED_VALUES.length,
                2);
        for (EnumCustomGenerator.Values value : results) {
            assertNotEquals(EnumCustomGenerator.Values.UNDESIRED, value);
        }
        results = GeneratorsTestUtils.assertNullableRandomGeneration(
                EnumCustomGenerator.Values.class,
                EnumCustomGenerator.GENERATED_VALUES.length,
                2);
        for (EnumCustomGenerator.Values value : results) {
            assertNotEquals(EnumCustomGenerator.Values.UNDESIRED, value);
        }
    }

    /**
     * Test for custom interface generator.
     * 
     * @see CustomBasicGenerator.BasicInterface
     */
    @Test
    void testCustomIntefaceGeneration() {
        CustomBasicGenerator.BasicInterface result;
        result = Generators.defaultValue(CustomBasicGenerator.BasicInterface.class);
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertNull(result.getName());
        result = Generators.nullableDefaultValue(CustomBasicGenerator.BasicInterface.class);
        assertNull(result);
        Set<CustomBasicGenerator.BasicInterface> results = GeneratorsTestUtils.assertRandomGeneration(
                CustomBasicGenerator.BasicInterface.class,
                50,
                2);
        for (CustomBasicGenerator.BasicInterface value : results) {
            assertTrue(value.getCode() > 1);
            assertNotNull(value.getName());
        }
        results = GeneratorsTestUtils.assertNullableRandomGeneration(
                CustomBasicGenerator.BasicInterface.class,
                50,
                2);
        for (CustomBasicGenerator.BasicInterface value : results) {
            assertTrue(value.getCode() > 1);
            assertNotNull(value.getName());
        }
    }

    /**
     * Test for custom bean generator.
     * 
     * @see CustomBasicGenerator.BasicImpl
     */
    @Test
    void testCustomBeanGeneration() {
        CustomBasicGenerator.BasicImpl result;
        result = Generators.defaultValue(CustomBasicGenerator.BasicImpl.class);
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertNull(result.getName());
        result = Generators.nullableDefaultValue(CustomBasicGenerator.BasicImpl.class);
        assertNull(result);
        Set<CustomBasicGenerator.BasicImpl> results = GeneratorsTestUtils.assertRandomGeneration(
                CustomBasicGenerator.BasicImpl.class,
                50,
                2);
        for (CustomBasicGenerator.BasicInterface value : results) {
            assertTrue(value.getCode() > 1);
            assertNotNull(value.getName());
        }
        results = GeneratorsTestUtils.assertNullableRandomGeneration(
                CustomBasicGenerator.BasicImpl.class,
                50,
                2);
        for (CustomBasicGenerator.BasicInterface value : results) {
            assertTrue(value.getCode() > 1);
            assertNotNull(value.getName());
        }
    }

    /**
     * Test for custom bean generator.
     * 
     * @see CustomBasicGenerator.ExtendedImpl
     */
    @Test
    void testCustomExtendedBeanGeneration() {
        CustomBasicGenerator.ExtendedImpl result;
        result = Generators.defaultValue(CustomBasicGenerator.ExtendedImpl.class);
        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertNull(result.getName());
        assertNull(result.getCreationDate());
        assertNull(result.getUpdateDate());
        result = Generators.nullableDefaultValue(CustomBasicGenerator.ExtendedImpl.class);
        assertNull(result);
        Set<CustomBasicGenerator.ExtendedImpl> results = GeneratorsTestUtils.assertRandomGeneration(
                CustomBasicGenerator.ExtendedImpl.class,
                50,
                2);
        for (CustomBasicGenerator.ExtendedImpl value : results) {
            assertTrue(value.getCode() > 1);
            assertNotNull(value.getName());
            assertNotNull(value.getCreationDate());
            assertNotNull(value.getUpdateDate());
        }
        results = GeneratorsTestUtils.assertNullableRandomGeneration(
                CustomBasicGenerator.ExtendedImpl.class,
                50,
                2);
        for (CustomBasicGenerator.ExtendedImpl value : results) {
            assertTrue(value.getCode() > 1);
            assertNotNull(value.getName());
            assertNotNull(value.getCreationDate());
            assertNotNull(value.getUpdateDate());
        }
    }
}
