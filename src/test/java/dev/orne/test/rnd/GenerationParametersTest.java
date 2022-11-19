package dev.orne.test.rnd;

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
     * Unit test for {@link GenerationParameters#GenerationParameters()}.
     */
    @Test
    void testEmptyConstructor() {
        final GenerationParameters params = new GenerationParameters();
        assertTrue(params.isNullable());
    }

    /**
     * Unit test for {@link GenerationParameters#GenerationParameters()}.
     */
    @Test
    void testCopyConstructor() {
        final GenerationParameters copy = new GenerationParameters();
        copy.setNullable(false);
        final GenerationParameters params = new GenerationParameters(copy);
        assertFalse(params.isNullable());
    }

    /**
     * Unit test for {@link GenerationParameters#withNullable(boolean)}.
     */
    @Test
    void testWithNullable() {
        final GenerationParameters params = new GenerationParameters();
        final GenerationParameters result = params.withNullable(false);
        assertSame(result, params);
        assertFalse(params.isNullable());
    }

    /**
     * Unit test for {@link GenerationParameters#clone()}.
     */
    @Test
    void testClone() {
        final GenerationParameters params = new GenerationParameters();
        params.setNullable(false);
        final GenerationParameters result = params.clone();
        assertNotSame(params, result);
        assertEquals(params, result);
    }

    /**
     * Unit test for {@link GenerationParameters#equals(Object)},
     * {@link GenerationParameters#hashCode()} and
     * {@link GenerationParameters#toString()}.
     */
    @Test
    @SuppressWarnings({ "java:S5785" })
    void testEqualsHashCodeToString() {
        final GenerationParameters params = new GenerationParameters();
        assertFalse(params.equals(null));
        assertTrue(params.equals(params));
        assertFalse(params.equals(new Object()));
        final GenerationParameters other = new GenerationParameters();
        assertTrue(params.equals(other));
        assertEquals(params.hashCode(), other.hashCode());
        assertEquals(params.toString(), other.toString());
        other.setNullable(false);
        assertFalse(params.equals(other));
    }
}
