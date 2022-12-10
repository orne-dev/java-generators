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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.GenerationException;
import dev.orne.test.rnd.Generators;

/**
 * Unit tests for {@code ConstructorGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 * @see ConstructorGenerator
 */
@Tag("ut")
class ConstructorGeneratorTest {

    private static final Constructor<MyType> CTR;
    private static final Constructor<MyType> PARAMS_CTR;

    static {
        try {
            CTR = MyType.class.getConstructor(
                    String.class,
                    Integer.class);
            PARAMS_CTR = MyType.class.getConstructor(
                    Object.class,
                    Object.class);
        } catch (Exception e) {
            final AssertionError error = new AssertionError();
            error.initCause(e);
            throw error;
        }
    }

    /**
     * Reset test constructor errors.
     */
    @AfterEach
    void resetTestConstructor() {
        MyType.error = null;
    }

    /**
     * Unit test for {@link ConstructorGenerator#ConstructorGenerator(Constructor, TargetedGenerator[])}
     */
    @Test
    void testConstructor() {
        final Class<?> type = MyType.class;
        final TargetedGenerator<?>[] paramGenerators = new TargetedGenerator[] {
                mock(TargetedGenerator.class),
                mock(TargetedGenerator.class)
        };
        final ConstructorGenerator<?> generator = new ConstructorGenerator<>(CTR, paramGenerators);
        assertSame(type, generator.getValueType());
        assertSame(CTR, generator.getExecutable());
        assertArrayEquals(paramGenerators, generator.getParameterGenerators());
    }

    /**
     * Unit test for {@link ConstructorGenerator#of(Constructor)}
     */
    @Test
    void testOf() {
        final ConstructorGenerator<?> generator = ConstructorGenerator.of(CTR);
        assertSame(MyType.class, generator.getValueType());
        assertSame(CTR, generator.getExecutable());
        final TargetedGenerator<?>[] paramGenerators = generator.getParameterGenerators();
        assertNotNull(paramGenerators);
        assertEquals(2, paramGenerators.length);
        assertEquals(Generators.forParameter(CTR, 0), paramGenerators[0]);
        assertEquals(Generators.forParameter(CTR, 1), paramGenerators[1]);
    }

    /**
     * Unit test for {@link ConstructorGenerator#generate(Object[])}
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
        final ConstructorGenerator<?> generator = new ConstructorGenerator<>(PARAMS_CTR, paramGenerators);
        final Object result = generator.generate(params);
        assertNotNull(result);
    }

    /**
     * Unit test for {@link ConstructorGenerator#generate(Object[])}
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
        final ConstructorGenerator<?> generator = new ConstructorGenerator<>(PARAMS_CTR, paramGenerators);
        assertThrows(GenerationException.class, () -> {
            generator.generate(params);
        });
    }

    public static class MyType {
        private static Exception error = null;
        public MyType() {}
        public MyType(String value0, Integer value1) {}
        public MyType(Object param0, Object param1)
        throws Exception {
            if (error != null) {
                throw error;
            }
        }
    }
}
