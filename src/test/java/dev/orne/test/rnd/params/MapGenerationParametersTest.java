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
 * Unit tests for {@code MapGenerationParameters}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 * @see MapGenerationParameters
 */
@Tag("ut")
class MapGenerationParametersTest {

    /**
     * Unit test for {@link MapGenerationParameters#MapGenerationParameters()}.
     */
    @Test
    void testEmptyConstructor() {
        final MapGenerationParameters params = new MapGenerationParameters();
        assertTrue(params.isNullable());
        assertNull(params.getKeysType());
        assertNull(params.getValuesType());
        assertEquals(0, params.getMinSize());
        assertEquals(Integer.MAX_VALUE, params.getMaxSize());
    }

    /**
     * Unit test for {@link MapGenerationParameters#MapGenerationParameters(GenerationParameters)}.
     */
    @Test
    void testCopyConstructor() {
        final MapGenerationParameters copy = new MapGenerationParameters();
        copy.setNullable(RandomUtils.nextBoolean());
        copy.setKeysType(Integer.class);
        copy.setValuesType(String.class);
        copy.setMinSize(RandomUtils.nextInt());
        copy.setMaxSize(RandomUtils.nextInt());
        final MapGenerationParameters params = new MapGenerationParameters(copy);
        assertEquals(copy, params);
    }

    /**
     * Unit test for {@link MapGenerationParameters#MapGenerationParameters(GenerationParameters)}.
     */
    @Test
    void testCopyConstructor_GenerationParameters() {
        final GenerationParameters copy = new GenerationParameters();
        copy.setNullable(false);
        final MapGenerationParameters params = new MapGenerationParameters(copy);
        assertFalse(params.isNullable());
        assertNull(params.getKeysType());
        assertNull(params.getValuesType());
        assertEquals(0, params.getMinSize());
        assertEquals(Integer.MAX_VALUE, params.getMaxSize());
    }

    /**
     * Unit test for {@link MapGenerationParameters#MapGenerationParameters(GenerationParameters)}.
     */
    @Test
    void testCopyConstructor_SimpleGenericParameters() {
        final SimpleParams copy = mock(SimpleParams.class);
        final boolean nullable = RandomUtils.nextBoolean();
        final Type keysType = Integer.class;
        final Type valuesType = String.class;
        given(copy.isNullable()).willReturn(nullable);
        given(copy.getKeysType()).willReturn(keysType);
        given(copy.getValuesType()).willReturn(valuesType);
        final MapGenerationParameters params = new MapGenerationParameters(copy);
        assertEquals(nullable, params.isNullable());
        assertEquals(keysType, params.getKeysType());
        assertEquals(valuesType, params.getValuesType());
        assertEquals(0, params.getMinSize());
        assertEquals(Integer.MAX_VALUE, params.getMaxSize());
    }

    /**
     * Unit test for {@link MapGenerationParameters#MapGenerationParameters(GenerationParameters)}.
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
        final MapGenerationParameters params = new MapGenerationParameters(copy);
        assertEquals(nullable, params.isNullable());
        assertNull(params.getKeysType());
        assertNull(params.getValuesType());
        assertEquals(minSize, params.getMinSize());
        assertEquals(maxSize, params.getMaxSize());
    }

    /**
     * Unit test for {@link MapGenerationParameters#withNullable(boolean)}.
     */
    @Test
    void testWithNullable() {
        final MapGenerationParameters params = new MapGenerationParameters();
        final MapGenerationParameters result = params.withNullable(false);
        assertSame(result, params);
        assertFalse(params.isNullable());
    }

    /**
     * Unit test for {@link MapGenerationParameters#withKeysType(Type)}.
     */
    @Test
    void testWithKeysType() {
        final MapGenerationParameters params = new MapGenerationParameters();
        final Type value = Integer.class;
        final MapGenerationParameters result = params.withKeysType(value);
        assertSame(result, params);
        assertEquals(value, params.getKeysType());
    }

    /**
     * Unit test for {@link MapGenerationParameters#withValuesType(Type)}.
     */
    @Test
    void testWithValuesType() {
        final MapGenerationParameters params = new MapGenerationParameters();
        final Type value = String.class;
        final MapGenerationParameters result = params.withValuesType(value);
        assertSame(result, params);
        assertEquals(value, params.getValuesType());
    }

    /**
     * Unit test for {@link MapGenerationParameters#withMinSize(int)}.
     */
    @Test
    void testWithMinSize() {
        final MapGenerationParameters params = new MapGenerationParameters();
        final int value = RandomUtils.nextInt();
        final MapGenerationParameters result = params.withMinSize(value);
        assertSame(result, params);
        assertEquals(value, params.getMinSize());
    }

    /**
     * Unit test for {@link MapGenerationParameters#withMaxSize(int)}.
     */
    @Test
    void testWithMaxSize() {
        final MapGenerationParameters params = new MapGenerationParameters();
        final int value = RandomUtils.nextInt();
        final MapGenerationParameters result = params.withMaxSize(value);
        assertSame(result, params);
        assertEquals(value, params.getMaxSize());
    }

    /**
     * Unit test for {@link MapGenerationParameters#clone()}.
     */
    @Test
    void testClone() {
        final MapGenerationParameters params = new MapGenerationParameters();
        params.setNullable(false);
        params.setKeysType(Integer.class);
        params.setValuesType(String.class);
        params.setMinSize(RandomUtils.nextInt());
        params.setMaxSize(RandomUtils.nextInt());
        final MapGenerationParameters result = params.clone();
        assertNotSame(params, result);
        assertEquals(params, result);
    }

    /**
     * Unit test for {@link MapGenerationParameters#equals(Object)},
     * {@link MapGenerationParameters#hashCode()} and
     * {@link MapGenerationParameters#toString()}.
     */
    @Test
    @SuppressWarnings({ "java:S5785" })
    void testEqualsHashCodeToString() {
        final MapGenerationParameters params = new MapGenerationParameters();
        assertFalse(params.equals(null));
        assertTrue(params.equals(params));
        assertFalse(params.equals(new Object()));
        MapGenerationParameters other = new MapGenerationParameters();
        assertTrue(params.equals(other));
        assertEquals(params.hashCode(), other.hashCode());
        assertEquals(params.toString(), other.toString());
        other = new MapGenerationParameters().withNullable(false);
        assertFalse(params.equals(other));
        other = new MapGenerationParameters().withKeysType(Integer.class);
        assertFalse(params.equals(other));
        other = new MapGenerationParameters().withValuesType(String.class);
        assertFalse(params.equals(other));
        other = new MapGenerationParameters().withMinSize(RandomUtils.nextInt());
        assertFalse(params.equals(other));
        other = new MapGenerationParameters().withMaxSize(RandomUtils.nextInt());
        assertFalse(params.equals(other));
    }

    /**
     * Unit test for {@link MapGenerationParameters#builder()}
     * and {@link MapGenerationParameters.BuilderImpl#withKeysType(Class)}.
     */
    @Test
    void testBuilder() {
        final MapGenerationParameters.Builder builder = MapGenerationParameters.builder();
        assertNotNull(builder);
        final Class<?> type = String.class;
        final MapGenerationParameters.ValuesBuilder builder2 = 
                builder.withKeysType(type);
        assertNotNull(builder2);
        final MapGenerationParameters params = builder2.withValuesType(Integer.class);
        assertNotNull(params);
        assertTrue(params.isNullable());
        assertEquals(type, params.getKeysType());
        assertEquals(0, params.getMinSize());
        assertEquals(Integer.MAX_VALUE, params.getMaxSize());
    }

    /**
     * Unit test for {@link MapGenerationParameters#builder()}
     * and {@link MapGenerationParameters.BuilderImpl#withKeysType(ParameterizedType)}.
     */
    @Test
    void testBuilder_ParameterizedType() {
        final MapGenerationParameters.Builder builder = MapGenerationParameters.builder();
        assertNotNull(builder);
        final ParameterizedType type = TypeUtils.parameterize(Set.class, String.class);
        final MapGenerationParameters.ValuesBuilder builder2 = 
                builder.withKeysType(type);
        assertNotNull(builder2);
        final MapGenerationParameters params = builder2.withValuesType(Integer.class);
        assertNotNull(params);
        assertTrue(params.isNullable());
        assertEquals(type, params.getKeysType());
        assertEquals(0, params.getMinSize());
        assertEquals(Integer.MAX_VALUE, params.getMaxSize());
    }

    /**
     * Unit test for {@link MapGenerationParameters#builder()}
     * and {@link MapGenerationParameters.BuilderImpl#withKeysType(GenericArrayType)}.
     */
    @Test
    void testBuilder_GenericArray() {
        final MapGenerationParameters.Builder builder = MapGenerationParameters.builder();
        assertNotNull(builder);
        final GenericArrayType type = TypeUtils.genericArrayType(
                TypeUtils.parameterize(Set.class, String.class));
        final MapGenerationParameters.ValuesBuilder builder2 = 
                builder.withKeysType(type);
        assertNotNull(builder2);
        final MapGenerationParameters params = builder2.withValuesType(Integer.class);
        assertNotNull(params);
        assertTrue(params.isNullable());
        assertEquals(type, params.getKeysType());
        assertEquals(0, params.getMinSize());
        assertEquals(Integer.MAX_VALUE, params.getMaxSize());
    }

    /**
     * Unit test for {@link MapGenerationParameters#builder()}
     * and {@link MapGenerationParameters.BuilderImpl#withValuesType(Class)}.
     */
    @Test
    void testValuesBuilder() {
        final MapGenerationParameters.ValuesBuilder builder = MapGenerationParameters.builder()
                .withKeysType(Integer.class);
        final Class<?> type = String.class;
        final MapGenerationParameters params = builder.withValuesType(type);
        assertNotNull(params);
        assertTrue(params.isNullable());
        assertEquals(type, params.getValuesType());
        assertEquals(0, params.getMinSize());
        assertEquals(Integer.MAX_VALUE, params.getMaxSize());
    }

    /**
     * Unit test for {@link MapGenerationParameters#builder()}
     * and {@link MapGenerationParameters.BuilderImpl#withValuesType(ParameterizedType)}.
     */
    @Test
    void testValuesBuilder_ParameterizedType() {
        final MapGenerationParameters.ValuesBuilder builder = MapGenerationParameters.builder()
                .withKeysType(Integer.class);
        final ParameterizedType type = TypeUtils.parameterize(Set.class, String.class);
        final MapGenerationParameters params = builder.withValuesType(type);
        assertNotNull(params);
        assertTrue(params.isNullable());
        assertEquals(type, params.getValuesType());
        assertEquals(0, params.getMinSize());
        assertEquals(Integer.MAX_VALUE, params.getMaxSize());
    }

    /**
     * Unit test for {@link MapGenerationParameters#builder()}
     * and {@link MapGenerationParameters.BuilderImpl#withValuesType(GenericArrayType)}.
     */
    @Test
    void testValuesBuilder_GenericArray() {
        final MapGenerationParameters.ValuesBuilder builder = MapGenerationParameters.builder()
                .withKeysType(Integer.class);
        final GenericArrayType type = TypeUtils.genericArrayType(
                TypeUtils.parameterize(Set.class, String.class));
        final MapGenerationParameters params = builder.withValuesType(type);
        assertNotNull(params);
        assertTrue(params.isNullable());
        assertEquals(type, params.getValuesType());
        assertEquals(0, params.getMinSize());
        assertEquals(Integer.MAX_VALUE, params.getMaxSize());
    }

    private static class SimpleParams
    extends GenerationParameters
    implements KeyValueGenericParameters {
        @Override
        public Type getKeysType() {
            return null;
        }
        @Override
        public void setKeysType(Type type) {}
        @Override
        public Type getValuesType() {
            return null;
        }
        @Override
        public void setValuesType(Type type) {}
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
