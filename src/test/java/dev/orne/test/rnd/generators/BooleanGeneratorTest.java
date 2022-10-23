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

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code BooleanGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2021-10
 * @since 0.1
 * @see BooleanGenerator
 */
@Tag("ut")
class BooleanGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(Boolean.class));
        assertTrue(Generators.supports(boolean.class));
        assertEquals(BooleanGenerator.class, Generators.getGenerator(Boolean.class).getClass());
        assertEquals(BooleanGenerator.class, Generators.getGenerator(boolean.class).getClass());
    }

    /**
     * Unit test for {@link BooleanGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new BooleanGenerator().getPriority());
    }

    /**
     * Unit test for {@link BooleanGenerator#supports()}
     */
    @Test
    void testSupports() {
        final BooleanGenerator generator = new BooleanGenerator();
        assertTrue(generator.supports(Boolean.class));
        assertTrue(generator.supports(boolean.class));
        assertFalse(generator.supports(Boolean[].class));
        assertFalse(generator.supports(boolean[].class));
    }

    /**
     * Unit test for {@link BooleanGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final BooleanGenerator generator = new BooleanGenerator();
        assertEquals(BooleanGenerator.DEFAULT_VALUE, generator.defaultValue());
    }

    /**
     * Unit test for {@link BooleanGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final BooleanGenerator generator = new BooleanGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean trueResult = false;
            boolean falseResult = false;
            while (!trueResult && !falseResult) {
                boolean result = generator.randomValue();
                if (result) {
                    trueResult = true;
                } else {
                    falseResult = true;
                }
            }
        });
    }
}
