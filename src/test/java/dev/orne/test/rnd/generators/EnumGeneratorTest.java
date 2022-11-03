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
import java.util.EnumSet;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;
import dev.orne.test.rnd.UnsupportedValueTypeException;

/**
 * Unit tests for {@code EnumGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 * @see EnumGenerator
 */
@Tag("ut")
class EnumGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(TestEnum.class));
        assertEquals(EnumGenerator.class, Generators.getGenerator(TestEnum.class).getClass());
    }

    /**
     * Unit test for {@link EnumGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.GENERIC_GENERATORS, new EnumGenerator().getPriority());
    }

    /**
     * Unit test for {@link EnumGenerator#supports()}
     */
    @Test
    void testSupports() {
        final EnumGenerator generator = new EnumGenerator();
        assertFalse(generator.supports(Enum.class));
        assertFalse(generator.supports(EmptyEnum.class));
        assertTrue(generator.supports(TestEnum.class));
        assertFalse(generator.supports(Enum[].class));
        assertFalse(generator.supports(EmptyEnum[].class));
        assertFalse(generator.supports(TestEnum[].class));
    }

    /**
     * Unit test for {@link EnumGenerator#defaultValue(Class)}
     */
    @Test
    void testDefaultValue() {
        final EnumGenerator generator = new EnumGenerator();
        assertThrows(UnsupportedValueTypeException.class, () -> {
            generator.defaultValue(Enum.class);
        });
        assertThrows(UnsupportedValueTypeException.class, () -> {
            generator.defaultValue(EmptyEnum.class);
        });
        assertEquals(TestEnum.VALUEA, generator.defaultValue(TestEnum.class));
    }

    /**
     * Unit test for {@link EnumGenerator#randomValue(Class)}
     */
    @Test
    void testRandomValue() {
        final EnumGenerator generator = new EnumGenerator();
        assertThrows(UnsupportedValueTypeException.class, () -> {
            generator.randomValue(Enum.class);
        });
        assertThrows(UnsupportedValueTypeException.class, () -> {
            generator.randomValue(EmptyEnum.class);
        });
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final EnumSet<TestEnum> results = EnumSet.noneOf(TestEnum.class); 
            // We check that returns all the possible values
            while (results.size() < TestEnum.values().length) {
                results.add(generator.randomValue(TestEnum.class));
            }
        });
    }

    /**
     * Unit test for {@link EnumGenerator#randomEnumValue(Class)}
     */
    @SuppressWarnings("unchecked")
    @Test
    void testRandomEnumValue() {
        assertThrows(NullPointerException.class, () -> {
            EnumGenerator.randomEnumValue(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            EnumGenerator.randomEnumValue(Enum.class);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            EnumGenerator.randomEnumValue(EmptyEnum.class);
        });
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final EnumSet<TestEnum> results = EnumSet.noneOf(TestEnum.class); 
            // We check that returns all the possible values
            while (results.size() < TestEnum.values().length) {
                results.add(EnumGenerator.randomEnumValue(TestEnum.class));
            }
        });
    }

    private static enum EmptyEnum {}
    private static enum TestEnum {
        VALUEA, VALUEB, VALUEC, VALUED;
    }
}
