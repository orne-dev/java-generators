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
import javax.validation.constraints.Positive;

import org.apache.commons.lang3.math.NumberUtils;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Generation parameters extractor that detects {@code Positive}
 * constraint annotations and sets {@code NumParameters.min}.
 * <p>
 * As {@code long} is a primitive type target's minimum value will
 * be the maximum of it's prior value and sources's value.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see Positive
 * @see NumberParameters
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public class PositiveConstraintExtractor
extends AbstractParametersSourceExtractor<NumberParameters, Positive> {

    /**
     * Creates a new instance.
     */
    public PositiveConstraintExtractor() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void extractParameters(
            final @NotNull Positive from,
            final @NotNull NumberParameters target) {
        target.setMin(NumberUtils.max(1, target.getMin().longValue()));
    }
}
