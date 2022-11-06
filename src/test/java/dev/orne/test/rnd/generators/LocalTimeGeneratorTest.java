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
import java.time.LocalTime;
import java.util.HashSet;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code LocalTimeGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see LocalTimeGenerator
 */
@Tag("ut")
class LocalTimeGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(LocalTime.class));
        assertEquals(LocalTimeGenerator.class, Generators.getGenerator(LocalTime.class).getClass());
    }

    /**
     * Unit test for {@link LocalTimeGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new LocalTimeGenerator().getPriority());
    }

    /**
     * Unit test for {@link LocalTimeGenerator#supports()}
     */
    @Test
    void testSupports() {
        final LocalTimeGenerator generator = new LocalTimeGenerator();
        assertTrue(generator.supports(LocalTime.class));
        assertFalse(generator.supports(LocalTime[].class));
    }

    /**
     * Unit test for {@link LocalTimeGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final LocalTimeGenerator generator = new LocalTimeGenerator();
        assertEquals(LocalTime.MIDNIGHT, generator.defaultValue());
    }

    /**
     * Unit test for {@link LocalTimeGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final LocalTimeGenerator generator = new LocalTimeGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<LocalTime> results = new HashSet<>(); 
            // We just check that there is some result variety
            while (results.size() < 200) {
                results.add(generator.randomValue());
            }
        });
    }
}
