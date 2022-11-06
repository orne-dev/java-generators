package dev.orne.test.rnd.generators;

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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.AbstractTypedGenerator;
import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;

/**
 * Generator of {@code LocalDateTime} values.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
@Priority(Priority.NATIVE_GENERATORS)
public class LocalDateTimeGenerator
extends AbstractTypedGenerator<LocalDateTime> {

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull LocalDateTime defaultValue() {
        return LocalDateTime.of(
                Generators.defaultValue(LocalDate.class),
                Generators.defaultValue(LocalTime.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull LocalDateTime randomValue() {
        return LocalDateTime.of(
                Generators.randomValue(LocalDate.class),
                Generators.randomValue(LocalTime.class));
    }
}
