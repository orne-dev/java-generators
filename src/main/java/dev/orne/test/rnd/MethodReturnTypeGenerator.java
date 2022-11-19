package dev.orne.test.rnd;

/*-
 * #%L
 * Orne Test Generators
 * %%
 * Copyright (C) 2021 - 2022 Orne Developments
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
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Implementation of {@code TargetedGenerator} that generates random values
 * of the target method return type.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @param <T> The type of generated values
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public class MethodReturnTypeGenerator<T>
extends AbstractTargetedGenerator<T> {

    /** The target method. */
    private final @NotNull Method method;

    /**
     * Creates a new instance.
     * 
     * @param valueType The type of generated values.
     * @param method The target method
     */
    protected MethodReturnTypeGenerator(
            final @NotNull Class<T> valueType,
            final @NotNull Method method) {
        super(valueType);
        this.method = Validate.notNull(method);
    }

    /**
     * Creates a new instance.
     * 
     * @param valueType The type of generated values.
     * @param method The target method
     * @param generator The generator to use.
     */
    protected MethodReturnTypeGenerator(
            final @NotNull Class<T> valueType,
            final @NotNull Method method,
            final @NotNull Generator generator) {
        super(valueType, generator);
        this.method = Validate.notNull(method);
    }

    /**
     * Creates a new generator targeting the specified method return type.
     * 
     * @param <T> The type of generated values.
     * @param method The target method.
     * @return The created generator.
     */
    public static <T> @NotNull MethodReturnTypeGenerator<T> targeting(
            final @NotNull Method method) {
        Validate.notNull(method);
        @SuppressWarnings("unchecked")
        final Class<T> targetType = (Class<T>) method.getReturnType();
        return new MethodReturnTypeGenerator<>(targetType, method);
    }

    /**
     * Creates a new generator targeting the specified method return type.
     * 
     * @param <T> The type of generated values.
     * @param cls The class containing the method.
     * @param methodName The method name.
     * @param parameterTypes The method parameter types.
     * @return The created generator.
     */
    public static <T> @NotNull MethodReturnTypeGenerator<T> targeting(
            final @NotNull Class<?> cls,
            final @NotNull String methodName,
            final @NotNull Class<?>... parameterTypes) {
        Validate.notNull(cls);
        Validate.notNull(methodName);
        final Method method = MethodUtils.getMatchingMethod(cls, methodName, parameterTypes);
        if (method == null) {
            throw new GenerationException("Target method not found");
        }
        return targeting(method);
    }

    /**
     * Returns the target method.
     * 
     * @return The target method
     */
    public Method getMethod() {
        return this.method;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Type getDeclaredType() {
        return this.method.getGenericReturnType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull Collection<Annotation> getTargetConstraints(
            final @NotNull Class<?>... groups) {
        return ConstraintIntrospector.findMethodResultConstrains(
                this.method,
                groups);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(this.method)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) { return false; }
        final MethodReturnTypeGenerator<?> other = (MethodReturnTypeGenerator<?>) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.method, other.method)
                .build();
    }
}
