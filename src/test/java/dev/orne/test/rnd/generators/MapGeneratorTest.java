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
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.GeneratorsTestUtils;
import dev.orne.test.rnd.Priority;
import dev.orne.test.rnd.params.DefaultParametersExtractor;
import dev.orne.test.rnd.params.KeyValueGenericParameters;
import dev.orne.test.rnd.params.MapGenerationParameters;
import dev.orne.test.rnd.params.NullableParameters;
import dev.orne.test.rnd.params.ParametersExtractor;
import dev.orne.test.rnd.params.ParametersSourceExtractor;
import dev.orne.test.rnd.params.SizeParameters;
import dev.orne.test.rnd.params.TypeDeclaration;

/**
 * Unit tests for {@code MapGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 * @see MapGenerator
 */
@Tag("ut")
class MapGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(Map.class));
        assertEquals(MapGenerator.class, Generators.getGenerator(Map.class).getClass());
    }

    /**
     * Unit test for {@link MapGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.GENERIC_GENERATORS, new MapGenerator().getPriority());
    }

    /**
     * Unit test for {@link MapGenerator#getParametersType()}
     */
    @Test
    void testParametersType() {
        assertEquals(MapGenerationParameters.class, new MapGenerator().getParametersType());
    }

    /**
     * Unit test for {@link MapGenerator#getExtractor()}
     */
    @Test
    void testBuiltInExtractors() {
        final ParametersExtractor<MapGenerationParameters> result = new MapGenerator().getExtractor();
        @SuppressWarnings("unchecked")
        final List<ParametersSourceExtractor<?, ?>> extractors =
                assertInstanceOf(DefaultParametersExtractor.class, result).getExtractors();
        assertExtractorPresent(extractors, TypeDeclaration.class);
        assertExtractorPresent(extractors, NotNull.class);
        assertExtractorPresent(extractors, Size.class);
        assertExtractorPresent(extractors, NullableParameters.class);
        assertExtractorPresent(extractors, KeyValueGenericParameters.class);
        assertExtractorPresent(extractors, SizeParameters.class);
        assertExtractorPresent(extractors, MapGenerationParameters.class);
    }

    private ParametersSourceExtractor<?, ?> assertExtractorPresent(
            final List<ParametersSourceExtractor<?, ?>> extractors,
            final Class<?> sourceType) {
        ParametersSourceExtractor<?, ?> result = null;
        for (final ParametersSourceExtractor<?, ?> candidate : extractors) {
            if (candidate.getSourceType().isAssignableFrom(sourceType)) {
                result = candidate;
            }
        }
        assertNotNull(result);
        return result;
    }

    /**
     * Unit test for {@link MapGenerator#supports()}
     */
    @Test
    void testSupports() {
        final MapGenerator generator = new MapGenerator();
        assertTrue(generator.supports(Map.class));
        assertFalse(generator.supports(Map[].class));
    }

    /**
     * Unit test for {@link MapGenerator#defaultValue(MapGenerationParameters)}
     */
    @Test
    void testDefaultValue() {
        final MapGenerationParameters params = new MapGenerationParameters();
        params.setKeysType(Integer.class);
        params.setValuesType(String.class);
        final MapGenerator generator = new MapGenerator();
        final Map<?, ?> result = generator.defaultValue(params);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link MapGenerator#nullableDefaultValue(MapGenerationParameters)}
     */
    @Test
    void testNullableDefaultValue() {
        final MapGenerationParameters params = new MapGenerationParameters();
        params.setKeysType(Integer.class);
        params.setValuesType(String.class);
        final MapGenerator generator = new MapGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean nullList = false;
            boolean nonNullList = false;
            // We just check that there is some result variety
            while (!nullList && !nonNullList) {
                final Map<?, ?> result = generator.nullableDefaultValue(params);
                if (result == null) {
                    nullList = true;
                } else {
                    nonNullList = true;
                    assertTrue(result.isEmpty());
                }
            }
        });
        params.setNullable(false);
        final Map<?, ?> result = generator.nullableDefaultValue(params);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link MapGenerator#randomValue(MapGenerationParameters)}
     */
    @Test
    void testRandomValue() {
        final MapGenerationParameters params = new MapGenerationParameters();
        params.setKeysType(Integer.class);
        params.setValuesType(String.class);
        final MapGenerator generator = new MapGenerator();
        GeneratorsTestUtils.assertRandomGeneration(generator, 100, 2, params);
    }

    /**
     * Unit test for {@link MapGenerator#nullableRandomValue(MapGenerationParameters)}
     */
    @Test
    void testNullableRandomValue() {
        final MapGenerationParameters params = new MapGenerationParameters();
        params.setKeysType(Integer.class);
        params.setValuesType(String.class);
        final MapGenerator generator = new MapGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<Map<?, ?>> results = new HashSet<>(); 
            boolean nullList = false;
            boolean nullKeys = false;
            boolean nullValues = false;
            // We just check that there is some result variety
            while (results.size() < 100) {
                final Map<?, ?> result = generator.nullableRandomValue(params);
                results.add(result);
                if (result == null) {
                    nullList = true;
                } else {
                    for (final Map.Entry<?, ?> entry : result.entrySet()) {
                        if (entry.getKey() == null) {
                            nullKeys = true;
                        }
                        if (entry.getValue() == null) {
                            nullValues = true;
                        }
                    }
                }
            }
            assertTrue(nullList);
            assertFalse(nullKeys);
            assertTrue(nullValues);
        });
        params.setNullable(false);
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<Map<?, ?>> results = new HashSet<>(); 
            boolean nullList = false;
            boolean nullKeys = false;
            boolean nullValues = false;
            // We just check that there is some result variety
            while (results.size() < 100) {
                final Map<?, ?> result = generator.nullableRandomValue(params);
                results.add(result);
                if (result == null) {
                    nullList = true;
                } else {
                    for (final Map.Entry<?, ?> entry : result.entrySet()) {
                        if (entry.getKey() == null) {
                            nullKeys = true;
                        }
                        if (entry.getValue() == null) {
                            nullValues = true;
                        }
                    }
                }
            }
            assertFalse(nullList);
            assertFalse(nullKeys);
            assertTrue(nullValues);
        });
    }

    /**
     * Unit test for {@link MapGenerator#nullableRandomValue(Class, MapGenerationParameters)}
     */
    @Test
    void testNullableRandomTypeValue() {
        final MapGenerationParameters params = new MapGenerationParameters();
        params.setKeysType(Integer.class);
        params.setValuesType(String.class);
        final MapGenerator generator = new MapGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<Map<?, ?>> results = new HashSet<>(); 
            boolean nullList = false;
            boolean nullKeys = false;
            boolean nullValues = false;
            // We just check that there is some result variety
            while (results.size() < 100) {
                final Map<?, ?> result = generator.nullableRandomValue(Map.class, params);
                results.add(result);
                if (result == null) {
                    nullList = true;
                } else {
                    for (final Map.Entry<?, ?> entry : result.entrySet()) {
                        if (entry.getKey() == null) {
                            nullKeys = true;
                        }
                        if (entry.getValue() == null) {
                            nullValues = true;
                        }
                    }
                }
            }
            assertTrue(nullList);
            assertFalse(nullKeys);
            assertTrue(nullValues);
        });
        params.setNullable(false);
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<Map<?, ?>> results = new HashSet<>(); 
            boolean nullList = false;
            boolean nullKeys = false;
            boolean nullValues = false;
            // We just check that there is some result variety
            while (results.size() < 100) {
                final Map<?, ?> result = generator.nullableRandomValue(Map.class, params);
                results.add(result);
                if (result == null) {
                    nullList = true;
                } else {
                    for (final Map.Entry<?, ?> entry : result.entrySet()) {
                        if (entry.getKey() == null) {
                            nullKeys = true;
                        }
                        if (entry.getValue() == null) {
                            nullValues = true;
                        }
                    }
                }
            }
            assertFalse(nullList);
            assertFalse(nullKeys);
            assertTrue(nullValues);
        });
    }
}
