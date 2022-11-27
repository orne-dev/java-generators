package dev.orne.test.rnd.params;

/*-
 * #%L
 * Orne Test ParametersExtractors
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

import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.lang3.reflect.TypeUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code TypeDeclaration}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see TypeDeclaration
 */
@Tag("ut")
class TypeDeclarationTest {

    /**
     * Unit test for {@link TypeDeclaration#TypeDeclaration(Type)}.
     */
    @Test
    void testConstructor() {
        TypeDeclaration declaration = new TypeDeclaration(MyType.class);
        assertSame(MyType.class, declaration.getType());
        final Type parameterizableType = TypeUtils.parameterize(List.class, MyType.class);
        declaration = new TypeDeclaration(parameterizableType);
        assertSame(parameterizableType, declaration.getType());
    }

    /**
     * Unit test for {@link TypeDeclaration#equals(Object)},
     * {@link TypeDeclaration#hashCode()} and
     * {@link TypeDeclaration#toString()}.
     */
    @Test
    @SuppressWarnings({ "java:S5785" })
    void testEqualsHashCodeToString() {
        final TypeDeclaration params = new TypeDeclaration(MyType.class);
        assertFalse(params.equals(null));
        assertTrue(params.equals(params));
        assertFalse(params.equals(new Object()));
        TypeDeclaration other = new TypeDeclaration(MyType.class);
        assertTrue(params.equals(other));
        assertEquals(params.hashCode(), other.hashCode());
        assertEquals(params.toString(), other.toString());
        other = new TypeDeclaration(OtherType.class);
        assertFalse(params.equals(other));
        other = new TypeDeclaration(TypeUtils.parameterize(List.class, MyType.class));
        assertFalse(params.equals(other));
    }

    private static interface MyType {};
    private static interface OtherType {};
}
