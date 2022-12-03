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
import dev.orne.test.rnd.params.StringGenerationParameters;

/**
 * Unit tests for {@code StringGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 * @see StringGenerator
 */
@Tag("ut")
class StringGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(String.class));
        assertTrue(Generators.supports(CharSequence.class));
        assertEquals(StringGenerator.class, Generators.getGenerator(String.class).getClass());
        assertEquals(StringGenerator.class, Generators.getGenerator(CharSequence.class).getClass());
    }

    /**
     * Unit test for {@link StringGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new StringGenerator().getPriority());
    }

    /**
     * Unit test for {@link StringGenerator#supports()}
     */
    @Test
    void testSupports() {
        final StringGenerator generator = new StringGenerator();
        assertTrue(generator.supports(String.class));
        assertTrue(generator.supports(CharSequence.class));
        assertFalse(generator.supports(String[].class));
        assertFalse(generator.supports(CharSequence[].class));
    }

    /**
     * Unit test for {@link StringGenerator#defaultValue(StringGenerationParameters)}
     */
    @Test
    void testDefaultValue() {
        final StringGenerationParameters params = StringGenerator.createParameters();
        final StringGenerator generator = new StringGenerator();
        assertEquals(StringGenerator.DEFAULT_VALUE, generator.defaultValue(params));
    }

    /**
     * Unit test for {@link StringGenerator#nullableDefaultValue(StringGenerationParameters)}
     */
    @Test
    void testNullableDefaultValue() {
        final StringGenerationParameters params = StringGenerator.createParameters();
        final StringGenerator generator = new StringGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean nullValues = false;
            boolean nonNullValues = false;
            // We just check that there is some result variety
            while (!nullValues && !nonNullValues) {
                final String value = generator.nullableDefaultValue(params);
                if (value == null) {
                    nullValues = true;
                } else {
                    nonNullValues = true;
                    assertEquals(StringGenerator.DEFAULT_VALUE, value);
                }
            }
        });
        generator.setNullProbability(1);
        assertNull(generator.nullableDefaultValue(params));
        params.setNullable(false);
        assertEquals(StringGenerator.DEFAULT_VALUE, generator.nullableDefaultValue(params));
    }

    /**
     * Unit test for {@link StringGenerator#randomValue(StringGenerationParameters)}
     */
    @Test
    void testRandomValue() {
        final StringGenerationParameters params = StringGenerator.createParameters();
        final StringGenerator generator = new StringGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<String> results = new HashSet<>(); 
            // We just check that there is some result variety
            while (results.size() < 100) {
                final String value = generator.randomValue(params);
                assertTrue(StringGenerator.MIN_SIZE <= value.length());
                assertTrue(StringGenerator.MAX_SIZE >= value.length());
                results.add(value);
            }
        });
        params.setMinSize(10);
        params.setMaxSize(20);
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<String> results = new HashSet<>(); 
            // We just check that there is some result variety
            while (results.size() < 100) {
                final String value = generator.randomValue(params);
                assertTrue(params.getMinSize() <= value.length());
                assertTrue(params.getMaxSize() >= value.length());
                results.add(value);
            }
        });
    }

    /**
     * Unit test for {@link StringGenerator#nullableRandomValue(StringGenerationParameters)}
     */
    @Test
    void testNullableRandomValue() {
        final StringGenerationParameters params = StringGenerator.createParameters();
        final StringGenerator generator = new StringGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<String> results = new HashSet<>();
            boolean nullValues = false;
            // We just check that there is some result variety
            while (results.size() < 100) {
                final String value = generator.nullableRandomValue(params);
                if (value == null) {
                    nullValues = true;
                } else {
                    assertTrue(StringGenerator.MIN_SIZE <= value.length());
                    assertTrue(StringGenerator.MAX_SIZE >= value.length());
                    results.add(value);
                }
            }
            assertTrue(nullValues);
        });
        params.setNullable(false);
        params.setMinSize(10);
        params.setMaxSize(20);
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<String> results = new HashSet<>(); 
            boolean nullValues = false;
            // We just check that there is some result variety
            while (results.size() < 100) {
                final String value = generator.nullableRandomValue(params);
                if (value == null) {
                    nullValues = true;
                } else {
                    assertTrue(params.getMinSize() <= value.length());
                    assertTrue(params.getMaxSize() >= value.length());
                    results.add(value);
                }
            }
            assertFalse(nullValues);
        });
    }
}
