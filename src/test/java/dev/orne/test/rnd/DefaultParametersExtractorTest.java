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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

/**
 * Unit tests for {@code DefaultParametersExtractor}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see DefaultParametersExtractor
 */
@Tag("ut")
class DefaultParametersExtractorTest {

    /**
     * Unit test for {@link DefaultParametersExtractor#DefaultParametersExtractor(Collection)}.
     */
    @Test
    void testConstructor() {
        @SuppressWarnings("unchecked")
        final ParametersSourceExtractor<? super MyParams, ?> extractor1 = spy(ParametersSourceExtractor.class);
        @SuppressWarnings("unchecked")
        final ParametersSourceExtractor<MyParams, ?> extractor2 = spy(ParametersSourceExtractor.class);
        @SuppressWarnings("unchecked")
        final ParametersSourceExtractor<MyParams, ?> extractor3 = spy(ParametersSourceExtractor.class);
        assertThrows(NullPointerException.class, () -> {
            new DefaultParametersExtractor<>(null);
        });
        final List<ParametersSourceExtractor<? super MyParams, ?>> extractorsWithNulls = Arrays.asList(
                extractor1,
                null,
                extractor2,
                extractor3
        );
        assertThrows(IllegalArgumentException.class, () -> {
            new DefaultParametersExtractor<>(extractorsWithNulls);
        });
        final Collection<ParametersSourceExtractor<? super MyParams, ?>> extractors = new ArrayList<>();
        extractors.add(extractor1);
        extractors.add(extractor2);
        final DefaultParametersExtractor<MyParams> result = new DefaultParametersExtractor<>(extractors);
        assertNotSame(extractors, result.getExtractors());
        assertEquals(extractors, result.getExtractors());
        final Collection<ParametersSourceExtractor<? super MyParams, ?>> extractorsBck = new ArrayList<>(extractors);
        extractors.add(extractor3);
        assertNotEquals(extractors, result.getExtractors());
        assertEquals(extractorsBck, result.getExtractors());
    }

    /**
     * Unit test for {@link DefaultParametersExtractor#extractParameters(Object, Object...)}.
     */
    @Test
    void testExtractParametersVarargs() {
        final DefaultParametersExtractor<MyParams> generator = spy(new DefaultParametersExtractor<>(
                Collections.emptyList()));
        final MyParams params = mock(MyParams.class);
        final MySource source1 = mock(MySource.class);
        final MySource source2 = mock(MySource.class);
        willDoNothing().given(generator).extractParameters(eq(params), any(Collection.class));
        assertThrows(NullPointerException.class, () -> {
            generator.extractParameters(null, (Object[]) null);
        });
        assertThrows(NullPointerException.class, () -> {
            generator.extractParameters(null, source1, source2);
        });
        assertThrows(NullPointerException.class, () -> {
            generator.extractParameters(params, (Object[]) null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            generator.extractParameters(params, source1, null, source2);
        });
        generator.extractParameters(params, source1, source2);
        then(generator).should().extractParameters(params, Arrays.asList(source1, source2));
        then(params).shouldHaveNoInteractions();
        then(source1).shouldHaveNoInteractions();
        then(source2).shouldHaveNoInteractions();
    }

    /**
     * Unit test for {@link DefaultParametersExtractor#extractParameters(Object, Collection)}.
     */
    @Test
    void testExtractParametersCollection() {
        final DefaultParametersExtractor<MyParams> generator = spy(new DefaultParametersExtractor<>(
                Collections.emptyList()));
        final MyParams params = mock(MyParams.class);
        final MySource source1 = mock(MySource.class);
        final MySource source2 = mock(MySource.class);
        final List<Object> sources = Arrays.asList(source1, source2);
        willDoNothing().given(generator).extract(eq(params), any());
        assertThrows(NullPointerException.class, () -> {
            generator.extractParameters(null, (Collection<?>) null);
        });
        assertThrows(NullPointerException.class, () -> {
            generator.extractParameters(null, sources);
        });
        assertThrows(NullPointerException.class, () -> {
            generator.extractParameters(params, (Collection<?>) null);
        });
        final List<Object> sourcesWithNull = Arrays.asList(source1, null, source2);
        assertThrows(IllegalArgumentException.class, () -> {
            generator.extractParameters(params, sourcesWithNull);
        });
        generator.extractParameters(params, sources);
        then(generator).should().extract(params, source1);
        then(generator).should().extract(params, source2);
        then(params).shouldHaveNoInteractions();
        then(source1).shouldHaveNoInteractions();
        then(source2).shouldHaveNoInteractions();
    }

    /**
     * Unit test for {@link DefaultParametersExtractor#extract(Object, Object)}.
     */
    @Test
    void testExtract() {
        @SuppressWarnings("unchecked")
        final ParametersSourceExtractor<MyParams, MySource> extractor1 = spy(ParametersSourceExtractor.class);
        willReturn(MySource.class).given(extractor1).getSourceType();
        @SuppressWarnings("unchecked")
        final ParametersSourceExtractor<MyParams, MySource> extractor2 = spy(ParametersSourceExtractor.class);
        willReturn(MySource.class).given(extractor2).getSourceType();
        @SuppressWarnings("unchecked")
        final ParametersSourceExtractor<MyParams, MySourceExt> extractor3 = spy(ParametersSourceExtractor.class);
        willReturn(MySourceExt.class).given(extractor3).getSourceType();
        final DefaultParametersExtractor<MyParams> generator = spy(new DefaultParametersExtractor<>(
                Arrays.asList(extractor1, extractor2, extractor3)));
        final MyParams params = mock(MyParams.class);
        final MySourceExt source = mock(MySourceExt.class);
        generator.extract(params, source);
        then(extractor1).should(atLeast(0)).getSourceType();
        then(extractor2).should(atLeast(0)).getSourceType();
        then(extractor3).should(atLeast(0)).getSourceType();
        final InOrder order = inOrder(extractor1, extractor2, extractor3);
        then(extractor3).should(order).extractParameters(source, params);
        then(extractor2).should(order).extractParameters(source, params);
        then(extractor1).should(order).extractParameters(source, params);
        then(extractor1).shouldHaveNoMoreInteractions();
        then(extractor2).shouldHaveNoMoreInteractions();
        then(extractor3).shouldHaveNoMoreInteractions();
    }

    /**
     * Unit test for {@link DefaultParametersExtractor#extract(Object, Object)}.
     */
    @Test
    void testExtractPartial() {
        @SuppressWarnings("unchecked")
        final ParametersSourceExtractor<MyParams, MySource> extractor1 = spy(ParametersSourceExtractor.class);
        willReturn(MySource.class).given(extractor1).getSourceType();
        @SuppressWarnings("unchecked")
        final ParametersSourceExtractor<MyParams, MySourceExt> extractor2 = spy(ParametersSourceExtractor.class);
        willReturn(MySourceExt.class).given(extractor2).getSourceType();
        @SuppressWarnings("unchecked")
        final ParametersSourceExtractor<MyParams, MySource> extractor3 = spy(ParametersSourceExtractor.class);
        willReturn(MySource.class).given(extractor3).getSourceType();
        final DefaultParametersExtractor<MyParams> generator = spy(new DefaultParametersExtractor<>(
                Arrays.asList(extractor1, extractor2, extractor3)));
        final MyParams params = mock(MyParams.class);
        final MySource source = mock(MySource.class);
        generator.extract(params, source);
        then(extractor1).should(atLeast(0)).getSourceType();
        then(extractor2).should(atLeast(0)).getSourceType();
        then(extractor3).should(atLeast(0)).getSourceType();
        final InOrder order = inOrder(extractor1, extractor2, extractor3);
        then(extractor3).should(order).extractParameters(source, params);
        then(extractor1).should(order).extractParameters(source, params);
        then(extractor1).shouldHaveNoMoreInteractions();
        then(extractor2).shouldHaveNoMoreInteractions();
        then(extractor3).shouldHaveNoMoreInteractions();
    }

    /**
     * Unit test for {@link DefaultParametersExtractor#extract(Object, Object)}.
     */
    @Test
    void testExtractUnsupported() {
        @SuppressWarnings("unchecked")
        final ParametersSourceExtractor<MyParams, ?> extractor1 = spy(ParametersSourceExtractor.class);
        willReturn(MySource.class).given(extractor1).getSourceType();
        @SuppressWarnings("unchecked")
        final ParametersSourceExtractor<MyParams, ?> extractor2 = spy(ParametersSourceExtractor.class);
        willReturn(MySource.class).given(extractor2).getSourceType();
        @SuppressWarnings("unchecked")
        final ParametersSourceExtractor<MyParams, ?> extractor3 = spy(ParametersSourceExtractor.class);
        willReturn(MySourceExt.class).given(extractor3).getSourceType();
        final DefaultParametersExtractor<MyParams> generator = spy(new DefaultParametersExtractor<>(
                Arrays.asList(extractor1, extractor2, extractor3)));
        final MyParams params = mock(MyParams.class);
        final OtherSource source = mock(OtherSource.class);
        generator.extract(params, source);
        then(params).shouldHaveNoInteractions();
        then(extractor1).should(atLeast(0)).getSourceType();
        then(extractor2).should(atLeast(0)).getSourceType();
        then(extractor3).should(atLeast(0)).getSourceType();
        then(extractor1).shouldHaveNoMoreInteractions();
        then(extractor2).shouldHaveNoMoreInteractions();
        then(extractor3).shouldHaveNoMoreInteractions();
    }

    /**
     * Unit test for {@link DefaultParametersExtractor#equals(Object)},
     * {@link DefaultParametersExtractor#hashCode()} and
     * {@link DefaultParametersExtractor#toString()}.
     */
    @Test
    @SuppressWarnings({ "java:S5785", "unlikely-arg-type" })
    void testEqualsHashCodeToString() {
        final DefaultParametersExtractor<?> generator = new DefaultParametersExtractor<>(
                Collections.emptyList());
        assertFalse(generator.equals(null));
        assertTrue(generator.equals(generator));
        assertFalse(generator.equals(new TestGenerator()));
        DefaultParametersExtractor<?> other = new DefaultParametersExtractor<>(
                Collections.emptyList());
        assertTrue(generator.equals(other));
        assertEquals(generator.hashCode(), other.hashCode());
        assertEquals(generator.toString(), other.toString());
        other = new DefaultParametersExtractor<>(
                Arrays.asList(
                        new TestSourceExtractor()
                ));
        assertFalse(generator.equals(other));
    }

    private static interface MyParams {}
    private static interface MySource {}
    private static interface MySourceExt
    extends MySource {}
    private static interface OtherSource {}
}
