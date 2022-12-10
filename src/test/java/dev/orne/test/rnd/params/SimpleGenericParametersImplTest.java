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
 * Unit tests for {@code SimpleGenericParametersImpl}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 * @see SimpleGenericParametersImpl
 */
@Tag("ut")
class SimpleGenericParametersImplTest {

    /**
     * Unit test for {@link SimpleGenericParametersImpl#SimpleGenericParametersImpl()}.
     */
    @Test
    void testEmptyConstructor() {
        final SimpleGenericParametersImpl params = new SimpleGenericParametersImpl();
        assertNull(params.getType());
    }

    /**
     * Unit test for {@link SimpleGenericParametersImpl#SimpleGenericParametersImpl()}.
     */
    @Test
    void testCopyConstructor_Generic() {
        final GenerationParameters copy = mock(GenerationParameters.class);
        final SimpleGenericParametersImpl params = new SimpleGenericParametersImpl(copy);
        assertNull(params.getType());
    }

    /**
     * Unit test for {@link SimpleGenericParametersImpl#SimpleGenericParametersImpl()}.
     */
    @Test
    void testCopyConstructor() {
        final SimpleGenericParameters copy = mock(SimpleGenericParameters.class);
        final Type type = String.class;
        given(copy.getType()).willReturn(type);
        final SimpleGenericParametersImpl params = new SimpleGenericParametersImpl(copy);
        assertEquals(type, params.getType());
    }

    /**
     * Unit test for {@link SimpleGenericParametersImpl#withElementsType(Class)}.
     */
    @Test
    void testWithElementType_Class() {
        final SimpleGenericParametersImpl params = new SimpleGenericParametersImpl();
        final Class<?> type = String.class;
        final SimpleGenericParametersImpl result = params.withElementsType(type);
        assertSame(result, params);
        assertEquals(type, params.getType());
    }

    /**
     * Unit test for {@link SimpleGenericParametersImpl#withElementsType(ParameterizedType)}.
     */
    @Test
    void testWithElementType_ParameterizedType() {
        final SimpleGenericParametersImpl params = new SimpleGenericParametersImpl();
        final ParameterizedType type = TypeUtils.parameterize(Set.class, String.class);
        final SimpleGenericParametersImpl result = params.withElementsType(type);
        assertSame(result, params);
        assertEquals(type, params.getType());
    }

    /**
     * Unit test for {@link SimpleGenericParametersImpl#withElementsType(GenericArrayType)}.
     */
    @Test
    void testWithElementType_GenericArrayType() {
        final SimpleGenericParametersImpl params = new SimpleGenericParametersImpl();
        final GenericArrayType type = TypeUtils.genericArrayType(
                TypeUtils.parameterize(Set.class, String.class));
        final SimpleGenericParametersImpl result = params.withElementsType(type);
        assertSame(result, params);
        assertEquals(type, params.getType());
    }

    /**
     * Unit test for {@link SimpleGenericParametersImpl#equals(Object)},
     * {@link SimpleGenericParametersImpl#hashCode()} and
     * {@link SimpleGenericParametersImpl#toString()}.
     */
    @Test
    @SuppressWarnings({ "java:S5785" })
    void testEqualsHashCodeToString() {
        final SimpleGenericParametersImpl params = new SimpleGenericParametersImpl();
        assertFalse(params.equals(null));
        assertTrue(params.equals(params));
        assertFalse(params.equals(new Object()));
        SimpleGenericParametersImpl other = new SimpleGenericParametersImpl();
        assertTrue(params.equals(other));
        assertEquals(params.hashCode(), other.hashCode());
        assertEquals(params.toString(), other.toString());
        other = new SimpleGenericParametersImpl().withElementsType(String.class);
        assertFalse(params.equals(other));
    }
}
