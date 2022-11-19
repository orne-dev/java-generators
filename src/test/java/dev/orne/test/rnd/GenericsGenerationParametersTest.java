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

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code GenericsGenerationParameters}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see GenericsGenerationParameters
 */
@Tag("ut")
class GenericsGenerationParametersTest {

    /**
     * Unit test for {@link GenericsGenerationParameters#GenericsGenerationParameters()}.
     */
    @Test
    void testEmptyConstructor() {
        final GenericsGenerationParameters<List<String>> params = new GenericsGenerationParameters<List<String>>() {};
        assertTrue(params.isNullable());
        assertNotNull(params.getType());
        assertEquals(List.class, params.getGenericsType());
        assertEquals(1, params.getTypeArguments().length);
        assertEquals(String.class, params.getTypeArguments()[0]);
        assertNotNull(params.toString());
        assertThrows(IllegalArgumentException.class, () -> {
            new GenericsGenerationParameters<String>() {};
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new GenericsGenerationParameters<List<?>>() {};
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new GenericsGenerationParameters<List<? super String>>() {};
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new GenericsGenerationParameters<List<? extends String>>() {};
        });
    }

    /**
     * Unit test for {@link GenericsGenerationParameters#GenericsGenerationParameters()}.
     */
    @Test
    void testTypeConstructor() {
        final GenericsGenerationParameters<List<String>> copy = new GenericsGenerationParameters<List<String>>() {};
        final GenericsGenerationParameters<List<String>> params = new GenericsGenerationParameters<>(copy.getType());
        assertTrue(params.isNullable());
        assertEquals(copy.getType(), params.getType());
        assertEquals(copy.getGenericsType(), params.getGenericsType());
        assertArrayEquals(copy.getTypeArguments(), params.getTypeArguments());
        assertNotNull(params.toString());
    }

    /**
     * Unit test for {@link GenericsGenerationParameters#GenericsGenerationParameters()}.
     */
    @Test
    void testCopyConstructor() {
        final GenericsGenerationParameters<List<String>> copy = new GenericsGenerationParameters<List<String>>() {};
        copy.setNullable(false);
        final GenericsGenerationParameters<List<String>> params = new GenericsGenerationParameters<>(copy);
        assertEquals(copy, params);
    }

    /**
     * Unit test for {@link GenericsGenerationParameters#withNullable(boolean)}.
     */
    @Test
    void testWithNullable() {
        final GenericsGenerationParameters<List<String>> params = new GenericsGenerationParameters<List<String>>() {};
        final GenericsGenerationParameters<List<String>> result = params.withNullable(false);
        assertSame(result, params);
        assertFalse(params.isNullable());
    }

    /**
     * Unit test for {@link GenericsGenerationParameters#clone()}.
     */
    @Test
    void testClone() {
        final GenericsGenerationParameters<List<String>> params = new GenericsGenerationParameters<List<String>>() {};
        params.setNullable(false);
        final GenericsGenerationParameters<List<String>> result = params.clone();
        assertNotSame(params, result);
        assertEquals(params, result);
    }

    /**
     * Unit test for {@link GenericsGenerationParameters#equals(Object)},
     * {@link GenericsGenerationParameters#hashCode()}.
     */
    @Test
    @SuppressWarnings({ "java:S5785" })
    void testEqualsHashCodeToString() {
        final GenericsGenerationParameters<List<String>> params = new GenericsGenerationParameters<List<String>>() {};
        assertFalse(params.equals(null));
        assertTrue(params.equals(params));
        assertFalse(params.equals(new Object()));
        final GenericsGenerationParameters<List<String>> other = new GenericsGenerationParameters<List<String>>() {};
        assertTrue(params.equals(other));
        assertEquals(params.hashCode(), other.hashCode());
        other.setNullable(false);
        assertFalse(params.equals(other));
        final GenericsGenerationParameters<Map<String, Number>> otherType = new GenericsGenerationParameters<Map<String, Number>>() {};
        assertFalse(params.equals(otherType));
    }
}
