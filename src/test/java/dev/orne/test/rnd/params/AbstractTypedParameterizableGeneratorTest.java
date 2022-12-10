package dev.orne.test.rnd.params;

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

import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Unit tests for {@code AbstractTypedParameterizableGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see AbstractTypedParameterizableGenerator
 */
@Tag("ut")
class AbstractTypedParameterizableGeneratorTest {

    /**
     * Unit test for {@link AbstractTypedParameterizableGenerator#AbstractTypedParameterizableGenerator()}
     */
    @Test
    void testTypeInferred() {
        final DirectChild generator = new DirectChild();
        assertSame(MyType.class, generator.getValueType());
        assertSame(MyParams.class, generator.getParametersType());
    }

    /**
     * Unit test for {@link AbstractTypedParameterizableGenerator#AbstractTypedParameterizableGenerator()}
     */
    @Test
    void testTypeInferred_MultipleLevels() {
        final TypedGrandchild generator = new TypedGrandchild();
        assertSame(MyType.class, generator.getValueType());
        assertSame(MyParams.class, generator.getParametersType());
    }

    /**
     * Unit test for {@link AbstractTypedParameterizableGenerator#AbstractTypedParameterizableGenerator()}
     */
    @Test
    void testTypeInferred_Generic() {
        assertThrows(NullPointerException.class, () -> {
            new GenericChild<>();
        });
    }

    /**
     * Unit test for {@link AbstractTypedParameterizableGenerator#AbstractTypedParameterizableGenerator()}
     */
    @Test
    void testTypeInferred_Unsupported() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TypedUnsupported();
        });
    }

    /**
     * Unit test for {@link AbstractTypedParameterizableGenerator#AbstractTypedParameterizableGenerator(Class)}
     */
    @Test
    void testTypeConstructor() {
        final GenericChild<MyType, MyParams> generator = new GenericChild<>(MyType.class, MyParams.class);
        assertSame(MyType.class, generator.getValueType());
        assertSame(MyParams.class, generator.getParametersType());
        assertThrows(NullPointerException.class, () -> {
            new GenericChild<>(null, null);
        });
        assertThrows(NullPointerException.class, () -> {
            new GenericChild<>(null, MyParams.class);
        });
        assertThrows(NullPointerException.class, () -> {
            new GenericChild<>(MyType.class, null);
        });
    }

    /**
     * Unit test for {@link AbstractTypedParameterizableGenerator#supports(Class)}
     */
    @Test
    void testSupports() {
        final GenericChild<Serializable, MyParams> generator = new GenericChild<>(Serializable.class, MyParams.class);
        assertTrue(generator.supports(Serializable.class));
        assertFalse(generator.supports(String.class));
        assertFalse(generator.supports(Object.class));
        assertThrows(NullPointerException.class, () -> {
            generator.supports(null);
        });
    }

    /**
     * Unit test for {@link AbstractTypedParameterizableGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final DirectChild generator = spy(new DirectChild());
        final MyParams params = mock(MyParams.class);
        final MyType expected = mock(MyType.class);
        willReturn(params).given(generator).createEmptyParams();
        willReturn(expected).given(generator).defaultValue(params);
        final MyType result = generator.defaultValue();
        assertSame(expected, result);
        then(generator).should().defaultValue();
        then(generator).should().createEmptyParams();
        then(generator).should().defaultValue(params);
        then(generator).shouldHaveNoMoreInteractions();
        then(params).shouldHaveNoInteractions();
        then(expected).shouldHaveNoInteractions();
    }

    /**
     * Unit test for {@link AbstractTypedParameterizableGenerator#defaultValue(Object...)}
     */
    @Test
    void testDefaultValueSources() {
        final DirectChild generator = spy(new DirectChild());
        final MyParams params = mock(MyParams.class);
        final MyType expected = mock(MyType.class);
        final Object[] sources = new Object[] {
                spy(Object.class),
                spy(Object.class)
        };
        willReturn(params).given(generator).createParams(sources);
        willReturn(expected).given(generator).defaultValue(params);
        final MyType result = generator.defaultValue(sources);
        assertSame(expected, result);
        then(generator).should().defaultValue(sources);
        then(generator).should().createParams(sources);
        then(generator).should().defaultValue(params);
        then(generator).shouldHaveNoMoreInteractions();
        then(params).shouldHaveNoInteractions();
        then(expected).shouldHaveNoInteractions();
        for (int i = 0; i < sources.length; i++) {
            then(sources[i]).shouldHaveNoInteractions();
        }
    }

    /**
     * Unit test for {@link AbstractTypedParameterizableGenerator#defaultValue(Class, GenerationParameters)}
     */
    @Test
    void testDefaultValueTypeParams() {
        final GenericChild<Object, MyParams> generator = spy(new GenericChild<>(Object.class, MyParams.class));
        final MyParams params = mock(MyParams.class);
        final Object mockResult = new Object();
        willReturn(true).given(generator).supports(Object.class);
        willReturn(mockResult).given(generator).defaultValue(params);
        assertSame(mockResult, generator.defaultValue(Object.class, params));
        then(generator).should().supports(Object.class);
        then(generator).should().defaultValue(params);
    }

    /**
     * Unit test for {@link AbstractTypedParameterizableGenerator#nullableDefaultValue()}
     */
    @Test
    void testNullableDefaultValue() {
        final DirectChild generator = spy(new DirectChild());
        final MyParams params = mock(MyParams.class);
        final MyType expected = mock(MyType.class);
        willReturn(params).given(generator).createEmptyParams();
        willReturn(expected).given(generator).nullableDefaultValue(params);
        final MyType result = generator.nullableDefaultValue();
        assertSame(expected, result);
        then(generator).should().nullableDefaultValue();
        then(generator).should().createEmptyParams();
        then(generator).should().nullableDefaultValue(params);
        then(generator).shouldHaveNoMoreInteractions();
        then(params).shouldHaveNoInteractions();
        then(expected).shouldHaveNoInteractions();
    }

    /**
     * Unit test for {@link AbstractTypedParameterizableGenerator#nullableDefaultValue(Object...)}
     */
    @Test
    void testNullableDefaultValueSources() {
        final DirectChild generator = spy(new DirectChild());
        final MyParams params = mock(MyParams.class);
        final MyType expected = mock(MyType.class);
        final Object[] sources = new Object[] {
                spy(Object.class),
                spy(Object.class)
        };
        willReturn(params).given(generator).createParams(sources);
        willReturn(expected).given(generator).nullableDefaultValue(params);
        final MyType result = generator.nullableDefaultValue(sources);
        assertSame(expected, result);
        then(generator).should().nullableDefaultValue(sources);
        then(generator).should().createParams(sources);
        then(generator).should().nullableDefaultValue(params);
        then(generator).shouldHaveNoMoreInteractions();
        then(params).shouldHaveNoInteractions();
        then(expected).shouldHaveNoInteractions();
        for (int i = 0; i < sources.length; i++) {
            then(sources[i]).shouldHaveNoInteractions();
        }
    }

    /**
     * Unit test for {@link AbstractTypedParameterizableGenerator#nullableDefaultValue(GenerationParameters)}
     */
    @ParameterizedTest
    @ValueSource(booleans = { true, false })
    void testNullableDefaultValueGenericParams(
            final boolean randomNull) {
        final GenericChild<MyType, MyParams> generator =
                spy(new GenericChild<>(MyType.class, MyParams.class));
        final MyParams params = mock(MyParams.class);
        final MyType expected = mock(MyType.class);
        willReturn(randomNull).given(generator).randomNull(MyType.class);
        willReturn(expected).given(generator).defaultValue(params);
        final MyType result = generator.nullableDefaultValue(params);
        assertNull(result);
    }

    /**
     * Unit test for {@link AbstractTypedParameterizableGenerator#nullableDefaultValue(GenerationParameters)}
     */
    @ParameterizedTest
    @MethodSource("testNullableTests_Parameters")
    void testNullableDefaultValueParams(
            final boolean paramsNullable,
            final boolean randomNull) {
        final GenericChild<MyType, MyNullableParams> generator =
                spy(new GenericChild<>(MyType.class, MyNullableParams.class));
        final MyNullableParams params = mock(MyNullableParams.class);
        willReturn(paramsNullable).given(params).isNullable();
        final MyType expected = mock(MyType.class);
        willReturn(randomNull).given(generator).randomNull(MyType.class);
        willReturn(expected).given(generator).defaultValue(params);
        final MyType result = generator.nullableDefaultValue(params);
        if (paramsNullable) {
            assertNull(result);
        } else {
            assertSame(expected, result);
        }
    }

    /**
     * Unit test for {@link AbstractTypedParameterizableGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final DirectChild generator = spy(new DirectChild());
        final MyParams params = mock(MyParams.class);
        final MyType expected = mock(MyType.class);
        willReturn(params).given(generator).createEmptyParams();
        willReturn(expected).given(generator).randomValue(params);
        final MyType result = generator.randomValue();
        assertSame(expected, result);
        then(generator).should().randomValue();
        then(generator).should().createEmptyParams();
        then(generator).should().randomValue(params);
        then(generator).shouldHaveNoMoreInteractions();
        then(params).shouldHaveNoInteractions();
        then(expected).shouldHaveNoInteractions();
    }

    /**
     * Unit test for {@link AbstractTypedParameterizableGenerator#randomValue(Object...)}
     */
    @Test
    void testRandomValueSources() {
        final DirectChild generator = spy(new DirectChild());
        final MyParams params = mock(MyParams.class);
        final MyType expected = mock(MyType.class);
        final Object[] sources = new Object[] {
                spy(Object.class),
                spy(Object.class)
        };
        willReturn(params).given(generator).createParams(sources);
        willReturn(expected).given(generator).randomValue(params);
        final MyType result = generator.randomValue(sources);
        assertSame(expected, result);
        then(generator).should().randomValue(sources);
        then(generator).should().createParams(sources);
        then(generator).should().randomValue(params);
        then(generator).shouldHaveNoMoreInteractions();
        then(params).shouldHaveNoInteractions();
        then(expected).shouldHaveNoInteractions();
        for (int i = 0; i < sources.length; i++) {
            then(sources[i]).shouldHaveNoInteractions();
        }
    }

    /**
     * Unit test for {@link AbstractTypedParameterizableGenerator#randomValue(Class, GenerationParameters)}
     */
    @Test
    void testRandomValueTypeParams() {
        final GenericChild<Object, MyParams> generator = spy(new GenericChild<>(Object.class, MyParams.class));
        final MyParams params = mock(MyParams.class);
        final Object mockResult = new Object();
        willReturn(true).given(generator).supports(Object.class);
        willReturn(mockResult).given(generator).randomValue(params);
        assertSame(mockResult, generator.randomValue(Object.class, params));
        then(generator).should().supports(Object.class);
        then(generator).should().randomValue(params);
    }

    /**
     * Unit test for {@link AbstractTypedParameterizableGenerator#nullableRandomValue()}
     */
    @Test
    void testNullableRandomValue() {
        final DirectChild generator = spy(new DirectChild());
        final MyParams params = mock(MyParams.class);
        final MyType expected = mock(MyType.class);
        willReturn(params).given(generator).createEmptyParams();
        willReturn(expected).given(generator).nullableRandomValue(params);
        final MyType result = generator.nullableRandomValue();
        assertSame(expected, result);
        then(generator).should().nullableRandomValue();
        then(generator).should().createEmptyParams();
        then(generator).should().nullableRandomValue(params);
        then(generator).shouldHaveNoMoreInteractions();
        then(params).shouldHaveNoInteractions();
        then(expected).shouldHaveNoInteractions();
    }

    /**
     * Unit test for {@link AbstractTypedParameterizableGenerator#nullableRandomValue(Object...)}
     */
    @Test
    void testNullableRandomValueSources() {
        final DirectChild generator = spy(new DirectChild());
        final MyParams params = mock(MyParams.class);
        final MyType expected = mock(MyType.class);
        final Object[] sources = new Object[] {
                spy(Object.class),
                spy(Object.class)
        };
        willReturn(params).given(generator).createParams(sources);
        willReturn(expected).given(generator).nullableRandomValue(params);
        final MyType result = generator.nullableRandomValue(sources);
        assertSame(expected, result);
        then(generator).should().nullableRandomValue(sources);
        then(generator).should().createParams(sources);
        then(generator).should().nullableRandomValue(params);
        then(generator).shouldHaveNoMoreInteractions();
        then(params).shouldHaveNoInteractions();
        then(expected).shouldHaveNoInteractions();
        for (int i = 0; i < sources.length; i++) {
            then(sources[i]).shouldHaveNoInteractions();
        }
    }

    /**
     * Unit test for {@link AbstractTypedParameterizableGenerator#nullableRandomValue(GenerationParameters)}
     */
    @ParameterizedTest
    @ValueSource(booleans = { true, false })
    void testNullableRandomValueGenericParams(
            final boolean randomNull) {
        final GenericChild<MyType, MyParams> generator =
                spy(new GenericChild<>(MyType.class, MyParams.class));
        final MyParams params = mock(MyParams.class);
        final MyType expected = mock(MyType.class);
        willReturn(randomNull).given(generator).randomNull(MyType.class);
        willReturn(expected).given(generator).randomValue(params);
        final MyType result = generator.nullableRandomValue(params);
        if (randomNull) {
            assertNull(result);
        } else {
            assertSame(expected, result);
        }
    }

    /**
     * Unit test for {@link AbstractTypedParameterizableGenerator#nullableRandomValue(GenerationParameters)}
     */
    @ParameterizedTest
    @MethodSource("testNullableTests_Parameters")
    void testNullableRandomValueParams(
            final boolean paramsNullable,
            final boolean randomNull) {
        final GenericChild<MyType, MyNullableParams> generator =
                spy(new GenericChild<>(MyType.class, MyNullableParams.class));
        final MyNullableParams params = mock(MyNullableParams.class);
        willReturn(paramsNullable).given(params).isNullable();
        final MyType expected = mock(MyType.class);
        willReturn(randomNull).given(generator).randomNull(MyType.class);
        willReturn(expected).given(generator).randomValue(params);
        final MyType result = generator.nullableRandomValue(params);
        if (paramsNullable && randomNull) {
            assertNull(result);
        } else {
            assertSame(expected, result);
        }
    }

    /**
     * Arguments for {@link #testNullableDefaultValueParams}
     * and {@link #testNullableRandomValueParams}
     */
    private static Stream<Arguments> testNullableTests_Parameters() {
        return Stream.of(
                Arguments.of(true, true),
                Arguments.of(true, false),
                Arguments.of(false, true),
                Arguments.of(false, false)
        );
    }

    /**
     * Unit test for {@link AbstractTypedParameterizableGenerator#equals(Object)},
     * {@link AbstractTypedParameterizableGenerator#hashCode()} and
     * {@link AbstractTypedParameterizableGenerator#toString()}
     */
    @Test
    @SuppressWarnings("java:S5785")
    void testEqualsHashCodeToString() {
        final DirectChild generator = new DirectChild();
        assertFalse(generator.equals(null));
        assertTrue(generator.equals(generator));
        assertFalse(generator.equals(new Object()));
        final DirectChild other = new DirectChild();
        assertTrue(generator.equals(other));
        assertEquals(generator.hashCode(), other.hashCode());
        assertEquals(generator.toString(), other.toString());
    }

    private interface MyType {}
    private class MyParams
    implements GenerationParameters {}
    private class MyNullableParams
    extends NullableParametersImpl {}

    /**
     * Generic extension of {@code AbstractTypedParameterizableGenerator} for
     * test purposes.
     * @param <T> The type of generated values
     * @param <P> The type of parameters
     */
    private static class GenericChild<
        T,
        P extends GenerationParameters>
    extends AbstractTypedParameterizableGenerator<T, P> {
        public GenericChild() {
            super();
        }
        public GenericChild(
                final @NotNull Class<T> valuesType,
                final @NotNull Class<P> paramsType) {
            super(valuesType, paramsType);
        }
        @Override
        public @NotNull T defaultValue(
                final @NotNull P parameters) {
            return null;
        }
        @Override
        public @NotNull T randomValue(
                final @NotNull P parameters) {
            return null;
        }
    }

    /**
     * Direct typed extension of {@code AbstractTypedParameterizableGenerator} for
     * test purposes.
     */
    protected static class DirectChild
    extends AbstractTypedParameterizableGenerator<MyType, MyParams> {
        @Override
        public @NotNull MyType defaultValue(
                final @NotNull MyParams parameters) {
            return null;
        }
        @Override
        public @NotNull MyType randomValue(
                final @NotNull MyParams parameters) {
            return null;
        }
    }

    private static class TypedGrandchild
    extends GenericChild<MyType, MyParams> {}

    private static class TypedUnsupported
    extends GenericChild<List<String>[], MyParams> {}
}
