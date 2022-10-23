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
import java.util.Locale;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code LocaleGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2021-10
 * @since 0.1
 * @see LocaleGenerator
 */
@Tag("ut")
class LocaleGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(Locale.class));
        assertEquals(LocaleGenerator.class, Generators.getGenerator(Locale.class).getClass());
    }

    /**
     * Unit test for {@link LocaleGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new LocaleGenerator().getPriority());
    }

    /**
     * Unit test for {@link LocaleGenerator#supports()}
     */
    @Test
    void testSupports() {
        final LocaleGenerator generator = new LocaleGenerator();
        assertTrue(generator.supports(Locale.class));
        assertFalse(generator.supports(Locale[].class));
    }

    /**
     * Unit test for {@link LocaleGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final LocaleGenerator generator = new LocaleGenerator();
        assertEquals(Locale.getDefault(), generator.defaultValue());
    }

    /**
     * Unit test for {@link LocaleGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final LocaleGenerator generator = new LocaleGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<Locale> results = new HashSet<>(); 
            // We just check that there is some result variety
            while (results.size() < (Locale.getAvailableLocales().length / 2)) {
                results.add(generator.randomValue());
            }
        });
    }
}
