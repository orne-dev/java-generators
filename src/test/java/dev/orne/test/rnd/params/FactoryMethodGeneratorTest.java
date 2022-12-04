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
import static org.mockito.Mockito.mock;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.GenerationException;
import dev.orne.test.rnd.Generators;

/**
 * Unit tests for {@code FactoryMethodGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 * @see FactoryMethodGenerator
 */
@Tag("ut")
class FactoryMethodGeneratorTest {

    private static final Method FACTORY;
    private static final Method OF_FACTORY;

    static {
        try {
            FACTORY = MyType.class.getDeclaredMethod(
                    "factory",
                    Object.class,
                    Object.class);
            OF_FACTORY = MyType.class.getDeclaredMethod(
                    "ofFactory",
                    String.class,
                    Integer.class);
        } catch (Exception e) {
            final AssertionError error = new AssertionError();
            error.initCause(e);
            throw error;
        }
    }

    /**
     * Reset test factory method errors.
     */
    @AfterEach
    void resetTestFactory() {
        MyType.error = null;
    }

    /**
     * Unit test for {@link FactoryMethodGenerator#FactoryMethodGenerator(Constructor, TargetedGenerator[])}
     */
    @Test
    void testConstructor() {
        final Class<?> type = MyType.class;
        final TargetedGenerator<?>[] paramGenerators = new TargetedGenerator[] {
                mock(TargetedGenerator.class),
                mock(TargetedGenerator.class)
        };
        final FactoryMethodGenerator<?> generator = new FactoryMethodGenerator<>(
                type, FACTORY, paramGenerators);
        assertSame(type, generator.getValueType());
        assertSame(FACTORY, generator.getExecutable());
        assertArrayEquals(paramGenerators, generator.getParameterGenerators());
    }

    /**
     * Unit test for {@link FactoryMethodGenerator#of(Constructor)}
     */
    @Test
    void testOf() {
        final FactoryMethodGenerator<?> generator = FactoryMethodGenerator.of(
                MyType.class,
                OF_FACTORY);
        assertSame(MyType.class, generator.getValueType());
        assertSame(OF_FACTORY, generator.getExecutable());
        final TargetedGenerator<?>[] paramGenerators = generator.getParameterGenerators();
        assertNotNull(paramGenerators);
        assertEquals(2, paramGenerators.length);
        assertEquals(Generators.forParameter(OF_FACTORY, 0), paramGenerators[0]);
        assertEquals(Generators.forParameter(OF_FACTORY, 1), paramGenerators[1]);
    }

    /**
     * Unit test for {@link FactoryMethodGenerator#generate(Object[])}
     */
    @Test
    void testGenerate()
    throws Exception {
        final TargetedGenerator<?>[] paramGenerators = new TargetedGenerator[] {
                mock(TargetedGenerator.class),
                mock(TargetedGenerator.class)
        };
        final Object mockParam0 = new Object();
        final Object mockParam1 = new Object();
        final Object[] params = new Object[] {mockParam0, mockParam1 };
        final FactoryMethodGenerator<?> generator = new FactoryMethodGenerator<>(
                MyType.class, FACTORY, paramGenerators);
        final Object result = generator.generate(params);
        assertNotNull(result);
    }

    /**
     * Unit test for {@link FactoryMethodGenerator#generate(Object[])}
     */
    @Test
    void testGenerateException()
    throws Exception {
        final TargetedGenerator<?>[] paramGenerators = new TargetedGenerator[] {
                mock(TargetedGenerator.class),
                mock(TargetedGenerator.class)
        };
        final Object mockParam0 = new Object();
        final Object mockParam1 = new Object();
        final Object[] params = new Object[] {mockParam0, mockParam1 };
        final Exception error = new Exception();
        MyType.error = error;
        final FactoryMethodGenerator<?> generator = new FactoryMethodGenerator<>(
                MyType.class, FACTORY, paramGenerators);
        assertThrows(GenerationException.class, () -> {
            generator.generate(params);
        });
    }

    public static class MyType {
        private static Exception error = null;
        public MyType() {}
        public static MyType ofFactory(String value0, Integer value1) {
            return new MyType();
        }
        public static MyType factory(Object param0, Object param1)
        throws Exception {
            if (error != null) {
                throw error;
            } else {
                return new MyType();
            }
        }
    }
}
