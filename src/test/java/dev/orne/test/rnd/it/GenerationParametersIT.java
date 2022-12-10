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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.GeneratorsTestUtils;
import dev.orne.test.rnd.params.GenerationParameters;
import dev.orne.test.rnd.params.KeyValueGenericParameters;
import dev.orne.test.rnd.params.NullableParameters;
import dev.orne.test.rnd.params.NumberParameters;
import dev.orne.test.rnd.params.SimpleGenericParameters;
import dev.orne.test.rnd.params.SizeParameters;

/**
 * Integration test for generation parameters.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 */
@Tag("it")
class GenerationParametersIT {

    /**
     * Test for {@code NullableParameters} creation.
     */
    @Test
    void testNullableParameters() {
        final NullableParameters params = GenerationParameters.forNullables().withNullable(false);
        String result;
        result = Generators.defaultValue(String.class, params);
        assertEquals("", result);
        result = Generators.nullableDefaultValue(String.class, params);
        assertEquals("", result);
        GeneratorsTestUtils.assertRandomGeneration(String.class, 100, 2, params);
        GeneratorsTestUtils.assertRandomGeneration(
                () -> Generators.nullableRandomValue(String.class, params),
                100,
                2);
    }

    /**
     * Test for {@code SizeParameters} creation.
     */
    @Test
    void testSizeParameters() {
        final SizeParameters params = GenerationParameters.forSizes().withMinSize(10);
        String result;
        result = Generators.defaultValue(String.class, params);
        assertEquals("", result);
        result = Generators.nullableDefaultValue(String.class, params);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(String.class, 100, 2, params);
        GeneratorsTestUtils.assertNullableRandomGeneration(String.class, 100, 2, params);
    }

    /**
     * Test for {@code NumberParameters} creation.
     */
    @Test
    @Disabled(value = "Needs support in number generators")
    void testNumberParameters() {
        final NumberParameters params = GenerationParameters.forNumbers().withMin(10);
        Long result;
        result = Generators.defaultValue(Long.class, params);
        assertEquals(10, result);
        result = Generators.nullableDefaultValue(Long.class, params);
        assertEquals(10, result);
        GeneratorsTestUtils.assertRandomGeneration(Long.class, 100, 2, params);
        GeneratorsTestUtils.assertNullableRandomGeneration(Long.class, 100, 2, params);
    }

    /**
     * Test for {@code SimpleGenericParameters} creation.
     */
    @Test
    void testSimpleGenericParameters() {
        final SimpleGenericParameters params = GenerationParameters
                .forSimpleGenerics()
                .withElementsType(Integer.class);
        List<?> result;
        result = Generators.defaultValue(List.class, params);
        assertEquals(new ArrayList<Integer>(), result);
        result = Generators.nullableDefaultValue(List.class, params);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(List.class, 100, 2, params);
        GeneratorsTestUtils.assertNullableRandomGeneration(List.class, 100, 2, params);
    }

    /**
     * Test for {@code KeyValueGenericParameters} creation.
     */
    @Test
    void testMapGeneration() {
        final KeyValueGenericParameters params = GenerationParameters
                .forKeyValueGenerics()
                .withKeysType(Integer.class)
                .withValuesType(String.class);
        Map<?, ?> result;
        result = Generators.defaultValue(Map.class, params);
        assertEquals(new HashMap<Integer, String>(), result);
        result = Generators.nullableDefaultValue(Map.class, params);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Map.class, 100, 2, params);
        GeneratorsTestUtils.assertNullableRandomGeneration(Map.class, 100, 2, params);
    }
}
