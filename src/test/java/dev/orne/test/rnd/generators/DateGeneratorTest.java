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

import java.util.Date;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.GeneratorsTestUtils;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code DateGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 * @see DateGenerator
 */
@Tag("ut")
class DateGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(Date.class));
        assertEquals(DateGenerator.class, Generators.getGenerator(Date.class).getClass());
    }

    /**
     * Unit test for {@link DateGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new DateGenerator().getPriority());
    }

    /**
     * Unit test for {@link DateGenerator#supports()}
     */
    @Test
    void testSupports() {
        final DateGenerator generator = new DateGenerator();
        assertTrue(generator.supports(Date.class));
        assertFalse(generator.supports(Date[].class));
    }

    /**
     * Unit test for {@link DateGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final DateGenerator generator = new DateGenerator();
        final Date result = generator.defaultValue();
        assertNotNull(result);
        assertEquals(0, result.getTime());
        assertNotSame(result, generator.defaultValue());
    }

    /**
     * Unit test for {@link DateGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final DateGenerator generator = new DateGenerator();
        GeneratorsTestUtils.assertRandomGeneration(generator, 100, 2);
    }
}
