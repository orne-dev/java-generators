package dev.orne.test.rnd.generators;

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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.lang3.reflect.TypeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.GenerationException;
import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;
import dev.orne.test.rnd.params.ParameterizableGenerator;
import dev.orne.test.rnd.params.TypeDeclaration;

/**
 * Unit tests for {@code CollectionGeneratorUtils}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 * @see CollectionGeneratorUtils
 */
@Tag("ut")
class CollectionGeneratorUtilsTest {

    /**
     * Reset registered generators.
     */
    @AfterEach
    void resetGenerators() {
        Generators.reset();
    }

    /**
     * Unit test for {@link CollectionGeneratorUtils#randomComponent(Type)}
     */
    @Test
    void testRandomComponent() {
        final ParameterizableGenerator mockGenerator = spy(ParameterizableGenerator.class);
        final ParameterizedType parameterizedType = TypeUtils.parameterize(MyGenericType.class, MyType.class);
        final TypeDeclaration params = new TypeDeclaration(parameterizedType);
        final MyType mockValue = mock(MyType.class);
        final MyGenericType<?> mockGenericValue = mock(MyGenericType.class);
        willReturn(Priority.DEFAULT).given(mockGenerator).getPriority();
        willReturn(mockGenerator).given(mockGenerator).asParameterizable();
        willReturn(true).given(mockGenerator).supports(MyType.class);
        willReturn(mockValue).given(mockGenerator).randomValue(MyType.class);
        willReturn(true).given(mockGenerator).supports(MyGenericType.class);
        willReturn(mockGenericValue).given(mockGenerator).randomValue(MyGenericType.class, params);
        Generators.register(mockGenerator);
        assertSame(mockValue, CollectionGeneratorUtils.randomComponent(MyType.class));
        assertSame(mockGenericValue, CollectionGeneratorUtils.randomComponent(parameterizedType));
        assertThrows(NullPointerException.class, () -> {
            CollectionGeneratorUtils.randomComponent(null);
        });
        final Type unsupportedType = mock(Type.class);
        assertThrows(GenerationException.class, () -> {
            CollectionGeneratorUtils.randomComponent(unsupportedType);
        });
    }

    /**
     * Unit test for {@link CollectionGeneratorUtils#nullableRandomComponent(Type)}
     */
    @Test
    void testNullableRandomComponent() {
        final ParameterizableGenerator mockGenerator = spy(ParameterizableGenerator.class);
        final ParameterizedType parameterizedType = TypeUtils.parameterize(MyGenericType.class, MyType.class);
        final TypeDeclaration params = new TypeDeclaration(parameterizedType);
        final MyType mockValue = mock(MyType.class);
        final MyGenericType<?> mockGenericValue = mock(MyGenericType.class);
        willReturn(Priority.DEFAULT).given(mockGenerator).getPriority();
        willReturn(mockGenerator).given(mockGenerator).asParameterizable();
        willReturn(true).given(mockGenerator).supports(MyType.class);
        willReturn(mockValue).given(mockGenerator).nullableRandomValue(MyType.class);
        willReturn(true).given(mockGenerator).supports(MyGenericType.class);
        willReturn(mockGenericValue).given(mockGenerator).nullableRandomValue(MyGenericType.class, params);
        Generators.register(mockGenerator);
        assertSame(mockValue, CollectionGeneratorUtils.nullableRandomComponent(MyType.class));
        assertSame(mockGenericValue, CollectionGeneratorUtils.nullableRandomComponent(parameterizedType));
        assertThrows(NullPointerException.class, () -> {
            CollectionGeneratorUtils.nullableRandomComponent(null);
        });
        final Type unsupportedType = mock(Type.class);
        assertThrows(GenerationException.class, () -> {
            CollectionGeneratorUtils.nullableRandomComponent(unsupportedType);
        });
    }

    private static class MyType {
        private MyType() {}
    }

    private static class MyGenericType<T> {
        private MyGenericType() {}
    }
}
