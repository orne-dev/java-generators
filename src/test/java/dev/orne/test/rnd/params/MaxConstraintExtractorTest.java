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

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Unit tests for {@code MaxConstraintExtractor}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see MaxConstraintExtractor
 */
@Tag("ut")
class MaxConstraintExtractorTest
extends BaseParametersSourceExtractorTest {

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    protected @NotNull Class<MaxConstraintExtractor> getType() {
        return MaxConstraintExtractor.class;
    }

    /**
     * Tests for supported parameters type.
     */
    @Test
    void testSupportedParametersTypes() {
        assertSupportsParametersType(NumberParameters.class);
    }

    /**
     * Tests for supported source type.
     */
    @Test
    void testSupportedSourceTypes() {
        assertSupportsSourceType(Max.class);
    }

    /**
     * Tests for {@link MaxConstraintExtractor#extractParameters(Max, NumberParameters)}.
     */
    @ParameterizedTest
    @MethodSource
    void testExtractParameters(
            final long paramsMax,
            final long sourceMax) {
        final MaxConstraintExtractor extractor = new MaxConstraintExtractor();
        final Max source = mock(Max.class);
        final NumberParameters params = mock(NumberParameters.class);
        given(params.getMax()).willReturn(paramsMax);
        given(source.value()).willReturn((long) sourceMax);
        extractor.extractParameters(source, params);
        then(params).should().setMax(sourceMax < paramsMax ? sourceMax : paramsMax);
    }

    private static Stream<Arguments> testExtractParameters() {
        return Stream.of(
                Arguments.of(Long.MAX_VALUE, Long.MAX_VALUE),
                Arguments.of(5L, Long.MAX_VALUE),
                Arguments.of(Long.MAX_VALUE, 5L),
                Arguments.of(
                    RandomUtils.nextLong(),
                    RandomUtils.nextLong())
        );
    }
}
