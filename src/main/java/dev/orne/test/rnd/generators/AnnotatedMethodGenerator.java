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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.WeakHashMap;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.orne.test.rnd.Generator;
import dev.orne.test.rnd.GeneratorMethod;
import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;
import dev.orne.test.rnd.UnsupportedValueTypeException;
import dev.orne.test.rnd.params.ExecutableGenerator;

/**
 * Generator of beans annotated with {@code Currency}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
@Priority(Priority.ANNOTATION_GENERATORS)
public class AnnotatedMethodGenerator
implements Generator {

    /** The class logger. */
    private static final Logger LOG = LoggerFactory.getLogger(AnnotatedMethodGenerator.class);

    /** Required modifiers for instance generation constructors. */
    private static final int GENERATOR_CONSTRUCTOR_MODIFIERS =
            Modifier.PUBLIC;
    /** Required modifiers for instance generation methods. */
    private static final int GENERATOR_METHOD_MODIFIERS =
            Modifier.STATIC | Modifier.PUBLIC;
    /** Informational message for misconfigured identity types. */
    private static final String HELP_MISCONFIGURED =
            "Class '{}' generation is misconfigured."
            + " See GeneratorMethod javadoc for correct bean generation configuration."
            + " No further generation attempts will succed.";
    /** Error message for wrong generation constructor modifiers. */
    private static final String ERR_GENERATOR_CONSTRUCTOR_MODIFIERS =
            "Invalid modifiers in annotated class generation constructor '{}' for type '{}'."
            + " Method must be public.";
    /** Error message for wrong generation method modifiers. */
    private static final String ERR_GENERATOR_METHOD_MODIFIERS =
            "Invalid modifiers in annotated class generation method '{}' for type '{}'."
            + " Method must be public and static.";
    /** Error message for wrong generation method return type. */
    private static final String ERR_GENERATOR_METHOD_RETURN_TYPE =
            "Invalid return type in annotated class generation method '{}' for type '{}'."
            + " Method must return an instance of the class.";

    /** The cache of discovered annotated generators. */
    private final WeakHashMap<Class<?>, Generator> cache =
            new WeakHashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(
            final @NotNull Class<?> type) {
        return getAnnotatedGenerator(type) != null;
    }

    /**
     * Verifies that the specified type is supported by this instance.
     * If test fails throws an {@code IllegalArgumentException} exception
     * as documented in {@link #defaultValue(Class)},
     * {@link #nullableDefaultValue(Class)}, {@link #randomValue(Class)} and
     * {@link #nullableRandomValue(Class)}.
     * 
     * @param type The requested value type.
     * @throws UnsupportedValueTypeException If the specified type is not supported.
     */
    protected void assertSupported(
            final @NotNull Class<?> type) {
        if (!supports(type)) {
            throw new UnsupportedValueTypeException(String.format(
                    "Generators of type %s does not support generation of values of type %s",
                    getClass(),
                    type));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> @NotNull T defaultValue(
            final @NotNull Class<T> type) {
        assertSupported(type);
        return getAnnotatedGenerator(type).defaultValue(type);
    }

    @Override
    public <T> T nullableDefaultValue(
            final @NotNull Class<T> type) {
        assertSupported(type);
        return getAnnotatedGenerator(type).nullableDefaultValue(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> @NotNull T randomValue(
            final @NotNull Class<T> type) {
        assertSupported(type);
        return getAnnotatedGenerator(type).randomValue(type);
    }

    @Override
    public <T> T nullableRandomValue(
            final @NotNull Class<T> type) {
        assertSupported(type);
        return getAnnotatedGenerator(type).nullableRandomValue(type);
    }

    /**
     * Returns annotated generator to be used to create random instances of
     * the specified target class.
     * <p>
     * This method caches the generator (found or not) for further calls.
     * 
     * @param targetType The target class
     * @return The found annotated generator, or {@code null}
     */
    protected Generator getAnnotatedGenerator(
            final @NotNull Class<?> targetType) {
        Validate.notNull(targetType);
        Generator generator;
        synchronized (this.cache) {
            generator = this.cache.computeIfAbsent(targetType, this::discoverAnnotatedGenerator);
        }
        if (generator == Generators.MissingGenerator.INSTANCE) {
            generator = null;
        }
        return generator;
    }

    /**
     * Clears the cache of discovered generators.
     */
    protected void clearCache() {
        synchronized (this.cache) {
            this.cache.clear();
        }
    }

    /**
     * Removes the cache of discovered generator for the specified class.
     * 
     * @param targetType The target class
     */
    protected void clearCache(
            final @NotNull Class<?> targetType) {
        synchronized (this.cache) {
            this.cache.remove(targetType);
        }
    }

    /**
     * Finds a constructor or static method annotated with
     * {@code GeneratorMethod} in the specified target class.
     * 
     * @param targetType The target class
     * @return The found annotated generator, or {@code null}
     */
    protected @NotNull Generator discoverAnnotatedGenerator(
            final @NotNull Class<?> targetType) {
        Generator generator = null;
        try {
            generator = findDeclaredConstructor(targetType);
            if (generator == null) {
                generator = findDeclaredMethod(targetType);
            }
        } catch (final RuntimeException re) {
            LOG.warn(HELP_MISCONFIGURED, targetType);
        }
        if (generator == null) {
            generator = Generators.MissingGenerator.INSTANCE;
        }
        return generator;
    }

    /**
     * Finds a constructor annotated with {@code GeneratorMethod} in the
     * specified target class.
     * The method must be public and return an instance of the class.
     * 
     * @param <T> The target class
     * @param targetType The target class
     * @return The found annotated generator, or {@code null}
     */
    protected <T> ExecutableGenerator<T> findDeclaredConstructor(
            final @NotNull Class<T> targetType) {
        Validate.notNull(targetType);
        ExecutableGenerator<T> generator = null;
        for (final Constructor<?> ctr : targetType.getDeclaredConstructors()) {
            if (ctr.isAnnotationPresent(GeneratorMethod.class)) {
                if ((ctr.getModifiers() & GENERATOR_CONSTRUCTOR_MODIFIERS) != GENERATOR_CONSTRUCTOR_MODIFIERS) {
                    LOG.warn(ERR_GENERATOR_CONSTRUCTOR_MODIFIERS,
                            ctr,
                            targetType);
                } else {
                    @SuppressWarnings("unchecked")
                    final Constructor<T> tCtr = (Constructor<T>) ctr;
                    generator = ExecutableGenerator.of(tCtr);
                }
                break;
            }
        }
        return generator;
    }

    /**
     * Finds a method annotated with {@code GeneratorMethod} in the
     * specified target class.
     * The method must be public, static and return an instance of the class.
     * 
     * @param <T> The target class
     * @param targetType The target class
     * @return The found annotated generator, or {@code null}
     */
    protected <T> ExecutableGenerator<T> findDeclaredMethod(
            final @NotNull Class<T> targetType) {
        Validate.notNull(targetType);
        ExecutableGenerator<T> generator = null;
        for (final Method method : targetType.getDeclaredMethods()) {
            if (method.isAnnotationPresent(GeneratorMethod.class)) {
                if ((method.getModifiers() & GENERATOR_METHOD_MODIFIERS) != GENERATOR_METHOD_MODIFIERS) {
                    LOG.warn(ERR_GENERATOR_METHOD_MODIFIERS,
                            method,
                            targetType);
                } else if (!(targetType.isAssignableFrom(method.getReturnType()))) {
                    LOG.warn(ERR_GENERATOR_METHOD_RETURN_TYPE,
                            method,
                            targetType);
                } else {
                    generator = ExecutableGenerator.of(targetType, method);
                }
                break;
            }
        }
        return generator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getClass())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        // Ignore cache
        return obj.getClass() == getClass();
    }
}
