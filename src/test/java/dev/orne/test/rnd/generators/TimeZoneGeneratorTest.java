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
import java.util.TimeZone;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code TimeZoneGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2021-10
 * @since 0.1
 * @see TimeZoneGenerator
 */
@Tag("ut")
class TimeZoneGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(TimeZone.class));
        assertEquals(TimeZoneGenerator.class, Generators.getGenerator(TimeZone.class).getClass());
    }

    /**
     * Unit test for {@link TimeZoneGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new TimeZoneGenerator().getPriority());
    }

    /**
     * Unit test for {@link TimeZoneGenerator#supports()}
     */
    @Test
    void testSupports() {
        final TimeZoneGenerator generator = new TimeZoneGenerator();
        assertTrue(generator.supports(TimeZone.class));
        assertFalse(generator.supports(TimeZone[].class));
    }

    /**
     * Unit test for {@link TimeZoneGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final TimeZoneGenerator generator = new TimeZoneGenerator();
        assertEquals(TimeZone.getDefault(), generator.defaultValue());
    }

    /**
     * Unit test for {@link TimeZoneGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final TimeZoneGenerator generator = new TimeZoneGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<TimeZone> results = new HashSet<>(); 
            // We just check that there is some result variety
            while (results.size() < (TimeZone.getAvailableIDs().length / 2)) {
                results.add(generator.randomValue());
            }
        });
    }
}
