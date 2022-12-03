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
 * Base class for value generation parameters.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public class GenerationParameters {

    /** If a {@code null} value is accepted. */
    private boolean nullable = true;

    /**
     * Empty constructor.
     */
    public GenerationParameters() {
        super();
    }

    /**
     * Copy constructor.
     * 
     * @param copy The instance to copy.
     */
    public GenerationParameters(
            final @NotNull GenerationParameters copy) {
        super();
        Validate.notNull(copy);
        this.nullable = copy.isNullable();
    }

    /**
     * Returns {@code true} if a {@code null} value is accepted.
     * 
     * @return If a {@code null} value is accepted.
     */
    public boolean isNullable() {
        return this.nullable;
    }

    /**
     * Sets if a {@code null} value is accepted.
     * 
     * @param nullable If a {@code null} value is accepted.
     */
    public void setNullable(
            final boolean nullable) {
        this.nullable = nullable;
    }

    /**
     * Sets if a {@code null} value is accepted.
     * 
     * @param nullable If a {@code null} value is accepted.
     * @return This instance, for method chaining.
     */
    public @NotNull GenerationParameters withNullable(
            final boolean nullable) {
        setNullable(nullable);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull GenerationParameters clone() {
        return new GenerationParameters(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.nullable)
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
        final GenerationParameters other = (GenerationParameters) obj;
        return new EqualsBuilder()
                .append(this.nullable, other.nullable)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
