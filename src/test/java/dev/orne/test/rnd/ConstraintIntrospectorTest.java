package dev.orne.test.rnd;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

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
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
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

    private static final String PATTERN_REGEXP = "\\d{5}";
    private static final int SIZE_MIN = 5;
    private static final int DIGITS_INT = 5;
    private static final int DIGITS_FRA = 0;

    static final Constructor<?> SIMPLE_CONSTRUCTOR;
    static final Constructor<?> PARAM_CONSTRUCTOR;
    static final Field PROP0_FIELD;
    static final Field PROP1_FIELD;
    static final Field PROP2_FIELD;
    static final Method PROP0_GETTER_METHOD;
    static final Method PROP1_GETTER_METHOD;
    static final Method TEST_METHOD;
    static final Method STATIC_METHOD;

    static {
        try {
            SIMPLE_CONSTRUCTOR = TestType.class.getConstructor(
                    String.class);
            PARAM_CONSTRUCTOR = TestType.class.getConstructor(
                    String.class,
                    String.class,
                    String.class);
        } catch (NoSuchMethodException | SecurityException e) {
            AssertionError err = new AssertionError("Cannot find the constructor");
            err.initCause(e);
            throw err;
        }
        PROP0_FIELD = FieldUtils.getField(
                TestType.class,
                "prop0",
                true);
        PROP1_FIELD = FieldUtils.getField(
                TestType.class,
                "prop1",
                true);
        PROP2_FIELD = FieldUtils.getField(
                TestType.class,
                "prop2",
                true);
        PROP0_GETTER_METHOD = MethodUtils.getMatchingMethod(
                TestType.class,
                "getProp0");
        PROP1_GETTER_METHOD = MethodUtils.getMatchingMethod(
                TestType.class,
                "getProp1");
        TEST_METHOD = MethodUtils.getMatchingMethod(
                TestType.class,
                "testMethod",
                String.class,
                String.class,
                String.class);
        STATIC_METHOD = MethodUtils.getMatchingMethod(
                TestType.class,
                "staticMethod",
                String.class,
                String.class,
                String.class);
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findPropertyConstrains(Class, String, Class...)}.
     */
    @Test
    void testFindPropertyConstrains() {
        final Set<Annotation> result = ConstraintIntrospector.findPropertyConstrains(TestType.class, "prop1");
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
        final Set<Annotation> result = ConstraintIntrospector.findPropertyConstrains(TestType.class, "prop0");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findPropertyConstrains(Class, String, Class...)}.
     */
    @Test
    void testFindPropertyConstrains_Multiple() {
        final Set<Annotation> result = ConstraintIntrospector.findPropertyConstrains(TestType.class, "prop2");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(SIZE_MIN, size.min());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findPropertyConstrains(Class, String, Class...)}.
     */
    @Test
    void testFindPropertyConstrains_Group() {
        final Set<Annotation> result = ConstraintIntrospector.findPropertyConstrains(
                TestType.class,
                "prop2",
                Default.class,
                Group1.class);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(3, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(SIZE_MIN, size.min());
        final Digits digits = assertContainsConstraint(result, Digits.class);
        assertEquals(DIGITS_INT, digits.integer());
        assertEquals(DIGITS_FRA, digits.fraction());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findPropertyConstrains(Class, String, Class...)}.
     */
    @Test
    void testFindPropertyConstrains_Composed() {
        final Set<Annotation> result = ConstraintIntrospector.findPropertyConstrains(
                TestType.class,
                "prop2",
                Default.class,
                Group2.class);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(4, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(SIZE_MIN, size.min());
        assertContainsConstraint(result, Composed.class);
        final Pattern pattern = assertContainsConstraint(result, Pattern.class);
        assertEquals(PATTERN_REGEXP, pattern.regexp());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findPropertyConstrains(Class, String, Class...)}.
     */
    @Test
    void testFindPropertyConstrains_Static() {
        final Set<Annotation> result = ConstraintIntrospector.findPropertyConstrains(TestType.class, "staticField");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodResultConstrains(Method, Class...)}.
     */
    @Test
    void testFindMethodResultConstrains() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodResultConstrains(PROP1_GETTER_METHOD);
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
        final Set<Annotation> result = ConstraintIntrospector.findMethodResultConstrains(PROP0_GETTER_METHOD);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodResultConstrains(Method, Class...)}.
     */
    @Test
    void testFindMethodResultConstrains_Multiple() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodResultConstrains(TEST_METHOD);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(SIZE_MIN, size.min());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodResultConstrains(Method, Class...)}.
     */
    @Test
    void testFindMethodResultConstrains_Group() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodResultConstrains(
                TEST_METHOD,
                Default.class,
                Group1.class);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(3, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(SIZE_MIN, size.min());
        final Digits digits = assertContainsConstraint(result, Digits.class);
        assertEquals(DIGITS_INT, digits.integer());
        assertEquals(DIGITS_FRA, digits.fraction());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodResultConstrains(Method, Class...)}.
     */
    @Test
    void testFindMethodResultConstrains_Composed() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodResultConstrains(
                TEST_METHOD,
                Default.class,
                Group2.class);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(4, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(SIZE_MIN, size.min());
        assertContainsConstraint(result, Composed.class);
        final Pattern pattern = assertContainsConstraint(result, Pattern.class);
        assertEquals(PATTERN_REGEXP, pattern.regexp());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodResultConstrains(Method, Class...)}.
     */
    @Test
    void testFindMethodResultConstrains_Static() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodResultConstrains(STATIC_METHOD);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodParameterConstrains(Method, int, Class...)}.
     */
    @Test
    void testFindMethodParameterConstrains() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodParameterConstrains(TEST_METHOD, 1);
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
        final Set<Annotation> result = ConstraintIntrospector.findMethodParameterConstrains(TEST_METHOD, 0);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodParameterConstrains(Method, int, Class...)}.
     */
    @Test
    void testFindMethodParameterConstrains_Multiple() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodParameterConstrains(TEST_METHOD, 2);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(SIZE_MIN, size.min());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodParameterConstrains(Method, int, Class...)}.
     */
    @Test
    void testFindMethodParameterConstrains_Group() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodParameterConstrains(
                TEST_METHOD,
                2,
                Default.class,
                Group1.class);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(3, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(SIZE_MIN, size.min());
        final Digits digits = assertContainsConstraint(result, Digits.class);
        assertEquals(DIGITS_INT, digits.integer());
        assertEquals(DIGITS_FRA, digits.fraction());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodParameterConstrains(Method, int, Class...)}.
     */
    @Test
    void testFindMethodParameterConstrains_Composed() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodParameterConstrains(
                TEST_METHOD,
                2,
                Default.class,
                Group2.class);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(4, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(SIZE_MIN, size.min());
        assertContainsConstraint(result, Composed.class);
        final Pattern pattern = assertContainsConstraint(result, Pattern.class);
        assertEquals(PATTERN_REGEXP, pattern.regexp());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findMethodParameterConstrains(Method, int, Class...)}.
     */
    @Test
    void testFindMethodParameterConstrains_Static() {
        final Set<Annotation> result = ConstraintIntrospector.findMethodParameterConstrains(STATIC_METHOD, 2);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findConstructorParameterConstrains(Constructor, int, Class...)}.
     */
    @Test
    void testFindConstructorParameterConstrains() {
        final Set<Annotation> result = ConstraintIntrospector.findConstructorParameterConstrains(PARAM_CONSTRUCTOR, 1);
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
        final Set<Annotation> result = ConstraintIntrospector.findConstructorParameterConstrains(SIMPLE_CONSTRUCTOR, 0);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findConstructorParameterConstrains(Constructor, int, Class...)}.
     */
    @Test
    void testFindConstructorParameterConstrains_Empty() {
        final Set<Annotation> result = ConstraintIntrospector.findConstructorParameterConstrains(PARAM_CONSTRUCTOR, 0);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findConstructorParameterConstrains(Constructor, int, Class...)}.
     */
    @Test
    void testFindConstructorParameterConstrains_Multiple() {
        final Set<Annotation> result = ConstraintIntrospector.findConstructorParameterConstrains(PARAM_CONSTRUCTOR, 2);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(SIZE_MIN, size.min());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findConstructorParameterConstrains(Constructor, int, Class...)}.
     */
    @Test
    void testFindConstructorParameterConstrains_Group() {
        final Set<Annotation> result = ConstraintIntrospector.findConstructorParameterConstrains(
                PARAM_CONSTRUCTOR,
                2,
                Default.class,
                Group1.class);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(3, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(SIZE_MIN, size.min());
        final Digits digits = assertContainsConstraint(result, Digits.class);
        assertEquals(DIGITS_INT, digits.integer());
        assertEquals(DIGITS_FRA, digits.fraction());
    }

    /**
     * Unit test for {@link ConstraintIntrospector#findConstructorParameterConstrains(Constructor, int, Class...)}.
     */
    @Test
    void testFindConstructorParameterConstrains_Composed() {
        final Set<Annotation> result = ConstraintIntrospector.findConstructorParameterConstrains(
                PARAM_CONSTRUCTOR,
                2,
                Default.class,
                Group2.class);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(4, result.size());
        assertContainsConstraint(result, NotNull.class);
        final Size size = assertContainsConstraint(result, Size.class);
        assertEquals(SIZE_MIN, size.min());
        assertContainsConstraint(result, Composed.class);
        final Pattern pattern = assertContainsConstraint(result, Pattern.class);
        assertEquals(PATTERN_REGEXP, pattern.regexp());
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

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @Constraint(validatedBy = { })
    @Pattern(regexp = PATTERN_REGEXP)
    @SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
    static @interface Composed {
        String message() default "{dev.orne.test.rnd.ConstraintIntrospectorTest.Composed.message}";
        Class<?>[] groups() default { };
        Class<? extends Payload>[] payload() default { };
    }
    static interface Group1 {}
    static interface Group2 {}
    static class TestType {
        @NotNull
        @Size(min = SIZE_MIN)
        @Digits(integer = DIGITS_INT, fraction = DIGITS_FRA, groups = Group1.class)
        @Composed(groups = Group2.class)
        private static String staticField;
        private String prop0;
        private String prop1;
        @Size(min = SIZE_MIN)
        private String prop2;
        public TestType() {}
        public TestType(
                String param0) {}
        public TestType(
                String param0,
                @NotNull
                String param1,
                @NotNull
                @Size(min = SIZE_MIN)
                @Digits(integer = DIGITS_INT, fraction = DIGITS_FRA, groups = Group1.class)
                @Composed(groups = Group2.class)
                String param2) {}
        public String getProp0() {
            return prop0;
        }
        public void setProp0(String value) {
            this.prop0 = value;
        }
        @NotNull
        public String getProp1() {
            return prop1;
        }
        public void setProp1(String value) {
            this.prop1 = value;
        }
        @NotNull
        @Digits(integer = DIGITS_INT, fraction = DIGITS_FRA, groups = Group1.class)
        @Composed(groups = Group2.class)
        public String getProp2() {
            return prop2;
        }
        public void setProp2(String value) {
            this.prop2 = value;
        }
        @NotNull
        @Size(min = SIZE_MIN)
        @Digits(integer = DIGITS_INT, fraction = DIGITS_FRA, groups = Group1.class)
        @Composed(groups = Group2.class)
        public String testMethod(
                final String prop0,
                @NotNull
                final String prop1,
                @NotNull
                @Size(min = SIZE_MIN)
                @Digits(integer = DIGITS_INT, fraction = DIGITS_FRA, groups = Group1.class)
                @Composed(groups = Group2.class)
                final String prop2) {
            return null;
        }
        @NotNull
        @Size(min = SIZE_MIN)
        @Digits(integer = DIGITS_INT, fraction = DIGITS_FRA, groups = Group1.class)
        @Composed(groups = Group2.class)
        public static String staticMethod(
                final String prop0,
                @NotNull
                final String prop1,
                @NotNull
                @Size(min = SIZE_MIN)
                @Digits(integer = DIGITS_INT, fraction = DIGITS_FRA, groups = Group1.class)
                @Composed(groups = Group2.class)
                final String prop2) {
            return null;
        }
    }
}
