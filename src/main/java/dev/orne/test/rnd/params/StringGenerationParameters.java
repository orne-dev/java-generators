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

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Parameters for random {@code String} generation.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public class StringGenerationParameters
extends NullableParametersImpl
implements SizeParameters {

    /** The minimum size. */
    private int minSize = SizeParameters.DEFAULT_MIN_SIZE;
    /** The maximum size. */
    private int maxSize = SizeParameters.DEFAULT_MAX_SIZE;

    /**
     * Creates a new instance.
     */
    public StringGenerationParameters() {
        super();
    }

    /**
     * Copy constructor.
     * 
     * @param copy The instance to copy.
     */
    public StringGenerationParameters(
            final @NotNull GenerationParameters copy) {
        super(copy);
        if (copy instanceof SizeParameters) {
            this.minSize = ((SizeParameters) copy).getMinSize();
            this.maxSize = ((SizeParameters) copy).getMaxSize();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull StringGenerationParameters withNullable(
            final boolean nullable) {
        setNullable(nullable);
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
    public @NotNull StringGenerationParameters withMinSize(
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
    public @NotNull StringGenerationParameters withMaxSize(
            final int value) {
        setMaxSize(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
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
        final StringGenerationParameters other = (StringGenerationParameters) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.minSize, other.minSize)
                .append(this.maxSize, other.maxSize)
                .build();
    }
}
