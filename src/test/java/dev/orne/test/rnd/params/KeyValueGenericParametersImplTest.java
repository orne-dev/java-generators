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

import org.apache.commons.lang3.reflect.TypeUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code KeyValueGenericParametersImpl}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 * @see KeyValueGenericParametersImpl
 */
@Tag("ut")
class KeyValueGenericParametersImplTest {

    /**
     * Unit test for {@link KeyValueGenericParametersImpl#KeyValueGenericParametersImpl()}.
     */
    @Test
    void testEmptyConstructor() {
        final KeyValueGenericParametersImpl params = new KeyValueGenericParametersImpl();
        assertNull(params.getKeysType());
        assertNull(params.getValuesType());
    }

    /**
     * Unit test for {@link KeyValueGenericParametersImpl#KeyValueGenericParametersImpl(GenerationParameters)}.
     */
    @Test
    void testCopyConstructor_Generic() {
        final GenerationParameters copy = mock(GenerationParameters.class);
        final KeyValueGenericParametersImpl params = new KeyValueGenericParametersImpl(copy);
        assertNull(params.getKeysType());
        assertNull(params.getValuesType());
    }

    /**
     * Unit test for {@link KeyValueGenericParametersImpl#KeyValueGenericParametersImpl(GenerationParameters)}.
     */
    @Test
    void testCopyConstructor() {
        final KeyValueGenericParameters copy = mock(KeyValueGenericParameters.class);
        final Type keysType = String.class;
        final Type valuesType = Long.class;
        given(copy.getKeysType()).willReturn(keysType);
        given(copy.getValuesType()).willReturn(valuesType);
        final KeyValueGenericParametersImpl params = new KeyValueGenericParametersImpl(copy);
        assertEquals(keysType, params.getKeysType());
        assertEquals(valuesType, params.getValuesType());
    }

    /**
     * Unit test for {@link KeyValueGenericParametersImpl#withKeysType(Class)}.
     */
    @Test
    void testWithKeysType_Class() {
        final KeyValueGenericParametersImpl params = new KeyValueGenericParametersImpl();
        final Class<?> type = String.class;
        final KeyValueGenericParametersImpl result = params.withKeysType(type);
        assertSame(result, params);
        assertEquals(type, params.getKeysType());
    }

    /**
     * Unit test for {@link KeyValueGenericParametersImpl#withKeysType(ParameterizedType)}.
     */
    @Test
    void testWithKeysType_ParameterizedType() {
        final KeyValueGenericParametersImpl params = new KeyValueGenericParametersImpl();
        final ParameterizedType type = TypeUtils.parameterize(Set.class, String.class);
        final KeyValueGenericParametersImpl result = params.withKeysType(type);
        assertSame(result, params);
        assertEquals(type, params.getKeysType());
    }

    /**
     * Unit test for {@link KeyValueGenericParametersImpl#withKeysType(GenericArrayType)}.
     */
    @Test
    void testWithKeysType_GenericArrayType() {
        final KeyValueGenericParametersImpl params = new KeyValueGenericParametersImpl();
        final GenericArrayType type = TypeUtils.genericArrayType(
                TypeUtils.parameterize(Set.class, String.class));
        final KeyValueGenericParametersImpl result = params.withKeysType(type);
        assertSame(result, params);
        assertEquals(type, params.getKeysType());
    }

    /**
     * Unit test for {@link KeyValueGenericParametersImpl#withValuesType(Class)}.
     */
    @Test
    void testWithValuesType_Class() {
        final KeyValueGenericParametersImpl params = new KeyValueGenericParametersImpl();
        final Class<?> type = String.class;
        final KeyValueGenericParametersImpl result = params.withValuesType(type);
        assertSame(result, params);
        assertEquals(type, params.getValuesType());
    }

    /**
     * Unit test for {@link KeyValueGenericParametersImpl#withValuesType(ParameterizedType)}.
     */
    @Test
    void testWithValuesType_ParameterizedType() {
        final KeyValueGenericParametersImpl params = new KeyValueGenericParametersImpl();
        final ParameterizedType type = TypeUtils.parameterize(Set.class, String.class);
        final KeyValueGenericParametersImpl result = params.withValuesType(type);
        assertSame(result, params);
        assertEquals(type, params.getValuesType());
    }

    /**
     * Unit test for {@link KeyValueGenericParametersImpl#withValuesType(GenericArrayType)}.
     */
    @Test
    void testWithValuesType_GenericArrayType() {
        final KeyValueGenericParametersImpl params = new KeyValueGenericParametersImpl();
        final GenericArrayType type = TypeUtils.genericArrayType(
                TypeUtils.parameterize(Set.class, String.class));
        final KeyValueGenericParametersImpl result = params.withValuesType(type);
        assertSame(result, params);
        assertEquals(type, params.getValuesType());
    }

    /**
     * Unit test for {@link KeyValueGenericParametersImpl#equals(Object)},
     * {@link KeyValueGenericParametersImpl#hashCode()} and
     * {@link KeyValueGenericParametersImpl#toString()}.
     */
    @Test
    @SuppressWarnings({ "java:S5785" })
    void testEqualsHashCodeToString() {
        final KeyValueGenericParametersImpl params = new KeyValueGenericParametersImpl();
        assertFalse(params.equals(null));
        assertTrue(params.equals(params));
        assertFalse(params.equals(new Object()));
        KeyValueGenericParametersImpl other = new KeyValueGenericParametersImpl();
        assertTrue(params.equals(other));
        assertEquals(params.hashCode(), other.hashCode());
        assertEquals(params.toString(), other.toString());
        other = new KeyValueGenericParametersImpl().withKeysType(String.class);
        assertFalse(params.equals(other));
        other = new KeyValueGenericParametersImpl().withValuesType(String.class);
        assertFalse(params.equals(other));
    }
}
