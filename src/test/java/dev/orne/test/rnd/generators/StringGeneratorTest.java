package dev.orne.test.rnd.generators;

/*-
 * #%L
 * Orne Test Generators
 * %%
 * Copyright (C) 2021 Orne Developments
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
 * Unit tests for {@code StringGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2021-10
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
     * Unit test for {@link StringGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final StringGenerator generator = new StringGenerator();
        assertEquals(StringGenerator.DEFAULT_VALUE, generator.defaultValue());
    }

    /**
     * Unit test for {@link StringGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final StringGenerator generator = new StringGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<String> results = new HashSet<>(); 
            // We just check that there is some result variety
            while (results.size() < 100) {
                final String value = generator.randomValue();
                assertEquals(StringGenerator.RANDOM_LENGTH, value.length());
                results.add(value);
            }
        });
    }
}
