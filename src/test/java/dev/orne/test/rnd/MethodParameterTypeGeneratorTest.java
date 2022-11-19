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
import static org.mockito.Mockito.spy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code MethodParameterTypeGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see MethodParameterTypeGenerator
 */
@Tag("ut")
class MethodParameterTypeGeneratorTest {

    static final Method TEST_METHOD;
    static final Method OTHER_METHOD;

    static {
        TEST_METHOD = MethodUtils.getMatchingMethod(
                MyType.class,
                "testMethod",
                String.class,
                MyValueType.class,
                List.class);
        OTHER_METHOD = MethodUtils.getMatchingMethod(
                MyType.class,
                "otherMethod",
                String.class,
                MyValueType.class,
                List.class);
    }

    /**
     * Unit test for {@link MethodParameterTypeGenerator#MethodParameterTypeGenerator(Class, Method, int)}.
     */
    @Test
    void testConstructor() {
        final MethodParameterTypeGenerator<String> generator = new MethodParameterTypeGenerator<>(
                String.class,
                TEST_METHOD,
                0);
        assertSame(String.class, generator.getValueType());
        assertSame(Generators.getGenerator(String.class), generator.getGenerator());
        assertSame(TEST_METHOD, generator.getMethod());
        assertSame(0, generator.getParameterIndex());
        assertSame(String.class, generator.getDeclaredType());
    }

    /**
     * Unit test for {@link MethodParameterTypeGenerator#MethodParameterTypeGenerator(Class, Method, int, Generator)}.
     */
    @Test
    void testGeneratorConstructor() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(MyValueType.class);
        final MethodParameterTypeGenerator<MyValueType> generator = new MethodParameterTypeGenerator<>(
                MyValueType.class,
                TEST_METHOD,
                1,
                delegated);
        assertSame(MyValueType.class, generator.getValueType());
        assertSame(delegated, generator.getGenerator());
        assertSame(TEST_METHOD, generator.getMethod());
        assertSame(1, generator.getParameterIndex());
        assertSame(MyValueType.class, generator.getDeclaredType());
    }

    /**
     * Unit test for {@link MethodParameterTypeGenerator#MethodParameterTypeGenerator(Class, Method, int, Generator)}.
     */
    @Test
    void testGenericsConstructor() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(List.class);
        @SuppressWarnings("rawtypes")
        final MethodParameterTypeGenerator<List> generator = new MethodParameterTypeGenerator<>(
                List.class,
                TEST_METHOD,
                2,
                delegated);
        assertSame(List.class, generator.getValueType());
        assertSame(delegated, generator.getGenerator());
        assertSame(TEST_METHOD, generator.getMethod());
        assertSame(2, generator.getParameterIndex());
        assertTrue(generator.getDeclaredType() instanceof ParameterizedType);
        final ParameterizedType declaredType = (ParameterizedType) generator.getDeclaredType();
        assertSame(List.class, declaredType.getRawType());
        assertSame(String.class, declaredType.getActualTypeArguments()[0]);
    }

    /**
     * Unit test for {@link MethodParameterTypeGenerator#targeting(Method, int)}.
     */
    @Test
    void testTargetingField() {
        final MethodParameterTypeGenerator<MyValueType> generator = MethodParameterTypeGenerator.targeting(TEST_METHOD, 0);
        assertSame(String.class, generator.getValueType());
        assertSame(Generators.getGenerator(String.class), generator.getGenerator());
        assertSame(TEST_METHOD, generator.getMethod());
        assertSame(0, generator.getParameterIndex());
        assertSame(String.class, generator.getDeclaredType());
    }

    /**
     * Unit test for {@link MethodParameterTypeGenerator#targeting(Class, String, int, Class...)}.
     */
    @Test
    void testTargetingClassString() {
        final MethodParameterTypeGenerator<MyValueType> generator = MethodParameterTypeGenerator.targeting(
                MySubType.class,
                "testMethod",
                0,
                String.class,
                MyValueType.class,
                List.class);
        assertSame(String.class, generator.getValueType());
        assertSame(Generators.getGenerator(String.class), generator.getGenerator());
        assertEquals(TEST_METHOD, generator.getMethod());
        assertSame(0, generator.getParameterIndex());
        assertSame(String.class, generator.getDeclaredType());
    }

    /**
     * Unit test for {@link MethodParameterTypeGenerator#targeting(Class, String)}.
     */
    @Test
    void testTargetingClassString_Missing() {
        assertThrows(GenerationException.class, () -> {
            MethodParameterTypeGenerator.targeting(MySubType.class, "noExistingMethod", 0);
        });
    }

    /**
     * Unit test for {@link MethodParameterTypeGenerator#getTargetConstraints(Class...)}.
     */
    @Test
    void testTargetGetConstraints() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(String.class);
        final MethodParameterTypeGenerator<String> generator = new MethodParameterTypeGenerator<>(
                String.class,
                ConstraintIntrospectorTest.TEST_METHOD,
                2,
                delegated);
        final Collection<Annotation> result = generator.getTargetConstraints(
                ConstraintIntrospectorTest.Group1.class,
                ConstraintIntrospectorTest.Group2.class);
        final Collection<Annotation> expected = ConstraintIntrospector.findMethodParameterConstrains(
                ConstraintIntrospectorTest.TEST_METHOD,
                2,
                ConstraintIntrospectorTest.Group1.class,
                ConstraintIntrospectorTest.Group2.class); 
        assertEquals(expected, result);
    }

    /**
     * Unit test for {@link MethodParameterTypeGenerator#equals(Object)},
     * {@link MethodParameterTypeGenerator#hashCode()} and
     * {@link MethodParameterTypeGenerator#toString()}
     */
    @Test
    @SuppressWarnings("java:S5785")
    void testEqualsHashCodeToString() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(Object.class);
        willReturn(true).given(delegated).supports(String.class);
        willReturn(true).given(delegated).supports(MyValueType.class);
        willReturn(true).given(delegated).supports(List.class);
        final Generator otherDelegated = spy(Generator.class);
        willReturn(true).given(otherDelegated).supports(Object.class);
        willReturn(true).given(otherDelegated).supports(String.class);
        willReturn(true).given(otherDelegated).supports(MyValueType.class);
        willReturn(true).given(otherDelegated).supports(List.class);
        final MethodParameterTypeGenerator<MyValueType> generator = new MethodParameterTypeGenerator<>(
                MyValueType.class,
                TEST_METHOD,
                0,
                delegated);
        assertFalse(generator.equals(null));
        assertTrue(generator.equals(generator));
        assertFalse(generator.equals(new Object()));
        MethodParameterTypeGenerator<?> other = new MethodParameterTypeGenerator<>(
                MyValueType.class,
                TEST_METHOD,
                0,
                delegated);
        assertTrue(generator.equals(other));
        assertEquals(generator.hashCode(), other.hashCode());
        assertEquals(generator.toString(), other.toString());
        other = new MethodParameterTypeGenerator<>(
                Object.class,
                TEST_METHOD,
                0,
                delegated);
        assertFalse(generator.equals(other));
        other = new MethodParameterTypeGenerator<>(
                MyValueType.class,
                OTHER_METHOD,
                0,
                delegated);
        assertFalse(generator.equals(other));
        other = new MethodParameterTypeGenerator<>(
                MyValueType.class,
                TEST_METHOD,
                1,
                delegated);
        assertFalse(generator.equals(other));
        other = new MethodParameterTypeGenerator<>(
                MyValueType.class,
                TEST_METHOD,
                0,
                otherDelegated);
        assertFalse(generator.equals(other));
    }

    private interface MyValueType {}
    @SuppressWarnings("unused")
    private static class MyType {
        public String testMethod(
                String param0,
                @NotNull
                MyValueType param1,
                @NotNull
                @Size(min = 1, max = 10)
                List<String> param2) {
            return null;
        }
        public String otherMethod(
                String param0,
                @NotNull
                MyValueType param1,
                @NotNull
                @Size(min = 1, max = 10)
                List<String> param2) {
            return null;
        }
        public List<String> method2() { return null; }
    }
    private static class MySubType
    extends MyType {}
}
