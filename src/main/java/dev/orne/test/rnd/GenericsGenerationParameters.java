package dev.orne.test.rnd;

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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Generation parameters for generic class generators.
 * <p>
 * Provides super type token with the type parameters of the requested value.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @param <T> The generic value type to generate
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public class GenericsGenerationParameters<T>
extends GenerationParameters {

    /** The target parameterized type. */
    private final @NotNull ParameterizedType type;

    /**
     * Crates a new instance.
     * <p>
     * Infers generated values generic type and type parameters from the
     * generic type arguments of this instance class.
     * The class must extend {@code GenericsGenerationParameters} specifying the
     * generic types.
     * <p>
     * Example:
     * <pre>
     * new GenericsGenerationParameters{@literal <}List{@literal <}String{@literal >}{@literal >}() {}
     * </pre>
     */
    protected GenericsGenerationParameters() {
        super();
        final Type typeParam = TypeUtils.unrollVariables(
                TypeUtils.getTypeArguments(getClass(), GenericsGenerationParameters.class),
                GenericsGenerationParameters.class.getTypeParameters()[0]);
        Validate.notNull(
                typeParam,
                "Cannot infer the type of generated values from class %s. Wrong implementation?",
                getClass());
        Validate.isTrue(typeParam instanceof ParameterizedType);
        this.type = validateType(typeParam);
    }

    /**
     * Creates a new instance.
     * 
     * @param type The target parameterized type
     */
    public GenericsGenerationParameters(
            final @NotNull ParameterizedType type) {
        super();
        this.type = validateType(type);
    }

    /**
     * Copy constructor.
     * 
     * @param copy The instance to copy.
     */
    public GenericsGenerationParameters(
            final @NotNull GenericsGenerationParameters<T> copy) {
        super(copy);
        this.type = copy.type;
    }

    /**
     * Validates that the specified
     * @param type
     */
    private final @NotNull ParameterizedType validateType(
            final @NotNull Type type) {
        Validate.notNull(type);
        Validate.isTrue(type instanceof ParameterizedType);
        final ParameterizedType pType = (ParameterizedType) type;
        Validate.isTrue(pType.getRawType() instanceof Class);
        for (final Type typeParam : pType.getActualTypeArguments()) {
            if (typeParam instanceof WildcardType) {
                throw new IllegalArgumentException("Generic type parameters must be generable types. No wildcards allowed.");
            }
        }
        return pType;
    }

    /**
     * Returns the target parameterized type.
     * 
     * @return The target parameterized type
     */
    public @NotNull ParameterizedType getType() {
        return this.type;
    }

    /**
     * Returns the target generic class.
     * 
     * @return The target generic class
     */
    public @NotNull Class<?> getGenericsType() {
        return (Class<?>) this.type.getRawType();
    }

    /**
     * Returns the generic type arguments.
     * 
     * @return The generic type arguments
     */
    public @NotNull Type[] getTypeArguments() {
        return this.type.getActualTypeArguments();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull GenericsGenerationParameters<T> withNullable(boolean nullable) {
        setNullable(nullable);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull GenericsGenerationParameters<T> clone() {
        return new GenericsGenerationParameters<>(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(this.type)
                .toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(
            final Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        Class<?> myClass = getClass();
        Class<?> objClass = obj.getClass();
        if (myClass.isAnonymousClass()) { myClass = myClass.getSuperclass(); }
        if (objClass.isAnonymousClass()) { objClass = objClass.getSuperclass(); }
        if (myClass != objClass) { return false; }
        final GenericsGenerationParameters<?> other = (GenericsGenerationParameters<?>) obj;
        return new EqualsBuilder()
                .append(this.type, other.type)
                .append(this.isNullable(), other.isNullable())
                .build();
    }
}
