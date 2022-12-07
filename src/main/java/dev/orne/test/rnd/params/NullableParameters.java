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
 * Interface for generation parameters for value types that can be {@code null}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public interface NullableParameters
extends GenerationParameters {

    /** If a {@code null} value is accepted by default. */
    public static final boolean DEFAULT_NULLABLE = true;

    /**
     * Returns {@code true} if a {@code null} value is accepted.
     * 
     * @return If a {@code null} value is accepted.
     */
    boolean isNullable();

    /**
     * Sets if a {@code null} value is accepted.
     * 
     * @param nullable If a {@code null} value is accepted.
     */
    void setNullable(
            boolean nullable);

    /**
     * Interface for {@code NullableParameters} builders.
     * 
     * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
     * @version 1.0, 2022-12
     * @since NullableParameters 1.0
     */
    interface Builder
    extends NullableParameters {

        /**
         * Sets if a {@code null} value is accepted.
         * 
         * @param nullable If a {@code null} value is accepted.
         * @return This instance, for method chaining.
         */
        @NotNull Builder withNullable(
                boolean nullable);
    }
}
