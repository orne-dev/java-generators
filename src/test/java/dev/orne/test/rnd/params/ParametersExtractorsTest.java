package dev.orne.test.rnd.params;

/*-
 * #%L
 * Orne Test ParametersExtractors
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

import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code ParametersExtractors}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see ParametersExtractors
 */
@Tag("ut")
class ParametersExtractorsTest {

    /**
     * Reset registered extractors.
     */
    @AfterEach
    void resetExtractors() {
        ParametersExtractors.setFilter(ParametersExtractors.DEFAULT_FILTER);
        ParametersExtractors.setBuilder(ParametersExtractors.DEFAULT_BUILDER);
        ParametersExtractors.reset();
    }

    /**
     * Test for {@link ParametersExtractors#COMPARATOR}.
     */
    @Test
    void testComparator() {
        final ParametersSourceExtractor<?, ?> importantGenerator = new ImportantExtractor();
        final ParametersSourceExtractor<?, ?> defaultGenerator = new DefaultExtractor();
        final ParametersSourceExtractor<?, ?> noAnnotationGenerator = new NoAnnotationExtractor();
        final ParametersSourceExtractor<?, ?> fallbackGenerator = new FallbackExtractor();
        final List<ParametersSourceExtractor<?, ?>> list = Arrays.asList(
                defaultGenerator,
                importantGenerator,
                fallbackGenerator,
                noAnnotationGenerator);
        final List<ParametersSourceExtractor<?, ?>> expected = Arrays.asList(
                importantGenerator,
                defaultGenerator,
                noAnnotationGenerator,
                fallbackGenerator);
        Collections.sort(list, ParametersExtractors.COMPARATOR);
        assertEquals(expected, list);
    }

    /**
     * Test for {@link ParametersExtractors#loadSpiExtractors()}.
     */
    @Test
    void testLoadSpiGenerators() {
        final List<ParametersSourceExtractor<?, ?>> result = ParametersExtractors.loadSpiExtractors();
        assertNotNull(result);
        assertTrue(result.contains(new TestSourceExtractor()));
        final List<ParametersSourceExtractor<?, ?>> other = ParametersExtractors.loadSpiExtractors();
        assertNotSame(result, other);
    }

    /**
     * Test for {@link ParametersExtractors#getCacheInt()}.
     */
    @Test
    void testGetCacheInt() {
        final Map<Class<?>, ParametersExtractor<?>> result = ParametersExtractors.getCacheInt();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Test for {@link ParametersExtractors#getSourceExtractorsInt()}.
     */
    @Test
    void testGetSourceExtractorsInt() {
        final List<ParametersSourceExtractor<?, ?>> result = ParametersExtractors.getSourceExtractorsInt();
        assertNotNull(result);
        final List<ParametersSourceExtractor<?, ?>> expected = ParametersExtractors.loadSpiExtractors();
        Collections.sort(expected, ParametersExtractors.COMPARATOR);
        assertEquals(expected, result);
        final List<ParametersSourceExtractor<?, ?>> other = ParametersExtractors.getSourceExtractorsInt();
        assertSame(result, other);
        final Map<Class<?>, ParametersExtractor<?>> cache = ParametersExtractors.getCacheInt();
        assertNotNull(cache);
        assertTrue(cache.isEmpty());
    }

    /**
     * Test for {@link ParametersExtractors#getRegisteredSourceExtractors()}.
     */
    @Test
    void testGetRegisteredSourceExtractors() {
        final List<ParametersSourceExtractor<?, ?>> result = ParametersExtractors.getRegisteredSourceExtractors();
        assertNotNull(result);
        assertNotSame(result, ParametersExtractors.getSourceExtractorsInt());
        assertEquals(result, ParametersExtractors.getSourceExtractorsInt());
        ParametersExtractors.reset();
        final Map<Class<?>, ParametersExtractor<?>> cache = ParametersExtractors.getCacheInt();
        assertNotNull(cache);
        assertTrue(cache.isEmpty());
        final ParametersSourceExtractor<?, ?> otherExtractor = new DefaultExtractor();
        final List<ParametersSourceExtractor<?, ?>> otherGenerators = Collections.singletonList(otherExtractor);
        assertThrows(UnsupportedOperationException.class, () -> {
            result.add(otherExtractor);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            result.add(0, otherExtractor);
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
        final Iterator<ParametersSourceExtractor<?, ?>> it = result.iterator();
        assertThrows(UnsupportedOperationException.class, () -> {
            it.remove();
        });
        final ListIterator<ParametersSourceExtractor<?, ?>> lit = result.listIterator();
        assertThrows(UnsupportedOperationException.class, () -> {
            lit.add(otherExtractor);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            lit.remove();
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            lit.set(otherExtractor);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            result.remove(0);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            result.remove(otherExtractor);
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
            result.set(0, otherExtractor);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            result.sort((c, v) -> 1);
        });
    }

    /**
     * Test for {@link ParametersExtractors#reset()}.
     */
    @Test
    void testReset() {
        final List<ParametersSourceExtractor<?, ?>> result = ParametersExtractors.getSourceExtractorsInt();
        List<ParametersSourceExtractor<?, ?>> other = ParametersExtractors.getSourceExtractorsInt();
        assertSame(result, other);
        ParametersExtractors.getExtractor(TestSourceExtractor.Params.class);
        final Map<Class<?>, ParametersExtractor<?>> cache = ParametersExtractors.getCacheInt();
        assertNotNull(cache);
        assertFalse(cache.isEmpty());
        ParametersExtractors.reset();
        other = ParametersExtractors.getSourceExtractorsInt();
        assertNotSame(result, other);
        ParametersExtractors.reset();
        assertTrue(cache.isEmpty());
    }

    /**
     * Test for {@link ParametersExtractors#register(ParametersSourceExtractor...)}.
     */
    @Test
    void testRegister_Varargs() {
        final Map<Class<?>, ParametersExtractor<?>> cache = ParametersExtractors.getCacheInt();
        ParametersExtractors.getExtractor(TestSourceExtractor.Params.class);
        assertFalse(cache.isEmpty());
        final ParametersSourceExtractor<?, ?>[] newExtractors = new ParametersSourceExtractor<?, ?>[] {
                new ImportantExtractor(),
                new DefaultExtractor(),
                new NoAnnotationExtractor(),
                new FallbackExtractor()
        };
        ParametersExtractors.register(newExtractors);
        final List<ParametersSourceExtractor<?, ?>> expected = ParametersExtractors.loadSpiExtractors();
        expected.addAll(Arrays.asList(newExtractors));
        Collections.sort(expected, ParametersExtractors.COMPARATOR);
        assertEquals(expected, ParametersExtractors.getSourceExtractorsInt());
        assertTrue(cache.isEmpty());
        assertThrows(NullPointerException.class, () -> {
            ParametersExtractors.register((ParametersSourceExtractor<?, ?>[]) null);
        });
        final ParametersSourceExtractor<?, ?>[] newGeneratorsWithNull = new ParametersSourceExtractor<?, ?>[] {
                new ImportantExtractor(),
                new DefaultExtractor(),
                null,
                new NoAnnotationExtractor(),
                new FallbackExtractor()
        };
        assertThrows(IllegalArgumentException.class, () -> {
            ParametersExtractors.register(newGeneratorsWithNull);
        });
    }

    /**
     * Test for {@link ParametersExtractors#register(ParametersSourceExtractor)}.
     */
    @Test
    void testRegister_Collection() {
        final Map<Class<?>, ParametersExtractor<?>> cache = ParametersExtractors.getCacheInt();
        ParametersExtractors.getExtractor(TestSourceExtractor.Params.class);
        assertFalse(cache.isEmpty());
        final List<ParametersSourceExtractor<?, ?>> newExtractors = Arrays.asList(
                new ImportantExtractor(),
                new DefaultExtractor(),
                new NoAnnotationExtractor(),
                new FallbackExtractor());
        ParametersExtractors.register(newExtractors);
        final List<ParametersSourceExtractor<?, ?>> expected = ParametersExtractors.loadSpiExtractors();
        expected.addAll(newExtractors);
        Collections.sort(expected, ParametersExtractors.COMPARATOR);
        assertEquals(expected, ParametersExtractors.getSourceExtractorsInt());
        assertTrue(cache.isEmpty());
        assertThrows(NullPointerException.class, () -> {
            ParametersExtractors.register((Collection<ParametersSourceExtractor<?, ?>>) null);
        });
        final List<ParametersSourceExtractor<?, ?>> newGeneratorsWithNull = Arrays.asList(
                new ImportantExtractor(),
                new DefaultExtractor(),
                null,
                new NoAnnotationExtractor(),
                new FallbackExtractor());
        assertThrows(IllegalArgumentException.class, () -> {
            ParametersExtractors.register(newGeneratorsWithNull);
        });
    }

    /**
     * Test for {@link ParametersExtractors#remove(ParametersSourceExtractor...)}.
     */
    @Test
    void testRemove_Varargs() {
        final Map<Class<?>, ParametersExtractor<?>> cache = ParametersExtractors.getCacheInt();
        ParametersExtractors.getExtractor(TestSourceExtractor.Params.class);
        assertFalse(cache.isEmpty());
        final ParametersSourceExtractor<?, ?>[] oldGenerators = new ParametersSourceExtractor<?, ?>[] {
                new TestSourceExtractor()
        };
        ParametersExtractors.remove(oldGenerators);
        final List<ParametersSourceExtractor<?, ?>> expected = ParametersExtractors.loadSpiExtractors();
        assertEquals(expected.size() - oldGenerators.length, ParametersExtractors.getSourceExtractorsInt().size());
        expected.removeAll(Arrays.asList(oldGenerators));
        Collections.sort(expected, ParametersExtractors.COMPARATOR);
        assertEquals(expected, ParametersExtractors.getSourceExtractorsInt());
        assertTrue(cache.isEmpty());
        assertThrows(NullPointerException.class, () -> {
            ParametersExtractors.remove((ParametersSourceExtractor<?, ?>[]) null);
        });
        final ParametersSourceExtractor<?, ?>[] oldGeneratorsWithNull = new ParametersSourceExtractor<?, ?>[] {
                new TestSourceExtractor(),
                null,
                new FallbackExtractor()
        };
        assertThrows(IllegalArgumentException.class, () -> {
            ParametersExtractors.remove(oldGeneratorsWithNull);
        });
    }

    /**
     * Test for {@link ParametersExtractors#remove(Collection)}.
     */
    @Test
    void testRemove_Collection() {
        final Map<Class<?>, ParametersExtractor<?>> cache = ParametersExtractors.getCacheInt();
        ParametersExtractors.getExtractor(TestSourceExtractor.Params.class);
        assertFalse(cache.isEmpty());
        final List<ParametersSourceExtractor<?, ?>> oldGenerators = Arrays.asList(
                new TestSourceExtractor());
        ParametersExtractors.remove(oldGenerators);
        final List<ParametersSourceExtractor<?, ?>> expected = ParametersExtractors.loadSpiExtractors();
        assertEquals(expected.size() - oldGenerators.size(), ParametersExtractors.getSourceExtractorsInt().size());
        expected.removeAll(oldGenerators);
        Collections.sort(expected, ParametersExtractors.COMPARATOR);
        assertEquals(expected, ParametersExtractors.getSourceExtractorsInt());
        assertTrue(cache.isEmpty());
        assertThrows(NullPointerException.class, () -> {
            ParametersExtractors.remove((Collection<ParametersSourceExtractor<?, ?>>) null);
        });
        final List<ParametersSourceExtractor<?, ?>> oldGeneratorsWithNull = Arrays.asList(
                new TestSourceExtractor(),
                null,
                new FallbackExtractor());
        assertThrows(IllegalArgumentException.class, () -> {
            ParametersExtractors.remove(oldGeneratorsWithNull);
        });
    }

    /**
     * Test for {@link ParametersExtractors#filterSourceExtractors(List, Class)}.
     */
    @Test
    void testFilterSourceExtractors() {
        verifyFilterSourceExtractors(ParametersExtractors::filterSourceExtractors);
    }

    /**
     * Test for {@link ParametersExtractors#DEFAULT_FILTER}.
     */
    @Test
    void testDefaultFilter() {
        assertSame(ParametersExtractors.DEFAULT_FILTER, ParametersExtractors.getFilter());
        verifyFilterSourceExtractors(ParametersExtractors.DEFAULT_FILTER);
    }

    private void verifyFilterSourceExtractors(
            final ParametersExtractors.SourceExtractorFilter filter) {
        final ParametersSourceExtractor<?, ?> baseExtractor = spy(MyExtractor.class);
        willReturn(MyParams.class).given(baseExtractor).getParametersType();
        willReturn(MySource.class).given(baseExtractor).getSourceType();
        final ParametersSourceExtractor<?, ?> extParamsExtractor = spy(MyExtractor.class);
        willReturn(MyParamsExt.class).given(extParamsExtractor).getParametersType();
        willReturn(MySource.class).given(extParamsExtractor).getSourceType();
        final ParametersSourceExtractor<?, ?> extSourceExtractor = spy(MyExtractor.class);
        willReturn(MyParams.class).given(extSourceExtractor).getParametersType();
        willReturn(MySourceExt.class).given(extSourceExtractor).getSourceType();
        final ParametersSourceExtractor<?, ?> extFullExtractor = spy(MyExtractor.class);
        willReturn(MyParamsExt.class).given(extFullExtractor).getParametersType();
        willReturn(MySourceExt.class).given(extFullExtractor).getSourceType();
        final List<ParametersSourceExtractor<?, ?>> extractors = Arrays.asList(
                extFullExtractor,
                extSourceExtractor,
                extParamsExtractor,
                baseExtractor);
        final List<ParametersSourceExtractor<? super MyParams, ?>> result = ParametersExtractors.filterSourceExtractors(
                extractors,
                MyParams.class);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertSame(extSourceExtractor, result.get(0));
        assertSame(baseExtractor, result.get(1));
        final List<ParametersSourceExtractor<? super MyParamsExt, ?>> extResult = ParametersExtractors.filterSourceExtractors(
                extractors,
                MyParamsExt.class);
        assertNotNull(extResult);
        assertEquals(4, extResult.size());
        assertSame(extFullExtractor, extResult.get(0));
        assertSame(extSourceExtractor, extResult.get(1));
        assertSame(extParamsExtractor, extResult.get(2));
        assertSame(baseExtractor, extResult.get(3));
    }

    /**
     * Test for {@link ParametersExtractors#setFilter(ParametersExtractors.SourceExtractorFilter)}.
     */
    @Test
    void testSetFilter() {
        ParametersExtractors.getExtractor(TestSourceExtractor.Params.class);
        final Map<Class<?>, ParametersExtractor<?>> cache = ParametersExtractors.getCacheInt();
        assertNotNull(cache);
        assertFalse(cache.isEmpty());
        final ParametersExtractors.SourceExtractorFilter mockFilter = spy(ParametersExtractors.SourceExtractorFilter.class);
        ParametersExtractors.setFilter(mockFilter);
        assertSame(mockFilter, ParametersExtractors.getFilter());
        assertTrue(cache.isEmpty());
    }

    /**
     * Test for {@link ParametersExtractors#DEFAULT_BUILDER}.
     */
    @Test
    void testDefaultBuilder() {
        assertSame(ParametersExtractors.DEFAULT_BUILDER, ParametersExtractors.getBuilder());
        @SuppressWarnings("unchecked")
        final ParametersSourceExtractor<? super MyParams, ?> extractor1 = spy(MyExtractor.class);
        @SuppressWarnings("unchecked")
        final ParametersSourceExtractor<? super MyParams, ?> extractor2 = spy(MyExtractor.class);
        final List<ParametersSourceExtractor<? super MyParams, ?>> extractors = Arrays.asList(
                extractor1,
                extractor2);
        final DefaultParametersExtractor<MyParams> expected = new DefaultParametersExtractor<>(extractors);
        final ParametersExtractor<MyParams> result = ParametersExtractors.DEFAULT_BUILDER.create(
                extractors);
        assertNotNull(result);
        assertEquals(expected, result);
    }

    /**
     * Test for {@link ParametersExtractors#setBuilder(ParametersExtractors.ExtractorBuilder)}.
     */
    @Test
    void testSetBuilder() {
        ParametersExtractors.getExtractor(TestSourceExtractor.Params.class);
        final Map<Class<?>, ParametersExtractor<?>> cache = ParametersExtractors.getCacheInt();
        assertNotNull(cache);
        assertFalse(cache.isEmpty());
        final ParametersExtractors.ExtractorBuilder mockBuilder = spy(ParametersExtractors.ExtractorBuilder.class);
        ParametersExtractors.setBuilder(mockBuilder);
        assertSame(mockBuilder, ParametersExtractors.getBuilder());
        assertTrue(cache.isEmpty());
    }

    /**
     * Test for {@link ParametersExtractors#createExtractor(Class)}.
     */
    @Test
    void testCreateExtractor() {
        @SuppressWarnings("unchecked")
        final ParametersSourceExtractor<? super MyParams, ?> extractor1 = spy(MyExtractor.class);
        @SuppressWarnings("unchecked")
        final ParametersSourceExtractor<? super MyParams, ?> extractor2 = spy(MyExtractor.class);
        final List<ParametersSourceExtractor<? super MyParams, ?>> extractors = Arrays.asList(
                extractor1,
                extractor2);
        final ParametersExtractors.SourceExtractorFilter mockFilter = spy(ParametersExtractors.SourceExtractorFilter.class);
        willReturn(extractors).given(mockFilter).findSuitable(ParametersExtractors.getSourceExtractorsInt(), MyParams.class);
        ParametersExtractors.setFilter(mockFilter);
        final ParametersExtractor<?> mockExtractor = spy(ParametersExtractor.class);
        final ParametersExtractors.ExtractorBuilder mockBuilder = spy(ParametersExtractors.ExtractorBuilder.class);
        willReturn(mockExtractor).given(mockBuilder).create(extractors);
        ParametersExtractors.setBuilder(mockBuilder);
        final Map<Class<?>, ParametersExtractor<?>> cache = ParametersExtractors.getCacheInt();
        assertNotNull(cache);
        assertTrue(cache.isEmpty());
        final ParametersExtractor<?> result = ParametersExtractors.createExtractor(MyParams.class);
        assertSame(mockExtractor, result);
        then(mockFilter).should().findSuitable(ParametersExtractors.getRegisteredSourceExtractors(), MyParams.class);
        then(mockBuilder).should().create(extractors);
        assertTrue(cache.isEmpty());
    }

    /**
     * Test for {@link ParametersExtractors#getExtractor(Class)}.
     */
    @Test
    void testGetExtractor() {
        @SuppressWarnings("unchecked")
        final ParametersSourceExtractor<? super MyParams, ?> extractor1 = spy(MyExtractor.class);
        @SuppressWarnings("unchecked")
        final ParametersSourceExtractor<? super MyParams, ?> extractor2 = spy(MyExtractor.class);
        final List<ParametersSourceExtractor<? super MyParams, ?>> extractors = Arrays.asList(
                extractor1,
                extractor2);
        final ParametersExtractors.SourceExtractorFilter mockFilter = spy(ParametersExtractors.SourceExtractorFilter.class);
        willReturn(extractors).given(mockFilter).findSuitable(ParametersExtractors.getRegisteredSourceExtractors(), MyParams.class);
        ParametersExtractors.setFilter(mockFilter);
        final ParametersExtractor<?> mockExtractor = spy(ParametersExtractor.class);
        final ParametersExtractors.ExtractorBuilder mockBuilder = spy(ParametersExtractors.ExtractorBuilder.class);
        willReturn(mockExtractor).given(mockBuilder).create(extractors);
        ParametersExtractors.setBuilder(mockBuilder);
        final Map<Class<?>, ParametersExtractor<?>> cache = ParametersExtractors.getCacheInt();
        assertNotNull(cache);
        assertTrue(cache.isEmpty());
        final ParametersExtractor<?> result = ParametersExtractors.getExtractor(MyParams.class);
        assertSame(mockExtractor, result);
        then(mockFilter).should().findSuitable(ParametersExtractors.getRegisteredSourceExtractors(), MyParams.class);
        then(mockBuilder).should().create(extractors);
        assertFalse(cache.isEmpty());
        assertEquals(1, cache.size());
        assertEquals(result, cache.get(MyParams.class));
    }

    private static interface MyParams {}
    private static interface MyParamsExt
    extends MyParams {}
    private static interface MySource {}
    private static interface MySourceExt
    extends MySource {}
    private interface MyExtractor<P, T>
    extends ParametersSourceExtractor<P, T> {}
    @Priority(Priority.MAX)
    private static class ImportantExtractor
    extends MockSourceExtractor {}
    @Priority(Priority.DEFAULT)
    private static class DefaultExtractor
    extends MockSourceExtractor {}
    private static class NoAnnotationExtractor
    extends MockSourceExtractor {}
    @Priority(Priority.MIN)
    private static class FallbackExtractor
    extends MockSourceExtractor {}
}
