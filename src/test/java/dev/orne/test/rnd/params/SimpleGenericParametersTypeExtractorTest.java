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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code SimpleGenericParametersTypeExtractor}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see SimpleGenericParametersTypeExtractor
 */
@Tag("ut")
class SimpleGenericParametersTypeExtractorTest
extends BaseParametersSourceExtractorTest {

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    protected @NotNull Class<SimpleGenericParametersTypeExtractor> getType() {
        return SimpleGenericParametersTypeExtractor.class;
    }

    /**
     * Tests for supported parameters type.
     */
    @Test
    void testSupportedParametersTypes() {
        assertSupportsParametersType(SimpleGenericParameters.class);
        assertSupportsParametersType(ListGenerationParameters.class);
    }

    /**
     * Tests for supported source type.
     */
    @Test
    void testSupportedSourceTypes() {
        assertSupportsSourceType(TypeDeclaration.class);
    }

    /**
     * Tests for {@link SimpleGenericParametersTypeExtractor#extractParameters(TypeDeclaration, SimpleGenericParameters)}.
     */
    @Test
    void testExtractParameters() {
        final SimpleGenericParametersTypeExtractor extractor = new SimpleGenericParametersTypeExtractor();
        final TypeDeclaration source = mock(TypeDeclaration.class);
        final SimpleGenericParameters params = mock(SimpleGenericParameters.class);
        final Type expected = Integer.class;
        final ParameterizedType pType = mock(ParameterizedType.class);
        given(source.getType()).willReturn(pType);
        given(pType.getActualTypeArguments()).willReturn(new Type[] { expected });
        extractor.extractParameters(source, params);
        then(params).should().setType(expected);
    }

    /**
     * Tests for {@link SimpleGenericParametersTypeExtractor#extractParameters(TypeDeclaration, SimpleGenericParameters)}.
     */
    @Test
    void testExtractParameters_Empty() {
        final SimpleGenericParametersTypeExtractor extractor = new SimpleGenericParametersTypeExtractor();
        final TypeDeclaration source = mock(TypeDeclaration.class);
        final SimpleGenericParameters params = mock(SimpleGenericParameters.class);
        final ParameterizedType pType = mock(ParameterizedType.class);
        given(source.getType()).willReturn(pType);
        given(pType.getActualTypeArguments()).willReturn(new Type[0]);
        assertThrows(IllegalArgumentException.class, () -> {
            extractor.extractParameters(source, params);
        });
    }

    /**
     * Tests for {@link SimpleGenericParametersTypeExtractor#extractParameters(TypeDeclaration, SimpleGenericParameters)}.
     */
    @Test
    void testExtractParameters_TooMuch() {
        final SimpleGenericParametersTypeExtractor extractor = new SimpleGenericParametersTypeExtractor();
        final TypeDeclaration source = mock(TypeDeclaration.class);
        final SimpleGenericParameters params = mock(SimpleGenericParameters.class);
        final ParameterizedType pType = mock(ParameterizedType.class);
        given(source.getType()).willReturn(pType);
        given(pType.getActualTypeArguments()).willReturn(new Type[] { Integer.class, String.class });
        assertThrows(IllegalArgumentException.class, () -> {
            extractor.extractParameters(source, params);
        });
    }
}
