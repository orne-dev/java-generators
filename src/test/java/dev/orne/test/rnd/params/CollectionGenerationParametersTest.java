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

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
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
        assertTrue(params.isNullable());
        assertNull(params.getType());
        assertEquals(0, params.getMinSize());
        assertEquals(Integer.MAX_VALUE, params.getMaxSize());
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
        final GenerationParameters copy = new GenerationParameters();
        copy.setNullable(false);
        final CollectionGenerationParameters params = new CollectionGenerationParameters(copy);
        assertFalse(params.isNullable());
        assertNull(params.getType());
        assertEquals(0, params.getMinSize());
        assertEquals(Integer.MAX_VALUE, params.getMaxSize());
    }

    /**
     * Unit test for {@link CollectionGenerationParameters#CollectionGenerationParameters(GenerationParameters)}.
     */
    @Test
    void testCopyConstructor_SimpleGenericParameters() {
        final SimpleParams copy = mock(SimpleParams.class);
        final boolean nullable = RandomUtils.nextBoolean();
        final Type type = String.class;
        given(copy.isNullable()).willReturn(nullable);
        given(copy.getType()).willReturn(type);
        final CollectionGenerationParameters params = new CollectionGenerationParameters(copy);
        assertEquals(nullable, params.isNullable());
        assertEquals(type, params.getType());
        assertEquals(0, params.getMinSize());
        assertEquals(Integer.MAX_VALUE, params.getMaxSize());
    }

    /**
     * Unit test for {@link CollectionGenerationParameters#CollectionGenerationParameters(GenerationParameters)}.
     */
    @Test
    void testCopyConstructor_SizeParameters() {
        final SizeParams copy = mock(SizeParams.class);
        final boolean nullable = RandomUtils.nextBoolean();
        final int minSize = RandomUtils.nextInt();
        final int maxSize = RandomUtils.nextInt();
        given(copy.isNullable()).willReturn(nullable);
        given(copy.getMinSize()).willReturn(minSize);
        given(copy.getMaxSize()).willReturn(maxSize);
        final CollectionGenerationParameters params = new CollectionGenerationParameters(copy);
        assertEquals(nullable, params.isNullable());
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
        final CollectionGenerationParameters result = params.withNullable(false);
        assertSame(result, params);
        assertFalse(params.isNullable());
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
     * Unit test for {@link CollectionGenerationParameters#clone()}.
     */
    @Test
    void testClone() {
        final CollectionGenerationParameters params = new CollectionGenerationParameters();
        params.setNullable(false);
        params.setType(String.class);
        params.setMinSize(RandomUtils.nextInt());
        params.setMaxSize(RandomUtils.nextInt());
        final CollectionGenerationParameters result = params.clone();
        assertNotSame(params, result);
        assertEquals(params, result);
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
        other = new CollectionGenerationParameters().withNullable(false);
        assertFalse(params.equals(other));
        other = new CollectionGenerationParameters().withType(String.class);
        assertFalse(params.equals(other));
        other = new CollectionGenerationParameters().withMinSize(RandomUtils.nextInt());
        assertFalse(params.equals(other));
        other = new CollectionGenerationParameters().withMaxSize(RandomUtils.nextInt());
        assertFalse(params.equals(other));
    }

    /**
     * Unit test for {@link CollectionGenerationParameters#builder()}
     * and {@link CollectionGenerationParameters.BuilderImpl#withElementsType(Class)}.
     */
    @Test
    void testBuilder() {
        final CollectionGenerationParameters.Builder builder = CollectionGenerationParameters.builder();
        assertNotNull(builder);
        final Class<?> type = String.class;
        final CollectionGenerationParameters params = builder.withElementsType(type);
        assertNotNull(params);
        assertTrue(params.isNullable());
        assertEquals(type, params.getType());
        assertEquals(0, params.getMinSize());
        assertEquals(Integer.MAX_VALUE, params.getMaxSize());
    }

    /**
     * Unit test for {@link CollectionGenerationParameters#builder()}
     * and {@link CollectionGenerationParameters.BuilderImpl#withElementsType(ParameterizedType)}.
     */
    @Test
    void testBuilder_ParameterizedType() {
        final CollectionGenerationParameters.Builder builder = CollectionGenerationParameters.builder();
        assertNotNull(builder);
        final ParameterizedType type = TypeUtils.parameterize(Set.class, String.class);
        final CollectionGenerationParameters params = builder.withElementsType(type);
        assertNotNull(params);
        assertTrue(params.isNullable());
        assertEquals(type, params.getType());
        assertEquals(0, params.getMinSize());
        assertEquals(Integer.MAX_VALUE, params.getMaxSize());
    }

    /**
     * Unit test for {@link CollectionGenerationParameters#builder()}
     * and {@link CollectionGenerationParameters.BuilderImpl#withElementsType(GenericArrayType)}.
     */
    @Test
    void testBuilder_GenericArray() {
        final CollectionGenerationParameters.Builder builder = CollectionGenerationParameters.builder();
        assertNotNull(builder);
        final GenericArrayType type = TypeUtils.genericArrayType(
                TypeUtils.parameterize(Set.class, String.class));
        final CollectionGenerationParameters params = builder.withElementsType(type);
        assertNotNull(params);
        assertTrue(params.isNullable());
        assertEquals(type, params.getType());
        assertEquals(0, params.getMinSize());
        assertEquals(Integer.MAX_VALUE, params.getMaxSize());
    }

    private static class SimpleParams
    extends GenerationParameters
    implements SimpleGenericParameters {
        @Override
        public Type getType() {
            return null;
        }
        @Override
        public void setType(Type type) {}
    }

    private static class SizeParams
    extends GenerationParameters
    implements SizeParameters {
        @Override
        public int getMinSize() {
            return 0;
        }
        @Override
        public void setMinSize(int value) {}
        @Override
        public int getMaxSize() {
            return 0;
        }
        @Override
        public void setMaxSize(int value) {}
    }
}
