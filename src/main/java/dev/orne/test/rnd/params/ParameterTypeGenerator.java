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
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.Generator;

/**
 * Implementation of {@code TargetedGenerator} that generates random values
 * of the target parameter type.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2023-11
 * @param <T> The type of generated values
 * @since 0.2
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public class ParameterTypeGenerator<T>
extends AbstractTargetedGenerator<T> {

    /** The target parameter. */
    private final @NotNull Parameter parameter;

    /**
     * Creates a new instance.
     * 
     * @param valueType The type of generated values.
     * @param parameter The target parameter
     */
    protected ParameterTypeGenerator(
            final @NotNull Class<T> valueType,
            final @NotNull Parameter parameter) {
        super(valueType);
        this.parameter = Validate.notNull(parameter);
    }

    /**
     * Creates a new instance.
     * 
     * @param valueType The type of generated values.
     * @param parameter The target parameter
     * @param generator The generator to use.
     */
    protected ParameterTypeGenerator(
            final @NotNull Class<T> valueType,
            final @NotNull Parameter parameter,
            final @NotNull Generator generator) {
        super(valueType, generator);
        this.parameter = Validate.notNull(parameter);
    }

    /**
     * Creates a new generator targeting the specified constructor parameter
     * type.
     * 
     * @param <T> The type of generated values.
     * @param parameter The target parameter
     * @return The created generator.
     */
    public static <T> ParameterTypeGenerator<T> targeting(
            final @NotNull Parameter parameter) {
        Validate.notNull(parameter);
        @SuppressWarnings("unchecked")
        final Class<T> targetType = (Class<T>) parameter.getType();
        return new ParameterTypeGenerator<>(targetType, parameter);
    }

    /**
     * Returns the target parameter.
     * 
     * @return The target parameter.
     */
    public @NotNull Parameter getParameter() {
        return this.parameter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type getDeclaredType() {
        return this.parameter.getParameterizedType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull Collection<Annotation> getTargetConstraints(
            final @NotNull Class<?>... groups) {
        return ConstraintIntrospector.findParameterConstrains(
                this.parameter,
                groups);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(this.parameter)
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
        final ParameterTypeGenerator<?> other = (ParameterTypeGenerator<?>) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.parameter, other.parameter)
                .build();
    }
}
