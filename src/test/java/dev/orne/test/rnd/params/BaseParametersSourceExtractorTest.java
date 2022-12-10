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

import org.junit.jupiter.api.Test;

/**
 * Base unit tests for {@code ParametersSourceExtractor} implementations.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see ParametersSourceExtractor
 */
public abstract class BaseParametersSourceExtractorTest {

    /**
     * Returns the tested generation parameters extractor type.
     * 
     * @return The tested generation parameters extractor type.
     */
    protected abstract @NotNull Class<? extends ParametersSourceExtractor<?, ?>> getType();

    /**
     * Tests that a public default constructor exists.
     */
    @Test
    void testDefaultConstructor() {
        final Class<? extends ParametersSourceExtractor<?, ?>> type = getType();
        final ParametersSourceExtractor<?, ?> result = assertDoesNotThrow(() -> {
            return type.newInstance();
        });
        assertNotNull(result.getParametersType());
        assertNotNull(result.getSourceType());
    }

    /**
     * Asserts that the tested generation parameters extractor type supports
     * specified generation parameters type.
     * 
     * @param parametersType The generation parameters type.
     */
    protected void assertSupportsParametersType(
            final Class<?> parametersType) {
        assertSupportsParametersType(getType(), parametersType);
    }

    /**
     * Asserts that the specified generation parameters extractor type supports
     * specified generation parameters type.
     * 
     * @param type The generation parameters extractor type.
     * @param parametersType The generation parameters type.
     */
    public static void assertSupportsParametersType(
            final Class<? extends ParametersSourceExtractor<?, ?>> type,
            final Class<?> parametersType) {
        final ParametersSourceExtractor<?, ?> extractor = assertDoesNotThrow(() -> {
            return type.newInstance();
        });
        assertSupportsParametersType(extractor, parametersType);
    }

    /**
     * Asserts that the specified generation parameters extractor supports
     * specified generation parameters type.
     * 
     * @param extractor The generation parameters extractor,.
     * @param parametersType The generation parameters type.
     */
    public static void assertSupportsParametersType(
            final ParametersSourceExtractor<?, ?> extractor,
            final Class<?> parametersType) {
        assertTrue(extractor.getParametersType().isAssignableFrom(parametersType));
    }

    /**
     * Asserts that the tested generation parameters extractor type supports
     * specified generation parameters source type.
     * 
     * @param sourceType The generation parameters source type.
     */
    protected void assertSupportsSourceType(
            final Class<?> sourceType) {
        assertSupportsSourceType(getType(), sourceType);
    }


    /**
     * Asserts that the specified generation parameters extractor type supports
     * specified generation parameters source type.
     * 
     * @param type The generation parameters extractor type.
     * @param sourceType The generation parameters source type.
     */
    public static void assertSupportsSourceType(
            final Class<? extends ParametersSourceExtractor<?, ?>> type,
            final Class<?> sourceType) {
        final ParametersSourceExtractor<?, ?> extractor = assertDoesNotThrow(() -> {
            return type.newInstance();
        });
        assertSupportsSourceType(extractor, sourceType);
    }

    /**
     * Asserts that the specified generation parameters extractor supports
     * specified generation parameters source type.
     * 
     * @param extractor The generation parameters extractor,.
     * @param sourceType The generation parameters source type.
     */
    public static void assertSupportsSourceType(
            final ParametersSourceExtractor<?, ?> extractor,
            final Class<?> sourceType) {
        assertTrue(extractor.getSourceType().isAssignableFrom(sourceType));
    }
}
