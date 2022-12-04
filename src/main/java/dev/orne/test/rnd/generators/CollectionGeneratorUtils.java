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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.GenerationException;
import dev.orne.test.rnd.GeneratorNotFoundException;
import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.params.GeneratorNotParameterizableException;
import dev.orne.test.rnd.params.TypeDeclaration;

/**
 * Utility class for generating components for generic types.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public final class CollectionGeneratorUtils {

    /** The error message for unsupported component types. */
    private static final String TYPE_NOT_SUPPORTED_ERR =
            "Cannot generate components of type %s. Only classes and parameterized types are supported";

    /**
     * Private constructor.
     */
    private CollectionGeneratorUtils() {
        // Utility class
    }

    /**
     * Returns a random value of the specified type.
     * 
     * @param type The requested value type.
     * @return A random value for the specified type.
     * @throws GeneratorNotFoundException If no generator supports the
     * requested value type.
     * @throws GeneratorNotParameterizableException If the component type is a
     * parameterized type and the generator registered for the raw class is not
     * parameterizable.
     */
    public static @NotNull Object randomComponent(
            final @NotNull Type type) {
        Validate.notNull(type);
        final Object result;
        if (type instanceof Class<?>) {
            result = Generators.randomValue((Class<?>) type);
        } else if (type instanceof ParameterizedType) {
            result = Generators.randomValue(
                    (Class<?>) ((ParameterizedType) type).getRawType(),
                    new TypeDeclaration(type));
        } else {
            throw new GenerationException(String.format(
                    TYPE_NOT_SUPPORTED_ERR,
                    type));
        }
        return result;
    }

    /**
     * Returns a random value of the specified type allowing {@code null}
     * values.
     * <p>
     * The returned value has a probability of be {@code null} except for
     * native types. If not {@code null} behaves as {@code randomValue()}.
     * 
     * @param type The requested value type.
     * @return A random nullable value for the specified type.
     * @throws GeneratorNotFoundException If no generator supports the
     * requested value type.
     * @throws GeneratorNotParameterizableException If the component type is a
     * parameterized type and the generator registered for the raw class is not
     * parameterizable.
     */
    public static @NotNull Object nullableRandomComponent(
            final @NotNull Type type) {
        Validate.notNull(type);
        final Object result;
        if (type instanceof Class<?>) {
            result = Generators.nullableRandomValue((Class<?>) type);
        } else if (type instanceof ParameterizedType) {
            result = Generators.nullableRandomValue(
                    (Class<?>) ((ParameterizedType) type).getRawType(),
                    new TypeDeclaration(type));
        } else {
            throw new GenerationException(String.format(
                    TYPE_NOT_SUPPORTED_ERR,
                    type));
        }
        return result;
    }
}
