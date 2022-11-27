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

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.AbstractGenerator;

/**
 * Unit tests for {@code AbstractParametersSourceExtractor}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see AbstractParametersSourceExtractor
 */
@Tag("ut")
class AbstractParametersSourceExtractorTest {

    /**
     * Unit test for {@link AbstractParametersSourceExtractor#AbstractParametersSourceExtractor()}
     */
    @Test
    void testTypeInferred() {
        final DirectChild extractor = new DirectChild();
        assertSame(MyParams.class, extractor.getParametersType());
        assertSame(MySource.class, extractor.getSourceType());
    }

    /**
     * Unit test for {@link AbstractParametersSourceExtractor#AbstractParametersSourceExtractor()}
     */
    @Test
    void testTypeInferred_MultipleLevels() {
        final TypedGrandchild extractor = new TypedGrandchild();
        assertSame(MyParams.class, extractor.getParametersType());
        assertSame(MySource.class, extractor.getSourceType());
    }

    /**
     * Unit test for {@link AbstractParametersSourceExtractor#AbstractParametersSourceExtractor()}
     */
    @Test
    void testTypeInferred_Generic() {
        assertThrows(NullPointerException.class, () -> {
            new GenericChild<>();
        });
    }

    /**
     * Unit test for {@link AbstractParametersSourceExtractor#AbstractParametersSourceExtractor(Class, Class)}
     */
    @Test
    void testTypeConstructor() {
        final GenericChild<MyParams, MySource> extractor =
                new GenericChild<>(MyParams.class, MySource.class);
        assertSame(MyParams.class, extractor.getParametersType());
        assertSame(MySource.class, extractor.getSourceType());
    }

    /**
     * Unit test for {@link AbstractGenerator#equals(Object)},
     * {@link AbstractGenerator#hashCode()} and
     * {@link AbstractGenerator#toString()}
     */
    @Test
    @SuppressWarnings("java:S5785")
    void testEqualsHashCodeToString() {
        final TestSourceExtractor extractor = new TestSourceExtractor();
        assertFalse(extractor.equals(null));
        assertTrue(extractor.equals(extractor));
        assertFalse(extractor.equals(new Object()));
        final TestSourceExtractor other = new TestSourceExtractor();
        assertTrue(extractor.equals(other));
        assertEquals(extractor.hashCode(), other.hashCode());
        assertEquals(extractor.toString(), other.toString());
    }

    private static interface MyParams {}
    private static interface MySource {}

    /**
     * Generic extension of {@code AbstractParametersSourceExtractor} for
     * test purposes.
     * @param <P> The generation parameters type
     * @param <S> The metadata sources type
     */
    private static class GenericChild<P, S>
    extends AbstractParametersSourceExtractor<P, S> {
        public GenericChild() {
            super();
        }
        public GenericChild(
                final @NotNull Class<P> parametersType,
                final @NotNull Class<S> sourceType) {
            super(parametersType, sourceType);
        }
        @Override
        public void extractParameters(
                final @NotNull S from,
                final @NotNull P target) {
            // NOP
        }
    }

    /**
     * Direct typed extension of {@code AbstractParametersSourceExtractor} for
     * test purposes.
     */
    private static class DirectChild
    extends AbstractParametersSourceExtractor<MyParams, MySource> {
        @Override
        public void extractParameters(
                final @NotNull MySource from,
                final @NotNull MyParams target) {
            // NOP
        }
    }

    /**
     * Multilevel typed extension of {@code AbstractParametersSourceExtractor} for
     * test purposes.
     */
    private static class TypedGrandchild
    extends GenericChild<MyParams, MySource> {}
}
