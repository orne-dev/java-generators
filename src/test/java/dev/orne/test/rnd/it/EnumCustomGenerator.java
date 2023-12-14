package dev.orne.test.rnd.it;

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

import org.apache.commons.lang3.RandomUtils;

import dev.orne.test.rnd.AbstractTypedGenerator;
import dev.orne.test.rnd.Priority;
import dev.orne.test.rnd.generators.EnumGenerator;

/**
 * Custom generator for an enumeration type that prevents generation of an
 * undesired value.
 * <p>
 * Registered through {@code META-INF/services/dev.orne.test.rnd.Generator}
 * SPI.
 * <p>
 * Generator has no {@code Priority} annotation, so gets the default priority
 * ({@value dev.orne.test.rnd.Priority#DEFAULT}), which is greater than built in
 * enumeration values generator
 * ({@value dev.orne.test.rnd.Priority#GENERIC_GENERATORS}) and therefore
 * takes precedence for generation of the supported types.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 * @see Priority
 * @see EnumGenerator
 */
public class EnumCustomGenerator
extends AbstractTypedGenerator<EnumCustomGenerator.Values> {

    /** The generated enumeration values. */
    public static final Values[] GENERATED_VALUES =
            new Values[] {
                Values.DEFAULT,
                Values.NON_DEFAULT_A,
                Values.NON_DEFAULT_B,
                Values.NON_DEFAULT_C,
            };

    /**
     * Creates a new instance.
     */
    public EnumCustomGenerator() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Values defaultValue() {
       return Values.DEFAULT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Values randomValue() {
        final int index = RandomUtils.nextInt(0, GENERATED_VALUES.length);
        return GENERATED_VALUES[index];
    }

    /**
     * Test enumeration.
     */
    public static enum Values {
        /** The default enumeration constant. */
        DEFAULT,
        /** The non generated enumeration constant. */
        UNDESIRED,
        /** The non default enumeration constant. */
        NON_DEFAULT_A,
        /** The non default enumeration constant. */
        NON_DEFAULT_B,
        /** The non default enumeration constant. */
        NON_DEFAULT_C,
    }
}
