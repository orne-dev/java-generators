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
 * Unit tests for {@code CharacterGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2021-10
 * @since 0.1
 * @see CharacterGenerator
 */
@Tag("ut")
class CharacterGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(Character.class));
        assertTrue(Generators.supports(char.class));
        assertEquals(CharacterGenerator.class, Generators.getGenerator(Character.class).getClass());
        assertEquals(CharacterGenerator.class, Generators.getGenerator(char.class).getClass());
    }

    /**
     * Unit test for {@link CharacterGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new CharacterGenerator().getPriority());
    }

    /**
     * Unit test for {@link CharacterGenerator#supports()}
     */
    @Test
    void testSupports() {
        final CharacterGenerator generator = new CharacterGenerator();
        assertTrue(generator.supports(Character.class));
        assertTrue(generator.supports(char.class));
        assertFalse(generator.supports(Character[].class));
        assertFalse(generator.supports(char[].class));
    }

    /**
     * Unit test for {@link CharacterGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final CharacterGenerator generator = new CharacterGenerator();
        assertEquals(CharacterGenerator.DEFAULT_VALUE, generator.defaultValue());
    }

    /**
     * Unit test for {@link CharacterGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final CharacterGenerator generator = new CharacterGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<Character> results = new HashSet<>(); 
            // We just check that there is some result variety
            while (results.size() < 100) {
                results.add(generator.randomValue());
            }
        });
    }
}
