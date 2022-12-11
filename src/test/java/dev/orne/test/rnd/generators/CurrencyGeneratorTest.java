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

import java.util.Currency;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.GeneratorsTestUtils;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code CurrencyGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 * @see CurrencyGenerator
 */
@Tag("ut")
class CurrencyGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(Currency.class));
        assertEquals(CurrencyGenerator.class, Generators.getGenerator(Currency.class).getClass());
    }

    /**
     * Unit test for {@link CurrencyGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new CurrencyGenerator().getPriority());
    }

    /**
     * Unit test for {@link CurrencyGenerator#supports()}
     */
    @Test
    void testSupports() {
        final CurrencyGenerator generator = new CurrencyGenerator();
        assertTrue(generator.supports(Currency.class));
        assertFalse(generator.supports(Currency[].class));
    }

    /**
     * Unit test for {@link CurrencyGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final CurrencyGenerator generator = new CurrencyGenerator();
        assertEquals(CurrencyGenerator.DEFAULT, generator.defaultValue());
    }

    /**
     * Unit test for {@link CurrencyGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final CurrencyGenerator generator = new CurrencyGenerator();
        GeneratorsTestUtils.assertRandomGeneration(
                generator,
                Currency.getAvailableCurrencies().size() / 2,
                2);
    }
}
