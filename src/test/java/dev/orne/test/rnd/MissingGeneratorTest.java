package dev.orne.test.rnd;

/*-
 * #%L
 * Orne Test Generators
 * %%
 * Copyright (C) 2023 Orne Developments
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

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code MissingGenerator}.
 * 
 * @author <a href="https://github.com/ihernaez">(w) Iker Hernaez</a>
 * @version 1.0, 2023-11
 * @since 0.2
 * @see MissingGenerator
 */
@Tag("ut")
class MissingGeneratorTest {

    /**
     * Test for {@link MissingGenerator#supports(Class)}.
     */
    @Test
    void testMissingGenerator_Support() {
        assertFalse(Generator.MISSING.supports(Object.class));
    }

    /**
     * Test for {@link MissingGenerator#defaultValue(Class)}.
     */
    @Test
    void testMissingGenerator_DefaultValue() {
        assertThrows(GeneratorNotFoundException.class, () -> {
            Generator.MISSING.defaultValue(Object.class);
        });
    }

    /**
     * Test for {@link MissingGenerator#nullableDefaultValue(Class)}.
     */
    @Test
    void testMissingGenerator_NullableDefaultValue() {
        assertThrows(GeneratorNotFoundException.class, () -> {
            Generator.MISSING.nullableDefaultValue(Object.class);
        });
    }

    /**
     * Test for {@link MissingGenerator#randomValue(Class)}.
     */
    @Test
    void testMissingGenerator_RandomValue() {
        assertThrows(GeneratorNotFoundException.class, () -> {
            Generator.MISSING.randomValue(Object.class);
        });
    }

    /**
     * Test for {@link MissingGenerator#nullableRandomValue(Class)}.
     */
    @Test
    void testMissingGenerator_NullableRandomValue() {
        assertThrows(GeneratorNotFoundException.class, () -> {
            Generator.MISSING.nullableRandomValue(Object.class);
        });
    }
}
