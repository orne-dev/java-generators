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
import static org.mockito.BDDMockito.*;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.UnsupportedValueTypeException;

/**
 * Unit tests for {@code AbstractPrimitiveGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 * @see AbstractPrimitiveGenerator
 */
@Tag("ut")
class AbstractPrimitiveGeneratorTest {

    /**
     * Unit test for {@link AbstractPrimitiveGenerator#AbstractPrimitiveGenerator()}
     */
    @Test
    void testConstructor_Infer() {
        final TestInferredGenerator result = new TestInferredGenerator();
        assertSame(Long.class, result.getValueType());
    }

    /**
     * Unit test for {@link AbstractPrimitiveGenerator#AbstractPrimitiveGenerator(Class)}
     */
    @Test
    void testConstructor_Type() {
        final TestGenerator<Long> result = new TestGenerator<>(Long.class);
        assertSame(Long.class, result.getValueType());
    }

    /**
     * Unit test for {@link AbstractPrimitiveGenerator#supports(Class)}
     */
    @Test
    void testSupports() {
        assertTrue(new TestGenerator<>(Boolean.class).supports(Boolean.class));
        assertTrue(new TestGenerator<>(Boolean.class).supports(boolean.class));
        assertTrue(new TestGenerator<>(Byte.class).supports(Byte.class));
        assertTrue(new TestGenerator<>(Byte.class).supports(byte.class));
        assertTrue(new TestGenerator<>(Short.class).supports(Short.class));
        assertTrue(new TestGenerator<>(Short.class).supports(short.class));
        assertTrue(new TestGenerator<>(Integer.class).supports(Integer.class));
        assertTrue(new TestGenerator<>(Integer.class).supports(int.class));
        assertTrue(new TestGenerator<>(Long.class).supports(Long.class));
        assertTrue(new TestGenerator<>(Long.class).supports(long.class));
        assertTrue(new TestGenerator<>(Float.class).supports(Float.class));
        assertTrue(new TestGenerator<>(Float.class).supports(float.class));
        assertTrue(new TestGenerator<>(Double.class).supports(Double.class));
        assertTrue(new TestGenerator<>(Double.class).supports(double.class));
        assertTrue(new TestGenerator<>(Character.class).supports(Character.class));
        assertTrue(new TestGenerator<>(Character.class).supports(char.class));
    }

    /**
     * Unit test for {@link AbstractPrimitiveGenerator#nullableDefaultValue(Class)}
     */
    @Test
    void testDefaultValue() {
        final TestGenerator<Integer> generator = spy(new TestGenerator<>(Integer.class));
        final Integer mockValue = RandomUtils.nextInt();
        willReturn(mockValue).given(generator).defaultValue();
        assertThrows(UnsupportedValueTypeException.class, () -> {
            generator.nullableDefaultValue(Long.class);
        });
        assertThrows(UnsupportedValueTypeException.class, () -> {
            generator.nullableDefaultValue(long.class);
        });
        then(generator).should(never()).defaultValue();
        assertNull(generator.nullableDefaultValue(Integer.class));
        then(generator).should(never()).defaultValue();
        assertEquals(mockValue, generator.nullableDefaultValue(int.class));
        then(generator).should(times(1)).defaultValue();
    }

    /**
     * Unit test for {@link AbstractPrimitiveGenerator#randomNull(Class)}
     */
    @Test
    void testRandomNull() {
        final TestGenerator<Integer> generator = spy(new TestGenerator<>(Integer.class));
        generator.setNullProbability(1f);
        assertTrue(generator.randomNull(Integer.class));
        assertFalse(generator.randomNull(int.class));
        generator.setNullProbability(0f);
        assertFalse(generator.randomNull(Integer.class));
        assertFalse(generator.randomNull(int.class));
    }

    private static class TestGenerator<T>
    extends AbstractPrimitiveGenerator<T> {
        protected TestGenerator() {
            super();
        }
        public TestGenerator(
                final @NotNull Class<T> valueType) {
            super(valueType);
        }
        @Override
        public @NotNull T defaultValue() {
            throw new AssertionError("Mock expected");
        }
        @Override
        public @NotNull T randomValue() {
            throw new AssertionError("Mock expected");
        }
    }
    private static class TestInferredGenerator
    extends TestGenerator<Long> {}
}
