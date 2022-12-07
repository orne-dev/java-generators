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

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * The basic implementation of {@code SizeParameters}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public class SizeParametersImpl
implements SizeParameters,
        SizeParameters.Builder {

    /** The minimum size. */
    private int minSize = SizeParameters.DEFAULT_MIN_SIZE;
    /** The maximum size. */
    private int maxSize = SizeParameters.DEFAULT_MAX_SIZE;

    /**
     * Empty constructor.
     */
    public SizeParametersImpl() {
        super();
    }

    /**
     * Copy constructor.
     * 
     * @param copy The instance to copy.
     */
    public SizeParametersImpl(
            final @NotNull GenerationParameters copy) {
        super();
        Validate.notNull(copy);
        if (copy instanceof SizeParameters) {
            final SizeParameters tcopy = (SizeParameters) copy;
            this.minSize = tcopy.getMinSize();
            this.maxSize = tcopy.getMaxSize();
        }
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
     * {@inheritDoc}
     */
    @Override
    public @NotNull SizeParametersImpl withMinSize(
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
     * {@inheritDoc}
     */
    @Override
    public @NotNull SizeParametersImpl withMaxSize(
            final int value) {
        setMaxSize(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}