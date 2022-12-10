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

import java.math.BigDecimal;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.GeneratorsTestUtils;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code BigDecimalGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 * @see BigDecimalGenerator
 */
@Tag("ut")
class BigDecimalGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(BigDecimal.class));
        assertTrue(Generators.supports(Number.class));
        assertEquals(BigDecimalGenerator.class, Generators.getGenerator(BigDecimal.class).getClass());
        assertEquals(BigDecimalGenerator.class, Generators.getGenerator(Number.class).getClass());
    }

    /**
     * Unit test for {@link BigDecimalGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new BigDecimalGenerator().getPriority());
    }

    /**
     * Unit test for {@link BigDecimalGenerator#supports()}
     */
    @Test
    void testSupports() {
        final BigDecimalGenerator generator = new BigDecimalGenerator();
        assertTrue(generator.supports(BigDecimal.class));
        assertTrue(generator.supports(Number.class));
        assertFalse(generator.supports(BigDecimal[].class));
        assertFalse(generator.supports(Number[].class));
    }

    /**
     * Unit test for {@link BigDecimalGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final BigDecimalGenerator generator = new BigDecimalGenerator();
        assertEquals(BigDecimalGenerator.DEFAULT_VALUE, generator.defaultValue());
    }

    /**
     * Unit test for {@link BigDecimalGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final BigDecimalGenerator generator = new BigDecimalGenerator();
        GeneratorsTestUtils.assertRandomGeneration(generator, 100, 2);
    }
}
