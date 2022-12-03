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
 * Unit tests for {@code SetGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 * @see SetGenerator
 */
@Tag("ut")
class SetGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(Set.class));
        assertEquals(SetGenerator.class, Generators.getGenerator(Set.class).getClass());
    }

    /**
     * Unit test for {@link SetGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.GENERIC_GENERATORS, new SetGenerator().getPriority());
    }

    /**
     * Unit test for {@link SetGenerator#getParametersType()}
     */
    @Test
    void testParametersType() {
        assertEquals(CollectionGenerationParameters.class, new SetGenerator().getParametersType());
    }

    /**
     * Unit test for {@link SetGenerator#getExtractor()}
     */
    @Test
    void testBuiltInExtractors() {
        final ParametersExtractor<CollectionGenerationParameters> result = new SetGenerator().getExtractor();
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
     * Unit test for {@link SetGenerator#supports()}
     */
    @Test
    void testSupports() {
        final SetGenerator generator = new SetGenerator();
        assertTrue(generator.supports(Set.class));
        assertFalse(generator.supports(Set[].class));
    }

    /**
     * Unit test for {@link SetGenerator#createParameters()}
     */
    @Test
    void testCreateParameters() {
        CollectionGenerationParameters.Builder builder = SetGenerator.createParameters();
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
     * Unit test for {@link SetGenerator#defaultValue(Parameters)}
     */
    @Test
    void testDefaultValue() {
        final CollectionGenerationParameters params = SetGenerator.createParameters()
                .withElementsType(Integer.class);
        final SetGenerator generator = new SetGenerator();
        final Set<?> result = generator.defaultValue(params);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link SetGenerator#nullableDefaultValue(Parameters)}
     */
    @Test
    void testNullableDefaultValue() {
        final CollectionGenerationParameters params = SetGenerator.createParameters()
                .withElementsType(Integer.class);
        final SetGenerator generator = new SetGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean nullList = false;
            boolean nonNullList = false;
            // We just check that there is some result variety
            while (!nullList && !nonNullList) {
                final Set<?> result = generator.nullableDefaultValue(params);
                if (result == null) {
                    nullList = true;
                } else {
                    nonNullList = true;
                    assertTrue(result.isEmpty());
                }
            }
        });
        params.setNullable(false);
        final Set<?> result = generator.nullableDefaultValue(params);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link SetGenerator#randomValue(Parameters)}
     */
    @Test
    void testRandomValue() {
        final CollectionGenerationParameters params = SetGenerator.createParameters()
                .withElementsType(Integer.class);
        final SetGenerator generator = new SetGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<Set<?>> results = new HashSet<>(); 
            // We just check that there is some result variety
            while (results.size() < 100) {
                results.add(generator.randomValue(params));
            }
        });
    }

    /**
     * Unit test for {@link SetGenerator#nullableRandomValue(Parameters)}
     */
    @Test
    void testNullableRandomValue() {
        final CollectionGenerationParameters params = SetGenerator.createParameters()
                .withElementsType(Integer.class);
        final SetGenerator generator = new SetGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<Set<?>> results = new HashSet<>(); 
            boolean nullList = false;
            boolean nullComponents = false;
            // We just check that there is some result variety
            while (results.size() < 100) {
                final Set<?> result = generator.nullableRandomValue(params);
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
            final HashSet<Set<?>> results = new HashSet<>(); 
            boolean nullList = false;
            boolean nullComponents = false;
            // We just check that there is some result variety
            while (results.size() < 100) {
                final Set<?> result = generator.nullableRandomValue(params);
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
     * Unit test for {@link SetGenerator#nullableRandomValue(Class, CollectionGenerationParameters)}
     */
    @Test
    void testNullableRandomTypeValue() {
        final CollectionGenerationParameters params = SetGenerator.createParameters()
                .withElementsType(Integer.class);
        final SetGenerator generator = new SetGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<Set<?>> results = new HashSet<>(); 
            boolean nullList = false;
            boolean nullComponents = false;
            // We just check that there is some result variety
            while (results.size() < 100) {
                final Set<?> result = generator.nullableRandomValue(Set.class, params);
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
            final HashSet<Set<?>> results = new HashSet<>(); 
            boolean nullList = false;
            boolean nullComponents = false;
            // We just check that there is some result variety
            while (results.size() < 100) {
                final Set<?> result = generator.nullableRandomValue(Set.class, params);
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
