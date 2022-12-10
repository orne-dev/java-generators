package dev.orne.test.rnd;

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
import static org.mockito.BDDMockito.*;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code AbstractTypedGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2021-03
 * @since 0.1
 * @see AbstractTypedGenerator
 */
@Tag("ut")
class AbstractTypedGeneratorTest {

    /**
     * Unit test for {@link AbstractTypedGenerator#AbstractTypedGenerator()}
     */
    @Test
    void testTypeInferred() {
        final DirectChild generator = new DirectChild();
        assertSame(String.class, generator.getValueType());
    }

    /**
     * Unit test for {@link AbstractTypedGenerator#AbstractTypedGenerator()}
     */
    @Test
    void testTypeInferred_MultipleLevels() {
        final TypedGrandchild generator = new TypedGrandchild();
        assertSame(String.class, generator.getValueType());
    }

    /**
     * Unit test for {@link AbstractTypedGenerator#AbstractTypedGenerator()}
     */
    @Test
    void testTypeInferred_Generic() {
        assertThrows(NullPointerException.class, () -> {
            new GenericChild<>();
        });
    }

    /**
     * Unit test for {@link AbstractTypedGenerator#AbstractTypedGenerator(Class)}
     */
    @Test
    void testTypeConstructor() {
        final GenericChild<String> generator = new GenericChild<>(String.class);
        assertSame(String.class, generator.getValueType());
    }

    /**
     * Unit test for {@link AbstractTypedGenerator#supports(Class)}
     */
    @Test
    void testSupports() {
        final GenericChild<Serializable> generator = new GenericChild<>(Serializable.class);
        assertTrue(generator.supports(Serializable.class));
        assertFalse(generator.supports(String.class));
        assertFalse(generator.supports(Object.class));
        assertThrows(NullPointerException.class, () -> {
            generator.supports(null);
        });
    }

    /**
     * Unit test for {@link AbstractTypedGenerator#defaultValue(Class)}
     */
    @Test
    void testDefaultValue() {
        final AbstractTypedGenerator<?> generator = spy(new GenericChild<>(Object.class));
        final Object mockResult = new Object();
        willReturn(true).given(generator).supports(Object.class);
        willReturn(mockResult).given(generator).defaultValue();
        assertSame(mockResult, generator.defaultValue(Object.class));
        then(generator).should().supports(Object.class);
        then(generator).should().defaultValue();
    }

    /**
     * Unit test for {@link AbstractTypedGenerator#defaultValue(Class)}
     */
    @Test
    void testDefaultValue_Unsupported() {
        final AbstractTypedGenerator<?> generator = spy(new GenericChild<>(Object.class));
        willReturn(false).given(generator).supports(Object.class);
        assertThrows(UnsupportedValueTypeException.class, () -> {
            generator.defaultValue(Object.class);
        });
        then(generator).should().supports(Object.class);
        then(generator).should(never()).defaultValue();
    }

    /**
     * Unit test for {@link AbstractTypedGenerator#nullableDefaultValue()}
     */
    @Test
    void testNullableDefaultValue() {
        final AbstractTypedGenerator<?> generator = spy(new GenericChild<>(Object.class));
        assertNull(generator.nullableDefaultValue());
    }

    /**
     * Unit test for {@link AbstractTypedGenerator#randomValue(Class)}
     */
    @Test
    void testRandomValue() {
        final AbstractTypedGenerator<?> generator = spy(new GenericChild<>(Object.class));
        final Object mockResult = new Object();
        willReturn(true).given(generator).supports(Object.class);
        willReturn(mockResult).given(generator).randomValue();
        assertSame(mockResult, generator.randomValue(Object.class));
        then(generator).should().supports(Object.class);
        then(generator).should().randomValue();
    }

    /**
     * Unit test for {@link AbstractTypedGenerator#randomValue(Class)}
     */
    @Test
    void testRandomValue_Unsupported() {
        final AbstractTypedGenerator<?> generator = spy(new GenericChild<>(Object.class));
        willReturn(false).given(generator).supports(Object.class);
        assertThrows(UnsupportedValueTypeException.class, () -> {
            generator.randomValue(Object.class);
        });
        then(generator).should().supports(Object.class);
        then(generator).should(never()).randomValue();
    }

    /**
     * Unit test for {@link AbstractTypedGenerator#nullableRandomValue()}
     */
    @Test
    void testNullableRandomValue() {
        final AbstractTypedGenerator<?> generator = spy(new GenericChild<>(Object.class));
        final Object mockResult = new Object();
        willReturn(mockResult).given(generator).randomValue();
        willReturn(true).given(generator).randomNull(Object.class);
        assertNull(generator.nullableRandomValue());
        then(generator).should(times(1)).randomNull(Object.class);
        then(generator).should(never()).randomValue();
        willReturn(false).given(generator).randomNull(Object.class);
        assertSame(mockResult, generator.nullableRandomValue());
        then(generator).should(times(2)).randomNull(Object.class);
        then(generator).should(times(1)).randomValue();
    }

    /**
     * Unit test for {@link AbstractTypedGenerator#equals(Object)},
     * {@link AbstractTypedGenerator#hashCode()} and
     * {@link AbstractTypedGenerator#toString()}
     */
    @Test
    @SuppressWarnings({ "java:S5785", "unlikely-arg-type" })
    void testEqualsHashCodeToString() {
        final GenericChild<?> generator = new GenericChild<>(String.class);
        assertFalse(generator.equals(null));
        assertTrue(generator.equals(generator));
        assertFalse(generator.equals(new TestGenerator()));
        GenericChild<?> other = new GenericChild<>(String.class);
        assertTrue(generator.equals(other));
        assertEquals(generator.hashCode(), other.hashCode());
        assertEquals(generator.toString(), other.toString());
        other = new GenericChild<>(Number.class);
        assertFalse(generator.equals(other));
    }

    /**
     * Generic extension of {@code AbstractTypedGenerator} for
     * test purposes.
     * @param <T> The type of generated values
     */
    private static class GenericChild<T>
    extends AbstractTypedGenerator<T> {
        public GenericChild() {
            super();
        }
        public GenericChild(
                final @NotNull Class<T> valuesType) {
            super(valuesType);
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull T defaultValue() {
            return null;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull T randomValue() {
            return null;
        }
    }

    /**
     * Direct typed extension of {@code AbstractTypedGenerator} for
     * test purposes.
     */
    protected static class DirectChild
    extends AbstractTypedGenerator<String> {
        public static final String DEFAULT_VALUE = "defaultValue";
        public static final String RND_VALUE = "randomValue";
        /**
         * {@inheritDoc}
         */
        @Override
        public String defaultValue() {
            return DEFAULT_VALUE;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public String randomValue() {
            return RND_VALUE;
        }
    }

    private static class TypedGrandchild
    extends GenericChild<String> {}
}
