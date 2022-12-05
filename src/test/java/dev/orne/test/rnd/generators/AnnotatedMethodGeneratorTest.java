package dev.orne.test.rnd.generators;

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
import java.lang.reflect.Method;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generator;
import dev.orne.test.rnd.GeneratorMethod;
import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;
import dev.orne.test.rnd.UnsupportedValueTypeException;
import dev.orne.test.rnd.params.ExecutableGenerator;

/**
 * Unit tests for {@code AnnotatedMethodGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 * @see AnnotatedMethodGenerator
 */
@Tag("ut")
class AnnotatedMethodGeneratorTest {

    private static final Constructor<ConstructorType> CTR;
    private static final Method FACTORY;

    static {
        try {
            CTR = ConstructorType.class.getConstructor(
                    Integer.class,
                    String.class);
            FACTORY = FactoryType.class.getDeclaredMethod(
                    "factory",
                    Integer.class,
                    String.class);
        } catch (Exception e) {
            final AssertionError error = new AssertionError();
            error.initCause(e);
            throw error;
        }
    }

    /**
     * Reset registered generators.
     */
    @AfterEach
    void resetGenerators() {
        Generators.reset();
    }

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(ConstructorType.class));
        assertEquals(AnnotatedMethodGenerator.class, Generators.getGenerator(ConstructorType.class).getClass());
        assertTrue(Generators.supports(FactoryType.class));
        assertEquals(AnnotatedMethodGenerator.class, Generators.getGenerator(FactoryType.class).getClass());
        assertFalse(Generators.supports(UnsupportedType.class));
    }

    /**
     * Unit test for {@link AnnotatedMethodGenerator#getPriority()}.
     */
    @Test
    void testPriority() {
        assertEquals(Priority.ANNOTATION_GENERATORS, new AnnotatedMethodGenerator().getPriority());
    }

    /**
     * Unit test for {@link AnnotatedMethodGenerator#supports(Class)}.
     */
    @Test
    void testSupports() {
        final AnnotatedMethodGenerator generator = spy(new AnnotatedMethodGenerator());
        final Class<?> type = UnsupportedType.class;
        final Generator expected = mock(Generator.class);
        willReturn(expected).given(generator).getAnnotatedGenerator(type);
        assertTrue(generator.supports(type));
        then(generator).should(times(1)).getAnnotatedGenerator(type);
        willReturn(null).given(generator).getAnnotatedGenerator(type);
        assertFalse(generator.supports(type));
        then(generator).should(times(2)).getAnnotatedGenerator(type);
    }

    /**
     * Unit test for {@link AnnotatedMethodGenerator#defaultValue(Class)}.
     */
    @Test
    void testDefaultValue() {
        final AnnotatedMethodGenerator generator = spy(new AnnotatedMethodGenerator());
        final Class<?> type = UnsupportedType.class;
        final Generator delegated = mock(Generator.class);
        final UnsupportedType expected = mock(UnsupportedType.class);
        willReturn(delegated).given(generator).getAnnotatedGenerator(type);
        willReturn(expected).given(delegated).defaultValue(type);
        Object result = generator.defaultValue(type);
        assertSame(expected, result);
        willReturn(null).given(generator).getAnnotatedGenerator(type);
        assertThrows(UnsupportedValueTypeException.class, () -> {
            generator.defaultValue(type);
        });
    }

    /**
     * Unit test for {@link AnnotatedMethodGenerator#nullableDefaultValue(Class)}.
     */
    @Test
    void testNullableDefaultValue() {
        final AnnotatedMethodGenerator generator = spy(new AnnotatedMethodGenerator());
        final Class<?> type = UnsupportedType.class;
        final Generator delegated = mock(Generator.class);
        final UnsupportedType expected = mock(UnsupportedType.class);
        willReturn(delegated).given(generator).getAnnotatedGenerator(type);
        willReturn(expected).given(delegated).nullableDefaultValue(type);
        Object result = generator.nullableDefaultValue(type);
        assertSame(expected, result);
        willReturn(null).given(delegated).nullableDefaultValue(type);
        result = generator.nullableDefaultValue(type);
        assertNull(result);
        willReturn(null).given(generator).getAnnotatedGenerator(type);
        assertThrows(UnsupportedValueTypeException.class, () -> {
            generator.nullableDefaultValue(type);
        });
    }

    /**
     * Unit test for {@link AnnotatedMethodGenerator#randomValue(Class)}
     */
    @Test
    void testRandomValue() {
        final AnnotatedMethodGenerator generator = spy(new AnnotatedMethodGenerator());
        final Class<?> type = UnsupportedType.class;
        final Generator delegated = mock(Generator.class);
        final UnsupportedType expected = mock(UnsupportedType.class);
        willReturn(delegated).given(generator).getAnnotatedGenerator(type);
        willReturn(expected).given(delegated).randomValue(type);
        Object result = generator.randomValue(type);
        assertSame(expected, result);
        willReturn(null).given(generator).getAnnotatedGenerator(type);
        assertThrows(UnsupportedValueTypeException.class, () -> {
            generator.randomValue(type);
        });
    }

    /**
     * Unit test for {@link AnnotatedMethodGenerator#nullableRandomValue(Class)}
     */
    @Test
    void testNullableRandomValue() {
        final AnnotatedMethodGenerator generator = spy(new AnnotatedMethodGenerator());
        final Class<?> type = UnsupportedType.class;
        final Generator delegated = mock(Generator.class);
        final UnsupportedType expected = mock(UnsupportedType.class);
        willReturn(delegated).given(generator).getAnnotatedGenerator(type);
        willReturn(expected).given(delegated).nullableRandomValue(type);
        Object result = generator.nullableRandomValue(type);
        assertSame(expected, result);
        willReturn(null).given(delegated).nullableRandomValue(type);
        result = generator.nullableRandomValue(type);
        assertNull(result);
        willReturn(null).given(generator).getAnnotatedGenerator(type);
        assertThrows(UnsupportedValueTypeException.class, () -> {
            generator.nullableRandomValue(type);
        });
    }

    /**
     * Unit test for {@link AnnotatedMethodGenerator#getAnnotatedGenerator(Class)}.
     */
    @Test
    void testGetAnnotatedGenerator() {
        final AnnotatedMethodGenerator generator = spy(new AnnotatedMethodGenerator());
        final Generator expected = mock(Generator.class);
        final Class<?> type = UnsupportedType.class;
        willReturn(expected).given(generator).discoverAnnotatedGenerator(type);
        Generator result = generator.getAnnotatedGenerator(type);
        assertSame(expected, result);
        then(generator).should(times(1)).discoverAnnotatedGenerator(type);
        result = generator.getAnnotatedGenerator(type);
        assertSame(expected, result);
        then(generator).should(times(1)).discoverAnnotatedGenerator(type);
        generator.clearCache();
        result = generator.getAnnotatedGenerator(type);
        assertSame(expected, result);
        then(generator).should(times(2)).discoverAnnotatedGenerator(type);
        result = generator.getAnnotatedGenerator(type);
        assertSame(expected, result);
        then(generator).should(times(2)).discoverAnnotatedGenerator(type);
        generator.clearCache(type);
        result = generator.getAnnotatedGenerator(type);
        assertSame(expected, result);
        then(generator).should(times(3)).discoverAnnotatedGenerator(type);
        result = generator.getAnnotatedGenerator(type);
        assertSame(expected, result);
        then(generator).should(times(3)).discoverAnnotatedGenerator(type);
    }

    /**
     * Unit test for {@link AnnotatedMethodGenerator#getAnnotatedGenerator(Class)}.
     */
    @Test
    void testGetAnnotatedGenerator_Missing() {
        final AnnotatedMethodGenerator generator = spy(new AnnotatedMethodGenerator());
        final Generator expected = Generators.MissingGenerator.INSTANCE;
        final Class<?> type = UnsupportedType.class;
        willReturn(expected).given(generator).discoverAnnotatedGenerator(type);
        Generator result = generator.getAnnotatedGenerator(type);
        assertNull(result);
        then(generator).should(times(1)).discoverAnnotatedGenerator(type);
        result = generator.getAnnotatedGenerator(type);
        assertNull(result);
        then(generator).should(times(1)).discoverAnnotatedGenerator(type);
        generator.clearCache();
        result = generator.getAnnotatedGenerator(type);
        assertNull(result);
        then(generator).should(times(2)).discoverAnnotatedGenerator(type);
        result = generator.getAnnotatedGenerator(type);
        assertNull(result);
        then(generator).should(times(2)).discoverAnnotatedGenerator(type);
        generator.clearCache(type);
        result = generator.getAnnotatedGenerator(type);
        assertNull(result);
        then(generator).should(times(3)).discoverAnnotatedGenerator(type);
        result = generator.getAnnotatedGenerator(type);
        assertNull(result);
        then(generator).should(times(3)).discoverAnnotatedGenerator(type);
    }

    /**
     * Unit test for {@link AnnotatedMethodGenerator#discoverAnnotatedGenerator(Class)}.
     */
    @Test
    void testDiscoverAnnotatedGenerator_Constructor() {
        final AnnotatedMethodGenerator generator = spy(new AnnotatedMethodGenerator());
        final Class<?> type = UnsupportedType.class;
        final ExecutableGenerator<?> expected = mock(ExecutableGenerator.class);
        willReturn(expected).given(generator).findDeclaredConstructor(type);
        willReturn(null).given(generator).findDeclaredMethod(type);
        final Generator result = generator.discoverAnnotatedGenerator(type);
        assertSame(expected, result);
    }

    /**
     * Unit test for {@link AnnotatedMethodGenerator#discoverAnnotatedGenerator(Class)}.
     */
    @Test
    void testDiscoverAnnotatedGenerator_Factory() {
        final AnnotatedMethodGenerator generator = spy(new AnnotatedMethodGenerator());
        final Class<?> type = UnsupportedType.class;
        final ExecutableGenerator<?> expected = mock(ExecutableGenerator.class);
        willReturn(null).given(generator).findDeclaredConstructor(type);
        willReturn(expected).given(generator).findDeclaredMethod(type);
        final Generator result = generator.discoverAnnotatedGenerator(type);
        assertSame(expected, result);
    }

    /**
     * Unit test for {@link AnnotatedMethodGenerator#discoverAnnotatedGenerator(Class)}.
     */
    @Test
    void testDiscoverAnnotatedGenerator_Missing() {
        final AnnotatedMethodGenerator generator = spy(new AnnotatedMethodGenerator());
        final Class<?> type = UnsupportedType.class;
        willReturn(null).given(generator).findDeclaredConstructor(type);
        willReturn(null).given(generator).findDeclaredMethod(type);
        final Generator result = generator.discoverAnnotatedGenerator(type);
        assertSame(Generators.MissingGenerator.INSTANCE, result);
    }

    /**
     * Unit test for {@link AnnotatedMethodGenerator#discoverAnnotatedGenerator(Class)}.
     */
    @Test
    void testDiscoverAnnotatedGenerator_Error() {
        final AnnotatedMethodGenerator generator = spy(new AnnotatedMethodGenerator());
        final Class<?> type = UnsupportedType.class;
        final RuntimeException err = new RuntimeException();
        willThrow(err).given(generator).findDeclaredConstructor(type);
        willThrow(err).given(generator).findDeclaredMethod(type);
        final Generator result = generator.discoverAnnotatedGenerator(type);
        assertSame(Generators.MissingGenerator.INSTANCE, result);
    }

    /**
     * Unit test for {@link AnnotatedMethodGenerator#findDeclaredConstructor(Class)}.
     */
    @Test
    void testFindDeclaredConstructor() {
        final AnnotatedMethodGenerator generator = new AnnotatedMethodGenerator();
        final ExecutableGenerator<?> result = generator.findDeclaredConstructor(ConstructorType.class);
        assertEquals(ExecutableGenerator.of(CTR), result);
        assertNull(generator.findDeclaredConstructor(ProtectedConstructorType.class));
        assertNull(generator.findDeclaredConstructor(PackageConstructorType.class));
        assertNull(generator.findDeclaredConstructor(PrivateConstructorType.class));
        assertNull(generator.findDeclaredConstructor(UnsupportedType.class));
    }

    /**
     * Unit test for {@link AnnotatedMethodGenerator#findDeclaredMethod(Class)}.
     */
    @Test
    void testFindDeclaredMethod() {
        final AnnotatedMethodGenerator generator = new AnnotatedMethodGenerator();
        final ExecutableGenerator<?> result = generator.findDeclaredMethod(FactoryType.class);
        assertEquals(ExecutableGenerator.of(FactoryType.class, FACTORY), result);
        assertNull(generator.findDeclaredMethod(ProtectedFactoryType.class));
        assertNull(generator.findDeclaredMethod(PackageFactoryType.class));
        assertNull(generator.findDeclaredMethod(PrivateFactoryType.class));
        assertNull(generator.findDeclaredMethod(NonStaticFactoryType.class));
        assertNull(generator.findDeclaredMethod(WrongTypeFactoryType.class));
        assertNull(generator.findDeclaredMethod(UnsupportedType.class));
    }

    /**
     * Unit test for {@link AnnotatedMethodGenerator#equals(Object)},
     * {@link AnnotatedMethodGenerator#hashCode()} and
     * {@link AnnotatedMethodGenerator#toString()}.
     */
    @Test
    @SuppressWarnings({ "java:S5785" })
    void testEqualsHashCodeToString() {
        final AnnotatedMethodGenerator generator = new AnnotatedMethodGenerator();
        assertFalse(generator.equals(null));
        assertTrue(generator.equals(generator));
        assertFalse(generator.equals(new Object()));
        AnnotatedMethodGenerator other = new AnnotatedMethodGenerator();
        assertTrue(generator.equals(other));
        assertEquals(generator.hashCode(), other.hashCode());
        assertEquals(generator.toString(), other.toString());
    }

    public static class ConstructorType {
        @GeneratorMethod
        public ConstructorType(
                final Integer paramA,
                final String paramB) {
            super();
        }
    }
    public static class ProtectedConstructorType {
        @GeneratorMethod
        protected ProtectedConstructorType(
                final Integer paramA,
                final String paramB) {
            super();
        }
    }
    static class PackageConstructorType {
        @GeneratorMethod
        PackageConstructorType(
                final Integer paramA,
                final String paramB) {
            super();
        }
    }
    public static class PrivateConstructorType {
        @GeneratorMethod
        private PrivateConstructorType(
                final Integer paramA,
                final String paramB) {
            super();
        }
    }
    public static class FactoryType {
        @GeneratorMethod
        public static @NotNull FactoryType factory(
                final Integer paramA,
                final String paramB) {
            return new FactoryType();
        }
    }
    public static class ProtectedFactoryType {
        @GeneratorMethod
        protected static @NotNull ProtectedFactoryType factory(
                final Integer paramA,
                final String paramB) {
            return new ProtectedFactoryType();
        }
    }
    public static class PackageFactoryType {
        @GeneratorMethod
        static @NotNull PackageFactoryType factory(
                final Integer paramA,
                final String paramB) {
            return new PackageFactoryType();
        }
    }
    public static class PrivateFactoryType {
        @GeneratorMethod
        private static @NotNull PrivateFactoryType factory(
                final Integer paramA,
                final String paramB) {
            return new PrivateFactoryType();
        }
    }
    public static class NonStaticFactoryType {
        @GeneratorMethod
        public @NotNull NonStaticFactoryType factory(
                final Integer paramA,
                final String paramB) {
            return new NonStaticFactoryType();
        }
    }
    public static class WrongTypeFactoryType {
        @GeneratorMethod
        public static @NotNull Object factory(
                final Integer paramA,
                final String paramB) {
            return new Object();
        }
    }
    private static class UnsupportedType {}
}
