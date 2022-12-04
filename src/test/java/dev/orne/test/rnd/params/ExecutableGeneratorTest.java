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

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;


/**
 * Unit tests for {@code ExecutableGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 * @see ExecutableGenerator
 */
@Tag("ut")
class ExecutableGeneratorTest {

    /**
     * Unit test for {@link ExecutableGenerator#ExecutableGenerator(Class, Executable, TargetedGenerator[])}
     */
    @Test
    void testConstructor() {
        final Class<?> type = MyType.class; 
        final Executable executable = mock(Executable.class);
        final TargetedGenerator<?>[] paramGenerators = new TargetedGenerator[] {
                mock(TargetedGenerator.class),
                mock(TargetedGenerator.class)
        };
        assertThrows(NullPointerException.class, () -> {
            new GenericChild<>(null, executable, paramGenerators);
        });
        assertThrows(NullPointerException.class, () -> {
            new GenericChild<>(type, null, paramGenerators);
        });
        assertThrows(NullPointerException.class, () -> {
            new GenericChild<>(type, executable, null);
        });
        final ExecutableGenerator<?> generator = new GenericChild<>(
                type,
                executable,
                paramGenerators);
        assertSame(type, generator.getValueType());
        assertSame(executable, generator.getExecutable());
        assertArrayEquals(paramGenerators, generator.getParameterGenerators());
    }

    /**
     * Unit test for {@link ExecutableGenerator#of(Constructor)}
     */
    @Test
    void testOfConstructor() {
        final Constructor<MyType> ctr;
        try {
            ctr = MyType.class.getConstructor(
                    String.class,
                    Integer.class);
        } catch (Exception e) {
            final AssertionError error = new AssertionError();
            error.initCause(e);
            throw error;
        }
        final ExecutableGenerator<?> generator = ExecutableGenerator.of(ctr);
        assertSame(MyType.class, generator.getValueType());
        assertSame(ctr, generator.getExecutable());
        final TargetedGenerator<?>[] paramGenerators = generator.getParameterGenerators();
        assertNotNull(paramGenerators);
        assertEquals(2, paramGenerators.length);
        assertEquals(Generators.forParameter(ctr, 0), paramGenerators[0]);
        assertEquals(Generators.forParameter(ctr, 1), paramGenerators[1]);
    }

    /**
     * Unit test for {@link ExecutableGenerator#of(Constructor)}
     */
    @Test
    void testOfFactoryMethod() {
        final Method method;
        try {
            method = MyType.class.getDeclaredMethod(
                    "factory",
                    String.class,
                    Integer.class);
        } catch (Exception e) {
            final AssertionError error = new AssertionError();
            error.initCause(e);
            throw error;
        }
        final ExecutableGenerator<?> generator = ExecutableGenerator.of(MyType.class, method);
        assertSame(MyType.class, generator.getValueType());
        assertSame(method, generator.getExecutable());
        final TargetedGenerator<?>[] paramGenerators = generator.getParameterGenerators();
        assertNotNull(paramGenerators);
        assertEquals(2, paramGenerators.length);
        assertEquals(Generators.forParameter(method, 0), paramGenerators[0]);
        assertEquals(Generators.forParameter(method, 1), paramGenerators[1]);
    }

    /**
     * Unit test for {@link ExecutableGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final Class<?> type = MyType.class; 
        final Executable executable = mock(Executable.class);
        final TargetedGenerator<?>[] paramGenerators = new TargetedGenerator[] {
                mock(TargetedGenerator.class),
                mock(TargetedGenerator.class)
        };
        final ExecutableGenerator<?> generator = spy(new GenericChild<>(
                type,
                executable,
                paramGenerators));
        final Object mockParam0 = new Object();
        final Object mockParam1 = new Object();
        final MyType mockResult = mock(MyType.class);
        willReturn(mockParam0).given(paramGenerators[0]).nullableDefaultValue(Default.class);
        willReturn(mockParam1).given(paramGenerators[1]).nullableDefaultValue(Default.class);
        willReturn(mockResult).given(generator).generate(
                eq(new Object[] { mockParam0, mockParam1 }));
        final Object result = generator.defaultValue();
        assertSame(mockResult, result);
        then(paramGenerators[0]).should().nullableDefaultValue(Default.class);
        then(paramGenerators[0]).shouldHaveNoMoreInteractions();
        then(paramGenerators[1]).should().nullableDefaultValue(Default.class);
        then(paramGenerators[1]).shouldHaveNoMoreInteractions();
    }

    /**
     * Unit test for {@link ExecutableGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final Class<?> type = MyType.class; 
        final Executable executable = mock(Executable.class);
        final TargetedGenerator<?>[] paramGenerators = new TargetedGenerator[] {
                mock(TargetedGenerator.class),
                mock(TargetedGenerator.class)
        };
        final ExecutableGenerator<?> generator = spy(new GenericChild<>(
                type,
                executable,
                paramGenerators));
        final Object mockParam0 = new Object();
        final Object mockParam1 = new Object();
        final MyType mockResult = mock(MyType.class);
        willReturn(mockParam0).given(paramGenerators[0]).nullableRandomValue(Default.class);
        willReturn(mockParam1).given(paramGenerators[1]).nullableRandomValue(Default.class);
        willReturn(mockResult).given(generator).generate(
                eq(new Object[] { mockParam0, mockParam1 }));
        final Object result = generator.randomValue();
        assertSame(mockResult, result);
        then(paramGenerators[0]).should().nullableRandomValue(Default.class);
        then(paramGenerators[0]).shouldHaveNoMoreInteractions();
        then(paramGenerators[1]).should().nullableRandomValue(Default.class);
        then(paramGenerators[1]).shouldHaveNoMoreInteractions();
    }

    /**
     * Unit test for {@link GenerationParameters#equals(Object)},
     * {@link GenerationParameters#hashCode()} and
     * {@link GenerationParameters#toString()}.
     */
    @Test
    @SuppressWarnings({ "java:S5785" })
    void testEqualsHashCodeToString() {
        final Class<?> type = MyType.class; 
        final Class<?> otherType = OtherType.class; 
        final Executable executable = mock(Executable.class);
        final Executable otherExecutable = mock(Executable.class);
        final TargetedGenerator<?>[] paramGenerators = new TargetedGenerator[] {
                mock(TargetedGenerator.class),
                mock(TargetedGenerator.class)
        };
        final TargetedGenerator<?>[] otherParamGenerators = new TargetedGenerator[] {
                mock(TargetedGenerator.class),
                mock(TargetedGenerator.class)
        };
        final ExecutableGenerator<?> generator = new GenericChild<>(
                type,
                executable,
                paramGenerators);
        assertFalse(generator.equals(null));
        assertTrue(generator.equals(generator));
        assertFalse(generator.equals(new Object()));
        ExecutableGenerator<?> other = new GenericChild<>(
                type,
                executable,
                paramGenerators);
        assertTrue(generator.equals(other));
        assertEquals(generator.hashCode(), other.hashCode());
        assertEquals(generator.toString(), other.toString());
        other = new GenericChild<>(
                otherType,
                executable,
                paramGenerators);
        assertFalse(generator.equals(other));
        other = new GenericChild<>(
                type,
                otherExecutable,
                paramGenerators);
        assertFalse(generator.equals(other));
        other = new GenericChild<>(
                type,
                executable,
                otherParamGenerators);
        assertFalse(generator.equals(other));
    }

    public static class MyType {
        public MyType() {}
        public MyType(String value0, Integer value1) {}
        public static MyType factory(String value0, Integer value1) {
            return new MyType();
        }
    }
    private static interface OtherType {}
    /**
     * Generic extension of {@code ExecutableGenerator} for
     * test purposes.
     * @param <T> The type of generated values
     */
    private static class GenericChild<T>
    extends ExecutableGenerator<T> {
        public GenericChild(@NotNull Class<T> type, @NotNull Executable executable,
                @NotNull TargetedGenerator<?>[] paramGenerators) {
            super(type, executable, paramGenerators);
        }
        @Override
        protected @NotNull T generate(Object[] params) {
            return null;
        }
    }
}
