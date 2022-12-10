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

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generator;
import dev.orne.test.rnd.Generators;

/**
 * Unit tests for {@code AbstractTargetedGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see AbstractTargetedGenerator
 */
@Tag("ut")
class AbstractTargetedGeneratorTest {

    /**
     * Unit test for {@link AbstractTargetedGenerator#AbstractTargetedGenerator(Class)}
     */
    @Test
    void testTypeConstructor() {
        final GenericChild<String> generator = new GenericChild<>(String.class);
        assertSame(String.class, generator.getValueType());
        assertSame(Generators.getGenerator(String.class), generator.getGenerator());
    }

    /**
     * Unit test for {@link AbstractTargetedGenerator#AbstractTargetedGenerator(Class, Generator)}
     */
    @Test
    void testGeneratorConstructor() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(MyType.class);
        final GenericChild<MyType> generator = new GenericChild<>(MyType.class, delegated);
        assertSame(MyType.class, generator.getValueType());
        assertSame(delegated, generator.getGenerator());
        then(delegated).should().supports(MyType.class);
        then(delegated).shouldHaveNoMoreInteractions();
    }

    /**
     * Unit test for {@link AbstractTargetedGenerator#AbstractTargetedGenerator(Class, Generator)}
     */
    @Test
    void testGeneratorConstructor_Unsupported() {
        final Generator delegated = spy(Generator.class);
        willReturn(false).given(delegated).supports(MyType.class);
        assertThrows(IllegalArgumentException.class, () -> {
            new GenericChild<>(MyType.class, delegated);
        });
        then(delegated).should().supports(MyType.class);
        then(delegated).shouldHaveNoMoreInteractions();
    }

    /**
     * Unit test for {@link AbstractTargetedGenerator#defaultValue(Class...)}
     */
    @Test
    void testDefaultValue() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(MyType.class);
        final GenericChild<MyType> generator = spy(new GenericChild<>(MyType.class, delegated));
        final MyType mockResult = mock(MyType.class);
        willReturn(mockResult).given(delegated).defaultValue(MyType.class);
        assertSame(mockResult, generator.defaultValue(MyGroup.class));
        then(delegated).should().supports(MyType.class);
        then(delegated).should().defaultValue(MyType.class);
        then(delegated).shouldHaveNoMoreInteractions();
        then(generator).should(never()).getParameterSources(MyGroup.class);
    }

    /**
     * Unit test for {@link AbstractTargetedGenerator#defaultValue(Class...)}
     */
    @Test
    void testDefaultValue_Parameterizable() {
        final ParameterizableGenerator delegated = spy(ParameterizableGenerator.class);
        willReturn(true).given(delegated).supports(MyType.class);
        final GenericChild<MyType> generator = spy(new GenericChild<>(MyType.class, delegated));
        final Object[] sources = new Object[] { new Object() };
        willReturn(sources).given(generator).getParameterSources(MyGroup.class);
        final MyType mockResult = mock(MyType.class);
        willReturn(mockResult).given(delegated).defaultValue(MyType.class, sources);
        assertSame(mockResult, generator.defaultValue(MyGroup.class));
        then(delegated).should().supports(MyType.class);
        then(delegated).should().defaultValue(MyType.class, sources);
        then(delegated).shouldHaveNoMoreInteractions();
        then(generator).should().getParameterSources(MyGroup.class);
    }

    /**
     * Unit test for {@link AbstractTargetedGenerator#nullableDefaultValue(Class...)}
     */
    @Test
    void testNullableDefaultValue() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(MyType.class);
        final GenericChild<MyType> generator = spy(new GenericChild<>(MyType.class, delegated));
        final MyType mockResult = mock(MyType.class);
        willReturn(mockResult).given(delegated).nullableDefaultValue(MyType.class);
        assertSame(mockResult, generator.nullableDefaultValue(MyGroup.class));
        then(delegated).should().supports(MyType.class);
        then(delegated).should().nullableDefaultValue(MyType.class);
        then(delegated).shouldHaveNoMoreInteractions();
        then(generator).should(never()).getParameterSources(MyGroup.class);
    }

    /**
     * Unit test for {@link AbstractTargetedGenerator#nullableDefaultValue(Class...)}
     */
    @Test
    void testNullableDefaultValue_Parameterizable() {
        final ParameterizableGenerator delegated = spy(ParameterizableGenerator.class);
        willReturn(true).given(delegated).supports(MyType.class);
        final GenericChild<MyType> generator = spy(new GenericChild<>(MyType.class, delegated));
        final Object[] sources = new Object[] { new Object() };
        willReturn(sources).given(generator).getParameterSources(MyGroup.class);
        final MyType mockResult = mock(MyType.class);
        willReturn(mockResult).given(delegated).nullableDefaultValue(MyType.class, sources);
        assertSame(mockResult, generator.nullableDefaultValue(MyGroup.class));
        then(delegated).should().supports(MyType.class);
        then(delegated).should().nullableDefaultValue(MyType.class, sources);
        then(delegated).shouldHaveNoMoreInteractions();
        then(generator).should().getParameterSources(MyGroup.class);
    }

    /**
     * Unit test for {@link AbstractTargetedGenerator#randomValue(Class...)}
     */
    @Test
    void testRandomValue() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(MyType.class);
        final GenericChild<MyType> generator = spy(new GenericChild<>(MyType.class, delegated));
        final MyType mockResult = mock(MyType.class);
        willReturn(mockResult).given(delegated).randomValue(MyType.class);
        assertSame(mockResult, generator.randomValue(MyGroup.class));
        then(delegated).should().supports(MyType.class);
        then(delegated).should().randomValue(MyType.class);
        then(delegated).shouldHaveNoMoreInteractions();
        then(generator).should(never()).getParameterSources(MyGroup.class);
    }

    /**
     * Unit test for {@link AbstractTargetedGenerator#randomValue(Class...)}
     */
    @Test
    void testRandomValue_Parameterizable() {
        final ParameterizableGenerator delegated = spy(ParameterizableGenerator.class);
        willReturn(true).given(delegated).supports(MyType.class);
        final GenericChild<MyType> generator = spy(new GenericChild<>(MyType.class, delegated));
        final Object[] sources = new Object[] { new Object() };
        willReturn(sources).given(generator).getParameterSources(MyGroup.class);
        final MyType mockResult = mock(MyType.class);
        willReturn(mockResult).given(delegated).randomValue(MyType.class, sources);
        assertSame(mockResult, generator.randomValue(MyGroup.class));
        then(delegated).should().supports(MyType.class);
        then(delegated).should().randomValue(MyType.class, sources);
        then(delegated).shouldHaveNoMoreInteractions();
        then(generator).should().getParameterSources(MyGroup.class);
    }

    /**
     * Unit test for {@link AbstractTargetedGenerator#nullableRandomValue(Class...)}
     */
    @Test
    void testNullableRandomValue() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(MyType.class);
        final GenericChild<MyType> generator = spy(new GenericChild<>(MyType.class, delegated));
        final MyType mockResult = mock(MyType.class);
        willReturn(mockResult).given(delegated).nullableRandomValue(MyType.class);
        assertSame(mockResult, generator.nullableRandomValue(MyGroup.class));
        then(delegated).should().supports(MyType.class);
        then(delegated).should().nullableRandomValue(MyType.class);
        then(delegated).shouldHaveNoMoreInteractions();
        then(generator).should(never()).getParameterSources(MyGroup.class);
    }

    /**
     * Unit test for {@link AbstractTargetedGenerator#nullableRandomValue(Class...)}
     */
    @Test
    void testNullableRandomValue_Parameterizable() {
        final ParameterizableGenerator delegated = spy(ParameterizableGenerator.class);
        willReturn(true).given(delegated).supports(MyType.class);
        final GenericChild<MyType> generator = spy(new GenericChild<>(MyType.class, delegated));
        final Object[] sources = new Object[] { new Object() };
        willReturn(sources).given(generator).getParameterSources(MyGroup.class);
        final MyType mockResult = mock(MyType.class);
        willReturn(mockResult).given(delegated).nullableRandomValue(MyType.class, sources);
        assertSame(mockResult, generator.nullableRandomValue(MyGroup.class));
        then(delegated).should().supports(MyType.class);
        then(delegated).should().nullableRandomValue(MyType.class, sources);
        then(delegated).shouldHaveNoMoreInteractions();
        then(generator).should().getParameterSources(MyGroup.class);
    }

    /**
     * Unit test for {@link AbstractTargetedGenerator#getParameterSources(Class...)}
     */
    @Test
    void testGetParameterSources() {
        final ParameterizableGenerator delegated = spy(ParameterizableGenerator.class);
        willReturn(true).given(delegated).supports(MyType.class);
        final GenericChild<MyType> generator = spy(new GenericChild<>(MyType.class, delegated));
        final List<Object> sources = Arrays.asList(new Object(), new Object());
        willReturn(sources).given(generator).getParameterSourceList(MyGroup.class);
        final Object[] result = generator.getParameterSources(MyGroup.class);
        assertNotNull(result);
        assertEquals(sources.size(), result.length);
        for (int i = 0; i < result.length; i++) {
            assertSame(sources.get(i), result[i]);
        }
        then(generator).should().getParameterSourceList(MyGroup.class);
    }

    /**
     * Unit test for {@link AbstractTargetedGenerator#getParameterSources(Class...)}
     */
    @Test
    void testGetParameterSourceList() {
        final ParameterizableGenerator delegated = spy(ParameterizableGenerator.class);
        willReturn(true).given(delegated).supports(MyType.class);
        final GenericChild<MyType> generator = spy(new GenericChild<>(MyType.class, delegated));
        final Type declaredType = spy(Type.class);
        willReturn(declaredType).given(generator).getDeclaredType();
        final List<Annotation> constraints = Arrays.asList(
                spy(Annotation.class), spy(Annotation.class));
        willReturn(constraints).given(generator).getTargetConstraints(MyGroup.class);
        final List<Object> result = generator.getParameterSourceList(MyGroup.class);
        assertNotNull(result);
        assertEquals(constraints.size() + 1, result.size());
        assertTrue(result.get(0) instanceof TypeDeclaration);
        final TypeDeclaration typeDeclaration = (TypeDeclaration) result.get(0);
        assertSame(declaredType, typeDeclaration.getType());
        for (int i = 0; i < constraints.size(); i++) {
            assertSame(constraints.get(i), result.get(i + 1));
        }
        then(generator).should().getDeclaredType();
        then(generator).should().getParameterSourceList(MyGroup.class);
    }

    /**
     * Unit test for {@link AbstractTargetedGenerator#getParameterSources(Class...)}
     */
    @Test
    void testGetParameterSourceList_NoDeclaredType() {
        final ParameterizableGenerator delegated = spy(ParameterizableGenerator.class);
        willReturn(true).given(delegated).supports(MyType.class);
        final GenericChild<MyType> generator = spy(new GenericChild<>(MyType.class, delegated));
        willReturn(null).given(generator).getDeclaredType();
        final List<Annotation> constraints = Arrays.asList(
                spy(Annotation.class), spy(Annotation.class));
        willReturn(constraints).given(generator).getTargetConstraints(MyGroup.class);
        final List<Object> result = generator.getParameterSourceList(MyGroup.class);
        assertNotNull(result);
        assertEquals(constraints.size(), result.size());
        for (int i = 0; i < constraints.size(); i++) {
            assertSame(constraints.get(i), result.get(i));
        }
        then(generator).should().getDeclaredType();
        then(generator).should().getParameterSourceList(MyGroup.class);
    }

    /**
     * Unit test for {@link AbstractTargetedGenerator#equals(Object)},
     * {@link AbstractTargetedGenerator#hashCode()} and
     * {@link AbstractTargetedGenerator#toString()}
     */
    @Test
    @SuppressWarnings("java:S5785")
    void testEqualsHashCodeToString() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(MyType.class);
        willReturn(true).given(delegated).supports(OtherType.class);
        final Generator otherDelegated = spy(Generator.class);
        willReturn(true).given(otherDelegated).supports(MyType.class);
        final GenericChild<?> generator = new GenericChild<>(MyType.class, delegated);
        assertFalse(generator.equals(null));
        assertTrue(generator.equals(generator));
        assertFalse(generator.equals(new Object()));
        GenericChild<?> other = new GenericChild<>(MyType.class, delegated);
        assertTrue(generator.equals(other));
        assertEquals(generator.hashCode(), other.hashCode());
        assertEquals(generator.toString(), other.toString());
        other = new GenericChild<>(OtherType.class, delegated);
        assertFalse(generator.equals(other));
        other = new GenericChild<>(MyType.class, otherDelegated);
        assertFalse(generator.equals(other));
    }

    private interface MyType {}
    private interface OtherType {}
    private interface MyGroup {}
    /**
     * Generic extension of {@code AbstractTargetedGenerator} for
     * test purposes.
     * @param <T> The type of generated values
     */
    private static class GenericChild<T>
    extends AbstractTargetedGenerator<T> {
        public GenericChild(
                final @NotNull Class<T> valuesType) {
            super(valuesType);
        }
        public GenericChild(
                final @NotNull Class<T> valueType,
                final @NotNull Generator generator) {
            super(valueType, generator);
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public Type getDeclaredType() {
            return null;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        protected @NotNull Collection<Annotation> getTargetConstraints(@NotNull Class<?>... groups) {
            return null;
        }
    }
}
