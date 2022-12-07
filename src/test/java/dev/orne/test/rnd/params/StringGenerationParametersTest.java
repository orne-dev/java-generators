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

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code StringGenerationParameters}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 * @see StringGenerationParameters
 */
@Tag("ut")
class StringGenerationParametersTest {

    /**
     * Unit test for {@link StringGenerationParameters#StringGenerationParameters()}.
     */
    @Test
    void testEmptyConstructor() {
        final StringGenerationParameters params = new StringGenerationParameters();
        assertTrue(params.isNullable());
        assertEquals(0, params.getMinSize());
        assertEquals(Integer.MAX_VALUE, params.getMaxSize());
    }

    /**
     * Unit test for {@link StringGenerationParameters#StringGenerationParameters(GenerationParameters)}.
     */
    @Test
    void testCopyConstructor() {
        final StringGenerationParameters copy = new StringGenerationParameters();
        copy.setNullable(RandomUtils.nextBoolean());
        copy.setMinSize(RandomUtils.nextInt());
        copy.setMaxSize(RandomUtils.nextInt());
        final StringGenerationParameters params = new StringGenerationParameters(copy);
        assertEquals(copy, params);
    }

    /**
     * Unit test for {@link StringGenerationParameters#StringGenerationParameters(GenerationParameters)}.
     */
    @Test
    void testCopyConstructor_GenerationParameters() {
        final GenerationParameters copy = mock(GenerationParameters.class);
        final StringGenerationParameters params = new StringGenerationParameters(copy);
        assertTrue(params.isNullable());
        assertEquals(0, params.getMinSize());
        assertEquals(Integer.MAX_VALUE, params.getMaxSize());
    }

    /**
     * Unit test for {@link StringGenerationParameters#StringGenerationParameters(GenerationParameters)}.
     */
    @Test
    void testCopyConstructor_NullableParameters() {
        final NullableParameters copy = mock(NullableParameters.class);
        given(copy.isNullable()).willReturn(false);
        final StringGenerationParameters params = new StringGenerationParameters(copy);
        assertFalse(params.isNullable());
        assertEquals(0, params.getMinSize());
        assertEquals(Integer.MAX_VALUE, params.getMaxSize());
    }

    /**
     * Unit test for {@link StringGenerationParameters#StringGenerationParameters(GenerationParameters)}.
     */
    @Test
    void testCopyConstructor_SizeParameters() {
        final SizeParameters copy = mock(SizeParameters.class);
        final int minSize = RandomUtils.nextInt();
        final int maxSize = RandomUtils.nextInt();
        given(copy.getMinSize()).willReturn(minSize);
        given(copy.getMaxSize()).willReturn(maxSize);
        final StringGenerationParameters params = new StringGenerationParameters(copy);
        assertTrue(params.isNullable());
        assertEquals(minSize, params.getMinSize());
        assertEquals(maxSize, params.getMaxSize());
    }

    /**
     * Unit test for {@link StringGenerationParameters#withNullable(boolean)}.
     */
    @Test
    void testWithNullable() {
        final StringGenerationParameters params = new StringGenerationParameters();
        final StringGenerationParameters result = params.withNullable(false);
        assertSame(result, params);
        assertFalse(params.isNullable());
    }

    /**
     * Unit test for {@link StringGenerationParameters#withMinSize(int)}.
     */
    @Test
    void testWithMinSize() {
        final StringGenerationParameters params = new StringGenerationParameters();
        final int value = RandomUtils.nextInt();
        final StringGenerationParameters result = params.withMinSize(value);
        assertSame(result, params);
        assertEquals(value, params.getMinSize());
    }

    /**
     * Unit test for {@link StringGenerationParameters#withMaxSize(int)}.
     */
    @Test
    void testWithMaxSize() {
        final StringGenerationParameters params = new StringGenerationParameters();
        final int value = RandomUtils.nextInt();
        final StringGenerationParameters result = params.withMaxSize(value);
        assertSame(result, params);
        assertEquals(value, params.getMaxSize());
    }

    /**
     * Unit test for {@link StringGenerationParameters#clone()}.
     */
    @Test
    void testClone() {
        final StringGenerationParameters params = new StringGenerationParameters();
        params.setNullable(false);
        params.setMinSize(RandomUtils.nextInt());
        params.setMaxSize(RandomUtils.nextInt());
        final StringGenerationParameters result = params.clone();
        assertNotSame(params, result);
        assertEquals(params, result);
    }

    /**
     * Unit test for {@link StringGenerationParameters#equals(Object)},
     * {@link StringGenerationParameters#hashCode()} and
     * {@link StringGenerationParameters#toString()}.
     */
    @Test
    @SuppressWarnings({ "java:S5785" })
    void testEqualsHashCodeToString() {
        final StringGenerationParameters params = new StringGenerationParameters();
        assertFalse(params.equals(null));
        assertTrue(params.equals(params));
        assertFalse(params.equals(new Object()));
        StringGenerationParameters other = new StringGenerationParameters();
        assertTrue(params.equals(other));
        assertEquals(params.hashCode(), other.hashCode());
        assertEquals(params.toString(), other.toString());
        other = new StringGenerationParameters().withNullable(false);
        assertFalse(params.equals(other));
        other = new StringGenerationParameters().withMinSize(RandomUtils.nextInt());
        assertFalse(params.equals(other));
        other = new StringGenerationParameters().withMaxSize(RandomUtils.nextInt());
        assertFalse(params.equals(other));
    }
}
