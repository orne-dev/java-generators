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
import java.time.YearMonth;
import java.util.HashSet;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code YearMonthGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see YearMonthGenerator
 */
@Tag("ut")
class YearMonthGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(YearMonth.class));
        assertEquals(YearMonthGenerator.class, Generators.getGenerator(YearMonth.class).getClass());
    }

    /**
     * Unit test for {@link YearMonthGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new YearMonthGenerator().getPriority());
    }

    /**
     * Unit test for {@link YearMonthGenerator#supports()}
     */
    @Test
    void testSupports() {
        final YearMonthGenerator generator = new YearMonthGenerator();
        assertTrue(generator.supports(YearMonth.class));
        assertFalse(generator.supports(YearMonth[].class));
    }

    /**
     * Unit test for {@link YearMonthGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final YearMonthGenerator generator = new YearMonthGenerator();
        assertEquals(YearMonth.of(0, Month.JANUARY), generator.defaultValue());
    }

    /**
     * Unit test for {@link YearMonthGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final YearMonthGenerator generator = new YearMonthGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<YearMonth> results = new HashSet<>(); 
            final HashSet<Integer> years = new HashSet<>(); 
            final HashSet<Month> months = new HashSet<>(); 
            // We just check that there is some result variety
            while (results.size() < 200
                    && years.size() < 100
                    && months.size() < Month.values().length) {
                final YearMonth result = generator.randomValue();
                results.add(result);
                years.add(result.getYear());
                months.add(result.getMonth());
            }
        });
    }
}
