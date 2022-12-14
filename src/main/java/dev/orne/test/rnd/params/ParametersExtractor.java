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

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Interface for generation parameters extractor.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @param <P> The target generation parameters type
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since = "0.1")
public interface ParametersExtractor<P> {

    /**
     * Extract the generation parameters from the specified sources.
     * 
     * @param params The generation parameters
     * @param sources The parameter sources to apply
     */
    void extractParameters(
            @NotNull P params,
            @NotNull Object... sources);

    /**
     * Extract the generation parameters from the specified sources.
     * 
     * @param params The generation parameters
     * @param sources The parameter sources to apply
     */
    void extractParameters(
            @NotNull P params,
            Collection<?> sources);
}
