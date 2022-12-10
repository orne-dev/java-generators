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

import org.apache.commons.lang3.math.NumberUtils;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Generation parameters extractor that copies parameters
 * between {@code SizeParameters} instances.
 * <p>
 * As {@code int} is a primitive type target's {@code minSize} value will
 * be the maximum of it's prior value and sources's value.
 * Same way target's {@code maxSize} value will
 * be the minimum of it's prior value and sources's value.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see SizeParameters
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public class SizeParametersExtractor
extends AbstractParametersSourceExtractor<SizeParameters, SizeParameters> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void extractParameters(
            final @NotNull SizeParameters from,
            final @NotNull SizeParameters target) {
        target.setMinSize(NumberUtils.max(from.getMinSize(), target.getMinSize()));
        target.setMaxSize(NumberUtils.min(from.getMaxSize(), target.getMaxSize()));
    }
}
