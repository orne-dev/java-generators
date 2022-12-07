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

import java.lang.reflect.Type;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Parameters for random {@code Collection} generation.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public class CollectionGenerationParameters
extends NullableParametersImpl
implements SimpleGenericParameters, SizeParameters {

    /** The components type. */
    private Type type;
    /** The minimum size. */
    private int minSize = SizeParameters.DEFAULT_MIN_SIZE;
    /** The maximum size. */
    private int maxSize = SizeParameters.DEFAULT_MAX_SIZE;

    /**
     * Creates a new instance.
     */
    public CollectionGenerationParameters() {
        super();
    }

    /**
     * Copy constructor.
     * 
     * @param copy The instance to copy.
     */
    public CollectionGenerationParameters(
            final @NotNull GenerationParameters copy) {
        super(copy);
        if (copy instanceof SimpleGenericParameters) {
            this.type = ((SimpleGenericParameters) copy).getType();
        }
        if (copy instanceof SizeParameters) {
            this.minSize = ((SizeParameters) copy).getMinSize();
            this.maxSize = ((SizeParameters) copy).getMaxSize();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull CollectionGenerationParameters withNullable(
            final boolean nullable) {
        setNullable(nullable);
        return this;
    }

    /**
     * Returns the components type.
     * 
     * @return The components type.
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Sets the components type.
     * 
     * @param type The components type.
     */
    public void setType(
            final Type type) {
        this.type = type;
    }

    /**
     * Sets the components type.
     * 
     * @param type The components type.
     * @return This instance, for method chaining.
     */
    public @NotNull CollectionGenerationParameters withType(
            final Type type) {
        setType(type);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMinSize() {
        return this.minSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMinSize(
            final int value) {
        this.minSize = value;
    }

    /**
     * Sets the minimum size.
     * 
     * @param value The minimum size.
     * @return This instance, for method chaining.
     */
    public @NotNull CollectionGenerationParameters withMinSize(
            final int value) {
        setMinSize(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxSize() {
        return this.maxSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaxSize(
            final int value) {
        this.maxSize = value;
    }

    /**
     * Sets the maximum size.
     * 
     * @param value The maximum size.
     * @return This instance, for method chaining.
     */
    public @NotNull CollectionGenerationParameters withMaxSize(
            final int value) {
        setMaxSize(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CollectionGenerationParameters clone() {
        return new CollectionGenerationParameters(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(this.type)
                .append(this.minSize)
                .append(this.maxSize)
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
        if (obj.getClass() != getClass()) { return false; }
        final CollectionGenerationParameters other = (CollectionGenerationParameters) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.type, other.type)
                .append(this.minSize, other.minSize)
                .append(this.maxSize, other.maxSize)
                .build();
    }
}
