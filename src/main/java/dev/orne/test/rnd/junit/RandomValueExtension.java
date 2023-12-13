package dev.orne.test.rnd.junit;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

/*-
 * #%L
 * Orne Test Generators
 * %%
 * Copyright (C) 2023 Orne Developments
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

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.ModifierSupport;

import dev.orne.test.rnd.GenerationException;
import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.params.ParameterTypeGenerator;

/**
 * JUnit Jupiter extension for automatic random value injection.
 * <p>
 * Supports injection in static fields (before all tests), in instance fields
 * (before each test) and in constructor and test or callback method arguments.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2023-11
 * @since 0.1
 * @see Random
 */
public class RandomValueExtension
implements BeforeAllCallback, BeforeEachCallback, ParameterResolver {

    /**
     * {@inheritDoc}
     * <p>
     * Injects static fields annotated with {@code @Random} with random
     * values.
     */
    @Override
    public void beforeAll(
            final @NotNull ExtensionContext context)
    throws GenerationException {
        injectFields(
                context.getRequiredTestClass(),
                null);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Injects instance fields annotated with {@code @Random} with random
     * values.
     */
    @Override
    public void beforeEach(
            final @NotNull ExtensionContext context)
    throws GenerationException {
        injectFields(
                context.getRequiredTestClass(),
                context.getRequiredTestInstance());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supportsParameter(
            final @NotNull ParameterContext parameterContext,
            final @NotNull ExtensionContext extensionContext)
    throws ParameterResolutionException {
        return parameterContext.isAnnotated(Random.class);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Resolves method parameters annotated with {@code @Random} with random
     * values.
     */
    @Override
    public Object resolveParameter(
            final @NotNull ParameterContext parameterContext,
            final @NotNull ExtensionContext extensionContext)
    throws ParameterResolutionException {
        final Parameter param = parameterContext.getParameter();
        final Random annot = param.getAnnotation(Random.class);
        final ParameterTypeGenerator<?> generator = Generators.forParameter(parameterContext.getParameter());
        // TODO Support extraction of constraints from static methods
        if (ModifierSupport.isStatic(param.getDeclaringExecutable())) {
            return generator.randomValue(annot.groups());
        } else {
            return generator.nullableRandomValue(annot.groups());
        }
    }

    /**
     * Injects to the fields annotated with {@code @Random} of the test class
     * generated random values.
     * <p>
     * If a test instance is provided annotated instance fields will be
     * injected. Otherwise instance fields will be injected.
     * 
     * @param testClass The test class.
     * @param testInstance The test instance, if any.
     * @throws ParameterResolutionException If an error occurs injecting the
     * field values.
     */
    protected void injectFields(
            final @NotNull Class<?> testClass,
            final Object testInstance) {
        FieldUtils.getFieldsListWithAnnotation(testClass, Random.class).parallelStream()
            .filter(f -> testInstance == null ? ModifierSupport.isStatic(f) : ModifierSupport.isNotStatic(f))
            .forEach(field -> injectField(testClass, field, testInstance, generateValue(testClass, field)));
    }

    /**
     * Generates a random value for the specified field of the test class.
     * 
     * @param testClass The test class.
     * @param field The target field.
     * @return The generated random value.
     */
    protected Object generateValue(
            final @NotNull Class<?> testClass,
            final @NotNull Field field) {
        final Random annot = field.getAnnotation(Random.class);
        // TODO Support extraction of constraints from static methods
        if (ModifierSupport.isStatic(field)) {
            return Generators.forField(testClass, field).randomValue(annot.groups());
        } else {
            return Generators.forField(testClass, field).nullableRandomValue(annot.groups());
        }
    }

    /**
     * Injects to the target field of the test class a generated random value.
     * 
     * @param testClass The test class.
     * @param field The target field.
     * @param testInstance The test instance, if any.
     * @throws ParameterResolutionException If an error occurs injecting the
     * field value.
     */
    protected void injectField(
            final @NotNull Class<?> testClass,
            final @NotNull Field field,
            final Object testInstance,
            final Object value) {
        if (testInstance != null && ModifierSupport.isFinal(field)) {
            throw new ParameterResolutionException(
                    String.format("Cannot inject random value to final field %s", field));
        }
        try {
            FieldUtils.writeField(field, testInstance, value, true);
        } catch (final ReflectiveOperationException e) {
            throw new ParameterResolutionException(
                    String.format("Cannot inject random value to field %s", field), e);
        }
    }
}
