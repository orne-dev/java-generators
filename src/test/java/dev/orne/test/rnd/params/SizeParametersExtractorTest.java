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

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Unit tests for {@code SizeParametersExtractor}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see SizeParametersExtractor
 */
@Tag("ut")
class SizeParametersExtractorTest
extends BaseParametersSourceExtractorTest {

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    protected @NotNull Class<SizeParametersExtractor> getType() {
        return SizeParametersExtractor.class;
    }

    /**
     * Tests for supported parameters type.
     */
    @Test
    void testSupportedParametersTypes() {
        assertSupportsParametersType(SizeParameters.class);
        assertSupportsParametersType(CollectionGenerationParameters.class);
    }

    /**
     * Tests for supported source type.
     */
    @Test
    void testSupportedSourceTypes() {
        assertSupportsSourceType(SizeParameters.class);
    }

    /**
     * Tests for {@link SizeParametersExtractor#extractParameters(SizeParameters, SizeParameters)}.
     */
    @ParameterizedTest
    @MethodSource
    void testExtractParameters(
            final int paramsMinValue,
            final int paramsMaxValue,
            final int sourceMinValue,
            final int sourceMaxValue) {
        final SizeParametersExtractor extractor = new SizeParametersExtractor();
        final SizeParameters source = mock(SizeParameters.class);
        final SizeParameters params = mock(SizeParameters.class);
        given(params.getMinSize()).willReturn(paramsMinValue);
        given(params.getMaxSize()).willReturn(paramsMaxValue);
        given(source.getMinSize()).willReturn(sourceMinValue);
        given(source.getMaxSize()).willReturn(sourceMaxValue);
        extractor.extractParameters(source, params);
        then(params).should().setMinSize(sourceMinValue > paramsMinValue ? sourceMinValue : paramsMinValue);
        then(params).should().setMaxSize(sourceMaxValue < paramsMaxValue ? sourceMaxValue : paramsMaxValue);
    }

    private static Stream<Arguments> testExtractParameters() {
        return Stream.of(
                Arguments.of(0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE),
                Arguments.of(0, 10, 0, Integer.MAX_VALUE),
                Arguments.of(0, Integer.MAX_VALUE, 0, 10),
                Arguments.of(5, Integer.MAX_VALUE, 0, Integer.MAX_VALUE),
                Arguments.of(0, Integer.MAX_VALUE, 5, Integer.MAX_VALUE),
                Arguments.of(0, Integer.MAX_VALUE, 5, 10),
                Arguments.of(
                    RandomUtils.nextInt(),
                    RandomUtils.nextInt(),
                    RandomUtils.nextInt(),
                    RandomUtils.nextInt())
        );
    }
}
