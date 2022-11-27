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

/**
 * Mock implementation of {@code ParametersSourceExtractor}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 */
public class MockSourceExtractor
extends AbstractParametersSourceExtractor<
        MockSourceExtractor.Params,
        MockSourceExtractor.Source> {

    /** Missing mock error message. */
    private static final String NO_MOCK_ERR = "Mocking of calls expected";

    /**
     * {@inheritDoc}
     */
    @Override
    public void extractParameters(
            final @NotNull Source from,
            final @NotNull Params target) {
        throw new AssertionError(NO_MOCK_ERR);
    }

    public static interface Params{}
    public static interface Source {}
}
