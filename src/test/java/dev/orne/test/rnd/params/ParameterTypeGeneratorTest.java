package dev.orne.test.rnd.params;

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
import static org.mockito.BDDMockito.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generator;
import dev.orne.test.rnd.Generators;

/**
 * Unit tests for {@code ParameterTypeGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2023-11
 * @since 0.2
 * @see ParameterTypeGenerator
 */
@Tag("ut")
class ParameterTypeGeneratorTest {

    static final Constructor<?> TEST_CTR;
    static final Parameter TEST_CTR_PARAM_0;
    static final Parameter TEST_CTR_PARAM_1;
    static final Parameter TEST_CTR_PARAM_2;
    static final Constructor<?> OTHER_CTR;
    static final Parameter OTHER_CTR_PARAM_0;
    static final Parameter OTHER_CTR_PARAM_1;
    static final Method TEST_METHOD;
    static final Parameter TEST_METHOD_PARAM_0;
    static final Parameter TEST_METHOD_PARAM_1;
    static final Parameter TEST_METHOD_PARAM_2;
    static final Method OTHER_METHOD;
    static final Parameter OTHER_METHOD_PARAM_0;
    static final Parameter OTHER_METHOD_PARAM_1;
    static final Parameter OTHER_METHOD_PARAM_2;

    static {
        try {
            TEST_CTR = MyType.class.getConstructor(
                    String.class,
                    MyValueType.class,
                    List.class);
            TEST_CTR_PARAM_0 = TEST_CTR.getParameters()[0];
            TEST_CTR_PARAM_1 = TEST_CTR.getParameters()[1];
            TEST_CTR_PARAM_2 = TEST_CTR.getParameters()[2];
            OTHER_CTR = MyType.class.getConstructor(
                    String.class,
                    MyValueType.class);
            OTHER_CTR_PARAM_0 = OTHER_CTR.getParameters()[0];
            OTHER_CTR_PARAM_1 = OTHER_CTR.getParameters()[1];
            TEST_METHOD = MethodUtils.getMatchingMethod(
                    MyType.class,
                    "testMethod",
                    String.class,
                    MyValueType.class,
                    List.class);
            TEST_METHOD_PARAM_0 = TEST_METHOD.getParameters()[0];
            TEST_METHOD_PARAM_1 = TEST_METHOD.getParameters()[1];
            TEST_METHOD_PARAM_2 = TEST_METHOD.getParameters()[2];
            OTHER_METHOD = MethodUtils.getMatchingMethod(
                    MyType.class,
                    "otherMethod",
                    String.class,
                    MyValueType.class,
                    List.class);
            OTHER_METHOD_PARAM_0 = OTHER_METHOD.getParameters()[0];
            OTHER_METHOD_PARAM_1 = OTHER_METHOD.getParameters()[1];
            OTHER_METHOD_PARAM_2 = OTHER_METHOD.getParameters()[2];
        } catch (NoSuchMethodException | SecurityException e) {
            AssertionError err = new AssertionError("Cannot find the constructor");
            err.initCause(e);
            throw err;
        }
    }

    /**
     * Unit test for {@link ParameterTypeGenerator#ParameterTypeGenerator(Class, Parameter)}.
     */
    @Test
    void testConstructor() {
        assertThrows(NullPointerException.class, () -> {
            new ParameterTypeGenerator<>(null, null);
        });
        assertThrows(NullPointerException.class, () -> {
            new ParameterTypeGenerator<>(String.class, null);
        });
        assertThrows(NullPointerException.class, () -> {
            new ParameterTypeGenerator<>(null, TEST_CTR_PARAM_0);
        });
        ParameterTypeGenerator<String> generator = new ParameterTypeGenerator<>(
                String.class,
                TEST_CTR_PARAM_0);
        assertSame(String.class, generator.getValueType());
        assertSame(Generators.getGenerator(String.class), generator.getGenerator());
        assertSame(TEST_CTR_PARAM_0, generator.getParameter());
        assertSame(String.class, generator.getDeclaredType());
        generator = new ParameterTypeGenerator<>(
                String.class,
                TEST_METHOD_PARAM_0);
        assertSame(String.class, generator.getValueType());
        assertSame(Generators.getGenerator(String.class), generator.getGenerator());
        assertSame(TEST_METHOD_PARAM_0, generator.getParameter());
        assertSame(String.class, generator.getDeclaredType());
    }

    /**
     * Unit test for {@link ParameterTypeGenerator#ParameterTypeGenerator(Class, Parameter, Generator)}.
     */
    @Test
    void testGeneratorConstructor() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(MyValueType.class);
        assertThrows(NullPointerException.class, () -> {
            new ParameterTypeGenerator<>(null, null, delegated);
        });
        assertThrows(NullPointerException.class, () -> {
            new ParameterTypeGenerator<>(MyValueType.class, null, delegated);
        });
        assertThrows(NullPointerException.class, () -> {
            new ParameterTypeGenerator<>(null, TEST_CTR_PARAM_0, delegated);
        });
        assertThrows(NullPointerException.class, () -> {
            new ParameterTypeGenerator<>(MyValueType.class, TEST_CTR_PARAM_0, null);
        });
        ParameterTypeGenerator<MyValueType> generator = new ParameterTypeGenerator<>(
                MyValueType.class,
                TEST_CTR_PARAM_1,
                delegated);
        assertSame(MyValueType.class, generator.getValueType());
        assertSame(delegated, generator.getGenerator());
        assertSame(TEST_CTR_PARAM_1, generator.getParameter());
        assertSame(MyValueType.class, generator.getDeclaredType());
        generator = new ParameterTypeGenerator<>(
                MyValueType.class,
                TEST_METHOD_PARAM_1,
                delegated);
        assertSame(MyValueType.class, generator.getValueType());
        assertSame(delegated, generator.getGenerator());
        assertSame(TEST_METHOD_PARAM_1, generator.getParameter());
        assertSame(MyValueType.class, generator.getDeclaredType());
    }

    /**
     * Unit test for {@link ParameterTypeGenerator#ParameterTypeGenerator(Class, Parameter, Generator)}.
     */
    @Test
    void testGenericsConstructor() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(List.class);
        @SuppressWarnings("rawtypes")
        ParameterTypeGenerator<List> generator = new ParameterTypeGenerator<>(
                List.class,
                TEST_CTR_PARAM_2,
                delegated);
        assertSame(List.class, generator.getValueType());
        assertSame(delegated, generator.getGenerator());
        assertSame(TEST_CTR_PARAM_2, generator.getParameter());
        assertTrue(generator.getDeclaredType() instanceof ParameterizedType);
        ParameterizedType declaredType = (ParameterizedType) generator.getDeclaredType();
        assertSame(List.class, declaredType.getRawType());
        assertSame(String.class, declaredType.getActualTypeArguments()[0]);
        generator = new ParameterTypeGenerator<>(
                List.class,
                TEST_METHOD_PARAM_2,
                delegated);
        assertSame(List.class, generator.getValueType());
        assertSame(delegated, generator.getGenerator());
        assertSame(TEST_METHOD_PARAM_2, generator.getParameter());
        assertTrue(generator.getDeclaredType() instanceof ParameterizedType);
        declaredType = (ParameterizedType) generator.getDeclaredType();
        assertSame(List.class, declaredType.getRawType());
        assertSame(String.class, declaredType.getActualTypeArguments()[0]);
    }

    /**
     * Unit test for {@link ParameterTypeGenerator#targeting(Parameter)}.
     */
    @Test
    void testTargeting() {
        ParameterTypeGenerator<MyValueType> generator = ParameterTypeGenerator.targeting(
                TEST_CTR_PARAM_0);
        assertSame(String.class, generator.getValueType());
        assertSame(Generators.getGenerator(String.class), generator.getGenerator());
        assertSame(TEST_CTR_PARAM_0, generator.getParameter());
        assertSame(String.class, generator.getDeclaredType());
        generator = ParameterTypeGenerator.targeting(
                TEST_METHOD_PARAM_0);
        assertSame(String.class, generator.getValueType());
        assertSame(Generators.getGenerator(String.class), generator.getGenerator());
        assertSame(TEST_METHOD_PARAM_0, generator.getParameter());
        assertSame(String.class, generator.getDeclaredType());
    }

    /**
     * Unit test for {@link ParameterTypeGenerator#equals(Object)},
     * {@link ParameterTypeGenerator#hashCode()} and
     * {@link ParameterTypeGenerator#toString()}
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
        final ParameterTypeGenerator<MyValueType> generator = new ParameterTypeGenerator<>(
                MyValueType.class,
                TEST_CTR_PARAM_0,
                delegated);
        assertFalse(generator.equals(null));
        assertTrue(generator.equals(generator));
        assertFalse(generator.equals(new Object()));
        ParameterTypeGenerator<?> other = new ParameterTypeGenerator<>(
                MyValueType.class,
                TEST_CTR_PARAM_0,
                delegated);
        assertTrue(generator.equals(other));
        assertEquals(generator.hashCode(), other.hashCode());
        assertEquals(generator.toString(), other.toString());
        other = new ParameterTypeGenerator<>(
                Object.class,
                TEST_CTR_PARAM_0,
                delegated);
        assertFalse(generator.equals(other));
        other = new ParameterTypeGenerator<>(
                MyValueType.class,
                OTHER_CTR_PARAM_0,
                delegated);
        assertFalse(generator.equals(other));
        other = new ParameterTypeGenerator<>(
                MyValueType.class,
                TEST_CTR_PARAM_1,
                delegated);
        assertFalse(generator.equals(other));
        other = new ParameterTypeGenerator<>(
                MyValueType.class,
                TEST_CTR_PARAM_1,
                otherDelegated);
        assertFalse(generator.equals(other));
    }

    private interface MyValueType {}
    public static class MyType {
        public MyType(
                String param0,
                @NotNull
                MyValueType param1,
                @NotNull
                @Size(min = 1, max = 10)
                List<String> param2) {}
        public MyType(
                String param0,
                @NotNull
                MyValueType param1) {}
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
}
