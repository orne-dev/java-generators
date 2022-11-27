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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.GenerationException;
import dev.orne.test.rnd.Generator;
import dev.orne.test.rnd.Generators;

/**
 * Unit tests for {@code PropertyTypeGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see PropertyTypeGenerator
 */
@Tag("ut")
class PropertyTypeGeneratorTest {

    static final Field PROP0_FIELD;
    static final Field PROP1_FIELD;
    static final Field PROP2_FIELD;

    static {
        PROP0_FIELD = FieldUtils.getField(
                MyType.class,
                "prop0",
                true);
        PROP1_FIELD = FieldUtils.getField(
                MyType.class,
                "prop1",
                true);
        PROP2_FIELD = FieldUtils.getField(
                MyType.class,
                "prop2",
                true);
    }

    /**
     * Unit test for {@link PropertyTypeGenerator#PropertyTypeGenerator(Class, Class, Field)}.
     */
    @Test
    void testConstructor() {
        final PropertyTypeGenerator<String> generator = new PropertyTypeGenerator<>(
                String.class,
                MySubType.class,
                PROP0_FIELD);
        assertSame(String.class, generator.getValueType());
        assertSame(Generators.getGenerator(String.class), generator.getGenerator());
        assertSame(MySubType.class, generator.getValidationClass());
        assertSame(PROP0_FIELD, generator.getProperty());
        assertSame(String.class, generator.getDeclaredType());
    }

    /**
     * Unit test for {@link PropertyTypeGenerator#PropertyTypeGenerator(Class, Class, Field, Generator)}.
     */
    @Test
    void testGeneratorConstructor() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(MyPropType.class);
        final PropertyTypeGenerator<MyPropType> generator = new PropertyTypeGenerator<>(
                MyPropType.class,
                MySubType.class,
                PROP1_FIELD,
                delegated);
        assertSame(MyPropType.class, generator.getValueType());
        assertSame(delegated, generator.getGenerator());
        assertSame(MySubType.class, generator.getValidationClass());
        assertSame(PROP1_FIELD, generator.getProperty());
        assertSame(MyPropType.class, generator.getDeclaredType());
    }

    /**
     * Unit test for {@link PropertyTypeGenerator#PropertyTypeGenerator(Class, Class, Field, Generator)}.
     */
    @Test
    void testGenericsConstructor() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(List.class);
        @SuppressWarnings("rawtypes")
        final PropertyTypeGenerator<List> generator = new PropertyTypeGenerator<>(
                List.class,
                MySubType.class,
                PROP2_FIELD,
                delegated);
        assertSame(List.class, generator.getValueType());
        assertSame(delegated, generator.getGenerator());
        assertSame(MySubType.class, generator.getValidationClass());
        assertSame(PROP2_FIELD, generator.getProperty());
        assertTrue(generator.getDeclaredType() instanceof ParameterizedType);
        final ParameterizedType declaredType = (ParameterizedType) generator.getDeclaredType();
        assertSame(List.class, declaredType.getRawType());
        assertSame(String.class, declaredType.getActualTypeArguments()[0]);
    }

    /**
     * Unit test for {@link PropertyTypeGenerator#targeting(Field)}.
     */
    @Test
    void testTargetingField() {
        final PropertyTypeGenerator<MyPropType> generator = PropertyTypeGenerator.targeting(PROP0_FIELD);
        assertSame(String.class, generator.getValueType());
        assertSame(Generators.getGenerator(String.class), generator.getGenerator());
        assertSame(MyType.class, generator.getValidationClass());
        assertSame(PROP0_FIELD, generator.getProperty());
        assertSame(String.class, generator.getDeclaredType());
    }

    /**
     * Unit test for {@link PropertyTypeGenerator#targeting(Class, Field)}.
     */
    @Test
    void testTargetingClassField() {
        final PropertyTypeGenerator<MyPropType> generator = PropertyTypeGenerator.targeting(MySubType.class, PROP0_FIELD);
        assertSame(String.class, generator.getValueType());
        assertSame(Generators.getGenerator(String.class), generator.getGenerator());
        assertSame(MySubType.class, generator.getValidationClass());
        assertSame(PROP0_FIELD, generator.getProperty());
        assertSame(String.class, generator.getDeclaredType());
    }

    /**
     * Unit test for {@link PropertyTypeGenerator#targeting(Class, String)}.
     */
    @Test
    void testTargetingClassString() {
        final PropertyTypeGenerator<MyPropType> generator = PropertyTypeGenerator.targeting(MySubType.class, "prop0");
        assertSame(String.class, generator.getValueType());
        assertSame(Generators.getGenerator(String.class), generator.getGenerator());
        assertSame(MySubType.class, generator.getValidationClass());
        assertEquals(PROP0_FIELD, generator.getProperty());
        assertSame(String.class, generator.getDeclaredType());
    }

    /**
     * Unit test for {@link PropertyTypeGenerator#targeting(Class, String)}.
     */
    @Test
    void testTargetingClassString_Missing() {
        assertThrows(GenerationException.class, () -> {
            PropertyTypeGenerator.targeting(MySubType.class, "noExistingProperty");
        });
    }

    /**
     * Unit test for {@link PropertyTypeGenerator#getTargetConstraints(Class...)}.
     */
    @Test
    void testTargetGetConstraints() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(String.class);
        final PropertyTypeGenerator<String> generator = new PropertyTypeGenerator<>(
                String.class,
                ConstraintIntrospectionTestType.class,
                ConstraintIntrospectionTestType.PROP2_FIELD,
                delegated);
        final Collection<Annotation> result = generator.getTargetConstraints(
                ConstraintIntrospectionTestType.Group1.class,
                ConstraintIntrospectionTestType.Group2.class);
        final Collection<Annotation> expected = ConstraintIntrospector.findPropertyConstrains(
                ConstraintIntrospectionTestType.class,
                ConstraintIntrospectionTestType.PROP2_FIELD.getName(),
                ConstraintIntrospectionTestType.Group1.class,
                ConstraintIntrospectionTestType.Group2.class); 
        assertEquals(expected, result);
    }

    /**
     * Unit test for {@link PropertyTypeGenerator#equals(Object)},
     * {@link PropertyTypeGenerator#hashCode()} and
     * {@link PropertyTypeGenerator#toString()}
     */
    @Test
    @SuppressWarnings("java:S5785")
    void testEqualsHashCodeToString() {
        final Generator delegated = spy(Generator.class);
        willReturn(true).given(delegated).supports(Object.class);
        willReturn(true).given(delegated).supports(String.class);
        willReturn(true).given(delegated).supports(MyPropType.class);
        willReturn(true).given(delegated).supports(List.class);
        final Generator otherDelegated = spy(Generator.class);
        willReturn(true).given(otherDelegated).supports(Object.class);
        willReturn(true).given(otherDelegated).supports(String.class);
        willReturn(true).given(otherDelegated).supports(MyPropType.class);
        willReturn(true).given(otherDelegated).supports(List.class);
        final PropertyTypeGenerator<MyPropType> generator = new PropertyTypeGenerator<>(
                MyPropType.class,
                MySubType.class,
                PROP1_FIELD,
                delegated);
        assertFalse(generator.equals(null));
        assertTrue(generator.equals(generator));
        assertFalse(generator.equals(new Object()));
        PropertyTypeGenerator<?> other = new PropertyTypeGenerator<>(
                MyPropType.class,
                MySubType.class,
                PROP1_FIELD,
                delegated);
        assertTrue(generator.equals(other));
        assertEquals(generator.hashCode(), other.hashCode());
        assertEquals(generator.toString(), other.toString());
        other = new PropertyTypeGenerator<>(
                Object.class,
                MySubType.class,
                PROP1_FIELD,
                delegated);
        assertFalse(generator.equals(other));
        other = new PropertyTypeGenerator<>(
                MyPropType.class,
                MySubType.class,
                PROP0_FIELD,
                delegated);
        assertFalse(generator.equals(other));
        other = new PropertyTypeGenerator<>(
                MyPropType.class,
                MySubType.class,
                PROP1_FIELD,
                otherDelegated);
        assertFalse(generator.equals(other));
    }

    private interface MyPropType {}
    private static class MyType {
        @SuppressWarnings("unused")
        private String prop0;
        @NotNull
        private MyPropType prop1;
        @NotNull
        @Size(min = 1, max = 10)
        private List<String> prop2;
    }
    private static class MySubType
    extends MyType {}
}
