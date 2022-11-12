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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators.MissingGenerator;

/**
 * Unit tests for {@code Generators}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 * @see Generators
 */
@Tag("ut")
class GeneratorsTest {

    /**
     * Reset registered generators.
     */
    @AfterEach
    void resetGenerators() {
        Generators.reset();
    }

    /**
     * Test for {@link Generators#COMPARATOR}.
     */
    @Test
    void testComparator() {
        final Generator importantGenerator = new ImportantGenerator();
        final Generator defaultGenerator = new DefaultGenerator();
        final Generator noAnnotationGenerator = new NoAnnotationGenerator();
        final Generator fallbackGenerator = new FallbackGenerator();
        final List<Generator> list = Arrays.asList(
                defaultGenerator,
                importantGenerator,
                fallbackGenerator,
                noAnnotationGenerator);
        final List<Generator> expected = Arrays.asList(
                importantGenerator,
                defaultGenerator,
                noAnnotationGenerator,
                fallbackGenerator);
        Collections.sort(list, Generators.COMPARATOR);
        assertEquals(expected, list);
    }

    /**
     * Test for {@link Generators#loadSpiGenerators()}.
     */
    @Test
    void testLoadSpiGenerators() {
        final List<Generator> result = Generators.loadSpiGenerators();
        assertNotNull(result);
        assertTrue(result.contains(new TestGenerator()));
        assertTrue(result.contains(new TestTypedGenerator()));
        final List<Generator> other = Generators.loadSpiGenerators();
        assertNotSame(result, other);
    }

    /**
     * Test for {@link Generators#getCacheInt()}.
     */
    @Test
    void testGetCacheInt() {
        final Map<Class<?>, Generator> result = Generators.getCacheInt();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Test for {@link Generators#getGeneratorsInt()}.
     */
    @Test
    void testGetGeneratorsInt() {
        final List<Generator> result = Generators.getGeneratorsInt();
        assertNotNull(result);
        final List<Generator> expected = Generators.loadSpiGenerators();
        Collections.sort(expected, Generators.COMPARATOR);
        assertEquals(expected, result);
        final List<Generator> other = Generators.getGeneratorsInt();
        assertSame(result, other);
        final Map<Class<?>, Generator> cache = Generators.getCacheInt();
        assertNotNull(cache);
        assertTrue(cache.isEmpty());
    }

    /**
     * Test for {@link Generators#getRegisteredGenerators()}.
     */
    @Test
    void testGetRegisteredGenerators() {
        final List<Generator> result = Generators.getRegisteredGenerators();
        assertNotNull(result);
        assertNotSame(result, Generators.getGeneratorsInt());
        assertEquals(result, Generators.getGeneratorsInt());
        final Map<Class<?>, Generator> cache = Generators.getCacheInt();
        assertNotNull(cache);
        assertTrue(cache.isEmpty());
        final Generator otherGenerator = new DefaultGenerator();
        final List<Generator> otherGenerators = Collections.singletonList(otherGenerator);
        assertThrows(UnsupportedOperationException.class, () -> {
            result.add(otherGenerator);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            result.add(0, otherGenerator);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            result.addAll(otherGenerators);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            result.addAll(0, otherGenerators);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            result.clear();
        });
        final Iterator<Generator> it = result.iterator();
        assertThrows(UnsupportedOperationException.class, () -> {
            it.remove();
        });
        final ListIterator<Generator> lit = result.listIterator();
        assertThrows(UnsupportedOperationException.class, () -> {
            lit.add(otherGenerator);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            lit.remove();
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            lit.set(otherGenerator);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            result.remove(0);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            result.remove(otherGenerator);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            result.removeAll(otherGenerators);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            result.removeIf(g -> true);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            result.replaceAll(g -> null);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            result.retainAll(otherGenerators);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            result.set(0, otherGenerator);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            result.sort((c, v) -> 1);
        });
    }

    /**
     * Test for {@link Generators#reset()}.
     */
    @Test
    void testReset() {
        final List<Generator> result = Generators.getGeneratorsInt();
        List<Generator> other = Generators.getGeneratorsInt();
        assertSame(result, other);
        Generators.getGenerator(TestGenerator.Type.class);
        final Map<Class<?>, Generator> cache = Generators.getCacheInt();
        assertNotNull(cache);
        assertFalse(cache.isEmpty());
        Generators.reset();
        other = Generators.getGeneratorsInt();
        assertNotSame(result, other);
        Generators.reset();
        assertTrue(cache.isEmpty());
    }

    /**
     * Test for {@link Generators#register(Generator...)}.
     */
    @Test
    void testRegister_Varargs() {
        final Map<Class<?>, Generator> cache = Generators.getCacheInt();
        Generators.getGenerator(TestGenerator.Type.class);
        assertFalse(cache.isEmpty());
        final Generator[] newGenerators = new Generator[] {
                new ImportantGenerator(),
                new DefaultGenerator(),
                new NoAnnotationGenerator(),
                new FallbackGenerator()
        };
        Generators.register(newGenerators);
        final List<Generator> expected = Generators.loadSpiGenerators();
        expected.addAll(Arrays.asList(newGenerators));
        Collections.sort(expected, Generators.COMPARATOR);
        assertEquals(expected, Generators.getGeneratorsInt());
        assertTrue(cache.isEmpty());
        assertThrows(NullPointerException.class, () -> {
            Generators.register((Generator[]) null);
        });
        final Generator[] newGeneratorsWithNull = new Generator[] {
                new ImportantGenerator(),
                new DefaultGenerator(),
                null,
                new NoAnnotationGenerator(),
                new FallbackGenerator()
        };
        assertThrows(IllegalArgumentException.class, () -> {
            Generators.register(newGeneratorsWithNull);
        });
    }

    /**
     * Test for {@link Generators#register(Collection)}.
     */
    @Test
    void testRegister_Collection() {
        final Map<Class<?>, Generator> cache = Generators.getCacheInt();
        Generators.getGenerator(TestGenerator.Type.class);
        assertFalse(cache.isEmpty());
        final List<Generator> newGenerators = Arrays.asList(
                new ImportantGenerator(),
                new DefaultGenerator(),
                new NoAnnotationGenerator(),
                new FallbackGenerator());
        Generators.register(newGenerators);
        final List<Generator> expected = Generators.loadSpiGenerators();
        expected.addAll(newGenerators);
        Collections.sort(expected, Generators.COMPARATOR);
        assertEquals(expected, Generators.getGeneratorsInt());
        assertTrue(cache.isEmpty());
        assertThrows(NullPointerException.class, () -> {
            Generators.register((Collection<Generator>) null);
        });
        final List<Generator> newGeneratorsWithNull = Arrays.asList(
                new ImportantGenerator(),
                new DefaultGenerator(),
                null,
                new NoAnnotationGenerator(),
                new FallbackGenerator());
        assertThrows(IllegalArgumentException.class, () -> {
            Generators.register(newGeneratorsWithNull);
        });
    }

    /**
     * Test for {@link Generators#remove(Generator...)}.
     */
    @Test
    void testRemove_Varargs() {
        final Map<Class<?>, Generator> cache = Generators.getCacheInt();
        Generators.getGenerator(TestGenerator.Type.class);
        assertFalse(cache.isEmpty());
        final Generator[] oldGenerators = new Generator[] {
                new TestTypedGenerator()
        };
        Generators.remove(oldGenerators);
        final List<Generator> expected = Generators.loadSpiGenerators();
        assertEquals(expected.size() - oldGenerators.length, Generators.getGeneratorsInt().size());
        expected.removeAll(Arrays.asList(oldGenerators));
        Collections.sort(expected, Generators.COMPARATOR);
        assertEquals(expected, Generators.getGeneratorsInt());
        assertTrue(cache.isEmpty());
        assertThrows(NullPointerException.class, () -> {
            Generators.remove((Generator[]) null);
        });
        final Generator[] oldGeneratorsWithNull = new Generator[] {
                new TestTypedGenerator(),
                null,
                new FallbackGenerator()
        };
        assertThrows(IllegalArgumentException.class, () -> {
            Generators.remove(oldGeneratorsWithNull);
        });
    }

    /**
     * Test for {@link Generators#remove(Collection)}.
     */
    @Test
    void testRemove_Collection() {
        final Map<Class<?>, Generator> cache = Generators.getCacheInt();
        Generators.getGenerator(TestGenerator.Type.class);
        assertFalse(cache.isEmpty());
        final List<Generator> oldGenerators = Arrays.asList(
                new TestTypedGenerator());
        Generators.remove(oldGenerators);
        final List<Generator> expected = Generators.loadSpiGenerators();
        assertEquals(expected.size() - oldGenerators.size(), Generators.getGeneratorsInt().size());
        expected.removeAll(oldGenerators);
        Collections.sort(expected, Generators.COMPARATOR);
        assertEquals(expected, Generators.getGeneratorsInt());
        assertTrue(cache.isEmpty());
        assertThrows(NullPointerException.class, () -> {
            Generators.remove((Collection<Generator>) null);
        });
        final List<Generator> oldGeneratorsWithNull = Arrays.asList(
                new TestTypedGenerator(),
                null,
                new FallbackGenerator());
        assertThrows(IllegalArgumentException.class, () -> {
            Generators.remove(oldGeneratorsWithNull);
        });
    }

    /**
     * Test for {@link Generators#getGeneratorInt(Class)}.
     */
    @Test
    void testGetGeneratorInt() {
        final Generator mockGenerator = spy(Generator.class);
        willReturn(true).given(mockGenerator).supports(MyType.class);
        Generators.register(mockGenerator);
        final Map<Class<?>, Generator> cache = Generators.getCacheInt();
        assertNotNull(cache);
        assertTrue(cache.isEmpty());
        final Generator result = Generators.getGeneratorInt(MyType.class);
        assertNotNull(result);
        assertSame(mockGenerator, result);
        assertFalse(cache.isEmpty());
        assertEquals(1, cache.size());
        assertEquals(result, cache.get(MyType.class));
        final Generator missingResult = Generators.getGeneratorInt(MyMissingType.class);
        assertNotNull(missingResult);
        assertSame(MissingGenerator.INSTANCE, missingResult);
        assertEquals(2, cache.size());
        assertEquals(MissingGenerator.INSTANCE, cache.get(MyMissingType.class));
    }

    /**
     * Test for {@link Generators#getGenerator(Class)}.
     */
    @Test
    void testGetGenerator() {
        final Generator mockGenerator = spy(Generator.class);
        willReturn(true).given(mockGenerator).supports(MyType.class);
        Generators.register(mockGenerator);
        final Map<Class<?>, Generator> cache = Generators.getCacheInt();
        assertNotNull(cache);
        assertTrue(cache.isEmpty());
        final Generator result = Generators.getGenerator(MyType.class);
        assertNotNull(result);
        assertSame(mockGenerator, result);
        assertFalse(cache.isEmpty());
        assertEquals(1, cache.size());
        assertEquals(result, cache.get(MyType.class));
        final Generator missingResult = Generators.getGenerator(MyMissingType.class);
        assertNull(missingResult);
        assertEquals(2, cache.size());
        assertEquals(MissingGenerator.INSTANCE, cache.get(MyMissingType.class));
    }

    /**
     * Test for {@link Generators#getParameterizableGenerator(Class)}.
     */
    @Test
    void testGetParameterizableGenerator() {
        final ParameterizableGenerator mockParamsGenerator = spy(ParameterizableGenerator.class);
        willReturn(true).given(mockParamsGenerator).supports(MyParameterizableType.class);
        final Generator mockGenerator = spy(Generator.class);
        willReturn(true).given(mockGenerator).supports(MyType.class);
        Generators.register(mockParamsGenerator);
        Generators.register(mockGenerator);
        final Map<Class<?>, Generator> cache = Generators.getCacheInt();
        assertNotNull(cache);
        assertTrue(cache.isEmpty());
        final Generator result = Generators.getParameterizableGenerator(MyParameterizableType.class);
        assertNotNull(result);
        assertSame(mockParamsGenerator, result);
        assertFalse(cache.isEmpty());
        assertEquals(1, cache.size());
        assertEquals(result, cache.get(MyParameterizableType.class));
        final Generator noParamsResult = Generators.getParameterizableGenerator(MyType.class);
        assertNull(noParamsResult);
        assertFalse(cache.isEmpty());
        assertEquals(2, cache.size());
        assertEquals(mockGenerator, cache.get(MyType.class));
        final Generator missingResult = Generators.getParameterizableGenerator(MyMissingType.class);
        assertNull(missingResult);
        assertEquals(3, cache.size());
        assertEquals(MissingGenerator.INSTANCE, cache.get(MyMissingType.class));
    }

    /**
     * Test for {@link Generators#supports(Class)}.
     */
    @Test
    void testSupports() {
        final Generator mockGenerator = spy(Generator.class);
        willReturn(true).given(mockGenerator).supports(MyType.class);
        willReturn(false).given(mockGenerator).supports(MyMissingType.class);
        Generators.register(mockGenerator);
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).shouldHaveNoMoreInteractions();
        assertTrue(Generators.supports(MyType.class));
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).should(times(1)).supports(MyType.class);
        then(mockGenerator).shouldHaveNoMoreInteractions();
        assertFalse(Generators.supports(MyMissingType.class));
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).should(times(1)).supports(MyType.class);
        then(mockGenerator).should(times(1)).supports(MyMissingType.class);
        then(mockGenerator).shouldHaveNoMoreInteractions();
    }

    /**
     * Test for {@link Generators#defaultValue(Class)}.
     */
    @Test
    void testDefaultValue() {
        final Generator mockGenerator = spy(Generator.class);
        final MyType mockValue = mock(MyType.class);
        willReturn(true).given(mockGenerator).supports(MyType.class);
        willReturn(mockValue).given(mockGenerator).defaultValue(MyType.class);
        willReturn(false).given(mockGenerator).supports(MyMissingType.class);
        Generators.register(mockGenerator);
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).shouldHaveNoMoreInteractions();
        assertSame(mockValue, Generators.defaultValue(MyType.class));
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).should(times(1)).supports(MyType.class);
        then(mockGenerator).should(times(1)).defaultValue(MyType.class);
        then(mockGenerator).shouldHaveNoMoreInteractions();
        assertThrows(GeneratorNotFoundException.class, () -> {
            Generators.defaultValue(MyMissingType.class);
        });
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).should(times(1)).supports(MyType.class);
        then(mockGenerator).should(times(1)).defaultValue(MyType.class);
        then(mockGenerator).should(times(1)).supports(MyMissingType.class);
        then(mockGenerator).shouldHaveNoMoreInteractions();
    }

    /**
     * Test for {@link Generators#nullableDefaultValue(Class)}.
     */
    @Test
    void testNullableDefaultValue() {
        final Generator mockGenerator = spy(Generator.class);
        final MyType mockValue = mock(MyType.class);
        willReturn(true).given(mockGenerator).supports(MyType.class);
        willReturn(mockValue).given(mockGenerator).nullableDefaultValue(MyType.class);
        willReturn(false).given(mockGenerator).supports(MyMissingType.class);
        Generators.register(mockGenerator);
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).shouldHaveNoMoreInteractions();
        assertSame(mockValue, Generators.nullableDefaultValue(MyType.class));
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).should(times(1)).supports(MyType.class);
        then(mockGenerator).should(times(1)).nullableDefaultValue(MyType.class);
        then(mockGenerator).shouldHaveNoMoreInteractions();
        assertThrows(GeneratorNotFoundException.class, () -> {
            Generators.nullableDefaultValue(MyMissingType.class);
        });
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).should(times(1)).supports(MyType.class);
        then(mockGenerator).should(times(1)).nullableDefaultValue(MyType.class);
        then(mockGenerator).should(times(1)).supports(MyMissingType.class);
        then(mockGenerator).shouldHaveNoMoreInteractions();
    }

    /**
     * Test for {@link Generators#randomValue(Class)}.
     */
    @Test
    void testRandomValue() {
        final Generator mockGenerator = spy(Generator.class);
        final MyType mockValue = mock(MyType.class);
        willReturn(true).given(mockGenerator).supports(MyType.class);
        willReturn(mockValue).given(mockGenerator).randomValue(MyType.class);
        willReturn(false).given(mockGenerator).supports(MyMissingType.class);
        Generators.register(mockGenerator);
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).shouldHaveNoMoreInteractions();
        assertSame(mockValue, Generators.randomValue(MyType.class));
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).should(times(1)).supports(MyType.class);
        then(mockGenerator).should(times(1)).randomValue(MyType.class);
        then(mockGenerator).shouldHaveNoMoreInteractions();
        assertThrows(GeneratorNotFoundException.class, () -> {
            Generators.randomValue(MyMissingType.class);
        });
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).should(times(1)).supports(MyType.class);
        then(mockGenerator).should(times(1)).randomValue(MyType.class);
        then(mockGenerator).should(times(1)).supports(MyMissingType.class);
        then(mockGenerator).shouldHaveNoMoreInteractions();
    }

    /**
     * Test for {@link Generators#nullableRandomValue(Class)}.
     */
    @Test
    void testNullableRandomValue() {
        final Generator mockGenerator = spy(Generator.class);
        final MyType mockValue = mock(MyType.class);
        willReturn(true).given(mockGenerator).supports(MyType.class);
        willReturn(mockValue).given(mockGenerator).nullableRandomValue(MyType.class);
        willReturn(false).given(mockGenerator).supports(MyMissingType.class);
        Generators.register(mockGenerator);
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).shouldHaveNoMoreInteractions();
        assertSame(mockValue, Generators.nullableRandomValue(MyType.class));
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).should(times(1)).supports(MyType.class);
        then(mockGenerator).should(times(1)).nullableRandomValue(MyType.class);
        then(mockGenerator).shouldHaveNoMoreInteractions();
        assertThrows(GeneratorNotFoundException.class, () -> {
            Generators.nullableRandomValue(MyMissingType.class);
        });
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).should(times(1)).supports(MyType.class);
        then(mockGenerator).should(times(1)).nullableRandomValue(MyType.class);
        then(mockGenerator).should(times(1)).supports(MyMissingType.class);
        then(mockGenerator).shouldHaveNoMoreInteractions();
    }

    /**
     * Test for {@link Generators#defaultValue(Class, GenerationParameters)}.
     */
    @Test
    void testDefaultValueParameterizable() {
        final ParameterizableGenerator mockParamsGenerator = spy(ParameterizableGenerator.class);
        final GenerationParameters params = spy(GenerationParameters.class);
        final MyParameterizableType mockValue = mock(MyParameterizableType.class);
        willReturn(true).given(mockParamsGenerator).supports(MyParameterizableType.class);
        willReturn(mockValue).given(mockParamsGenerator).defaultValue(MyParameterizableType.class, params);
        willReturn(false).given(mockParamsGenerator).supports(MyMissingType.class);
        Generators.register(mockParamsGenerator);
        then(mockParamsGenerator).should(atLeastOnce()).getPriority();
        then(mockParamsGenerator).shouldHaveNoMoreInteractions();
        assertSame(mockValue, Generators.defaultValue(MyParameterizableType.class, params));
        then(mockParamsGenerator).should(atLeastOnce()).getPriority();
        then(mockParamsGenerator).should(times(1)).supports(MyParameterizableType.class);
        then(mockParamsGenerator).should(times(1)).defaultValue(MyParameterizableType.class, params);
        then(mockParamsGenerator).shouldHaveNoMoreInteractions();
        assertThrows(GeneratorNotFoundException.class, () -> {
            Generators.defaultValue(MyMissingType.class, params);
        });
        then(mockParamsGenerator).should(atLeastOnce()).getPriority();
        then(mockParamsGenerator).should(times(1)).supports(MyParameterizableType.class);
        then(mockParamsGenerator).should(times(1)).defaultValue(MyParameterizableType.class, params);
        then(mockParamsGenerator).should(times(1)).supports(MyMissingType.class);
        then(mockParamsGenerator).shouldHaveNoMoreInteractions();
    }

    /**
     * Test for {@link Generators#defaultValue(Class, GenerationParameters)}.
     */
    @Test
    void testDefaultValueNoParameterizable() {
        final Generator mockGenerator = spy(Generator.class);
        final GenerationParameters params = spy(GenerationParameters.class);
        willReturn(true).given(mockGenerator).supports(MyType.class);
        willReturn(false).given(mockGenerator).supports(MyMissingType.class);
        Generators.register(mockGenerator);
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).shouldHaveNoMoreInteractions();
        assertThrows(GeneratorNotParameterizableException.class, () -> {
            Generators.defaultValue(MyType.class, params);
        });
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).should(times(1)).supports(MyType.class);
        then(mockGenerator).shouldHaveNoMoreInteractions();
    }

    /**
     * Test for {@link Generators#nullableDefaultValue(Class, GenerationParameters)}.
     */
    @Test
    void testNullableDefaultValueParameterizable() {
        final ParameterizableGenerator mockParamsGenerator = spy(ParameterizableGenerator.class);
        final GenerationParameters params = spy(GenerationParameters.class);
        final MyParameterizableType mockValue = mock(MyParameterizableType.class);
        willReturn(true).given(mockParamsGenerator).supports(MyParameterizableType.class);
        willReturn(mockValue).given(mockParamsGenerator).nullableDefaultValue(MyParameterizableType.class, params);
        willReturn(false).given(mockParamsGenerator).supports(MyMissingType.class);
        Generators.register(mockParamsGenerator);
        then(mockParamsGenerator).should(atLeastOnce()).getPriority();
        then(mockParamsGenerator).shouldHaveNoMoreInteractions();
        assertSame(mockValue, Generators.nullableDefaultValue(MyParameterizableType.class, params));
        then(mockParamsGenerator).should(atLeastOnce()).getPriority();
        then(mockParamsGenerator).should(times(1)).supports(MyParameterizableType.class);
        then(mockParamsGenerator).should(times(1)).nullableDefaultValue(MyParameterizableType.class, params);
        then(mockParamsGenerator).shouldHaveNoMoreInteractions();
        assertThrows(GeneratorNotFoundException.class, () -> {
            Generators.nullableDefaultValue(MyMissingType.class, params);
        });
        then(mockParamsGenerator).should(atLeastOnce()).getPriority();
        then(mockParamsGenerator).should(times(1)).supports(MyParameterizableType.class);
        then(mockParamsGenerator).should(times(1)).nullableDefaultValue(MyParameterizableType.class, params);
        then(mockParamsGenerator).should(times(1)).supports(MyMissingType.class);
        then(mockParamsGenerator).shouldHaveNoMoreInteractions();
    }

    /**
     * Test for {@link Generators#nullableDefaultValue(Class, GenerationParameters)}.
     */
    @Test
    void testNullableDefaultValueNoParameterizable() {
        final Generator mockGenerator = spy(Generator.class);
        final GenerationParameters params = spy(GenerationParameters.class);
        willReturn(true).given(mockGenerator).supports(MyType.class);
        willReturn(false).given(mockGenerator).supports(MyMissingType.class);
        Generators.register(mockGenerator);
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).shouldHaveNoMoreInteractions();
        assertThrows(GeneratorNotParameterizableException.class, () -> {
            Generators.nullableDefaultValue(MyType.class, params);
        });
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).should(times(1)).supports(MyType.class);
        then(mockGenerator).shouldHaveNoMoreInteractions();
    }

    /**
     * Test for {@link Generators#randomValue(Class, GenerationParameters)}.
     */
    @Test
    void testRandomValueParameterizable() {
        final ParameterizableGenerator mockParamsGenerator = spy(ParameterizableGenerator.class);
        final GenerationParameters params = spy(GenerationParameters.class);
        final MyParameterizableType mockValue = mock(MyParameterizableType.class);
        willReturn(true).given(mockParamsGenerator).supports(MyParameterizableType.class);
        willReturn(mockValue).given(mockParamsGenerator).randomValue(MyParameterizableType.class, params);
        willReturn(false).given(mockParamsGenerator).supports(MyMissingType.class);
        Generators.register(mockParamsGenerator);
        then(mockParamsGenerator).should(atLeastOnce()).getPriority();
        then(mockParamsGenerator).shouldHaveNoMoreInteractions();
        assertSame(mockValue, Generators.randomValue(MyParameterizableType.class, params));
        then(mockParamsGenerator).should(atLeastOnce()).getPriority();
        then(mockParamsGenerator).should(times(1)).supports(MyParameterizableType.class);
        then(mockParamsGenerator).should(times(1)).randomValue(MyParameterizableType.class, params);
        then(mockParamsGenerator).shouldHaveNoMoreInteractions();
        assertThrows(GeneratorNotFoundException.class, () -> {
            Generators.randomValue(MyMissingType.class, params);
        });
        then(mockParamsGenerator).should(atLeastOnce()).getPriority();
        then(mockParamsGenerator).should(times(1)).supports(MyParameterizableType.class);
        then(mockParamsGenerator).should(times(1)).randomValue(MyParameterizableType.class, params);
        then(mockParamsGenerator).should(times(1)).supports(MyMissingType.class);
        then(mockParamsGenerator).shouldHaveNoMoreInteractions();
    }

    /**
     * Test for {@link Generators#randomValue(Class, GenerationParameters)}.
     */
    @Test
    void testRandomValueNoParameterizable() {
        final Generator mockGenerator = spy(Generator.class);
        final GenerationParameters params = spy(GenerationParameters.class);
        willReturn(true).given(mockGenerator).supports(MyType.class);
        willReturn(false).given(mockGenerator).supports(MyMissingType.class);
        Generators.register(mockGenerator);
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).shouldHaveNoMoreInteractions();
        assertThrows(GeneratorNotParameterizableException.class, () -> {
            Generators.randomValue(MyType.class, params);
        });
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).should(times(1)).supports(MyType.class);
        then(mockGenerator).shouldHaveNoMoreInteractions();
    }

    /**
     * Test for {@link Generators#nullableRandomValue(Class, GenerationParameters)}.
     */
    @Test
    void testNullableRandomValueParameterizable() {
        final ParameterizableGenerator mockParamsGenerator = spy(ParameterizableGenerator.class);
        final GenerationParameters params = spy(GenerationParameters.class);
        final MyParameterizableType mockValue = mock(MyParameterizableType.class);
        willReturn(true).given(mockParamsGenerator).supports(MyParameterizableType.class);
        willReturn(mockValue).given(mockParamsGenerator).nullableRandomValue(MyParameterizableType.class, params);
        willReturn(false).given(mockParamsGenerator).supports(MyMissingType.class);
        Generators.register(mockParamsGenerator);
        then(mockParamsGenerator).should(atLeastOnce()).getPriority();
        then(mockParamsGenerator).shouldHaveNoMoreInteractions();
        assertSame(mockValue, Generators.nullableRandomValue(MyParameterizableType.class, params));
        then(mockParamsGenerator).should(atLeastOnce()).getPriority();
        then(mockParamsGenerator).should(times(1)).supports(MyParameterizableType.class);
        then(mockParamsGenerator).should(times(1)).nullableRandomValue(MyParameterizableType.class, params);
        then(mockParamsGenerator).shouldHaveNoMoreInteractions();
        assertThrows(GeneratorNotFoundException.class, () -> {
            Generators.nullableRandomValue(MyMissingType.class, params);
        });
        then(mockParamsGenerator).should(atLeastOnce()).getPriority();
        then(mockParamsGenerator).should(times(1)).supports(MyParameterizableType.class);
        then(mockParamsGenerator).should(times(1)).nullableRandomValue(MyParameterizableType.class, params);
        then(mockParamsGenerator).should(times(1)).supports(MyMissingType.class);
        then(mockParamsGenerator).shouldHaveNoMoreInteractions();
    }

    /**
     * Test for {@link Generators#nullableRandomValue(Class, GenerationParameters)}.
     */
    @Test
    void testNullableRandomValueNoParameterizable() {
        final Generator mockGenerator = spy(Generator.class);
        final GenerationParameters params = spy(GenerationParameters.class);
        willReturn(true).given(mockGenerator).supports(MyType.class);
        willReturn(false).given(mockGenerator).supports(MyMissingType.class);
        Generators.register(mockGenerator);
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).shouldHaveNoMoreInteractions();
        assertThrows(GeneratorNotParameterizableException.class, () -> {
            Generators.nullableRandomValue(MyType.class, params);
        });
        then(mockGenerator).should(atLeastOnce()).getPriority();
        then(mockGenerator).should(times(1)).supports(MyType.class);
        then(mockGenerator).shouldHaveNoMoreInteractions();
    }

    /**
     * Test for {@link MissingGenerator#supports(Class)}.
     */
    @Test
    void testMissingGenerator_Support() {
        assertFalse(MissingGenerator.INSTANCE.supports(Object.class));
    }

    /**
     * Test for {@link MissingGenerator#defaultValue(Class)}.
     */
    @Test
    void testMissingGenerator_DefaultValue() {
        assertThrows(GeneratorNotFoundException.class, () -> {
            MissingGenerator.INSTANCE.defaultValue(Object.class);
        });
    }

    /**
     * Test for {@link MissingGenerator#nullableDefaultValue(Class)}.
     */
    @Test
    void testMissingGenerator_NullableDefaultValue() {
        assertThrows(GeneratorNotFoundException.class, () -> {
            MissingGenerator.INSTANCE.nullableDefaultValue(Object.class);
        });
    }

    /**
     * Test for {@link MissingGenerator#randomValue(Class)}.
     */
    @Test
    void testMissingGenerator_RandomValue() {
        assertThrows(GeneratorNotFoundException.class, () -> {
            MissingGenerator.INSTANCE.randomValue(Object.class);
        });
    }

    /**
     * Test for {@link MissingGenerator#nullableRandomValue(Class)}.
     */
    @Test
    void testMissingGenerator_NullableRandomValue() {
        assertThrows(GeneratorNotFoundException.class, () -> {
            MissingGenerator.INSTANCE.nullableRandomValue(Object.class);
        });
    }

    private static class MyType {
        private MyType() {}
    }
    private static class MyMissingType {
        private MyMissingType() {}
    }
    private static class MyParameterizableType {
        private MyParameterizableType() {}
    }
    @Priority(Priority.MAX)
    private static class ImportantGenerator
    extends MockGenerator {}
    @Priority(Priority.DEFAULT)
    private static class DefaultGenerator
    extends MockGenerator {}
    private static class NoAnnotationGenerator
    extends MockGenerator {}
    @Priority(Priority.MIN)
    private static class FallbackGenerator
    extends MockGenerator {}
}
