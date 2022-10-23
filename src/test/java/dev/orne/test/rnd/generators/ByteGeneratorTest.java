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
 * Unit tests for {@code ByteGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2021-10
 * @since 0.1
 * @see ByteGenerator
 */
@Tag("ut")
class ByteGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(Byte.class));
        assertTrue(Generators.supports(byte.class));
        assertEquals(ByteGenerator.class, Generators.getGenerator(Byte.class).getClass());
        assertEquals(ByteGenerator.class, Generators.getGenerator(byte.class).getClass());
    }

    /**
     * Unit test for {@link ByteGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new ByteGenerator().getPriority());
    }

    /**
     * Unit test for {@link ByteGenerator#supports()}
     */
    @Test
    void testSupports() {
        final ByteGenerator generator = new ByteGenerator();
        assertTrue(generator.supports(Byte.class));
        assertTrue(generator.supports(byte.class));
        assertFalse(generator.supports(Byte[].class));
        assertFalse(generator.supports(byte[].class));
    }

    /**
     * Unit test for {@link ByteGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final ByteGenerator generator = new ByteGenerator();
        assertEquals(ByteGenerator.DEFAULT_VALUE, generator.defaultValue());
    }

    /**
     * Unit test for {@link ByteGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final ByteGenerator generator = new ByteGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<Byte> results = new HashSet<>(); 
            // We just check that there is some result variety
            while (results.size() < 100) {
                results.add(generator.randomValue());
            }
        });
    }
}
