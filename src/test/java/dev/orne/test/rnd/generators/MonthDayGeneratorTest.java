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
import java.time.Month;
import java.time.MonthDay;
import java.util.HashSet;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code MonthDayGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 * @see MonthDayGenerator
 */
@Tag("ut")
class MonthDayGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(MonthDay.class));
        assertEquals(MonthDayGenerator.class, Generators.getGenerator(MonthDay.class).getClass());
    }

    /**
     * Unit test for {@link MonthDayGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new MonthDayGenerator().getPriority());
    }

    /**
     * Unit test for {@link MonthDayGenerator#supports()}
     */
    @Test
    void testSupports() {
        final MonthDayGenerator generator = new MonthDayGenerator();
        assertTrue(generator.supports(MonthDay.class));
        assertFalse(generator.supports(MonthDay[].class));
    }

    /**
     * Unit test for {@link MonthDayGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final MonthDayGenerator generator = new MonthDayGenerator();
        assertEquals(MonthDay.of(Month.JANUARY, 1), generator.defaultValue());
    }

    /**
     * Unit test for {@link MonthDayGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final MonthDayGenerator generator = new MonthDayGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<MonthDay> results = new HashSet<>(); 
            final HashSet<Month> months = new HashSet<>(); 
            final HashSet<Integer> days = new HashSet<>(); 
            // We just check that there is some result variety
            while (results.size() < 200
                    && months.size() < Month.values().length
                    && days.size() < 31) {
                final MonthDay result = generator.randomValue();
                results.add(result);
                months.add(result.getMonth());
                days.add(result.getDayOfMonth());
            }
        });
    }
}
