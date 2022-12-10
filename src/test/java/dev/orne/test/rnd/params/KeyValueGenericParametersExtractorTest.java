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

import java.lang.reflect.Type;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code KeyValueGenericParametersExtractor}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 * @see KeyValueGenericParametersExtractor
 */
@Tag("ut")
class KeyValueGenericParametersExtractorTest
extends BaseParametersSourceExtractorTest {

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    protected @NotNull Class<KeyValueGenericParametersExtractor> getType() {
        return KeyValueGenericParametersExtractor.class;
    }

    /**
     * Tests for supported parameters type.
     */
    @Test
    void testSupportedParametersTypes() {
        assertSupportsParametersType(KeyValueGenericParameters.class);
        assertSupportsParametersType(MapGenerationParameters.class);
    }

    /**
     * Tests for supported source type.
     */
    @Test
    void testSupportedSourceTypes() {
        assertSupportsSourceType(KeyValueGenericParameters.class);
    }

    /**
     * Tests for {@link KeyValueGenericParametersExtractor#extractParameters(KeyValueGenericParameters, KeyValueGenericParameters)}.
     */
    @Test
    void testExtractParameters() {
        final KeyValueGenericParametersExtractor extractor = new KeyValueGenericParametersExtractor();
        final KeyValueGenericParameters source = mock(KeyValueGenericParameters.class);
        final KeyValueGenericParameters params = mock(KeyValueGenericParameters.class);
        final Type expectedKey = Integer.class;
        final Type expectedValue = String.class;
        given(source.getKeysType()).willReturn(expectedKey);
        given(source.getValuesType()).willReturn(expectedValue);
        extractor.extractParameters(source, params);
        then(params).should().setKeysType(expectedKey);
        then(params).should().setValuesType(expectedValue);
    }

    /**
     * Tests for {@link KeyValueGenericParametersExtractor#extractParameters(KeyValueGenericParameters, KeyValueGenericParameters)}.
     */
    @Test
    void testExtractParameters_Null() {
        final KeyValueGenericParametersExtractor extractor = new KeyValueGenericParametersExtractor();
        final KeyValueGenericParameters source = mock(KeyValueGenericParameters.class);
        final KeyValueGenericParameters params = mock(KeyValueGenericParameters.class);
        given(source.getKeysType()).willReturn(null);
        given(source.getValuesType()).willReturn(null);
        extractor.extractParameters(source, params);
        then(params).shouldHaveNoInteractions();
    }
}
