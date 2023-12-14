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

import java.lang.reflect.ParameterizedType;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Generation parameters extractor that detects {@code TypeDeclaration}
 * parameter sources and sets {@code KeyValueGenericParameters.keysType}
 * and {@code KeyValueGenericParameters.valuesType}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 * @see TypeDeclaration
 * @see SimpleGenericParameters
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public class KeyValueGenericParametersTypeExtractor
extends AbstractParametersSourceExtractor<KeyValueGenericParameters, TypeDeclaration> {

    /**
     * Creates a new instance.
     */
    public KeyValueGenericParametersTypeExtractor() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void extractParameters(
            final @NotNull TypeDeclaration from,
            final @NotNull KeyValueGenericParameters target) {
        Validate.notNull(from);
        Validate.notNull(target);
        Validate.isInstanceOf(ParameterizedType.class, from.getType());
        final ParameterizedType type = (ParameterizedType) from.getType();
        Validate.isTrue(type.getActualTypeArguments().length == 2);
        target.setKeysType(type.getActualTypeArguments()[0]);
        target.setValuesType(type.getActualTypeArguments()[1]);
    }
}
