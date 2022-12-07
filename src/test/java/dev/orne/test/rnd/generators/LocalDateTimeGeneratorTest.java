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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.GeneratorsTestUtils;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code LocalDateTimeGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see LocalDateTimeGenerator
 */
@Tag("ut")
class LocalDateTimeGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(LocalDateTime.class));
        assertEquals(LocalDateTimeGenerator.class, Generators.getGenerator(LocalDateTime.class).getClass());
    }

    /**
     * Unit test for {@link LocalDateTimeGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new LocalDateTimeGenerator().getPriority());
    }

    /**
     * Unit test for {@link LocalDateTimeGenerator#supports()}
     */
    @Test
    void testSupports() {
        final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();
        assertTrue(generator.supports(LocalDateTime.class));
        assertFalse(generator.supports(LocalDateTime[].class));
    }

    /**
     * Unit test for {@link LocalDateTimeGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();
        assertEquals(
                LocalDateTime.of(
                    Generators.defaultValue(LocalDate.class),
                    Generators.defaultValue(LocalTime.class)),
                generator.defaultValue());
    }

    /**
     * Unit test for {@link LocalDateTimeGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final LocalDateTimeGenerator generator = new LocalDateTimeGenerator();
        GeneratorsTestUtils.assertRandomGeneration(generator, 200, 2);
    }
}
