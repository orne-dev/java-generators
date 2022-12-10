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

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code GenerationParameters}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see GenerationParameters
 */
@Tag("ut")
class GenerationParametersTest {

    /**
     * Unit test for {@link GenerationParameters#forNullables()}.
     */
    @Test
    void testForNullables() {
        final NullableParameters.Builder params = GenerationParameters.forNullables();
        assertEquals(new NullableParametersImpl(), params);
    }

    /**
     * Unit test for {@link GenerationParameters#forNumbers()}.
     */
    @Test
    void testForNumbers() {
        final NumberParameters.Builder params = GenerationParameters.forNumbers();
        assertEquals(new NumberParametersImpl(), params);
    }

    /**
     * Unit test for {@link GenerationParameters#forSizes()}.
     */
    @Test
    void testForSizes() {
        final SizeParameters.Builder params = GenerationParameters.forSizes();
        assertEquals(new SizeParametersImpl(), params);
    }

    /**
     * Unit test for {@link GenerationParameters#forSimpleGenerics()}.
     */
    @Test
    void testForSimpleGenerics() {
        final SimpleGenericParameters.Builder params = GenerationParameters.forSimpleGenerics();
        assertEquals(new SimpleGenericParametersImpl(), params);
    }

    /**
     * Unit test for {@link GenerationParameters#forKeyValueGenerics()}.
     */
    @Test
    void testForKeyValueGenerics() {
        final KeyValueGenericParameters.KeysTypeBuilder params = GenerationParameters.forKeyValueGenerics();
        assertEquals(new KeyValueGenericParametersImpl(), params);
    }
}
