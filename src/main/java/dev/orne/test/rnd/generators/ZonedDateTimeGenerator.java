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

import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.validation.constraints.NotNull;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.AbstractTypedGenerator;
import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;

/**
 * Generator of {@code ZonedDateTime} values.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
@Priority(Priority.NATIVE_GENERATORS)
public class ZonedDateTimeGenerator
extends AbstractTypedGenerator<ZonedDateTime> {

    /**
     * Creates a new instance.
     */
    public ZonedDateTimeGenerator() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ZonedDateTime defaultValue() {
        return ZonedDateTime.of(
                Generators.defaultValue(LocalDateTime.class),
                Generators.defaultValue(ZoneId.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ZonedDateTime randomValue() {
        return ZonedDateTime.of(
                Generators.randomValue(LocalDateTime.class),
                Generators.randomValue(ZoneId.class));
    }
}
