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

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.Generator;
import dev.orne.test.rnd.Generators;

/**
 * Abstract implementation of {@code TargetedGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @param <T> The type of generated values
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public abstract class AbstractTargetedGenerator<T>
implements TargetedGenerator<T> {

    /** The type of generated values. */
    private final @NotNull Class<T> valueType;
    /** The generator for the type of the target. */
    private final @NotNull Generator generator;

    /**
     * Creates a new instance.
     * 
     * @param valueType The type of generated values.
     */
    protected AbstractTargetedGenerator(
            final @NotNull Class<T> valueType) {
        this(valueType, Generators.getGenerator(valueType));
    }

    /**
     * Creates a new instance.
     * 
     * @param valueType The type of generated values.
     * @param generator The generator to use.
     */
    protected AbstractTargetedGenerator(
            final @NotNull Class<T> valueType,
            final @NotNull Generator generator) {
        super();
        this.valueType = Validate.notNull(valueType);
        this.generator = Validate.notNull(generator);
        Validate.isTrue(generator.supports(valueType));
    }

    /**
     * Returns the type of generated values.
     * 
     * @return The type of generated values.
     */
    public @NotNull Class<T> getValueType() {
        return this.valueType;
    }

    /**
     * Returns the declared type of the target.
     * 
     * @return The declared type of the target.
     */
    public abstract Type getDeclaredType();

    /**
     * Returns the generator to use.
     * 
     * @return The generator to use.
     */
    protected @NotNull Generator getGenerator() {
        return this.generator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull T defaultValue(
            final @NotNull Class<?>... groups) {
        final T result;
        if (this.generator instanceof ParameterizableGenerator) {
            result = ((ParameterizableGenerator) this.generator).defaultValue(
                    getValueType(),
                    getParameterSources(groups));
            
        } else {
            result = this.generator.defaultValue(getValueType());
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T nullableDefaultValue(
            final @NotNull Class<?>... groups) {
        final T result;
        if (this.generator instanceof ParameterizableGenerator) {
            result = ((ParameterizableGenerator) this.generator).nullableDefaultValue(
                    getValueType(),
                    getParameterSources(groups));
            
        } else {
            result = this.generator.nullableDefaultValue(getValueType());
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull T randomValue(
            final @NotNull Class<?>... groups) {
        final T result;
        if (this.generator instanceof ParameterizableGenerator) {
            result = ((ParameterizableGenerator) this.generator).randomValue(
                    getValueType(),
                    getParameterSources(groups));
            
        } else {
            result = this.generator.randomValue(getValueType());
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T nullableRandomValue(
            final @NotNull Class<?>... groups) {
        final T result;
        if (this.generator instanceof ParameterizableGenerator) {
            result = ((ParameterizableGenerator) this.generator).nullableRandomValue(
                    getValueType(),
                    getParameterSources(groups));
            
        } else {
            result = this.generator.nullableRandomValue(getValueType());
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Object[] getParameterSources(
            @NotNull Class<?>... groups) {
        final List<Object> list = getParameterSourceList(groups);
        return list.toArray(new Object[list.size()]);
    }

    /**
     * Returns the generation parameter sources to be used.
     * <p>
     * The specified validation groups will be used to retrieve the constraints
     * to use as sources.
     * 
     * @param groups The validation groups .
     * @return The generation parameter sources
     */
    protected @NotNull List<Object> getParameterSourceList(
            @NotNull Class<?>... groups) {
        final List<Object> result = new ArrayList<>();
        final Type declaredType = getDeclaredType();
        if (declaredType != null) {
            result.add(new TypeDeclaration(declaredType));
        }
        result.addAll(getTargetConstraints(groups));
        return result;
    }

    /**
     * Returns the constraints of the target for the specified validation
     * groups.
     * 
     * @param groups The validation groups to use when extracting the
     * constraints of the target
     * @return The constraints of the target
     */
    protected abstract @NotNull Collection<Annotation> getTargetConstraints(
            @NotNull Class<?>... groups);

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getClass())
                .append(this.valueType)
                .append(this.generator)
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
        final AbstractTargetedGenerator<?> other = (AbstractTargetedGenerator<?>) obj;
        return new EqualsBuilder()
                .append(this.valueType, other.valueType)
                .append(this.generator, other.generator)
                .build();
    }
}
