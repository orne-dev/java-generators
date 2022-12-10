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
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.GenerationException;
import dev.orne.test.rnd.Generator;
import dev.orne.test.rnd.Generators;

/**
 * Unit tests for {@code MethodReturnTypeGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see MethodReturnTypeGenerator
 */
@Tag("ut")
class MethodReturnTypeGeneratorTest {

    static final Method METHOD0;
    static final Method METHOD1;
    static final Method METHOD2;

    static {
        METHOD0 = MethodUtils.getMatchingMethod(
                MyType.class,
                "method0");
        METHOD1 = MethodUtils.getMatchingMethod(
                MyType.class,
                "method1");
        METHOD2 = MethodUtils.getMatchingMethod(
                MyType.class,
                "method2");
    }

    /**
     * Unit test for {@link MethodReturnTypeGenerator#MethodReturnTypeGenerator(Class, Method)}.
     */
    @Test
    void testConstructor() {
        final MethodReturnTypeGenerator<String> generator = new MethodReturnTypeGenerator<>(
                String.class,
                METHOD0);
        assertSame(String.class, generator.getValueType());
        assertSame(Generators.getGenerator(String.class), generator.getGenerator());
        assertSame(METHOD0, generator.getMethod());
        assertSame(String.class, generator.getDeclaredType());
    }

    /**
     * Unit test for {@link MethodReturnTypeGenerator#MethodReturnTypeGenerator(Class, Method, Generator)}.
     */
    @Test
    void testGeneratorConstructor() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(MyValueType.class);
        final MethodReturnTypeGenerator<MyValueType> generator = new MethodReturnTypeGenerator<>(
                MyValueType.class,
                METHOD1,
                delegated);
        assertSame(MyValueType.class, generator.getValueType());
        assertSame(delegated, generator.getGenerator());
        assertSame(METHOD1, generator.getMethod());
        assertSame(MyValueType.class, generator.getDeclaredType());
    }

    /**
     * Unit test for {@link MethodReturnTypeGenerator#MethodReturnTypeGenerator(Class, Method, Generator)}.
     */
    @Test
    void testGenericsConstructor() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(List.class);
        @SuppressWarnings("rawtypes")
        final MethodReturnTypeGenerator<List> generator = new MethodReturnTypeGenerator<>(
                List.class,
                METHOD2,
                delegated);
        assertSame(List.class, generator.getValueType());
        assertSame(delegated, generator.getGenerator());
        assertSame(METHOD2, generator.getMethod());
        assertTrue(generator.getDeclaredType() instanceof ParameterizedType);
        final ParameterizedType declaredType = (ParameterizedType) generator.getDeclaredType();
        assertSame(List.class, declaredType.getRawType());
        assertSame(String.class, declaredType.getActualTypeArguments()[0]);
    }

    /**
     * Unit test for {@link MethodReturnTypeGenerator#targeting(Method)}.
     */
    @Test
    void testTargetingField() {
        final MethodReturnTypeGenerator<MyValueType> generator = MethodReturnTypeGenerator.targeting(METHOD0);
        assertSame(String.class, generator.getValueType());
        assertSame(Generators.getGenerator(String.class), generator.getGenerator());
        assertSame(METHOD0, generator.getMethod());
        assertSame(String.class, generator.getDeclaredType());
    }

    /**
     * Unit test for {@link MethodReturnTypeGenerator#targeting(Class, String, Class...)}.
     */
    @Test
    void testTargetingClassString() {
        final MethodReturnTypeGenerator<MyValueType> generator = MethodReturnTypeGenerator.targeting(
                MySubType.class,
                METHOD0.getName());
        assertSame(String.class, generator.getValueType());
        assertSame(Generators.getGenerator(String.class), generator.getGenerator());
        assertEquals(METHOD0, generator.getMethod());
        assertSame(String.class, generator.getDeclaredType());
    }

    /**
     * Unit test for {@link MethodReturnTypeGenerator#targeting(Class, String, Class...)}.
     */
    @Test
    void testTargetingClassString_Missing() {
        assertThrows(GenerationException.class, () -> {
            MethodReturnTypeGenerator.targeting(MySubType.class, "noExistingMethod");
        });
    }

    /**
     * Unit test for {@link MethodReturnTypeGenerator#getTargetConstraints(Class...)}.
     */
    @Test
    void testTargetGetConstraints() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(String.class);
        final MethodReturnTypeGenerator<String> generator = new MethodReturnTypeGenerator<>(
                String.class,
                ConstraintIntrospectionTestType.TEST_METHOD,
                delegated);
        final Collection<Annotation> result = generator.getTargetConstraints(
                ConstraintIntrospectionTestType.Group1.class,
                ConstraintIntrospectionTestType.Group2.class);
        final Collection<Annotation> expected = ConstraintIntrospector.findMethodResultConstrains(
                ConstraintIntrospectionTestType.TEST_METHOD,
                ConstraintIntrospectionTestType.Group1.class,
                ConstraintIntrospectionTestType.Group2.class); 
        assertEquals(expected, result);
    }

    /**
     * Unit test for {@link MethodReturnTypeGenerator#equals(Object)},
     * {@link MethodReturnTypeGenerator#hashCode()} and
     * {@link MethodReturnTypeGenerator#toString()}
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
        final MethodReturnTypeGenerator<MyValueType> generator = new MethodReturnTypeGenerator<>(
                MyValueType.class,
                METHOD1,
                delegated);
        assertFalse(generator.equals(null));
        assertTrue(generator.equals(generator));
        assertFalse(generator.equals(new Object()));
        MethodReturnTypeGenerator<?> other = new MethodReturnTypeGenerator<>(
                MyValueType.class,
                METHOD1,
                delegated);
        assertTrue(generator.equals(other));
        assertEquals(generator.hashCode(), other.hashCode());
        assertEquals(generator.toString(), other.toString());
        other = new MethodReturnTypeGenerator<>(
                Object.class,
                METHOD1,
                delegated);
        assertFalse(generator.equals(other));
        other = new MethodReturnTypeGenerator<>(
                MyValueType.class,
                METHOD0,
                delegated);
        assertFalse(generator.equals(other));
        other = new MethodReturnTypeGenerator<>(
                MyValueType.class,
                METHOD1,
                otherDelegated);
        assertFalse(generator.equals(other));
    }

    private interface MyValueType {}
    private static class MyType {
        @SuppressWarnings("unused")
        public String method0() { return null; }
        @NotNull
        public MyValueType method1() { return null; }
        @NotNull
        @Size(min = 1, max = 10)
        public List<String> method2() { return null; }
    }
    private static class MySubType
    extends MyType {}
}
