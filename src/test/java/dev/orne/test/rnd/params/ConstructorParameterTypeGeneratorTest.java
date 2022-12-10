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
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.GenerationException;
import dev.orne.test.rnd.Generator;
import dev.orne.test.rnd.Generators;

/**
 * Unit tests for {@code ConstructorParameterTypeGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see ConstructorParameterTypeGenerator
 */
@Tag("ut")
class ConstructorParameterTypeGeneratorTest {

    static final Constructor<?> TEST_CTR;
    static final Constructor<?> OTHER_CTR;

    static {
        try {
            TEST_CTR = MyType.class.getConstructor(
                    String.class,
                    MyValueType.class,
                    List.class);
            OTHER_CTR = MyType.class.getConstructor(
                    String.class,
                    MyValueType.class);
        } catch (NoSuchMethodException | SecurityException e) {
            AssertionError err = new AssertionError("Cannot find the constructor");
            err.initCause(e);
            throw err;
        }
    }

    /**
     * Unit test for {@link ConstructorParameterTypeGenerator#ConstructorParameterTypeGenerator(Class, Constructor, int)}.
     */
    @Test
    void testConstructor() {
        final ConstructorParameterTypeGenerator<String> generator = new ConstructorParameterTypeGenerator<>(
                String.class,
                TEST_CTR,
                0);
        assertSame(String.class, generator.getValueType());
        assertSame(Generators.getGenerator(String.class), generator.getGenerator());
        assertSame(TEST_CTR, generator.getConstructor());
        assertSame(0, generator.getParameterIndex());
        assertSame(String.class, generator.getDeclaredType());
    }

    /**
     * Unit test for {@link ConstructorParameterTypeGenerator#ConstructorParameterTypeGenerator(Class, Constructor, int, Generator)}.
     */
    @Test
    void testGeneratorConstructor() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(MyValueType.class);
        final ConstructorParameterTypeGenerator<MyValueType> generator = new ConstructorParameterTypeGenerator<>(
                MyValueType.class,
                TEST_CTR,
                1,
                delegated);
        assertSame(MyValueType.class, generator.getValueType());
        assertSame(delegated, generator.getGenerator());
        assertSame(TEST_CTR, generator.getConstructor());
        assertSame(1, generator.getParameterIndex());
        assertSame(MyValueType.class, generator.getDeclaredType());
    }

    /**
     * Unit test for {@link ConstructorParameterTypeGenerator#ConstructorParameterTypeGenerator(Class, Constructor, int, Generator)}.
     */
    @Test
    void testGenericsConstructor() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(List.class);
        @SuppressWarnings("rawtypes")
        final ConstructorParameterTypeGenerator<List> generator = new ConstructorParameterTypeGenerator<>(
                List.class,
                TEST_CTR,
                2,
                delegated);
        assertSame(List.class, generator.getValueType());
        assertSame(delegated, generator.getGenerator());
        assertSame(TEST_CTR, generator.getConstructor());
        assertSame(2, generator.getParameterIndex());
        assertTrue(generator.getDeclaredType() instanceof ParameterizedType);
        final ParameterizedType declaredType = (ParameterizedType) generator.getDeclaredType();
        assertSame(List.class, declaredType.getRawType());
        assertSame(String.class, declaredType.getActualTypeArguments()[0]);
    }

    /**
     * Unit test for {@link ConstructorParameterTypeGenerator#targeting(Constructor, int)}.
     */
    @Test
    void testTargetingField() {
        final ConstructorParameterTypeGenerator<MyValueType> generator = ConstructorParameterTypeGenerator.targeting(TEST_CTR, 0);
        assertSame(String.class, generator.getValueType());
        assertSame(Generators.getGenerator(String.class), generator.getGenerator());
        assertSame(TEST_CTR, generator.getConstructor());
        assertSame(0, generator.getParameterIndex());
        assertSame(String.class, generator.getDeclaredType());
    }

    /**
     * Unit test for {@link ConstructorParameterTypeGenerator#targeting(Class, int, Class...)}.
     */
    @Test
    void testTargetingClassString() {
        final ConstructorParameterTypeGenerator<MyValueType> generator = ConstructorParameterTypeGenerator.targeting(
                Integer.class,
                0,
                String.class);
        assertSame(String.class, generator.getValueType());
        assertSame(Generators.getGenerator(String.class), generator.getGenerator());
        final Constructor<?> intCtr = assertDoesNotThrow(() -> {
            return Integer.class.getConstructor(String.class);
        });
        assertEquals(intCtr, generator.getConstructor());
        assertSame(0, generator.getParameterIndex());
        assertSame(String.class, generator.getDeclaredType());
    }

    /**
     * Unit test for {@link ConstructorParameterTypeGenerator#targeting(Class, String)}.
     */
    @Test
    void testTargetingClassString_Missing() {
        assertThrows(GenerationException.class, () -> {
            ConstructorParameterTypeGenerator.targeting(MyType.class, 0, MyValueType.class);
        });
    }

    /**
     * Unit test for {@link ConstructorParameterTypeGenerator#getTargetConstraints(Class...)}.
     */
    @Test
    void testTargetGetConstraints() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(String.class);
        final ConstructorParameterTypeGenerator<String> generator = new ConstructorParameterTypeGenerator<>(
                String.class,
                ConstraintIntrospectionTestType.PARAM_CONSTRUCTOR,
                2,
                delegated);
        final Collection<Annotation> result = generator.getTargetConstraints(
                ConstraintIntrospectionTestType.Group1.class,
                ConstraintIntrospectionTestType.Group2.class);
        final Collection<Annotation> expected = ConstraintIntrospector.findConstructorParameterConstrains(
                ConstraintIntrospectionTestType.PARAM_CONSTRUCTOR,
                2,
                ConstraintIntrospectionTestType.Group1.class,
                ConstraintIntrospectionTestType.Group2.class); 
        assertEquals(expected, result);
    }

    /**
     * Unit test for {@link ConstructorParameterTypeGenerator#equals(Object)},
     * {@link ConstructorParameterTypeGenerator#hashCode()} and
     * {@link ConstructorParameterTypeGenerator#toString()}
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
        final ConstructorParameterTypeGenerator<MyValueType> generator = new ConstructorParameterTypeGenerator<>(
                MyValueType.class,
                TEST_CTR,
                0,
                delegated);
        assertFalse(generator.equals(null));
        assertTrue(generator.equals(generator));
        assertFalse(generator.equals(new Object()));
        ConstructorParameterTypeGenerator<?> other = new ConstructorParameterTypeGenerator<>(
                MyValueType.class,
                TEST_CTR,
                0,
                delegated);
        assertTrue(generator.equals(other));
        assertEquals(generator.hashCode(), other.hashCode());
        assertEquals(generator.toString(), other.toString());
        other = new ConstructorParameterTypeGenerator<>(
                Object.class,
                TEST_CTR,
                0,
                delegated);
        assertFalse(generator.equals(other));
        other = new ConstructorParameterTypeGenerator<>(
                MyValueType.class,
                OTHER_CTR,
                0,
                delegated);
        assertFalse(generator.equals(other));
        other = new ConstructorParameterTypeGenerator<>(
                MyValueType.class,
                TEST_CTR,
                1,
                delegated);
        assertFalse(generator.equals(other));
        other = new ConstructorParameterTypeGenerator<>(
                MyValueType.class,
                TEST_CTR,
                0,
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
    }
}
