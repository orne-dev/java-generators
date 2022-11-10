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

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Abstract implementation of {@code ParametersSourceExtractor}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @param <P> The generation parameters type
 * @param <S> The metadata sources type
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public abstract class AbstractParametersSourceExtractor<P, S>
implements ParametersSourceExtractor<P, S> {

    /** The target generation parameters type. */
    private final @NotNull Class<P> parametersType;
    /** The metadata sources type. */
    private final @NotNull Class<S> sourceType;

    /**
     * Crates a new instance.
     * 
     * @param parametersType The target generation parameters type
     * @param sourceType The metadata sources type
     */
    protected AbstractParametersSourceExtractor(
            final @NotNull Class<P> parametersType,
            final @NotNull Class<S> sourceType) {
        super();
        this.parametersType = Validate.notNull(parametersType);
        this.sourceType = Validate.notNull(sourceType);
    }

    /**
     * Crates a new instance.
     * <p>
     * Infers the target generation parameters and metadata sources types
     * from the generic type arguments of this class.
     * The class must extend this class specifying the generic types.
     * <p>
     * Example:
     * <pre>
     * class MyExtractor
     * extends AbstractParametersSourceExtractor{@literal <}MyParams, MySource{@literal >} {
     *     ...
     * }
     * </pre>
     */
    @SuppressWarnings("unchecked")
    protected AbstractParametersSourceExtractor() {
        super();
        this.parametersType = (Class<P>) TypeUtils.unrollVariables(
                TypeUtils.getTypeArguments(getClass(), AbstractParametersSourceExtractor.class),
                AbstractParametersSourceExtractor.class.getTypeParameters()[0]);
        this.sourceType = (Class<S>) TypeUtils.unrollVariables(
                TypeUtils.getTypeArguments(getClass(), AbstractParametersSourceExtractor.class),
                AbstractParametersSourceExtractor.class.getTypeParameters()[1]);
        Validate.notNull(
                this.parametersType,
                "Cannot infer the target generation parameters type from class %s. Wrong implementation?",
                getClass());
        Validate.notNull(
                this.sourceType,
                "Cannot infer the metadata sources type from class %s. Wrong implementation?",
                getClass());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<P> getParametersType() {
        return this.parametersType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<S> getSourceType() {
        return this.sourceType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getClass())
                .append(this.parametersType)
                .append(this.sourceType)
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
        final AbstractParametersSourceExtractor<?, ?> other = (AbstractParametersSourceExtractor<?, ?>) obj;
        return new EqualsBuilder()
                .append(this.parametersType, other.parametersType)
                .append(this.sourceType, other.sourceType)
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
