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

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.RandomUtils;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.AbstractTypedGenerator;
import dev.orne.test.rnd.Priority;

/**
 * Generator of {@code Currency} values.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 2.0, 2022-10
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
@Priority(Priority.NATIVE_GENERATORS)
public class CurrencyGenerator
extends AbstractTypedGenerator<Currency> {

    /** The available currencies. */
    private static final List<Currency> CURRENCIES = new ArrayList<>(
            Currency.getAvailableCurrencies());
    /** The default currency. */
    public static final Currency DEFAULT;

    static {
        Currency defaultValue;
        try {
            defaultValue = Currency.getInstance(Locale.getDefault());
        } catch (IllegalArgumentException e) {
            defaultValue = Currency.getInstance("EUR");
        }
        DEFAULT = defaultValue;
    }

    /**
     * Creates a new instance.
     */
    public CurrencyGenerator() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Currency defaultValue() {
        return DEFAULT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Currency randomValue() {
        final int index = RandomUtils.nextInt(0, CURRENCIES.size());
        return CURRENCIES.get(index);
    }
}
