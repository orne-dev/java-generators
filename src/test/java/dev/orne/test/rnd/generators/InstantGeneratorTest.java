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
import java.time.Instant;
import java.util.HashSet;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code InstantGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see InstantGenerator
 */
@Tag("ut")
class InstantGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(Instant.class));
        assertEquals(InstantGenerator.class, Generators.getGenerator(Instant.class).getClass());
    }

    /**
     * Unit test for {@link InstantGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new InstantGenerator().getPriority());
    }

    /**
     * Unit test for {@link InstantGenerator#supports()}
     */
    @Test
    void testSupports() {
        final InstantGenerator generator = new InstantGenerator();
        assertTrue(generator.supports(Instant.class));
        assertFalse(generator.supports(Instant[].class));
    }

    /**
     * Unit test for {@link InstantGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final InstantGenerator generator = new InstantGenerator();
        assertEquals(Instant.ofEpochMilli(0), generator.defaultValue());
    }

    /**
     * Unit test for {@link InstantGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final InstantGenerator generator = new InstantGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<Instant> results = new HashSet<>(); 
            // We just check that there is some result variety
            while (results.size() < 200) {
                results.add(generator.randomValue());
            }
        });
    }
}
