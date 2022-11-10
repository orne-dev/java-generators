package dev.orne.test.rnd;

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

import java.util.stream.Stream;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Unit tests for {@code AbstractParametrizableGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see AbstractParametrizableGenerator
 */
@Tag("ut")
class AbstractParametrizableGeneratorTest {

    /**
     * Unit test for {@link AbstractParametrizableGenerator#AbstractParametrizableGenerator()}
     */
    @Test
    void testTypeInferred() {
        final DirectChild generator = new DirectChild();
        assertSame(MyParams.class, generator.getParametersType());
    }

    /**
     * Unit test for {@link AbstractParametrizableGenerator#AbstractParametrizableGenerator()}
     */
    @Test
    void testTypeInferred_MultipleLevels() {
        final TypedGrandchild generator = new TypedGrandchild();
        assertSame(MyParams.class, generator.getParametersType());
    }

    /**
     * Unit test for {@link AbstractParametrizableGenerator#AbstractParametrizableGenerator()}
     */
    @Test
    void testTypeInferred_Generic() {
        assertThrows(NullPointerException.class, () -> {
            new GenericChild<>();
        });
    }

    /**
     * Unit test for {@link AbstractParametrizableGenerator#AbstractParametrizableGenerator(Class)}
     */
    @Test
    void testTypeConstructor() {
        final GenericChild<MyParams> generator = new GenericChild<>(MyParams.class);
        assertSame(MyParams.class, generator.getParametersType());
    }

    /**
     * Unit test for {@link AbstractParametrizableGenerator#getExtractor()}
     */
    @Test
    void testGetExtractor() {
        final AbstractParametrizableGenerator<?> generator = spy(new GenericChild<>(MyParams.class));
        final ParametersExtractor<MyParams> expected = ParametersExtractors.getExtractor(MyParams.class);
        assertSame(expected, generator.getExtractor());
        ParametersExtractors.reset();
        final ParametersExtractor<MyParams> newExpected = ParametersExtractors.getExtractor(MyParams.class);
        assertNotSame(expected, generator.getExtractor());
        assertSame(newExpected, generator.getExtractor());
    }

    /**
     * Unit test for {@link AbstractParametrizableGenerator#createEmptyParams()}
     */
    @Test
    void testCreateEmptyParams() {
        final AbstractParametrizableGenerator<?> generator = spy(new GenericChild<>(MyParamsImpl.class));
        assertNotNull(generator.createEmptyParams());
        final AbstractParametrizableGenerator<?> ifaceParamsGen = spy(new GenericChild<>(MyParams.class));
        assertThrows(IllegalArgumentException.class, () -> {
            ifaceParamsGen.createEmptyParams();
        });
        final AbstractParametrizableGenerator<?> privateParamsGen = spy(new GenericChild<>(MyIllegalParamsImpl.class));
        assertThrows(IllegalArgumentException.class, () -> {
            privateParamsGen.createEmptyParams();
        });
    }

    /**
     * Unit test for {@link AbstractParametrizableGenerator#createParams(Object...)}
     */
    @Test
    void testCreateParams() {
        final DirectChild generator = spy(new DirectChild());
        final MyParams params = mock(MyParams.class);
        willReturn(params).given(generator).createEmptyParams();
        @SuppressWarnings("unchecked")
        final ParametersExtractor<? super MyParams> extractor = spy(ParametersExtractor.class);
        willReturn(extractor).given(generator).getExtractor();
        final Object[] sources = new Object[] {
                spy(Object.class),
                spy(Object.class)
        };
        final MyParams result = generator.createParams(sources);
        assertSame(params, result);
        then(generator).should().createParams(sources);
        then(generator).should().createEmptyParams();
        then(generator).should().getExtractor();
        then(generator).shouldHaveNoMoreInteractions();
        then(extractor).should().extractParameters(params, sources);
        then(extractor).shouldHaveNoMoreInteractions();
    }

    /**
     * Unit test for {@link AbstractParametrizableGenerator#defaultValue(Class)}
     */
    @Test
    void testDefaultValue() {
        final DirectChild generator = spy(new DirectChild());
        final MyParams params = mock(MyParams.class);
        final MyType expected = mock(MyType.class);
        willReturn(params).given(generator).createEmptyParams();
        willReturn(expected).given(generator).defaultValue(MyType.class, params);
        final MyType result = generator.defaultValue(MyType.class);
        assertSame(expected, result);
        then(generator).should().defaultValue(MyType.class);
        then(generator).should().createEmptyParams();
        then(generator).should().defaultValue(MyType.class, params);
        then(generator).shouldHaveNoMoreInteractions();
        then(params).shouldHaveNoInteractions();
        then(expected).shouldHaveNoInteractions();
    }

    /**
     * Unit test for {@link AbstractParametrizableGenerator#defaultValue(Class, Object...)}
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
        willReturn(expected).given(generator).defaultValue(MyType.class, params);
        final MyType result = generator.defaultValue(MyType.class, sources);
        assertSame(expected, result);
        then(generator).should().defaultValue(MyType.class, sources);
        then(generator).should().createParams(sources);
        then(generator).should().defaultValue(MyType.class, params);
        then(generator).shouldHaveNoMoreInteractions();
        then(params).shouldHaveNoInteractions();
        then(expected).shouldHaveNoInteractions();
        for (int i = 0; i < sources.length; i++) {
            then(sources[i]).shouldHaveNoInteractions();
        }
    }

    /**
     * Unit test for {@link AbstractParametrizableGenerator#nullableDefaultValue(Class)}
     */
    @Test
    void testNullableDefaultValue() {
        final DirectChild generator = spy(new DirectChild());
        final MyParams params = mock(MyParams.class);
        final MyType expected = mock(MyType.class);
        willReturn(params).given(generator).createEmptyParams();
        willReturn(expected).given(generator).nullableDefaultValue(MyType.class, params);
        final MyType result = generator.nullableDefaultValue(MyType.class);
        assertSame(expected, result);
        then(generator).should().nullableDefaultValue(MyType.class);
        then(generator).should().createEmptyParams();
        then(generator).should().nullableDefaultValue(MyType.class, params);
        then(generator).shouldHaveNoMoreInteractions();
        then(params).shouldHaveNoInteractions();
        then(expected).shouldHaveNoInteractions();
    }

    /**
     * Unit test for {@link AbstractParametrizableGenerator#nullableDefaultValue(Class, Object...)}
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
        willReturn(expected).given(generator).nullableDefaultValue(MyType.class, params);
        final MyType result = generator.nullableDefaultValue(MyType.class, sources);
        assertSame(expected, result);
        then(generator).should().nullableDefaultValue(MyType.class, sources);
        then(generator).should().createParams(sources);
        then(generator).should().nullableDefaultValue(MyType.class, params);
        then(generator).shouldHaveNoMoreInteractions();
        then(params).shouldHaveNoInteractions();
        then(expected).shouldHaveNoInteractions();
        for (int i = 0; i < sources.length; i++) {
            then(sources[i]).shouldHaveNoInteractions();
        }
    }

    /**
     * Unit test for {@link AbstractParametrizableGenerator#nullableDefaultValue(Class, AbstractParametrizableGenerator.Parameters)}
     */
    @ParameterizedTest
    @MethodSource("testNullableTests_Parameters")
    void testNullableDefaultValueParams(
            final boolean paramsNullable,
            final boolean randomNull) {
        final DirectChild generator = spy(new DirectChild());
        final MyParams params = mock(MyParams.class);
        willReturn(paramsNullable).given(params).isNullable();
        final MyType expected = mock(MyType.class);
        willReturn(true).given(generator).supports(MyType.class);
        willReturn(randomNull).given(generator).randomNull(MyType.class);
        willReturn(expected).given(generator).defaultValue(MyType.class, params);
        final MyType result = generator.nullableDefaultValue(MyType.class, params);
        if (paramsNullable && randomNull) {
            assertNull(result);
        } else {
            assertSame(expected, result);
        }
    }

    /**
     * Unit test for {@link AbstractParametrizableGenerator#randomValue(Class)}
     */
    @Test
    void testRandomValue() {
        final DirectChild generator = spy(new DirectChild());
        final MyParams params = mock(MyParams.class);
        final MyType expected = mock(MyType.class);
        willReturn(params).given(generator).createEmptyParams();
        willReturn(expected).given(generator).randomValue(MyType.class, params);
        final MyType result = generator.randomValue(MyType.class);
        assertSame(expected, result);
        then(generator).should().randomValue(MyType.class);
        then(generator).should().createEmptyParams();
        then(generator).should().randomValue(MyType.class, params);
        then(generator).shouldHaveNoMoreInteractions();
        then(params).shouldHaveNoInteractions();
        then(expected).shouldHaveNoInteractions();
    }

    /**
     * Unit test for {@link AbstractParametrizableGenerator#randomValue(Class, Object...)}
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
        willReturn(expected).given(generator).randomValue(MyType.class, params);
        final MyType result = generator.randomValue(MyType.class, sources);
        assertSame(expected, result);
        then(generator).should().randomValue(MyType.class, sources);
        then(generator).should().createParams(sources);
        then(generator).should().randomValue(MyType.class, params);
        then(generator).shouldHaveNoMoreInteractions();
        then(params).shouldHaveNoInteractions();
        then(expected).shouldHaveNoInteractions();
        for (int i = 0; i < sources.length; i++) {
            then(sources[i]).shouldHaveNoInteractions();
        }
    }

    /**
     * Unit test for {@link AbstractParametrizableGenerator#nullableRandomValue(Class)}
     */
    @Test
    void testNullableRandomValue() {
        final DirectChild generator = spy(new DirectChild());
        final MyParams params = mock(MyParams.class);
        final MyType expected = mock(MyType.class);
        willReturn(params).given(generator).createEmptyParams();
        willReturn(expected).given(generator).nullableRandomValue(MyType.class, params);
        final MyType result = generator.nullableRandomValue(MyType.class);
        assertSame(expected, result);
        then(generator).should().nullableRandomValue(MyType.class);
        then(generator).should().createEmptyParams();
        then(generator).should().nullableRandomValue(MyType.class, params);
        then(generator).shouldHaveNoMoreInteractions();
        then(params).shouldHaveNoInteractions();
        then(expected).shouldHaveNoInteractions();
    }

    /**
     * Unit test for {@link AbstractParametrizableGenerator#nullableRandomValue(Class, Object...)}
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
        willReturn(expected).given(generator).nullableRandomValue(MyType.class, params);
        final MyType result = generator.nullableRandomValue(MyType.class, sources);
        assertSame(expected, result);
        then(generator).should().nullableRandomValue(MyType.class, sources);
        then(generator).should().createParams(sources);
        then(generator).should().nullableRandomValue(MyType.class, params);
        then(generator).shouldHaveNoMoreInteractions();
        then(params).shouldHaveNoInteractions();
        then(expected).shouldHaveNoInteractions();
        for (int i = 0; i < sources.length; i++) {
            then(sources[i]).shouldHaveNoInteractions();
        }
    }

    /**
     * Unit test for {@link AbstractParametrizableGenerator#nullableRandomValue(Class, AbstractParametrizableGenerator.Parameters)}
     */
    @ParameterizedTest
    @MethodSource("testNullableTests_Parameters")
    void testNullableRandomValueParams(
            final boolean paramsNullable,
            final boolean randomNull) {
        final DirectChild generator = spy(new DirectChild());
        final MyParams params = mock(MyParams.class);
        willReturn(paramsNullable).given(params).isNullable();
        final MyType expected = mock(MyType.class);
        willReturn(true).given(generator).supports(MyType.class);
        willReturn(randomNull).given(generator).randomNull(MyType.class);
        willReturn(expected).given(generator).randomValue(MyType.class, params);
        final MyType result = generator.nullableRandomValue(MyType.class, params);
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
     * Unit test for {@link AbstractParametrizableGenerator#equals(Object)},
     * {@link AbstractParametrizableGenerator#hashCode()} and
     * {@link AbstractParametrizableGenerator#toString()}
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

    private interface MyParams
    extends AbstractParametrizableGenerator.Parameters {}
    public static class MyParamsImpl
    implements MyParams {
        @Override
        public boolean isNullable() {
            return false;
        }
    }
    private static class MyPrivateParamsImpl
    implements MyParams {
        @Override
        public boolean isNullable() {
            return false;
        }
    }
    private static class MyIllegalParamsImpl
    extends MyPrivateParamsImpl {
        private MyIllegalParamsImpl() {}
    }
    private interface MyType {}

    /**
     * Generic extension of {@code AbstractParametrizableGenerator} for
     * test purposes.
     * @param <PT> The type of parameters
     */
    private static class GenericChild<P extends AbstractParametrizableGenerator.Parameters>
    extends AbstractParametrizableGenerator<P> {
        public GenericChild() {
            super();
        }
        public GenericChild(
                final @NotNull Class<P> paramsType) {
            super(paramsType);
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean supports(
                final @NotNull Class<?> type) {
            return false;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public <T> @NotNull T defaultValue(
                final @NotNull Class<T> type,
                final @NotNull P parameters) {
            return null;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public <T> @NotNull T randomValue(
                final @NotNull Class<T> type,
                final @NotNull P parameters) {
            return null;
        }
    }

    /**
     * Direct typed extension of {@code AbstractParametrizableGenerator} for
     * test purposes.
     */
    protected static class DirectChild
    extends AbstractParametrizableGenerator<MyParams> {
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean supports(
                final @NotNull Class<?> type) {
            return false;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public <T> @NotNull T defaultValue(
                final @NotNull Class<T> type,
                final @NotNull MyParams parameters) {
            return null;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public <T> @NotNull T randomValue(
                final @NotNull Class<T> type,
                final @NotNull MyParams parameters) {
            return null;
        }
    }

    private static class TypedGrandchild
    extends GenericChild<MyParams> {}
}
