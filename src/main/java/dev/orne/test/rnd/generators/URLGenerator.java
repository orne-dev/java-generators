package dev.orne.test.rnd.generators;

import java.io.IOException;

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

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.NotImplementedException;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.AbstractTypedGenerator;
import dev.orne.test.rnd.GenerationException;
import dev.orne.test.rnd.Priority;

/**
 * Generator of {@code URL} values.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
@Priority(Priority.NATIVE_GENERATORS)
public class URLGenerator
extends AbstractTypedGenerator<URL> {

    /** The default protocol. */
    public static final String DEFAULT_PROTOCOL = "http";
    /** The default host. */
    public static final String DEFAULT_HOST = "localhost";
    /** The default path. */
    public static final String DEFAULT_PATH = "/";

    /** The NOP {@code URLStreamHandler} to prevent opening generated URLs. */
    private static final NopStreamHandler HANDLER = new NopStreamHandler();

    /**
     * Creates a new instance.
     */
    public URLGenerator() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull URL defaultValue() {
        try {
            return new URL(DEFAULT_PROTOCOL, DEFAULT_HOST, -1, DEFAULT_PATH, HANDLER);
        } catch (final MalformedURLException e) {
            throw new GenerationException("Error generating default URL", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull URL randomValue() {
        return randomURL();
    }

    /**
     * Returns a random URL.
     * 
     * @return The URL
     */
    public static @NotNull URL randomURL() {
        try {
            return new URL(
                    null,
                    URIGenerator.randomAbsoluteURI().toString(),
                    HANDLER);
        } catch (final MalformedURLException e) {
            throw new GenerationException("Error generating random URL", e);
        }
    }

    /**
     * Implementation of {@code URLStreamHandler} that prevents opening
     * generated random URLs.
     * 
     * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
     * @version 1.0, 2022-11
     * @since URLGenerator 1.0
     */
    protected static class NopStreamHandler
    extends URLStreamHandler {

        /**
         * Creates a new instance.
         */
        protected NopStreamHandler() {
            super();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected URLConnection openConnection(URL u) throws IOException {
            throw new NotImplementedException("Open generated URLs is not allowed");
        }
    }
}
