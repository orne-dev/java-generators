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
 * Unit tests for {@code NullableParametersImpl}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 * @see NullableParametersImpl
 */
@Tag("ut")
class NullableParametersImplTest {

    /**
     * Unit test for {@link NullableParametersImpl#NullableParametersImpl()}.
     */
    @Test
    void testEmptyConstructor() {
        final NullableParametersImpl params = new NullableParametersImpl();
        assertEquals(NullableParameters.DEFAULT_NULLABLE, params.isNullable());
    }

    /**
     * Unit test for {@link NullableParametersImpl#NullableParametersImpl()}.
     */
    @Test
    void testCopyConstructor_Generic() {
        final GenerationParameters copy = mock(GenerationParameters.class);
        final NullableParametersImpl params = new NullableParametersImpl(copy);
        assertEquals(NullableParameters.DEFAULT_NULLABLE, params.isNullable());
    }

    /**
     * Unit test for {@link NullableParametersImpl#NullableParametersImpl()}.
     */
    @Test
    void testCopyConstructor() {
        final NullableParameters copy = mock(NullableParameters.class);
        given(copy.isNullable()).willReturn(!NullableParameters.DEFAULT_NULLABLE);
        final NullableParametersImpl params = new NullableParametersImpl(copy);
        assertEquals(!NullableParameters.DEFAULT_NULLABLE, params.isNullable());
    }

    /**
     * Unit test for {@link NullableParametersImpl#withNullable(boolean)}.
     */
    @Test
    void testWithNullable() {
        final NullableParametersImpl params = new NullableParametersImpl();
        final NullableParametersImpl result = params.withNullable(!NullableParameters.DEFAULT_NULLABLE);
        assertSame(result, params);
        assertEquals(!NullableParameters.DEFAULT_NULLABLE, params.isNullable());
    }

    /**
     * Unit test for {@link NullableParametersImpl#equals(Object)},
     * {@link NullableParametersImpl#hashCode()} and
     * {@link NullableParametersImpl#toString()}.
     */
    @Test
    @SuppressWarnings({ "java:S5785" })
    void testEqualsHashCodeToString() {
        final NullableParametersImpl params = new NullableParametersImpl();
        assertFalse(params.equals(null));
        assertTrue(params.equals(params));
        assertFalse(params.equals(new Object()));
        final NullableParametersImpl other = new NullableParametersImpl();
        assertTrue(params.equals(other));
        assertEquals(params.hashCode(), other.hashCode());
        assertEquals(params.toString(), other.toString());
        other.setNullable(!NullableParameters.DEFAULT_NULLABLE);
        assertFalse(params.equals(other));
    }
}
