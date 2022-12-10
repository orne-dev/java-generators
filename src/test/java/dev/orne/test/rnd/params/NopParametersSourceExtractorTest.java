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

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code ParametersSourceExtractor.NopExtractor}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see ParametersSourceExtractor.NopExtractor
 */
@Tag("ut")
class NopParametersSourceExtractorTest {

    /**
     * Unit test for {@link ParametersSourceExtractor#NOP}
     */
    @Test
    void testSharedInstance() {
        assertTrue(ParametersSourceExtractor.NOP instanceof ParametersSourceExtractor.NopExtractor);
    }

    /**
     * Unit test for {@link ParametersSourceExtractor.NopExtractor#getParametersType()}
     */
    @Test
    void testGetParametersType() {
        assertEquals(Object.class, ParametersSourceExtractor.NOP.getParametersType());
    }

    /**
     * Unit test for {@link ParametersSourceExtractor.NopExtractor#getSourceType()}
     */
    @Test
    void testGetSourceType() {
        assertEquals(Object.class, ParametersSourceExtractor.NOP.getSourceType());
    }

    /**
     * Unit test for {@link ParametersSourceExtractor.NopExtractor#extractParameters(Object, Object)}
     */
    @Test
    void testExtractParameters() {
        final Object params = spy(Object.class);
        final Object source = spy(Object.class);
        ParametersSourceExtractor.NOP.extractParameters(source, params);
        then(params).shouldHaveNoInteractions();
        then(source).shouldHaveNoInteractions();
    }
}
