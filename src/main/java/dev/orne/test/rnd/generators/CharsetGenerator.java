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

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.RandomUtils;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.AbstractTypedGenerator;
import dev.orne.test.rnd.Priority;

/**
 * Generator of {@code Charset} values.
 * By default generates only values that can encode.
 * Use {@link #randomDecodeOnlyValue()} for values without encoding support.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.1, 2023-10
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
@Priority(Priority.NATIVE_GENERATORS)
public class CharsetGenerator
extends AbstractTypedGenerator<Charset> {

    /** The supported charsets with encoding capability. */
    private static final List<Charset> CHARSETS;
    /** The supported charsets without encoding capability. */
    private static final List<Charset> DECODE_ONLY_CHARSETS;
    static {
        final Collection<Charset> availables =
                Charset.availableCharsets().values();
        CHARSETS = new ArrayList<>(availables.size());
        DECODE_ONLY_CHARSETS = new ArrayList<>();
        for (final Charset available : availables) {
            if (available.canEncode()) {
                CHARSETS.add(available);
            } else {
                DECODE_ONLY_CHARSETS.add(available);
            }
        }
    }

    /**
     * Creates a new instance.
     */
    public CharsetGenerator() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Charset defaultValue() {
        return Charset.defaultCharset();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Charset randomValue() {
        final int index = RandomUtils.nextInt(0, CHARSETS.size());
        return CHARSETS.get(index);
    }

    /**
     * Generate a random {@code Charset} that does not support encoding.
     * 
     * @return A random {@code Charset} that does not support encoding.
     */
    public @NotNull Charset randomDecodeOnlyValue() {
        final int index = RandomUtils.nextInt(0, DECODE_ONLY_CHARSETS.size());
        return DECODE_ONLY_CHARSETS.get(index);
    }
}
