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
 * Base class for value generation parameters.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public interface GenerationParameters {

    /**
     * Creates a new builder of {@code NullableParameters}.
     * 
     * @return The {@code NullableParameters} builder.
     */
    public static @NotNull NullableParameters.Builder forNullables() {
        return new NullableParametersImpl();
    }

    /**
     * Creates a new builder of {@code NumberParameters}.
     * 
     * @return The instance of {@code NumberParameters} builder.
     */
    public static @NotNull NumberParameters.Builder forNumbers() {
        return new NumberParametersImpl();
    }

    /**
     * Creates a new builder of {@code SizeParameters}.
     * 
     * @return The instance of {@code SizeParameters} builder.
     */
    public static @NotNull SizeParameters.Builder forSizes() {
        return new SizeParametersImpl();
    }

    /**
     * Creates a new builder of {@code SimpleGenericParameters}.
     * 
     * @return The instance of {@code SimpleGenericParameters} builder.
     */
    public static @NotNull SimpleGenericParameters.Builder forSimpleGenerics() {
        return new SimpleGenericParametersImpl();
    }

    /**
     * Creates a new builder of {@code KeyValueGenericParameters}.
     * 
     * @return The instance of {@code KeyValueGenericParameters} builder.
     */
    public static @NotNull KeyValueGenericParameters.KeysTypeBuilder forKeyValueGenerics() {
        return new KeyValueGenericParametersImpl();
    }
}
