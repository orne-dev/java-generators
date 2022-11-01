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
import java.util.Calendar;
import java.util.HashSet;
import java.util.TimeZone;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code CalendarGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2021-11
 * @since 0.1
 * @see CalendarGenerator
 */
@Tag("ut")
class CalendarGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(Calendar.class));
        assertEquals(CalendarGenerator.class, Generators.getGenerator(Calendar.class).getClass());
    }

    /**
     * Unit test for {@link CalendarGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new CalendarGenerator().getPriority());
    }

    /**
     * Unit test for {@link CalendarGenerator#supports()}
     */
    @Test
    void testSupports() {
        final CalendarGenerator generator = new CalendarGenerator();
        assertTrue(generator.supports(Calendar.class));
        assertFalse(generator.supports(Calendar[].class));
    }

    /**
     * Unit test for {@link CalendarGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final CalendarGenerator generator = new CalendarGenerator();
        final Calendar result = generator.defaultValue();
        assertNotNull(result);
        assertEquals(TimeZone.getDefault(), result.getTimeZone());
        assertEquals(0, result.getTimeInMillis());
        assertNotSame(result, generator.defaultValue());
    }

    /**
     * Unit test for {@link CalendarGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final CalendarGenerator generator = new CalendarGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<Calendar> results = new HashSet<>(); 
            final HashSet<TimeZone> zones = new HashSet<>(); 
            final HashSet<String> types = new HashSet<>(); 
            // We just check that there is some result variety
            while (results.size() < 100 &&
                    zones.size() < (TimeZone.getAvailableIDs().length / 2) &&
                    types.size() < (Calendar.getAvailableCalendarTypes().size() / 2)) {
                final Calendar value = generator.randomValue();
                results.add(generator.randomValue());
                zones.add(value.getTimeZone());
                types.add(value.getCalendarType());
            }
        });
    }
}
