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

import java.lang.reflect.Type;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code CollectionGenerationParameters}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 * @see CollectionGenerationParameters
 */
@Tag("ut")
class CollectionGenerationParametersTest {

    /**
     * Unit test for {@link CollectionGenerationParameters#CollectionGenerationParameters()}.
     */
    @Test
    void testEmptyConstructor() {
        final CollectionGenerationParameters params = new CollectionGenerationParameters();
        assertEquals(NullableParameters.DEFAULT_NULLABLE, params.isNullable());
        assertNull(params.getType());
        assertEquals(SizeParameters.DEFAULT_MIN_SIZE, params.getMinSize());
        assertEquals(SizeParameters.DEFAULT_MAX_SIZE, params.getMaxSize());
    }

    /**
     * Unit test for {@link CollectionGenerationParameters#CollectionGenerationParameters(GenerationParameters)}.
     */
    @Test
    void testCopyConstructor() {
        final CollectionGenerationParameters copy = new CollectionGenerationParameters();
        copy.setNullable(RandomUtils.nextBoolean());
        copy.setType(String.class);
        copy.setMinSize(RandomUtils.nextInt());
        copy.setMaxSize(RandomUtils.nextInt());
        final CollectionGenerationParameters params = new CollectionGenerationParameters(copy);
        assertEquals(copy, params);
    }

    /**
     * Unit test for {@link CollectionGenerationParameters#CollectionGenerationParameters(GenerationParameters)}.
     */
    @Test
    void testCopyConstructor_GenerationParameters() {
        final GenerationParameters copy = mock(GenerationParameters.class);
        final CollectionGenerationParameters params = new CollectionGenerationParameters(copy);
        assertEquals(NullableParameters.DEFAULT_NULLABLE, params.isNullable());
        assertNull(params.getType());
        assertEquals(SizeParameters.DEFAULT_MIN_SIZE, params.getMinSize());
        assertEquals(SizeParameters.DEFAULT_MAX_SIZE, params.getMaxSize());
    }

    /**
     * Unit test for {@link CollectionGenerationParameters#CollectionGenerationParameters(GenerationParameters)}.
     */
    @Test
    void testCopyConstructor_NullableParameters() {
        final NullableParameters copy = mock(NullableParameters.class);
        given(copy.isNullable()).willReturn(!NullableParameters.DEFAULT_NULLABLE);
        final CollectionGenerationParameters params = new CollectionGenerationParameters(copy);
        assertEquals(!NullableParameters.DEFAULT_NULLABLE, params.isNullable());
        assertFalse(params.isNullable());
        assertNull(params.getType());
        assertEquals(SizeParameters.DEFAULT_MIN_SIZE, params.getMinSize());
        assertEquals(SizeParameters.DEFAULT_MAX_SIZE, params.getMaxSize());
    }

    /**
     * Unit test for {@link CollectionGenerationParameters#CollectionGenerationParameters(GenerationParameters)}.
     */
    @Test
    void testCopyConstructor_SimpleGenericParameters() {
        final SimpleGenericParameters copy = mock(SimpleGenericParameters.class);
        final Type type = String.class;
        given(copy.getType()).willReturn(type);
        final CollectionGenerationParameters params = new CollectionGenerationParameters(copy);
        assertTrue(params.isNullable());
        assertEquals(type, params.getType());
        assertEquals(SizeParameters.DEFAULT_MIN_SIZE, params.getMinSize());
        assertEquals(SizeParameters.DEFAULT_MAX_SIZE, params.getMaxSize());
    }

    /**
     * Unit test for {@link CollectionGenerationParameters#CollectionGenerationParameters(GenerationParameters)}.
     */
    @Test
    void testCopyConstructor_SizeParameters() {
        final SizeParameters copy = mock(SizeParameters.class);
        final int minSize = RandomUtils.nextInt();
        final int maxSize = RandomUtils.nextInt();
        given(copy.getMinSize()).willReturn(minSize);
        given(copy.getMaxSize()).willReturn(maxSize);
        final CollectionGenerationParameters params = new CollectionGenerationParameters(copy);
        assertTrue(params.isNullable());
        assertNull(params.getType());
        assertEquals(minSize, params.getMinSize());
        assertEquals(maxSize, params.getMaxSize());
    }

    /**
     * Unit test for {@link CollectionGenerationParameters#withNullable(boolean)}.
     */
    @Test
    void testWithNullable() {
        final CollectionGenerationParameters params = new CollectionGenerationParameters();
        final CollectionGenerationParameters result = params.withNullable(!NullableParameters.DEFAULT_NULLABLE);
        assertSame(result, params);
        assertEquals(!NullableParameters.DEFAULT_NULLABLE, params.isNullable());
    }

    /**
     * Unit test for {@link CollectionGenerationParameters#withType(Type)}.
     */
    @Test
    void testWithType() {
        final CollectionGenerationParameters params = new CollectionGenerationParameters();
        final Type value = String.class;
        final CollectionGenerationParameters result = params.withType(value);
        assertSame(result, params);
        assertEquals(value, params.getType());
    }

    /**
     * Unit test for {@link CollectionGenerationParameters#withMinSize(int)}.
     */
    @Test
    void testWithMinSize() {
        final CollectionGenerationParameters params = new CollectionGenerationParameters();
        final int value = RandomUtils.nextInt();
        final CollectionGenerationParameters result = params.withMinSize(value);
        assertSame(result, params);
        assertEquals(value, params.getMinSize());
    }

    /**
     * Unit test for {@link CollectionGenerationParameters#withMaxSize(int)}.
     */
    @Test
    void testWithMaxSize() {
        final CollectionGenerationParameters params = new CollectionGenerationParameters();
        final int value = RandomUtils.nextInt();
        final CollectionGenerationParameters result = params.withMaxSize(value);
        assertSame(result, params);
        assertEquals(value, params.getMaxSize());
    }

    /**
     * Unit test for {@link CollectionGenerationParameters#equals(Object)},
     * {@link CollectionGenerationParameters#hashCode()} and
     * {@link CollectionGenerationParameters#toString()}.
     */
    @Test
    @SuppressWarnings({ "java:S5785" })
    void testEqualsHashCodeToString() {
        final CollectionGenerationParameters params = new CollectionGenerationParameters();
        assertFalse(params.equals(null));
        assertTrue(params.equals(params));
        assertFalse(params.equals(new Object()));
        CollectionGenerationParameters other = new CollectionGenerationParameters();
        assertTrue(params.equals(other));
        assertEquals(params.hashCode(), other.hashCode());
        assertEquals(params.toString(), other.toString());
        other = new CollectionGenerationParameters().withNullable(!NullableParameters.DEFAULT_NULLABLE);
        assertFalse(params.equals(other));
        other = new CollectionGenerationParameters().withType(String.class);
        assertFalse(params.equals(other));
        other = new CollectionGenerationParameters().withMinSize(RandomUtils.nextInt());
        assertFalse(params.equals(other));
        other = new CollectionGenerationParameters().withMaxSize(RandomUtils.nextInt());
        assertFalse(params.equals(other));
    }
}
