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
 * The basic implementation of {@code KeyValueGenericParameters}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public class KeyValueGenericParametersImpl
implements KeyValueGenericParameters,
        KeyValueGenericParameters.KeysTypeBuilder,
        KeyValueGenericParameters.ValuesTypeBuilder {

    /** The keys type. */
    private Type keysType;
    /** The values type. */
    private Type valuesType;

    /**
     * Empty constructor.
     */
    public KeyValueGenericParametersImpl() {
        super();
    }

    /**
     * Copy constructor.
     * 
     * @param copy The instance to copy.
     */
    public KeyValueGenericParametersImpl(
            final @NotNull GenerationParameters copy) {
        super();
        Validate.notNull(copy);
        if (copy instanceof KeyValueGenericParameters) {
            final KeyValueGenericParameters tcopy = (KeyValueGenericParameters) copy;
            this.keysType = tcopy.getKeysType();
            this.valuesType = tcopy.getValuesType();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type getKeysType() {
        return this.keysType;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setKeysType(
            final Type type) {
        this.keysType = type;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull KeyValueGenericParametersImpl withKeysType(
            final @NotNull Class<?> type) {
        Validate.notNull(type);
        setKeysType(type);
        return this;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull KeyValueGenericParametersImpl withKeysType(
            final @NotNull ParameterizedType type) {
        Validate.notNull(type);
        setKeysType(type);
        return this;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull KeyValueGenericParametersImpl withKeysType(
            final @NotNull GenericArrayType type) {
        Validate.notNull(type);
        setKeysType(type);
        return this;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Type getValuesType() {
        return this.valuesType;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setValuesType(
            final Type type) {
        this.valuesType = type;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull KeyValueGenericParametersImpl withValuesType(
            final @NotNull Class<?> type) {
        Validate.notNull(type);
        setValuesType(type);
        return this;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull KeyValueGenericParametersImpl withValuesType(
            final @NotNull ParameterizedType type) {
        Validate.notNull(type);
        setValuesType(type);
        return this;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull KeyValueGenericParametersImpl withValuesType(
            final @NotNull GenericArrayType type) {
        Validate.notNull(type);
        setValuesType(type);
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