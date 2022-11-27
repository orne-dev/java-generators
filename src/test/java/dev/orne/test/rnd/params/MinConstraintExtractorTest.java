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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Unit tests for {@code MinConstraintExtractor}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see MinConstraintExtractor
 */
@Tag("ut")
class MinConstraintExtractorTest
extends BaseParametersSourceExtractorTest {

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    protected @NotNull Class<MinConstraintExtractor> getType() {
        return MinConstraintExtractor.class;
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
        assertSupportsSourceType(Min.class);
    }

    /**
     * Tests for {@link MinConstraintExtractor#extractParameters(Min, NumberParameters)}.
     */
    @ParameterizedTest
    @MethodSource
    void testExtractParameters(
            final long paramsMin,
            final long sourceMin) {
        final MinConstraintExtractor extractor = new MinConstraintExtractor();
        final Min source = mock(Min.class);
        final NumberParameters params = mock(NumberParameters.class);
        given(params.getMin()).willReturn(paramsMin);
        given(source.value()).willReturn((long) sourceMin);
        extractor.extractParameters(source, params);
        then(params).should().setMin(sourceMin > paramsMin ? sourceMin : paramsMin);
    }

    private static Stream<Arguments> testExtractParameters() {
        return Stream.of(
                Arguments.of(Long.MIN_VALUE, Long.MIN_VALUE),
                Arguments.of(5L, Long.MIN_VALUE),
                Arguments.of(Long.MIN_VALUE, 5L),
                Arguments.of(0L, 5L),
                Arguments.of(5L, 5L),
                Arguments.of(
                    RandomUtils.nextLong(),
                    RandomUtils.nextLong())
        );
    }
}
