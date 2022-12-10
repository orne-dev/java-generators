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

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * The basic implementation of {@code SimpleGenericParameters}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public class SimpleGenericParametersImpl
implements SimpleGenericParameters,
        SimpleGenericParameters.Builder {

    /** The components type. */
    private Type type;

    /**
     * Empty constructor.
     */
    public SimpleGenericParametersImpl() {
        super();
    }

    /**
     * Copy constructor.
     * 
     * @param copy The instance to copy.
     */
    public SimpleGenericParametersImpl(
            final @NotNull GenerationParameters copy) {
        super();
        Validate.notNull(copy);
        if (copy instanceof SimpleGenericParameters) {
            final SimpleGenericParameters tcopy = (SimpleGenericParameters) copy;
            this.type = tcopy.getType();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type getType() {
        return this.type;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setType(
            final Type type) {
        this.type = type;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull SimpleGenericParametersImpl withElementsType(
            final @NotNull Class<?> type) {
        Validate.notNull(type);
        setType(type);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull SimpleGenericParametersImpl withElementsType(
            final @NotNull GenericArrayType type) {
        Validate.notNull(type);
        setType(type);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull SimpleGenericParametersImpl withElementsType(
            final @NotNull ParameterizedType type) {
        Validate.notNull(type);
        setType(type);
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