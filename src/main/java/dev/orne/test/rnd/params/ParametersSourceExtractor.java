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

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.Priority;

/**
 * Interface for generation parameters extractor from a source type.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @param <P> The target generation parameters type
 * @param <S> The parameters source type
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public interface ParametersSourceExtractor<P, S> {

    /** Shared instance of fallback NOP extractor. */
    public static final NopExtractor NOP = new NopExtractor();

    /**
     * Returns the target generation parameters type.
     * 
     * @return The target generation parameters type
     */
    @NotNull Class<P> getParametersType();

    /**
     * Returns the parameters source type.
     * 
     * @return The parameters source type
     */
    @NotNull Class<S> getSourceType();

    /**
     * Extracts the parameters from the specified source,
     * modifying the generation parameters as required.
     * 
     * @param from The metadata source
     * @param target The target generation parameters
     */
    void extractParameters(
            @NotNull S from,
            @NotNull P target);

    /**
     * Returns the priority of this extractor.
     * 
     * @return The priority of this extractor
     */
    default int getPriority() {
        final Class<?> type = getClass();
        final Priority annot = type.getAnnotation(Priority.class);
        return annot == null ? Priority.DEFAULT : annot.value();
    }

    /**
     * Fallback implementation of {@code ParametersSourceExtractor} that
     * does nothing.
     * <p>
     * For by missed extractor results mainly.
     * 
     * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
     * @version 1.0, 2022-11
     * @since ParametersSourceExtractor 1.0
     */
    public static final class NopExtractor
    implements ParametersSourceExtractor<Object, Object> {

        /**
         * Private constructor. Only one instance allowed.
         */
        private NopExtractor() {
            super();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Class<Object> getParametersType() {
            return Object.class;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Class<Object> getSourceType() {
            return Object.class;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void extractParameters(
                final @NotNull Object from,
                final @NotNull Object target) {
            // NOP
        }
    }
}
