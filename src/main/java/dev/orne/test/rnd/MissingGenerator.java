package dev.orne.test.rnd;

/*-
 * #%L
 * Orne Test Generators
 * %%
 * Copyright (C) 2021 - 2025 Orne Developments
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

/**
 * Cache value for missing generators for a value type.
 * 
 * @author <a href="https://github.com/ihernaez">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @version 2.0, 2025-11
 * @since 0.1
 */
@API(status=API.Status.INTERNAL, since="0.1")
final class MissingGenerator
implements Generator {

    /** The error message. */
    static final String ERR_MSG = "No suitable generator found";

    /**
     * Private constructor.
     */
    MissingGenerator() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(
            final @NotNull Class<?> type) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> @NotNull T defaultValue(
            final @NotNull Class<T> type) {
        throw new GeneratorNotFoundException(ERR_MSG);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T nullableDefaultValue(
            final @NotNull Class<T> type) {
        throw new GeneratorNotFoundException(ERR_MSG);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> @NotNull T randomValue(
            final @NotNull Class<T> type) {
        throw new GeneratorNotFoundException(ERR_MSG);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T nullableRandomValue(
            final @NotNull Class<T> type) {
        throw new GeneratorNotFoundException(ERR_MSG);
    }
}
