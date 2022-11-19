package dev.orne.test.rnd;

/*-
 * #%L
 * Orne Test Generators
 * %%
 * Copyright (C) 2021 Orne Developments
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.ConstructorDescriptor;
import javax.validation.metadata.MethodDescriptor;
import javax.validation.metadata.ParameterDescriptor;
import javax.validation.metadata.PropertyDescriptor;
import javax.validation.metadata.ReturnValueDescriptor;
import javax.validation.metadata.ElementDescriptor.ConstraintFinder;

import org.apache.commons.lang3.Validate;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Utility class for retrieving constraints annotations from tar.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public final class ConstraintIntrospector {

    /** The default validation groups when no one is provided. */
    private static final Class<?>[] DEFAULT_GROUPS = new Class<?>[] {
        Default.class
    };

    /**
     * Private constructor.
     */
    private ConstraintIntrospector() {
        // Utility class
    }

    /** The default validator. */
    private static final Validator DEFAULT_VALIDATOR =
            Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * Retrieves the constraint annotations of the specified property
     * for the specified validation groups.
     * 
     * @param type The bean type.
     * @param property The property name.
     * @param groups The validation groups.
     * @return The constraint annotations of the specified property.
     */
    public static @NotNull Set<Annotation> findPropertyConstrains(
            final @NotNull Class<?> type,
            final @NotNull String property,
            final @NotNull Class<?>... groups) {
        return findPropertyConstrains(DEFAULT_VALIDATOR, type, property, groups);
    }

    /**
     * Retrieves the constraint annotations of the specified property
     * for the specified validation groups.
     * 
     * @param validator The validator to use.
     * @param type The bean type.
     * @param property The property name.
     * @param groups The validation groups.
     * @return The constraint annotations of the specified property.
     */
    public static @NotNull Set<Annotation> findPropertyConstrains(
            final @NotNull Validator validator,
            final @NotNull Class<?> type,
            final @NotNull String property,
            final @NotNull Class<?>... groups) {
        Validate.notNull(validator);
        Validate.notNull(type);
        Validate.notNull(property);
        final BeanDescriptor beanDesc = validator.getConstraintsForClass(type);
        final PropertyDescriptor propDesc = beanDesc.getConstraintsForProperty(property);
        if (propDesc == null) {
            return Collections.emptySet();
        } else {
            return extractAnnotations(propDesc.findConstraints(), groups);
        }
    }

    /**
     * Retrieves the constraint annotations of the specified method parameter
     * for the specified validation groups.
     * 
     * @param method The target method.
     * @param parameterIndex The parameter index.
     * @param groups The validation groups.
     * @return The constraint annotations of the specified method parameter.
     */
    public static @NotNull Set<Annotation> findMethodParameterConstrains(
            final @NotNull Method method,
            final int parameterIndex,
            final @NotNull Class<?>... groups) {
        return findMethodParameterConstrains(DEFAULT_VALIDATOR, method, parameterIndex, groups);
    }

    /**
     * Retrieves the constraint annotations of the specified method parameter
     * for the specified validation groups.
     * 
     * @param validator The validator to use.
     * @param method The target method.
     * @param parameterIndex The parameter index.
     * @param groups The validation groups.
     * @return The constraint annotations of the specified method parameter.
     */
    public static @NotNull Set<Annotation> findMethodParameterConstrains(
            final @NotNull Validator validator,
            final @NotNull Method method,
            final int parameterIndex,
            final @NotNull Class<?>... groups) {
        Validate.notNull(validator);
        Validate.notNull(method);
        Validate.validIndex(method.getParameterTypes(), parameterIndex);
        final BeanDescriptor beanDesc = validator.getConstraintsForClass(
                method.getDeclaringClass());
        final MethodDescriptor methodDesc = beanDesc.getConstraintsForMethod(
                method.getName(),
                method.getParameterTypes());
        if (methodDesc == null) {
            return Collections.emptySet();
        }
        final ParameterDescriptor paramDesc =
                methodDesc.getParameterDescriptors().get(parameterIndex);
        return extractAnnotations(
                paramDesc.findConstraints(),
                groups);
    }

    /**
     * Retrieves the constraint annotations of the specified method result
     * for the specified validation groups.
     * 
     * @param method The target method.
     * @param groups The validation groups.
     * @return The constraint annotations of the specified method result.
     */
    public static @NotNull Set<Annotation> findMethodResultConstrains(
            final @NotNull Method method,
            final Class<?>... groups) {
        return findMethodResultConstrains(DEFAULT_VALIDATOR, method, groups);
    }

    /**
     * Retrieves the constraint annotations of the specified method result
     * for the specified validation groups.
     * 
     * @param validator The validator to use.
     * @param method The target method.
     * @param groups The validation groups.
     * @return The constraint annotations of the specified method result.
     */
    public static @NotNull Set<Annotation> findMethodResultConstrains(
            final @NotNull Validator validator,
            final @NotNull Method method,
            final @NotNull Class<?>... groups) {
        Validate.notNull(validator);
        Validate.notNull(method);
        final BeanDescriptor beanDesc = validator.getConstraintsForClass(
                method.getDeclaringClass());
        final MethodDescriptor methodDesc = beanDesc.getConstraintsForMethod(
                method.getName(),
                method.getParameterTypes());
        if (methodDesc == null) {
            return Collections.emptySet();
        }
        final ReturnValueDescriptor returnDesc = methodDesc.getReturnValueDescriptor();
        return extractAnnotations(
                returnDesc.findConstraints(),
                groups);
    }

    /**
     * Retrieves the constraint annotations of the specified constructor
     * parameter for the specified validation groups.
     * 
     * @param method The target method.
     * @param parameterIndex The parameter index.
     * @param groups The validation groups.
     * @return The constraint annotations of the specified constructor
     * parameter.
     */
    public static @NotNull Set<Annotation> findConstructorParameterConstrains(
            final @NotNull Constructor<?> constructor,
            final int parameterIndex,
            final @NotNull Class<?>... groups) {
        return findConstructorParameterConstrains(DEFAULT_VALIDATOR, constructor, parameterIndex, groups);
    }

    /**
     * Retrieves the constraint annotations of the specified constructor
     * parameter for the specified validation groups.
     * 
     * @param validator The validator to use.
     * @param method The target method.
     * @param parameterIndex The parameter index.
     * @param groups The validation groups.
     * @return The constraint annotations of the specified constructor
     * parameter.
     */
    public static @NotNull Set<Annotation> findConstructorParameterConstrains(
            final @NotNull Validator validator,
            final @NotNull Constructor<?> constructor,
            final int parameterIndex,
            final @NotNull Class<?>... groups) {
        Validate.notNull(validator);
        Validate.notNull(constructor);
        Validate.validIndex(constructor.getParameterTypes(), parameterIndex);
        final BeanDescriptor beanDesc = validator.getConstraintsForClass(
                constructor.getDeclaringClass());
        final ConstructorDescriptor ctrDesc = beanDesc.getConstraintsForConstructor(
                constructor.getParameterTypes());
        if (ctrDesc == null) {
            return Collections.emptySet();
        }
        final ParameterDescriptor paramDesc =
                ctrDesc.getParameterDescriptors().get(parameterIndex);
        return extractAnnotations(
                paramDesc.findConstraints(),
                groups);
    }

    /**
     * Extracts the constraint annotations from the specified constraint finder
     * for the specified validation groups.
     * 
     * @param finder The constraint finder.
     * @param groups The validation groups.
     * @return The constraint annotations found.
     */
    private static @NotNull Set<Annotation> extractAnnotations(
            final @NotNull ConstraintFinder finder,
            @NotNull Class<?>... groups) {
        if (groups.length == 0) {
            groups = DEFAULT_GROUPS;
        }
        finder.unorderedAndMatchingGroups(groups);
        final Set<Annotation> result = new HashSet<>();
        for (final ConstraintDescriptor<?> descriptor : finder.getConstraintDescriptors()) {
            extractConstraints(descriptor, result);
        }
        return result;
    }

    /**
     * Gets the constraint annotations from the specified constraint
     * descriptor, including composing constraints annotations, and adds
     * to the passed annotation set.
     * for the specified validation groups.
     * 
     * @param descriptor The constraint descriptor.
     * @param result The set to add the annotations to.
     */
    private static void extractConstraints(
            final @NotNull ConstraintDescriptor<?> descriptor,
            final @NotNull Set<Annotation> result) {
        result.add(descriptor.getAnnotation());
        for (final ConstraintDescriptor<?> nested : descriptor.getComposingConstraints()) {
            extractConstraints(nested, result);
        }
    }
}
