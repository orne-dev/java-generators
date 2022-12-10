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

/**
 * Generation parameters extractor that copies parameters
 * between {@code NumberParameters} instances.
 * <p>
 * Target's minimum value will be the maximum of it's prior
 * value and sources's value.
 * Same way target's maximum value will be the minimum of it's
 * prior value and sources's value.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see SizeParameters
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public class NumberParametersExtractor
extends AbstractParametersSourceExtractor<NumberParameters, NumberParameters> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void extractParameters(
            final @NotNull NumberParameters from,
            final @NotNull NumberParameters target) {
        target.setMin(max(from.getMin(), target.getMin()));
        target.setMax(min(from.getMax(), target.getMax()));
    }

    /**
     * Returns the minimum value. If both {@code newValue}'s long value and
     * double {@code doubleValue} are greater than {@code oldValue}'s values
     * then {@code newValue} is returned. Otherwise {@code oldValue} is returned.
     * 
     * @param oldValue The previous value.
     * @param newValue The new value.
     * @return The minimum value.
     */
    private static @NotNull Number max(
            final @NotNull Number oldValue,
            final @NotNull Number newValue) {
        if (newValue.longValue() > oldValue.longValue() &&
                newValue.doubleValue() > oldValue.doubleValue()) {
            return newValue;
        } else {
            return oldValue;
        }
    }

    /**
     * Returns the minimum value. If both {@code newValue}'s long value and
     * double {@code doubleValue} are lower than {@code oldValue}'s values
     * then {@code newValue} is returned. Otherwise {@code oldValue} is returned.
     * 
     * @param oldValue The previous value.
     * @param newValue The new value.
     * @return The minimum value.
     */
    private static @NotNull Number min(
            final @NotNull Number oldValue,
            final @NotNull Number newValue) {
        if (newValue.longValue() < oldValue.longValue() &&
                newValue.doubleValue() < oldValue.doubleValue()) {
            return newValue;
        } else {
            return oldValue;
        }
    }
}
