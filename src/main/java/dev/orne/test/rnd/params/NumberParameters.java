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
 * Interface for number generation parameters.
 * <p>
 * Depending of the target number type to be generated the minimum
 * of the parameters minimum value and the number type's minimum value
 * will be used.
 * Same way the maximum of the parameters maximum value and the number type's
 * maximum value will be used.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public interface NumberParameters {

    /**
     * Returns the minimum (inclusive) value.
     * 
     * @return The minimum value.
     */
    @NotNull Number getMin();

    /**
     * Sets the minimum (inclusive) value.
     * 
     * @param value The minimum value.
     */
    void setMin(@NotNull Number value);

    /**
     * Returns the maximum (inclusive) value.
     * 
     * @return The maximum value.
     */
    @NotNull Number getMax();

    /**
     * Sets the maximum (inclusive) value.
     * 
     * @param value The maximum value.
     */
    void setMax(@NotNull Number value);
}
