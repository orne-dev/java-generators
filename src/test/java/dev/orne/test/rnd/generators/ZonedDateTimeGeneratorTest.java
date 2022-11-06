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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashSet;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code ZonedDateTimeGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see ZonedDateTimeGenerator
 */
@Tag("ut")
class ZonedDateTimeGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(ZonedDateTime.class));
        assertEquals(ZonedDateTimeGenerator.class, Generators.getGenerator(ZonedDateTime.class).getClass());
    }

    /**
     * Unit test for {@link ZonedDateTimeGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new ZonedDateTimeGenerator().getPriority());
    }

    /**
     * Unit test for {@link ZonedDateTimeGenerator#supports()}
     */
    @Test
    void testSupports() {
        final ZonedDateTimeGenerator generator = new ZonedDateTimeGenerator();
        assertTrue(generator.supports(ZonedDateTime.class));
        assertFalse(generator.supports(ZonedDateTime[].class));
    }

    /**
     * Unit test for {@link ZonedDateTimeGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final ZonedDateTimeGenerator generator = new ZonedDateTimeGenerator();
        assertEquals(
                ZonedDateTime.of(
                        Generators.defaultValue(LocalDateTime.class),
                        Generators.defaultValue(ZoneId.class)),
                generator.defaultValue());
    }

    /**
     * Unit test for {@link ZonedDateTimeGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final ZonedDateTimeGenerator generator = new ZonedDateTimeGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<ZonedDateTime> results = new HashSet<>(); 
            final HashSet<ZoneId> zones = new HashSet<>(); 
            // We just check that there is some result variety
            while (results.size() < 200
                    && zones.size() < ZoneOffset.getAvailableZoneIds().size() / 2) {
                final ZonedDateTime result = generator.randomValue();
                results.add(result);
                zones.add(result.getZone());
            }
        });
    }
}
