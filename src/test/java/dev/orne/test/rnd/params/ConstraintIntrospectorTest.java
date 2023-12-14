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

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code ConstraintIntrospector}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see ConstraintIntrospector
 */
@Tag("ut")
class ConstraintIntrospectorTest {

    /**
     * Unit test for {@link ConstraintIntrospector#findPropertyConstrains(Class, String, Class...)}.
     */
    @Test
    void testFindPropertyConstrains() {
        final Set<Annotation> result = ConstraintIntrospector.findPropertyConstrains(
                ConstraintIntrospectionTestType.class,
                ConstraintIntrospectionTestType.PROP1_FIELD.getName());
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertContainsConstraint(result, NotNull.class);
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findPropertyConstrains(Class, String, Class...)}.
     */
    @Test
    void testFindPropertyConstrains_Empty() {
        final Set<Annotation> result = ConstraintIntrospector.findPropertyConstrains(
                ConstraintIntrospectionTestType.class,
                ConstraintIntrospectionTestType.PROP0_FIELD.getName());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findPropertyConstrains(Class, String, Class...)}.
     */
    @Test
    void testFindPropertyConstrains_Multiple() {
        final Set<Annotation> result = ConstraintIntrospector.findPropertyConstrains(
                ConstraintIntrospectionTestType.class,
                ConstraintIntrospectionTestType.PROP2_FIELD.getName());
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(ConstraintIntrospectionTestType.SIZE_MIN, size.min());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findPropertyConstrains(Class, String, Class...)}.
     */
    @Test
    void testFindPropertyConstrains_Group() {
        final Set<Annotation> result = ConstraintIntrospector.findPropertyConstrains(
                ConstraintIntrospectionTestType.class,
                ConstraintIntrospectionTestType.PROP2_FIELD.getName(),
                Default.class,
                ConstraintIntrospectionTestType.Group1.class);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(3, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(ConstraintIntrospectionTestType.SIZE_MIN, size.min());
        final Digits digits = assertContainsConstraint(result, Digits.class);
        assertEquals(ConstraintIntrospectionTestType.DIGITS_INT, digits.integer());
        assertEquals(ConstraintIntrospectionTestType.DIGITS_FRA, digits.fraction());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findPropertyConstrains(Class, String, Class...)}.
     */
    @Test
    void testFindPropertyConstrains_Composed() {
        final Set<Annotation> result = ConstraintIntrospector.findPropertyConstrains(
                ConstraintIntrospectionTestType.class,
                ConstraintIntrospectionTestType.PROP2_FIELD.getName(),
                Default.class,
                ConstraintIntrospectionTestType.Group2.class);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(4, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(ConstraintIntrospectionTestType.SIZE_MIN, size.min());
        assertContainsConstraint(result, ConstraintIntrospectionTestType.Composed.class);
        final Pattern pattern = assertContainsConstraint(result, Pattern.class);
        assertEquals(ConstraintIntrospectionTestType.PATTERN_REGEXP, pattern.regexp());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findPropertyConstrains(Class, String, Class...)}.
     */
    @Test
    void testFindPropertyConstrains_Static() {
        final Set<Annotation> result = ConstraintIntrospector.findPropertyConstrains(
                ConstraintIntrospectionTestType.class,
                ConstraintIntrospectionTestType.STATIC_FIELD.getName());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodResultConstrains(Method, Class...)}.
     */
    @Test
    void testFindMethodResultConstrains() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodResultConstrains(
                ConstraintIntrospectionTestType.PROP1_GETTER_METHOD);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertContainsConstraint(result, NotNull.class);
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodResultConstrains(Method, Class...)}.
     */
    @Test
    void testFindMethodResultConstrains_Empty() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodResultConstrains(
                ConstraintIntrospectionTestType.PROP0_GETTER_METHOD);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodResultConstrains(Method, Class...)}.
     */
    @Test
    void testFindMethodResultConstrains_Multiple() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodResultConstrains(
                ConstraintIntrospectionTestType.TEST_METHOD);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(ConstraintIntrospectionTestType.SIZE_MIN, size.min());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodResultConstrains(Method, Class...)}.
     */
    @Test
    void testFindMethodResultConstrains_Group() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodResultConstrains(
                ConstraintIntrospectionTestType.TEST_METHOD,
                Default.class,
                ConstraintIntrospectionTestType.Group1.class);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(3, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(ConstraintIntrospectionTestType.SIZE_MIN, size.min());
        final Digits digits = assertContainsConstraint(result, Digits.class);
        assertEquals(ConstraintIntrospectionTestType.DIGITS_INT, digits.integer());
        assertEquals(ConstraintIntrospectionTestType.DIGITS_FRA, digits.fraction());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodResultConstrains(Method, Class...)}.
     */
    @Test
    void testFindMethodResultConstrains_Composed() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodResultConstrains(
                ConstraintIntrospectionTestType.TEST_METHOD,
                Default.class,
                ConstraintIntrospectionTestType.Group2.class);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(4, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(ConstraintIntrospectionTestType.SIZE_MIN, size.min());
        assertContainsConstraint(result, ConstraintIntrospectionTestType.Composed.class);
        final Pattern pattern = assertContainsConstraint(result, Pattern.class);
        assertEquals(ConstraintIntrospectionTestType.PATTERN_REGEXP, pattern.regexp());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodResultConstrains(Method, Class...)}.
     */
    @Test
    void testFindMethodResultConstrains_Static() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodResultConstrains(
                ConstraintIntrospectionTestType.STATIC_METHOD);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findParameterConstrains(Parameter, Class...)}.
     */
    @Test
    void testFindParameterConstrains() {
        final Set<Annotation> result = ConstraintIntrospector.findParameterConstrains(
                ConstraintIntrospectionTestType.TEST_METHOD.getParameters()[1]);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertContainsConstraint(result, NotNull.class);
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findParameterConstrains(Parameter, Class...)}.
     */
    @Test
    void testFindParameterConstrains_Empty() {
        final Set<Annotation> result = ConstraintIntrospector.findParameterConstrains(
                ConstraintIntrospectionTestType.TEST_METHOD.getParameters()[0]);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findParameterConstrains(Parameter, Class...)}.
     */
    @Test
    void testFindParameterConstrains_Multiple() {
        final Set<Annotation> result = ConstraintIntrospector.findParameterConstrains(
                ConstraintIntrospectionTestType.TEST_METHOD.getParameters()[2]);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(ConstraintIntrospectionTestType.SIZE_MIN, size.min());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findParameterConstrains(Parameter, Class...)}.
     */
    @Test
    void testFindParameterConstrains_Group() {
        final Set<Annotation> result = ConstraintIntrospector.findParameterConstrains(
                ConstraintIntrospectionTestType.TEST_METHOD.getParameters()[2],
                Default.class,
                ConstraintIntrospectionTestType.Group1.class);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(3, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(ConstraintIntrospectionTestType.SIZE_MIN, size.min());
        final Digits digits = assertContainsConstraint(result, Digits.class);
        assertEquals(ConstraintIntrospectionTestType.DIGITS_INT, digits.integer());
        assertEquals(ConstraintIntrospectionTestType.DIGITS_FRA, digits.fraction());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findParameterConstrains(Parameter, Class...)}.
     */
    @Test
    void testFindParameterConstrains_Composed() {
        final Set<Annotation> result = ConstraintIntrospector.findParameterConstrains(
                ConstraintIntrospectionTestType.TEST_METHOD.getParameters()[2],
                Default.class,
                ConstraintIntrospectionTestType.Group2.class);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(4, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(ConstraintIntrospectionTestType.SIZE_MIN, size.min());
        assertContainsConstraint(result, ConstraintIntrospectionTestType.Composed.class);
        final Pattern pattern = assertContainsConstraint(result, Pattern.class);
        assertEquals(ConstraintIntrospectionTestType.PATTERN_REGEXP, pattern.regexp());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findParameterConstrains(Parameter, Class...)}.
     */
    @Test
    void testFindParameterConstrains_Constructor() {
        final Set<Annotation> result = ConstraintIntrospector.findParameterConstrains(
                ConstraintIntrospectionTestType.PARAM_CONSTRUCTOR.getParameters()[2],
                Default.class,
                ConstraintIntrospectionTestType.Group1.class);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(3, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(ConstraintIntrospectionTestType.SIZE_MIN, size.min());
        final Digits digits = assertContainsConstraint(result, Digits.class);
        assertEquals(ConstraintIntrospectionTestType.DIGITS_INT, digits.integer());
        assertEquals(ConstraintIntrospectionTestType.DIGITS_FRA, digits.fraction());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findParameterConstrains(Parameter, Class...)}.
     */
    @Test
    void testFindParameterConstrains_Static() {
        final Set<Annotation> result = ConstraintIntrospector.findParameterConstrains(
                ConstraintIntrospectionTestType.STATIC_METHOD.getParameters()[2]);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodParameterConstrains(Method, int, Class...)}.
     */
    @Test
    void testFindMethodParameterConstrains() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodParameterConstrains(
                ConstraintIntrospectionTestType.TEST_METHOD, 1);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertContainsConstraint(result, NotNull.class);
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodParameterConstrains(Method, int, Class...)}.
     */
    @Test
    void testFindMethodParameterConstrains_Empty() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodParameterConstrains(
                ConstraintIntrospectionTestType.TEST_METHOD, 0);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodParameterConstrains(Method, int, Class...)}.
     */
    @Test
    void testFindMethodParameterConstrains_Multiple() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodParameterConstrains(
                ConstraintIntrospectionTestType.TEST_METHOD, 2);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(ConstraintIntrospectionTestType.SIZE_MIN, size.min());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodParameterConstrains(Method, int, Class...)}.
     */
    @Test
    void testFindMethodParameterConstrains_Group() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodParameterConstrains(
                ConstraintIntrospectionTestType.TEST_METHOD,
                2,
                Default.class,
                ConstraintIntrospectionTestType.Group1.class);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(3, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(ConstraintIntrospectionTestType.SIZE_MIN, size.min());
        final Digits digits = assertContainsConstraint(result, Digits.class);
        assertEquals(ConstraintIntrospectionTestType.DIGITS_INT, digits.integer());
        assertEquals(ConstraintIntrospectionTestType.DIGITS_FRA, digits.fraction());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodParameterConstrains(Method, int, Class...)}.
     */
    @Test
    void testFindMethodParameterConstrains_Composed() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodParameterConstrains(
                ConstraintIntrospectionTestType.TEST_METHOD,
                2,
                Default.class,
                ConstraintIntrospectionTestType.Group2.class);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(4, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(ConstraintIntrospectionTestType.SIZE_MIN, size.min());
        assertContainsConstraint(result, ConstraintIntrospectionTestType.Composed.class);
        final Pattern pattern = assertContainsConstraint(result, Pattern.class);
        assertEquals(ConstraintIntrospectionTestType.PATTERN_REGEXP, pattern.regexp());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodParameterConstrains(Method, int, Class...)}.
     */
    @Test
    void testFindMethodParameterConstrains_Static() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodParameterConstrains(
                ConstraintIntrospectionTestType.STATIC_METHOD, 2);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findConstructorParameterConstrains(Constructor, int, Class...)}.
     */
    @Test
    void testFindConstructorParameterConstrains() {
        final Set<Annotation> result = ConstraintIntrospector.findConstructorParameterConstrains(
                ConstraintIntrospectionTestType.PARAM_CONSTRUCTOR, 1);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertContainsConstraint(result, NotNull.class);
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findConstructorParameterConstrains(Constructor, int, Class...)}.
     */
    @Test
    void testFindConstructorParameterConstrains_NoConstraints() {
        final Set<Annotation> result = ConstraintIntrospector.findConstructorParameterConstrains(
                ConstraintIntrospectionTestType.SIMPLE_CONSTRUCTOR, 0);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findConstructorParameterConstrains(Constructor, int, Class...)}.
     */
    @Test
    void testFindConstructorParameterConstrains_Empty() {
        final Set<Annotation> result = ConstraintIntrospector.findConstructorParameterConstrains(
                ConstraintIntrospectionTestType.PARAM_CONSTRUCTOR, 0);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findConstructorParameterConstrains(Constructor, int, Class...)}.
     */
    @Test
    void testFindConstructorParameterConstrains_Multiple() {
        final Set<Annotation> result = ConstraintIntrospector.findConstructorParameterConstrains(
                ConstraintIntrospectionTestType.PARAM_CONSTRUCTOR, 2);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(ConstraintIntrospectionTestType.SIZE_MIN, size.min());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findConstructorParameterConstrains(Constructor, int, Class...)}.
     */
    @Test
    void testFindConstructorParameterConstrains_Group() {
        final Set<Annotation> result = ConstraintIntrospector.findConstructorParameterConstrains(
                ConstraintIntrospectionTestType.PARAM_CONSTRUCTOR,
                2,
                Default.class,
                ConstraintIntrospectionTestType.Group1.class);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(3, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(ConstraintIntrospectionTestType.SIZE_MIN, size.min());
        final Digits digits = assertContainsConstraint(result, Digits.class);
        assertEquals(ConstraintIntrospectionTestType.DIGITS_INT, digits.integer());
        assertEquals(ConstraintIntrospectionTestType.DIGITS_FRA, digits.fraction());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findConstructorParameterConstrains(Constructor, int, Class...)}.
     */
    @Test
    void testFindConstructorParameterConstrains_Composed() {
        final Set<Annotation> result = ConstraintIntrospector.findConstructorParameterConstrains(
                ConstraintIntrospectionTestType.PARAM_CONSTRUCTOR,
                2,
                Default.class,
                ConstraintIntrospectionTestType.Group2.class);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(4, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(ConstraintIntrospectionTestType.SIZE_MIN, size.min());
        assertContainsConstraint(result, ConstraintIntrospectionTestType.Composed.class);
        final Pattern pattern = assertContainsConstraint(result, Pattern.class);
        assertEquals(ConstraintIntrospectionTestType.PATTERN_REGEXP, pattern.regexp());
    }

    @SuppressWarnings("unchecked")
    <T extends Annotation> T assertContainsConstraint(
            final Collection<?> values,
            final Class<T> type) {
        for (final Object value : values) {
            if (type.isInstance(value)) {
                return (T) value;
            }
        }
        throw new AssertionError(String.format(
                "Constraint of type %s not present in collection: %s",
                type,
                values));
    }
}
