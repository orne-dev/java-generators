package dev.orne.test.rnd;

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

/**
 * Implementation of {@code Generator} for tests.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 */
@Priority(TestGenerator.PRIORITY)
public class TestGenerator
extends AbstractGenerator {

    /** The generator priority. */
    public static final int PRIORITY = 100;

    /**
     * Creates a new instance.
     */
    public TestGenerator() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(
            final @NotNull Class<?> type) {
        return Type.class.equals(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> @NotNull T defaultValue(
            final @NotNull Class<T> type) {
        return type.cast(new Type());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> @NotNull T randomValue(
            final @NotNull Class<T> type) {
        return type.cast(new Type());
    }

    /**
     * Test empty type.
     */
    public static class Type {

        /**
         * Creates a new instance.
         */
        public Type() {
            super();
        }
    }
}
