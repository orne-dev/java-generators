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

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import javax.validation.constraints.NotNull;

import dev.orne.test.rnd.params.ParameterizableGenerator;
import dev.orne.test.rnd.params.TypedParameterizableGenerator;

/**
 * Utilities for generators test.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 */
public final class GeneratorsTestUtils {

    /**
     * Private constructor.
     */
    private GeneratorsTestUtils() {
        // Utility class
    }

    /**
     * Assert that calling {@code Generators.randomValue(Class<?>)}
     * for the specified type generates non null random values.
     * 
     * @param <T> The type of values to generate.
     * @param type The type of values to generate.
     * @param minValues The minimum different values to generate.
     * @param timeout The test timeout, in seconds.
     * @return The generated values.
     */
    public static <T> @NotNull Set<@NotNull T> assertRandomGeneration(
            final @NotNull Class<T> type,
            final int minValues,
            final int timeout) {
        return assertRandomGeneration(
                () -> Generators.randomValue(type),
                minValues,
                timeout);
    }

    /**
     * Assert that calling {@code Generator.randomValue(Class<?>)}
     * for the specified type generates non null random values.
     * 
     * @param <T> The type of values to generate.
     * @param generator The generator to use.
     * @param type The type of values to generate.
     * @param minValues The minimum different values to generate.
     * @param timeout The test timeout, in seconds.
     * @return The generated values.
     */
    public static <T> @NotNull Set<@NotNull T> assertRandomGeneration(
            final @NotNull Generator generator,
            final @NotNull Class<T> type,
            final int minValues,
            final int timeout) {
        return assertRandomGeneration(
                () -> generator.randomValue(type),
                minValues,
                timeout);
    }

    /**
     * Assert that calling {@code TypedGenerator.randomValue()}
     * generates non null random values.
     * 
     * @param <T> The type of values to generate
     * @param generator The generator to use.
     * @param minValues The minimum different values to generate.
     * @param timeout The test timeout, in seconds.
     * @return The generated values.
     */
    public static <T> @NotNull Set<@NotNull T> assertRandomGeneration(
            final @NotNull TypedGenerator<T> generator,
            final int minValues,
            final int timeout) {
        return assertRandomGeneration(
                generator::randomValue,
                minValues,
                timeout);
    }

    /**
     * Assert that calling {@code Generators.randomValue(Class<?>, Object...)}
     * for the specified type generates non null random values.
     * 
     * @param <T> The type of values to generate.
     * @param type The type of values to generate.
     * @param minValues The minimum different values to generate.
     * @param timeout The test timeout, in seconds.
     * @param params The generation parameters.
     * @return The generated values.
     */
    public static <T> @NotNull Set<@NotNull T> assertRandomGeneration(
            final @NotNull Class<T> type,
            final int minValues,
            final int timeout,
            final Object... params) {
        return assertRandomGeneration(
                () -> Generators.randomValue(type, params),
                minValues,
                timeout);
    }

    /**
     * Assert that calling
     * {@code ParameterizableGenerator.randomValue(Class<?>, Object...)}
     * for the specified type generates non null random values.
     * 
     * @param <T> The type of values to generate.
     * @param generator The generator to use.
     * @param type The type of values to generate.
     * @param minValues The minimum different values to generate.
     * @param timeout The test timeout, in seconds.
     * @param params The generation parameters.
     * @return The generated values.
     */
    public static <T> @NotNull Set<@NotNull T> assertRandomGeneration(
            final @NotNull ParameterizableGenerator generator,
            final @NotNull Class<T> type,
            final int minValues,
            final int timeout,
            final Object... params) {
        return assertRandomGeneration(
                () -> generator.randomValue(type, params),
                minValues,
                timeout);
    }

    /**
     * Assert that calling
     * {@code TypedParameterizableGenerator.randomValue(Object...)}
     * generates non null random values.
     * 
     * @param <T> The type of values to generate
     * @param generator The generator to use.
     * @param minValues The minimum different values to generate.
     * @param timeout The test timeout, in seconds.
     * @param params The generation parameters.
     * @return The generated values.
     */
    public static <T> @NotNull Set<@NotNull T> assertRandomGeneration(
            final @NotNull TypedParameterizableGenerator<T> generator,
            final int minValues,
            final int timeout,
            final Object... params) {
        return assertRandomGeneration(
                () -> generator.randomValue(params),
                minValues,
                timeout);
    }

    /**
     * Assert that calling the specified supplier generates non null random
     * values.
     * 
     * @param <T> The type of values to generate.
     * @param supplier The value ramdom supplier.
     * @param minValues The minimum different values to generate.
     * @param timeout The test timeout, in seconds.
     * @return The generated values.
     */
    public static <T> @NotNull Set<@NotNull T> assertRandomGeneration(
            final @NotNull Supplier<T> supplier,
            final int minValues,
            final int timeout) {
        final HashSet<T> results = new HashSet<>();
        assertTimeoutPreemptively(Duration.ofSeconds(timeout), () -> {
            while (results.size() < minValues) {
                final T result = supplier.get();
                assertNotNull(result);
                results.add(result);
            }
        });
        return results;
    }

    /**
     * Assert that calling {@code Generators.nullableRandomValue(Class<?>)}
     * for the specified type generates null and random values.
     * 
     * @param <T> The type of values to generate.
     * @param type The type of values to generate.
     * @param minValues The minimum different values to generate.
     * @param timeout The test timeout, in seconds.
     * @return The generated values.
     */
    public static <T> @NotNull Set<@NotNull T> assertNullableRandomGeneration(
            final @NotNull Class<T> type,
            final int minValues,
            final int timeout) {
        return assertNullableRandomGeneration(
                () -> Generators.nullableRandomValue(type),
                minValues,
                timeout);
    }

    /**
     * Assert that calling {@code Generator.nullableRandomValue(Class<?>)}
     * for the specified type generates null and random values.
     * 
     * @param <T> The type of values to generate.
     * @param generator The generator to use.
     * @param type The type of values to generate.
     * @param minValues The minimum different values to generate.
     * @param timeout The test timeout, in seconds.
     * @return The generated values.
     */
    public static <T> @NotNull Set<T> assertNullableRandomGeneration(
            final @NotNull Generator generator,
            final @NotNull Class<T> type,
            final int minValues,
            final int timeout) {
        return assertNullableRandomGeneration(
                () -> generator.nullableRandomValue(type),
                minValues,
                timeout);
    }

    /**
     * Assert that calling {@code TypedGenerator.nullableRandomValue()}
     * generates null and random values.
     * 
     * @param <T> The type of values to generate.
     * @param generator The generator to use.
     * @param minValues The minimum different values to generate.
     * @param timeout The test timeout, in seconds.
     * @return The generated values.
     */
    public static <T> @NotNull Set<@NotNull T> assertNullableRandomGeneration(
            final @NotNull TypedGenerator<T> generator,
            final int minValues,
            final int timeout) {
        return assertNullableRandomGeneration(
                generator::nullableRandomValue,
                minValues,
                timeout);
    }

    /**
     * Assert that calling
     * {@code Generators.nullableRandomValue(Class<?>, Object...)}
     * for the specified type generates null and random values.
     * 
     * @param <T> The type of values to generate.
     * @param type The type of values to generate.
     * @param minValues The minimum different values to generate.
     * @param timeout The test timeout, in seconds.
     * @param params The generation parameters.
     * @return The generated values.
     */
    public static <T> @NotNull Set<T> assertNullableRandomGeneration(
            final @NotNull Class<T> type,
            final int minValues,
            final int timeout,
            final Object... params) {
        return assertNullableRandomGeneration(
                () -> Generators.nullableRandomValue(type, params),
                minValues,
                timeout);
    }

    /**
     * Assert that calling
     * {@code Generator.nullableRandomValue(Class<?>, Object...)}
     * for the specified type generates null and random values.
     * 
     * @param <T> The type of values to generate.
     * @param generator The generator to use.
     * @param type The type of values to generate.
     * @param minValues The minimum different values to generate.
     * @param timeout The test timeout, in seconds.
     * @param params The generation parameters.
     * @return The generated values.
     */
    public static <T> @NotNull Set<T> assertNullableRandomGeneration(
            final @NotNull ParameterizableGenerator generator,
            final @NotNull Class<T> type,
            final int minValues,
            final int timeout,
            final Object... params) {
        return assertNullableRandomGeneration(
                () -> generator.nullableRandomValue(type, params),
                minValues,
                timeout);
    }

    /**
     * Assert that calling
     * {@code TypedGenerator.nullableRandomValue(Object...)}
     * generates null and random values.
     * 
     * @param <T> The type of values to generate.
     * @param generator The generator to use.
     * @param minValues The minimum different values to generate.
     * @param timeout The test timeout, in seconds.
     * @param params The generation parameters.
     * @return The generated values.
     */
    public static <T> @NotNull Set<@NotNull T> assertNullableRandomGeneration(
            final @NotNull TypedParameterizableGenerator<T> generator,
            final int minValues,
            final int timeout,
            final Object... params) {
        return assertNullableRandomGeneration(
                () -> generator.nullableRandomValue(params),
                minValues,
                timeout);
    }

    /**
     * Assert that calling the specified supplier generates null and random
     * values.
     * 
     * @param <T> The type of values to generate.
     * @param supplier The value ramdom supplier.
     * @param minValues The minimum different values to generate.
     * @param timeout The test timeout, in seconds.
     * @return The generated values.
     */
    public static <T> @NotNull Set<@NotNull T> assertNullableRandomGeneration(
            final @NotNull Supplier<T> supplier,
            final int minValues,
            final int timeout) {
        final HashSet<T> results = new HashSet<>();
        assertTimeoutPreemptively(Duration.ofSeconds(timeout), () -> {
            boolean nullValues = false;
            while (results.size() < minValues && !nullValues) {
                final T result = supplier.get();
                if (result == null) {
                    nullValues = true;
                } else {
                    results.add(result);
                }
            }
        });
        return results;
    }
}
