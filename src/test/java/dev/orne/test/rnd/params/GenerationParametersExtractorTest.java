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

import static org.mockito.BDDMockito.*;

import java.util.stream.Stream;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Unit tests for {@code GenerationParametersExtractor}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see GenerationParametersExtractor
 */
@Tag("ut")
class GenerationParametersExtractorTest
extends BaseParametersSourceExtractorTest {

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    protected @NotNull Class<GenerationParametersExtractor> getType() {
        return GenerationParametersExtractor.class;
    }

    /**
     * Tests for supported parameters type.
     */
    @Test
    void testSupportedParametersTypes() {
        assertSupportsParametersType(GenerationParameters.class);
        assertSupportsParametersType(StringGenerationParameters.class);
        assertSupportsParametersType(CollectionGenerationParameters.class);
        assertSupportsParametersType(MapGenerationParameters.class);
    }

    /**
     * Tests for supported source type.
     */
    @Test
    void testSupportedSourceTypes() {
        assertSupportsSourceType(GenerationParameters.class);
    }

    /**
     * Tests for {@link GenerationParametersExtractor#extractParameters(SimpleGenericParameters, SimpleGenericParameters)}.
     */
    @ParameterizedTest
    @MethodSource
    void testExtractParameters(
            final boolean paramsValue,
            final boolean sourceValue) {
        final GenerationParametersExtractor extractor = new GenerationParametersExtractor();
        final GenerationParameters source = mock(GenerationParameters.class);
        final GenerationParameters params = mock(GenerationParameters.class);
        given(params.isNullable()).willReturn(paramsValue);
        given(source.isNullable()).willReturn(sourceValue);
        extractor.extractParameters(source, params);
        then(params).should().setNullable(sourceValue && paramsValue);
    }

    private static Stream<Arguments> testExtractParameters() {
        return Stream.of(
                Arguments.of(false, false),
                Arguments.of(true, false),
                Arguments.of(false, true),
                Arguments.of(true, true)
        );
    }
}
