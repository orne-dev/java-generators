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

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.reflect.TypeUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;
import dev.orne.test.rnd.params.DefaultParametersExtractor;
import dev.orne.test.rnd.params.GenerationParameters;
import dev.orne.test.rnd.params.CollectionGenerationParameters;
import dev.orne.test.rnd.params.ParametersExtractor;
import dev.orne.test.rnd.params.ParametersSourceExtractor;
import dev.orne.test.rnd.params.SimpleGenericParameters;
import dev.orne.test.rnd.params.SizeParameters;
import dev.orne.test.rnd.params.TypeDeclaration;

/**
 * Unit tests for {@code ListGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 * @see ListGenerator
 */
@Tag("ut")
class ListGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(Collection.class));
        assertTrue(Generators.supports(List.class));
        assertEquals(ListGenerator.class, Generators.getGenerator(Collection.class).getClass());
        assertEquals(ListGenerator.class, Generators.getGenerator(List.class).getClass());
    }

    /**
     * Unit test for {@link ListGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.GENERIC_GENERATORS, new ListGenerator().getPriority());
    }

    /**
     * Unit test for {@link ListGenerator#getParametersType()}
     */
    @Test
    void testParametersType() {
        assertEquals(CollectionGenerationParameters.class, new ListGenerator().getParametersType());
    }

    /**
     * Unit test for {@link ListGenerator#getExtractor()}
     */
    @Test
    void testBuiltInExtractors() {
        final ParametersExtractor<CollectionGenerationParameters> result = new ListGenerator().getExtractor();
        @SuppressWarnings("unchecked")
        final List<ParametersSourceExtractor<?, ?>> extractors =
                assertInstanceOf(DefaultParametersExtractor.class, result).getExtractors();
        assertExtractorPresent(extractors, TypeDeclaration.class);
        assertExtractorPresent(extractors, NotNull.class);
        assertExtractorPresent(extractors, Size.class);
        assertExtractorPresent(extractors, GenerationParameters.class);
        assertExtractorPresent(extractors, SimpleGenericParameters.class);
        assertExtractorPresent(extractors, SizeParameters.class);
        assertExtractorPresent(extractors, CollectionGenerationParameters.class);
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
     * Unit test for {@link ListGenerator#supports()}
     */
    @Test
    void testSupports() {
        final ListGenerator generator = new ListGenerator();
        assertTrue(generator.supports(Collection.class));
        assertFalse(generator.supports(Collection[].class));
        assertTrue(generator.supports(List.class));
        assertFalse(generator.supports(List[].class));
    }

    /**
     * Unit test for {@link ListGenerator#createParameters()}
     */
    @Test
    void testCreateParameters() {
        CollectionGenerationParameters.Builder builder = ListGenerator.createParameters();
        assertNotNull(builder);
        assertThrows(NullPointerException.class, () -> {
            builder.withElementsType((Class<?>) null);
        });
        assertThrows(NullPointerException.class, () -> {
            builder.withElementsType((ParameterizedType) null);
        });
        assertThrows(NullPointerException.class, () -> {
            builder.withElementsType((GenericArrayType) null);
        });
        CollectionGenerationParameters result = builder.withElementsType(Integer.class);
        assertEquals(Integer.class, result.getType());
        final ParameterizedType pType = TypeUtils.parameterize(Set.class, Long.class);
        result = builder.withElementsType(pType);
        assertEquals(pType, result.getType());
        final GenericArrayType gaType = TypeUtils.genericArrayType(pType);
        result = builder.withElementsType(gaType);
        assertEquals(gaType, result.getType());
    }

    /**
     * Unit test for {@link ListGenerator#defaultValue(Parameters)}
     */
    @Test
    void testDefaultValue() {
        final CollectionGenerationParameters params = ListGenerator.createParameters()
                .withElementsType(Integer.class);
        final ListGenerator generator = new ListGenerator();
        final List<?> result = generator.defaultValue(params);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ListGenerator#defaultValue(Parameters)}
     */
    @Test
    void testDefaultCollectionValue() {
        final CollectionGenerationParameters params = ListGenerator.createParameters()
                .withElementsType(Integer.class);
        final ListGenerator generator = new ListGenerator();
        final Collection<?> result = generator.defaultValue(Collection.class, params);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ListGenerator#nullableDefaultValue(Parameters)}
     */
    @Test
    void testNullableDefaultValue() {
        final CollectionGenerationParameters params = ListGenerator.createParameters()
                .withElementsType(Integer.class);
        final ListGenerator generator = new ListGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean nullList = false;
            boolean nonNullList = false;
            // We just check that there is some result variety
            while (!nullList && !nonNullList) {
                final List<?> result = generator.nullableDefaultValue(params);
                if (result == null) {
                    nullList = true;
                } else {
                    nonNullList = true;
                    assertTrue(result.isEmpty());
                }
            }
        });
        params.setNullable(false);
        final List<?> result = generator.nullableDefaultValue(params);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ListGenerator#nullableDefaultValue(Parameters)}
     */
    @Test
    void testNullableDefaultCollectionValue() {
        final CollectionGenerationParameters params = ListGenerator.createParameters()
                .withElementsType(Integer.class);
        final ListGenerator generator = new ListGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean nullList = false;
            boolean nonNullList = false;
            // We just check that there is some result variety
            while (!nullList && !nonNullList) {
                final Collection<?> result = generator.nullableDefaultValue(Collection.class, params);
                if (result == null) {
                    nullList = true;
                } else {
                    nonNullList = true;
                    assertTrue(result.isEmpty());
                }
            }
        });
        params.setNullable(false);
        final Collection<?> result = generator.nullableDefaultValue(Collection.class, params);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ListGenerator#randomValue(Parameters)}
     */
    @Test
    void testRandomValue() {
        final CollectionGenerationParameters params = ListGenerator.createParameters()
                .withElementsType(Integer.class);
        final ListGenerator generator = new ListGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<List<?>> results = new HashSet<>(); 
            // We just check that there is some result variety
            while (results.size() < 100) {
                results.add(generator.randomValue(params));
            }
        });
    }

    /**
     * Unit test for {@link ListGenerator#randomValue(Parameters)}
     */
    @Test
    void testRandomCollectionValue() {
        final CollectionGenerationParameters params = ListGenerator.createParameters()
                .withElementsType(Integer.class);
        final ListGenerator generator = new ListGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<Collection<?>> results = new HashSet<>(); 
            // We just check that there is some result variety
            while (results.size() < 100) {
                results.add(generator.randomValue(Collection.class, params));
            }
        });
    }

    /**
     * Unit test for {@link ListGenerator#nullableRandomValue(Parameters)}
     */
    @Test
    void testNullableRandomValue() {
        final CollectionGenerationParameters params = ListGenerator.createParameters()
                .withElementsType(Integer.class);
        final ListGenerator generator = new ListGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<List<?>> results = new HashSet<>(); 
            boolean nullList = false;
            boolean nullComponents = false;
            // We just check that there is some result variety
            while (results.size() < 100) {
                final List<?> result = generator.nullableRandomValue(params);
                results.add(result);
                if (result == null) {
                    nullList = true;
                } else {
                    for (final Object component  : result) {
                        if (component == null) {
                            nullComponents = true;
                        }
                    }
                }
            }
            assertTrue(nullList);
            assertTrue(nullComponents);
        });
        params.setNullable(false);
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<List<?>> results = new HashSet<>(); 
            boolean nullList = false;
            boolean nullComponents = false;
            // We just check that there is some result variety
            while (results.size() < 100) {
                final List<?> result = generator.nullableRandomValue(params);
                results.add(result);
                if (result == null) {
                    nullList = true;
                } else {
                    for (final Object component  : result) {
                        if (component == null) {
                            nullComponents = true;
                        }
                    }
                }
            }
            assertFalse(nullList);
            assertTrue(nullComponents);
        });
    }

    /**
     * Unit test for {@link ListGenerator#nullableRandomValue(Class, CollectionGenerationParameters)}
     */
    @Test
    void testNullableRandomTypeValue() {
        final CollectionGenerationParameters params = ListGenerator.createParameters()
                .withElementsType(Integer.class);
        final ListGenerator generator = new ListGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<List<?>> results = new HashSet<>(); 
            boolean nullList = false;
            boolean nullComponents = false;
            // We just check that there is some result variety
            while (results.size() < 100) {
                final List<?> result = generator.nullableRandomValue(List.class, params);
                results.add(result);
                if (result == null) {
                    nullList = true;
                } else {
                    for (final Object component  : result) {
                        if (component == null) {
                            nullComponents = true;
                        }
                    }
                }
            }
            assertTrue(nullList);
            assertTrue(nullComponents);
        });
        params.setNullable(false);
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<List<?>> results = new HashSet<>(); 
            boolean nullList = false;
            boolean nullComponents = false;
            // We just check that there is some result variety
            while (results.size() < 100) {
                final List<?> result = generator.nullableRandomValue(List.class, params);
                results.add(result);
                if (result == null) {
                    nullList = true;
                } else {
                    for (final Object component  : result) {
                        if (component == null) {
                            nullComponents = true;
                        }
                    }
                }
            }
            assertFalse(nullList);
            assertTrue(nullComponents);
        });
    }

    /**
     * Unit test for {@link ListGenerator#nullableRandomValue(Parameters)}
     */
    @Test
    void testNullableRandomCollectionValue() {
        final CollectionGenerationParameters params = ListGenerator.createParameters()
                .withElementsType(Integer.class);
        final ListGenerator generator = new ListGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<Collection<?>> results = new HashSet<>(); 
            boolean nullList = false;
            boolean nullComponents = false;
            // We just check that there is some result variety
            while (results.size() < 100) {
                final Collection<?> result = generator.nullableRandomValue(Collection.class, params);
                results.add(result);
                if (result == null) {
                    nullList = true;
                } else {
                    for (final Object component  : result) {
                        if (component == null) {
                            nullComponents = true;
                        }
                    }
                }
            }
            assertTrue(nullList);
            assertTrue(nullComponents);
        });
        params.setNullable(false);
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<Collection<?>> results = new HashSet<>(); 
            boolean nullList = false;
            boolean nullComponents = false;
            // We just check that there is some result variety
            while (results.size() < 100) {
                final Collection<?> result = generator.nullableRandomValue(Collection.class, params);
                results.add(result);
                if (result == null) {
                    nullList = true;
                } else {
                    for (final Object component  : result) {
                        if (component == null) {
                            nullComponents = true;
                        }
                    }
                }
            }
            assertFalse(nullList);
            assertTrue(nullComponents);
        });
    }
}
