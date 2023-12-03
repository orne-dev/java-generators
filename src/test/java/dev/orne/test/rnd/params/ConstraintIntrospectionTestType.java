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

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

public class ConstraintIntrospectionTestType {

    public static final String PATTERN_REGEXP = "\\d{5}";
    public static final int SIZE_MIN = 5;
    public static final int DIGITS_INT = 5;
    public static final int DIGITS_FRA = 0;

    public static final Constructor<?> SIMPLE_CONSTRUCTOR;
    public static final Constructor<?> PARAM_CONSTRUCTOR;
    public static final Field PROP0_FIELD;
    public static final Field PROP1_FIELD;
    public static final Field PROP2_FIELD;
    public static final Field STATIC_FIELD;
    public static final Method PROP0_GETTER_METHOD;
    public static final Method PROP1_GETTER_METHOD;
    public static final Method TEST_METHOD;
    public static final Parameter TEST_METHOD_PARAM_0;
    public static final Parameter TEST_METHOD_PARAM_1;
    public static final Parameter TEST_METHOD_PARAM_2;
    public static final Method STATIC_METHOD;
    public static final Parameter STATIC_METHOD_PARAM_0;
    public static final Parameter STATIC_METHOD_PARAM_1;
    public static final Parameter STATIC_METHOD_PARAM_2;

    static {
        try {
            SIMPLE_CONSTRUCTOR = ConstraintIntrospectionTestType.class.getConstructor(
                    String.class);
            PARAM_CONSTRUCTOR = ConstraintIntrospectionTestType.class.getConstructor(
                    String.class,
                    String.class,
                    String.class);
        } catch (NoSuchMethodException | SecurityException e) {
            AssertionError err = new AssertionError("Cannot find the constructor");
            err.initCause(e);
            throw err;
        }
        PROP0_FIELD = FieldUtils.getField(
                ConstraintIntrospectionTestType.class,
                "prop0",
                true);
        PROP1_FIELD = FieldUtils.getField(
                ConstraintIntrospectionTestType.class,
                "prop1",
                true);
        PROP2_FIELD = FieldUtils.getField(
                ConstraintIntrospectionTestType.class,
                "prop2",
                true);
        STATIC_FIELD = FieldUtils.getField(
                ConstraintIntrospectionTestType.class,
                "staticField",
                true);
        PROP0_GETTER_METHOD = MethodUtils.getMatchingMethod(
                ConstraintIntrospectionTestType.class,
                "getProp0");
        PROP1_GETTER_METHOD = MethodUtils.getMatchingMethod(
                ConstraintIntrospectionTestType.class,
                "getProp1");
        TEST_METHOD = MethodUtils.getMatchingMethod(
                ConstraintIntrospectionTestType.class,
                "testMethod",
                String.class,
                String.class,
                String.class);
        TEST_METHOD_PARAM_0 = TEST_METHOD.getParameters()[0];
        TEST_METHOD_PARAM_1 = TEST_METHOD.getParameters()[1];
        TEST_METHOD_PARAM_2 = TEST_METHOD.getParameters()[2];
        STATIC_METHOD = MethodUtils.getMatchingMethod(
                ConstraintIntrospectionTestType.class,
                "staticMethod",
                String.class,
                String.class,
                String.class);
        STATIC_METHOD_PARAM_0 = STATIC_METHOD.getParameters()[0];
        STATIC_METHOD_PARAM_1 = STATIC_METHOD.getParameters()[1];
        STATIC_METHOD_PARAM_2 = STATIC_METHOD.getParameters()[2];
    }

    @NotNull
    @Size(min = SIZE_MIN)
    @Digits(integer = DIGITS_INT, fraction = DIGITS_FRA, groups = Group1.class)
    @Composed(groups = Group2.class)
    private static String staticField;
    private String prop0;
    private String prop1;
    @Size(min = SIZE_MIN)
    private String prop2;
    public ConstraintIntrospectionTestType() {}
    public ConstraintIntrospectionTestType(
            String param0) {}
    public ConstraintIntrospectionTestType(
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

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @Constraint(validatedBy = { })
    @Pattern(regexp = PATTERN_REGEXP)
    @SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
    public static @interface Composed {
        String message() default "{dev.orne.test.rnd.params.ConstraintIntrospectionTestType.Composed.message}";
        Class<?>[] groups() default { };
        Class<? extends Payload>[] payload() default { };
    }
    public static interface Group1 {}
    public static interface Group2 {}
}