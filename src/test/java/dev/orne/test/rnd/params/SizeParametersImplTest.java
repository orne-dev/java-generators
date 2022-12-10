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
 * Unit tests for {@code SizeParametersImpl}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 * @see SizeParametersImpl
 */
@Tag("ut")
class SizeParametersImplTest {

    /**
     * Unit test for {@link SizeParametersImpl#SizeParametersImpl()}.
     */
    @Test
    void testEmptyConstructor() {
        final SizeParametersImpl params = new SizeParametersImpl();
        assertEquals(SizeParameters.DEFAULT_MIN_SIZE, params.getMinSize());
        assertEquals(SizeParameters.DEFAULT_MAX_SIZE, params.getMaxSize());
    }

    /**
     * Unit test for {@link SizeParametersImpl#SizeParametersImpl()}.
     */
    @Test
    void testCopyConstructor_Generic() {
        final GenerationParameters copy = mock(GenerationParameters.class);
        final SizeParametersImpl params = new SizeParametersImpl(copy);
        assertEquals(SizeParameters.DEFAULT_MIN_SIZE, params.getMinSize());
        assertEquals(SizeParameters.DEFAULT_MAX_SIZE, params.getMaxSize());
    }

    /**
     * Unit test for {@link SizeParametersImpl#SizeParametersImpl()}.
     */
    @Test
    void testCopyConstructor() {
        final SizeParameters copy = mock(SizeParameters.class);
        final int minSize = RandomUtils.nextInt();
        final int maxSize = RandomUtils.nextInt();
        given(copy.getMinSize()).willReturn(minSize);
        given(copy.getMaxSize()).willReturn(maxSize);
        final SizeParametersImpl params = new SizeParametersImpl(copy);
        assertEquals(minSize, params.getMinSize());
        assertEquals(maxSize, params.getMaxSize());
    }

    /**
     * Unit test for {@link SizeParametersImpl#withMinSize(int)}.
     */
    @Test
    void testWithMinSize() {
        final SizeParametersImpl params = new SizeParametersImpl();
        final int value = RandomUtils.nextInt();
        final SizeParametersImpl result = params.withMinSize(value);
        assertSame(result, params);
        assertEquals(value, params.getMinSize());
    }

    /**
     * Unit test for {@link SizeParametersImpl#withMaxSize(int)}.
     */
    @Test
    void testWithMaxSize() {
        final SizeParametersImpl params = new SizeParametersImpl();
        final int value = RandomUtils.nextInt();
        final SizeParametersImpl result = params.withMaxSize(value);
        assertSame(result, params);
        assertEquals(value, params.getMaxSize());
    }

    /**
     * Unit test for {@link SizeParametersImpl#equals(Object)},
     * {@link SizeParametersImpl#hashCode()} and
     * {@link SizeParametersImpl#toString()}.
     */
    @Test
    @SuppressWarnings({ "java:S5785" })
    void testEqualsHashCodeToString() {
        final SizeParametersImpl params = new SizeParametersImpl();
        assertFalse(params.equals(null));
        assertTrue(params.equals(params));
        assertFalse(params.equals(new Object()));
        SizeParametersImpl other = new SizeParametersImpl();
        assertTrue(params.equals(other));
        assertEquals(params.hashCode(), other.hashCode());
        assertEquals(params.toString(), other.toString());
        other = new SizeParametersImpl().withMinSize(5);
        assertFalse(params.equals(other));
        other = new SizeParametersImpl().withMaxSize(20);
        assertFalse(params.equals(other));
    }
}
