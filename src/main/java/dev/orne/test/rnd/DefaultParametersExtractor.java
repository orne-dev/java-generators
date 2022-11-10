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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Default implementation for {@code ParametersExtrator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @param <P> The target generation parameters type
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since = "0.1")
public class DefaultParametersExtractor<P>
implements ParametersExtractor<P> {

    /** The available parameters source extractors. */
    private final List<ParametersSourceExtractor<? super P, ?>> extractors;

    /**
     * Creates a new instance.
     * 
     * @param extractors The generation parameters source extractors, in priority order
     */
    public DefaultParametersExtractor(
            final @NotNull Collection<ParametersSourceExtractor<? super P, ?>> extractors) {
        super();
        Validate.notNull(extractors);
        Validate.noNullElements(extractors);
        this.extractors = new ArrayList<>(extractors);
    }

    /**
     * Returns the available parameters source extractors.
     * <p>
     * The returned list
     * 
     * @return The parameters source extractors, in inverse priority order
     */
    @SuppressWarnings("java:S1452")
    public @NotNull List<ParametersSourceExtractor<? super P, ?>> getExtractors() {
        return Collections.unmodifiableList(this.extractors);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void extractParameters(
            final @NotNull P params,
            final @NotNull Object... sources) {
        Validate.notNull(params);
        Validate.notNull(sources);
        Validate.noNullElements(sources);
        extractParameters(params, Arrays.asList(sources));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void extractParameters(
            final @NotNull P params,
            final @NotNull Collection<?> sources) {
        Validate.notNull(params);
        Validate.notNull(sources);
        Validate.noNullElements(sources);
        for (final Object source : sources) {
            extract(params, source);
        }
    }

    /**
     * Tries to populating the specified target parameters extracting the
     * parameter values from the specified source.
     * <p>
     * This implementation applied the suitable source extractors in reverse
     * priority order to allow extractors with higher priority to overwrite
     * parameters extracted by lower priority extractors.
     * 
     * @param <T> The parameters type
     * @param <S> The generation parameters source type
     * @param params The target parameters
     * @param source The generation parameters source
     */
    protected <T extends P, S> void extract(
            final @NotNull T params,
            final @NotNull S source) {
        @SuppressWarnings("unchecked")
        final Class<S> sourceType = (Class<S>) source.getClass();
        for (int i = this.extractors.size() - 1; i >= 0; i--) {
            final ParametersSourceExtractor<? super P, ?> extractor =
                    this.extractors.get(i);
            if (extractor.getSourceType().isAssignableFrom(sourceType)) {
                @SuppressWarnings("unchecked")
                final ParametersSourceExtractor<? super P, ? super S> tmp =
                (ParametersSourceExtractor<? super P, ? super S>) extractor;
                tmp.extractParameters(source, params);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getClass())
                .append(this.extractors)
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
        final DefaultParametersExtractor<?> other = (DefaultParametersExtractor<?>) obj;
        return new EqualsBuilder()
                .append(this.extractors, other.extractors)
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
