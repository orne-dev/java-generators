package dev.orne.test.rnd.params;

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
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.GenerationException;
import dev.orne.test.rnd.Generator;

/**
 * Implementation of {@code TargetedGenerator} that generates random values
 * of the target constructor parameter type.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @param <T> The type of generated values
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public class ConstructorParameterTypeGenerator<T>
extends AbstractTargetedGenerator<T> {

    /** The target constructor. */
    private final @NotNull Constructor<?> constructor;
    /** The target constructor parameter index. */
    private final int parameterIndex;

    /**
     * Creates a new instance.
     * 
     * @param valueType The type of generated values.
     * @param constructor The target constructor
     * @param parameterIndex The target constructor parameter index.
     */
    protected ConstructorParameterTypeGenerator(
            @NotNull Class<T> valueType,
            @NotNull Constructor<?> constructor,
            int parameterIndex) {
        super(valueType);
        this.constructor = Validate.notNull(constructor);
        this.parameterIndex = parameterIndex;
        Validate.validIndex(constructor.getParameterTypes(), parameterIndex);
    }

    /**
     * Creates a new instance.
     * 
     * @param valueType The type of generated values.
     * @param constructor The target constructor
     * @param parameterIndex The target constructor parameter index.
     * @param generator The generator to use.
     */
    protected ConstructorParameterTypeGenerator(
            @NotNull Class<T> valueType,
            @NotNull Constructor<?> constructor,
            int parameterIndex,
            @NotNull Generator generator) {
        super(valueType, generator);
        this.constructor = Validate.notNull(constructor);
        this.parameterIndex = parameterIndex;
        Validate.validIndex(constructor.getParameterTypes(), parameterIndex);
    }

    /**
     * Creates a new generator targeting the specified constructor parameter
     * type.
     * 
     * @param <T> The type of generated values.
     * @param constructor The target constructor
     * @param parameterIndex The target constructor parameter index.
     * @return The created generator.
     */
    public static <T> dev.orne.test.rnd.params.ConstructorParameterTypeGenerator<T> targeting(
            final @NotNull Constructor<?> constructor,
            final int parameterIndex) {
        Validate.notNull(constructor);
        Validate.validIndex(constructor.getParameterTypes(), parameterIndex);
        @SuppressWarnings("unchecked")
        final Class<T> targetType = (Class<T>) constructor.getParameterTypes()[parameterIndex];
        return new ConstructorParameterTypeGenerator<>(targetType, constructor, parameterIndex);
    }

    /**
     * Creates a new generator targeting the specified constructor parameter
     * type.
     * 
     * @param <T> The type of generated values.
     * @param cls The class containing the constructor.
     * @param parameterIndex The target parameter index.
     * @param parameterTypes The method parameter types.
     * @return The created generator.
     */
    public static <T> dev.orne.test.rnd.params.ConstructorParameterTypeGenerator<T> targeting(
            final @NotNull Class<?> cls,
            final int parameterIndex,
            final @NotNull Class<?>... parameterTypes) {
        Validate.notNull(cls);
        Constructor<?> ctr;
        try {
            ctr = cls.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new GenerationException("Target constructor not found");
        }
        return targeting(ctr, parameterIndex);
    }

    /**
     * Returns the target constructor.
     * 
     * @return The target constructor.
     */
    public @NotNull Constructor<?> getConstructor() {
        return this.constructor;
    }

    /**
     * Returns the target constructor parameter index.
     * 
     * @return The target constructor parameter index.
     */
    public int getParameterIndex() {
        return this.parameterIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type getDeclaredType() {
        return this.constructor.getGenericParameterTypes()[this.parameterIndex];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull Collection<Annotation> getTargetConstraints(
            final @NotNull Class<?>... groups) {
        return ConstraintIntrospector.findConstructorParameterConstrains(
                this.constructor,
                this.parameterIndex,
                groups);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(this.constructor)
                .append(this.parameterIndex)
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
        final ConstructorParameterTypeGenerator<?> other = (ConstructorParameterTypeGenerator<?>) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.constructor, other.constructor)
                .append(this.parameterIndex, other.parameterIndex)
                .build();
    }
}
