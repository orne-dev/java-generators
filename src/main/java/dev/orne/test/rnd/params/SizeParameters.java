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
 * Interface for generation parameters that support sizing
 * parameters.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public interface SizeParameters
extends GenerationParameters {

    /** The default minimum size. */
    public static final int DEFAULT_MIN_SIZE = 0;
    /** The default maximum size. */
    public static final int DEFAULT_MAX_SIZE = Integer.MAX_VALUE;

    /**
     * Returns the minimum size.
     * 
     * @return The minimum size.
     */
    int getMinSize();

    /**
     * Sets the minimum size.
     * 
     * @param value The minimum size.
     */
    void setMinSize(int value);

    /**
     * Returns the maximum size.
     * 
     * @return The maximum size.
     */
    int getMaxSize();

    /**
     * Sets the maximum size.
     * 
     * @param value The maximum size.
     */
    void setMaxSize(int value);

    /**
     * Interface for {@code NumberParameters} builders.
     * 
     * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
     * @version 1.0, 2022-12
     * @since SizeParameters 1.0
     */
    interface Builder
    extends SizeParameters {

        /**
         * Sets the minimum size.
         * 
         * @param value The minimum size.
         * @return This instance, for method chaining.
         */
        @NotNull Builder withMinSize(int value);

        /**
         * Sets the maximum size.
         * 
         * @param value The maximum size.
         * @return This instance, for method chaining.
         */
        @NotNull Builder withMaxSize(int value);
    }
}
