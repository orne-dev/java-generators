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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;

/**
 * Registry of generation parameters source extractors.
 * <p>
 * Registers extractors declared in
 * {@code /META-INF/services/dev.orne.test.rnd.ParametersSourceExtractor}
 * SPI files in the class path.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 */
public final class ParametersExtractors {

    /**
     * The parameter source extractors comparator by priority.
     */
    @SuppressWarnings("rawtypes")
    public static final Comparator<ParametersSourceExtractor> COMPARATOR =
            Collections.reverseOrder(Comparator.comparingInt(ParametersSourceExtractor::getPriority));
    /**
     * The default parameters source extractors filter.
     * <p>
     * Default filter returns all the registered generation parameters source
     * extractors that accept parameters of the specified type
     */
    public static final SourceExtractorFilter DEFAULT_FILTER =
            ParametersExtractors::filterSourceExtractors;
    /**
     * The default parameters extractor builder.
     * <p>
     * Default builder creates a instance of {@code DefaultParametersExtractor}.
     * 
     * @see DefaultParametersExtractor
     */
    public static final ExtractorBuilder DEFAULT_BUILDER =
            DefaultParametersExtractor::new;

    /** The parameter extractors cache by parameters type. */
    private static final Map<Class<?>, ParametersExtractor<?>> CACHE =
            new HashMap<>();
    /** The registered generation parameters source extractors. */
    private static List<ParametersSourceExtractor<?, ?>> registeredSourceExtractors;
    /** The generation parameters source extractors filter. */
    private static SourceExtractorFilter filter = DEFAULT_FILTER;
    /** The parameters extractor builder. */
    private static ExtractorBuilder builder = DEFAULT_BUILDER;

    /**
     * Private constructor.
     */
    private ParametersExtractors() {
        // Utility class
    }

    /**
     * Returns an unmodifiable list with the registered parameter source
     * extractors.
     * 
     * @return The registered parameter source extractors
     */
    @SuppressWarnings("java:S1452")
    public static @NotNull List<ParametersSourceExtractor<?, ?>> getRegisteredSourceExtractors() {
        return Collections.unmodifiableList(getSourceExtractorsInt());
    }

    /**
     * Adds the specified parameter source extractors to the registered
     * extractors.
     * 
     * @param extractors The parameter source extractors to register
     */
    public static void register(
            final @NotNull ParametersSourceExtractor<?, ?>... extractors) {
        Validate.notNull(extractors);
        register(Arrays.asList(extractors));
    }

    /**
     * Adds the specified parameter source extractors to the registered
     * extractors.
     * 
     * @param extractors The parameter source extractors to register
     */
    public static void register(
            final @NotNull Collection<ParametersSourceExtractor<?, ?>> extractors) {
        Validate.notNull(extractors);
        Validate.noNullElements(extractors);
        synchronized (Generators.class) {
            final List<ParametersSourceExtractor<?, ?>> intList = getSourceExtractorsInt();
            intList.addAll(extractors);
            Collections.sort(intList, COMPARATOR);
            CACHE.clear();
        }
    }

    /**
     * Removes the specified parameter source extractors from the registered
     * extractors.
     * 
     * @param extractors The parameter source extractors to remove
     */
    public static void remove(
            final @NotNull ParametersSourceExtractor<?, ?>... extractors) {
        Validate.notNull(extractors);
        remove(Arrays.asList(extractors));
    }

    /**
     * Removes the specified parameter source extractors from the registered
     * extractors.
     * 
     * @param extractors The parameter source extractors to remove
     */
    public static void remove(
            final @NotNull Collection<ParametersSourceExtractor<?, ?>> extractors) {
        Validate.notNull(extractors);
        Validate.noNullElements(extractors);
        synchronized (Generators.class) {
            final List<ParametersSourceExtractor<?, ?>> intList = getSourceExtractorsInt();
            intList.removeAll(extractors);
            Collections.sort(intList, COMPARATOR);
            CACHE.clear();
        }
    }

    /**
     * Resets the loaded and cached extractors. Next call will regenerate the
     * parameter source extractors list (including SPI extractor) and restart
     * parameter extractors caching.
     */
    public static void reset() {
        synchronized (Generators.class) {
            registeredSourceExtractors = null;
            CACHE.clear();
        }
    }

    /**
     * Returns a generation parameters extractor for the specified parameters
     * type.
     * <p>
     * The extractor will contain all parameter source extractors suitable for
     * the given parameters type.
     * 
     * @param <P> The target parameters type
     * @param parametersType The target parameters type
     * @return The parameters extractor for the target parameters type
     */
    @SuppressWarnings("unchecked")
    public static <P> @NotNull ParametersExtractor<P> getExtractor(
            final @NotNull Class<P> parametersType) {
        return (ParametersExtractor<P>) CACHE.computeIfAbsent(
                parametersType,
                ParametersExtractors::createExtractor);
    }

    /**
     * Returns the generation parameters source extractors filter.
     * 
     * @return The generation parameters source extractors filter
     */
    public static @NotNull SourceExtractorFilter getFilter() {
        return ParametersExtractors.filter;
    }

    /**
     * Sets the generation parameters source extractors filter.
     * 
     * @param builder The generation parameters source extractors filter
     */
    public static void setFilter(
            final @NotNull SourceExtractorFilter filter) {
        ParametersExtractors.filter = Validate.notNull(filter);
        reset();
    }

    /**
     * Returns the parameters extractor builder.
     * 
     * @return The parameters extractor builder
     */
    public static @NotNull ExtractorBuilder getBuilder() {
        return ParametersExtractors.builder;
    }

    /**
     * Sets the parameters extractor builder.
     * 
     * @param builder The parameters extractor builder
     */
    public static void setBuilder(
            final @NotNull ExtractorBuilder builder) {
        ParametersExtractors.builder = Validate.notNull(builder);
        reset();
    }

    /**
     * Creates a new parameter extractors with all the parameter source
     * extractors suitable for the given parameters type.
     * 
     * @param <P> The target parameters type
     * @param parametersType The target parameters type
     * @return The parameters extractor for the target parameters type
     */
    static <P> @NotNull ParametersExtractor<P> createExtractor(
            final @NotNull Class<P> parametersType) {
        final List<ParametersSourceExtractor<? super P, ?>> extractors =
                filter.findSuitable(
                        getRegisteredSourceExtractors(),
                        parametersType);
        return builder.create(extractors);
    }

    /**
     * Returns all the registered generation parameters source extractors that
     * accept parameters of the specified type.
     * 
     * @param <P> The target parameter type
     * @param extractors The registered source extractors, in priority order
     * @param parametersType The target parameter type
     * @return The source extractors that accept the specified parameters typet,
     * in priority order
     */
    static <P> @NotNull List<ParametersSourceExtractor<? super P, ?>> filterSourceExtractors(
            final @NotNull List<ParametersSourceExtractor<?, ?>> extractors,
            final @NotNull Class<P> parametersType) {
        final List<ParametersSourceExtractor<? super P, ?>> result =
                new ArrayList<>();
        for (final ParametersSourceExtractor<?, ?> sourceExtractor : extractors) {
            if (sourceExtractor.getParametersType().isAssignableFrom(parametersType)) {
                @SuppressWarnings("unchecked")
                final ParametersSourceExtractor<? super P, ?> tExtractor =
                        (ParametersSourceExtractor<? super P, ?>) sourceExtractor;
                result.add(tExtractor);
            }
        }
        return result;
    }

    /**
     * Returns a modifiable list with the registered parameter source
     * extractors.
     * If extractors has not been loaded loads the default extractors,
     * including extractors registered through SPI.
     * 
     * @return The registered parameter source extractors
     */
    @SuppressWarnings("java:S1452")
    static @NotNull List<ParametersSourceExtractor<?, ?>> getSourceExtractorsInt() {
        synchronized (ParametersExtractors.class) {
            if (registeredSourceExtractors == null) {
                registeredSourceExtractors = new ArrayList<>();
                registeredSourceExtractors.addAll(loadSpiExtractors());
                Collections.sort(registeredSourceExtractors, COMPARATOR);
            }
            return registeredSourceExtractors;
        }
    }

    /**
     * Returns the internal by type parameters extractor cache.
     * 
     * @return The internal by type parameters extractor cache
     */
    @SuppressWarnings("java:S1452")
    static @NotNull Map<Class<?>, ParametersExtractor<?>> getCacheInt() {
        return CACHE;
    }

    /**
     * Loads the parameter source extractors declared through SPI for interface
     * {@code dev.orne.test.rnd.ParametersSourceExtractor}
     * 
     * @return The SPI declared parameter source extractors
     * @see ServiceLoader
     */
    @SuppressWarnings({ "rawtypes", "java:S1452" })
    static @NotNull List<ParametersSourceExtractor<?, ?>> loadSpiExtractors() {
        final List<ParametersSourceExtractor<?, ?>> result = new ArrayList<>();
        final ServiceLoader<ParametersSourceExtractor> loader =
                ServiceLoader.load(ParametersSourceExtractor.class);
        final Iterator<ParametersSourceExtractor> it = loader.iterator();
        while (it.hasNext()) {
            final ParametersSourceExtractor<?, ?> generator = it.next();
            result.add(generator);
        }
        return result;
    }

    /**
     * Functional interface for generation parameters source extractors filter.
     * 
     * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
     * @version 1.0, 2022-11
     * @since ParametersExtractors 1.0
     */
    @FunctionalInterface
    public interface SourceExtractorFilter {

        /**
         * Returns the generation parameters source extractors to be used when
         * extracting parameters of the specified type.
         * 
         * @param <P> The target parameter type
         * @param extractors The registered source extractors, in priority order
         * @param parametersType The target parameter type
         * @return The source extractors to use, in priority order
         */
        <P> @NotNull List<ParametersSourceExtractor<? super P, ?>> findSuitable(
                @NotNull List<ParametersSourceExtractor<?, ?>> extractors,
                @NotNull Class<P> parametersType);
    }

    /**
     * Functional interface for parameters extractor builder.
     * 
     * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
     * @version 1.0, 2022-11
     * @since ParametersExtractors 1.0
     */
    @FunctionalInterface
    public interface ExtractorBuilder {

        /**
         * Creates a new parameters extractor from the specified suitable
         * parameter source extractors.
         * 
         * @param <P> The parameters type
         * @param sourceExtractors The suitable parameter source extractors,
         * in priority order
         * @return The created parameters extractor
         */
        <P> @NotNull ParametersExtractor<P> create(
                @NotNull Collection<ParametersSourceExtractor<? super P, ?>> sourceExtractors);
    }
}
