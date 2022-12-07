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

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.GeneratorsTestUtils;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code DurationGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see DurationGenerator
 */
@Tag("ut")
class DurationGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(Duration.class));
        assertEquals(DurationGenerator.class, Generators.getGenerator(Duration.class).getClass());
    }

    /**
     * Unit test for {@link DurationGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new DurationGenerator().getPriority());
    }

    /**
     * Unit test for {@link DurationGenerator#supports()}
     */
    @Test
    void testSupports() {
        final DurationGenerator generator = new DurationGenerator();
        assertTrue(generator.supports(Duration.class));
        assertFalse(generator.supports(Duration[].class));
    }

    /**
     * Unit test for {@link DurationGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final DurationGenerator generator = new DurationGenerator();
        assertEquals(Duration.ZERO, generator.defaultValue());
    }

    /**
     * Unit test for {@link DurationGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final DurationGenerator generator = new DurationGenerator();
        GeneratorsTestUtils.assertRandomGeneration(generator, 100, 2);
    }
}
