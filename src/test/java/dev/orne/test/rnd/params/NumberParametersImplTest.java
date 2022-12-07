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
 * Unit tests for {@code NumberParametersImpl}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 * @see NumberParametersImpl
 */
@Tag("ut")
class NumberParametersImplTest {

    /**
     * Unit test for {@link NumberParametersImpl#NumberParametersImpl()}.
     */
    @Test
    void testEmptyConstructor() {
        final NumberParametersImpl params = new NumberParametersImpl();
        assertEquals(NumberParameters.DEFAULT_MIN, params.getMin());
        assertEquals(NumberParameters.DEFAULT_MAX, params.getMax());
    }

    /**
     * Unit test for {@link NumberParametersImpl#NumberParametersImpl()}.
     */
    @Test
    void testCopyConstructor_Generic() {
        final GenerationParameters copy = mock(GenerationParameters.class);
        final NumberParametersImpl params = new NumberParametersImpl(copy);
        assertEquals(NumberParameters.DEFAULT_MIN, params.getMin());
        assertEquals(NumberParameters.DEFAULT_MAX, params.getMax());
    }

    /**
     * Unit test for {@link NumberParametersImpl#NumberParametersImpl()}.
     */
    @Test
    void testCopyConstructor() {
        final NumberParameters copy = mock(NumberParameters.class);
        final int min = RandomUtils.nextInt();
        final int max = RandomUtils.nextInt();
        given(copy.getMin()).willReturn(min);
        given(copy.getMax()).willReturn(max);
        final NumberParametersImpl params = new NumberParametersImpl(copy);
        assertEquals(min, params.getMin());
        assertEquals(max, params.getMax());
    }

    /**
     * Unit test for {@link NumberParametersImpl#withMin(Number)}.
     */
    @Test
    void testWithMin() {
        final NumberParametersImpl params = new NumberParametersImpl();
        final int value = RandomUtils.nextInt();
        final NumberParametersImpl result = params.withMin(value);
        assertSame(result, params);
        assertEquals(value, params.getMin());
    }

    /**
     * Unit test for {@link NumberParametersImpl#withMax(Number)}.
     */
    @Test
    void testWithMax() {
        final NumberParametersImpl params = new NumberParametersImpl();
        final int value = RandomUtils.nextInt();
        final NumberParametersImpl result = params.withMax(value);
        assertSame(result, params);
        assertEquals(value, params.getMax());
    }

    /**
     * Unit test for {@link NumberParametersImpl#equals(Object)},
     * {@link NumberParametersImpl#hashCode()} and
     * {@link NumberParametersImpl#toString()}.
     */
    @Test
    @SuppressWarnings({ "java:S5785" })
    void testEqualsHashCodeToString() {
        final NumberParametersImpl params = new NumberParametersImpl();
        assertFalse(params.equals(null));
        assertTrue(params.equals(params));
        assertFalse(params.equals(new Object()));
        NumberParametersImpl other = new NumberParametersImpl();
        assertTrue(params.equals(other));
        assertEquals(params.hashCode(), other.hashCode());
        assertEquals(params.toString(), other.toString());
        other = new NumberParametersImpl().withMin(5);
        assertFalse(params.equals(other));
        other = new NumberParametersImpl().withMax(20);
        assertFalse(params.equals(other));
    }
}
