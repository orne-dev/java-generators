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
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.GenerationException;
import dev.orne.test.rnd.Generator;

/**
 * Implementation of {@code TargetedGenerator} that generates random values
 * of the type a target property.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @param <T> The type of generated values
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public class PropertyTypeGenerator<T>
extends AbstractTargetedGenerator<T> {

    /** The class containing the target property. */
    private final @NotNull Class<?> validationClass;
    /** The target property. */
    private final @NotNull Field property;

    /**
     * Creates a new instance.
     * 
     * @param valueType The type of generated values.
     * @param validationClass The class containing the target property.
     * @param property The target property.
     */
    protected PropertyTypeGenerator(
            final @NotNull Class<T> valueType,
            final @NotNull Class<?> validationClass,
            final @NotNull Field property) {
        super(valueType);
        this.validationClass = Validate.notNull(validationClass);
        this.property = Validate.notNull(property);
    }

    /**
     * Creates a new instance.
     * 
     * @param valueType The type of generated values.
     * @param validationClass The class containing the target property.
     * @param property The target property.
     * @param generator The generator to use.
     */
    protected PropertyTypeGenerator(
            final @NotNull Class<T> valueType,
            final @NotNull Class<?> validationClass,
            final @NotNull Field property,
            final @NotNull Generator generator) {
        super(valueType, generator);
        this.validationClass = Validate.notNull(validationClass);
        this.property = Validate.notNull(property);
    }

    /**
     * Creates a new generator targeting the specified field.
     * <p>
     * The validation class will be the class declaring the field.
     * 
     * @param <T> The type of generated values.
     * @param field The target property field.
     * @return The created generator.
     */
    public static <T> dev.orne.test.rnd.params.PropertyTypeGenerator<T> targeting(
            final @NotNull Field field) {
        Validate.notNull(field);
        return targeting(field.getDeclaringClass(), field);
    }

    /**
     * Creates a new generator targeting the specified field, using the
     * specified class as validation class.
     * 
     * @param <T> The type of generated values.
     * @param cls The validation class to use.
     * @param field The target property field.
     * @return The created generator.
     */
    public static <T> dev.orne.test.rnd.params.PropertyTypeGenerator<T> targeting(
            final @NotNull Class<?> cls,
            final @NotNull Field field) {
        Validate.notNull(cls);
        Validate.notNull(field);
        Validate.isTrue(field.getDeclaringClass().isAssignableFrom(cls));
        @SuppressWarnings("unchecked")
        final Class<T> targetType = (Class<T>) field.getType();
        return new PropertyTypeGenerator<>(
                targetType,
                cls,
                field);
    }

    /**
     * Creates a new generator targeting the specified property of the
     * specified class.
     * <p>
     * The specified class will be used as validation class.
     * 
     * @param <T> The type of generated values.
     * @param cls The class containing the property.
     * @param property The property name.
     * @return The created generator.
     */
    public static <T> dev.orne.test.rnd.params.PropertyTypeGenerator<T> targeting(
            final @NotNull Class<?> cls,
            final @NotNull String property) {
        Validate.notNull(cls);
        Validate.notNull(property);
        final Field field = FieldUtils.getField(cls, property, true);
        if (field == null) {
            throw new GenerationException("Target property not found");
        }
        return targeting(cls, field);
    }

    /**
     * Returns the class containing the target property.
     * Used when extracting the property constraints, as constraints can be
     * declared in overridden getters.
     * 
     * @return The class containing the target property.
     */
    public @NotNull Class<?> getValidationClass() {
        return this.validationClass;
    }

    /**
     * Returns the target property.
     * 
     * @return The target property
     */
    public @NotNull Field getProperty() {
        return this.property;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type getDeclaredType() {
        return this.property.getGenericType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull Collection<Annotation> getTargetConstraints(
            final @NotNull Class<?>... groups) {
        return ConstraintIntrospector.findPropertyConstrains(
                this.validationClass,
                this.property.getName(),
                groups);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(this.validationClass)
                .append(this.property)
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
        final PropertyTypeGenerator<?> other = (PropertyTypeGenerator<?>) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.validationClass, other.validationClass)
                .append(this.property, other.property)
                .build();
    }
}
