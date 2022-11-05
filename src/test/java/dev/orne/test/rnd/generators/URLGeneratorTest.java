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

import static org.junit.jupiter.api.Assertions.*;

import java.net.URL;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code URLGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see URLGenerator
 */
@Tag("ut")
class URLGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(URL.class));
        assertEquals(URLGenerator.class, Generators.getGenerator(URL.class).getClass());
    }

    /**
     * Unit test for {@link URLGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new URLGenerator().getPriority());
    }

    /**
     * Unit test for {@link URLGenerator#supports()}
     */
    @Test
    void testSupports() {
        final URLGenerator generator = new URLGenerator();
        assertTrue(generator.supports(URL.class));
        assertFalse(generator.supports(URL[].class));
    }

    /**
     * Unit test for {@link URLGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final URLGenerator generator = new URLGenerator();
        final URL result = generator.defaultValue();
        assertEquals(URLGenerator.DEFAULT_PROTOCOL, result.getProtocol());
        assertNull(result.getUserInfo());
        assertEquals(URLGenerator.DEFAULT_HOST, result.getHost());
        assertEquals(-1, result.getPort());
        assertEquals(URLGenerator.DEFAULT_PATH, result.getPath());
        assertNull(result.getQuery());
        assertNull(result.getRef());
        assertThrows(NotImplementedException.class, () -> {
            result.openConnection();
        });
    }

    /**
     * Unit test for {@link URLGenerator#randomURL()}
     */
    @Test
    void testRandomURL() {
        verifyURLGeneration(URLGenerator::randomURL);
    }

    private void  verifyURLGeneration(
            final Supplier<URL> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean nullUserInfo = false;
            boolean withUserInfo = false;
            boolean defaultPort = false;
            boolean customPort = false;
            boolean nullQuery = false;
            boolean withQuery = false;
            boolean nullFragment = false;
            boolean withFragment = false;
            final Set<URL> values = new HashSet<>();
            while (values.size() < 20
                    && !nullUserInfo && !withUserInfo
                    && !defaultPort && !customPort
                    && !nullQuery && !withQuery
                    && !nullFragment && !withFragment) {
                final URL result = supplier.get();
                verifyURL(result);
                values.add(result);
                if (result.getUserInfo() == null) {
                    nullUserInfo = true;
                } else {
                    withUserInfo = true;
                }
                if (result.getPort() == -1) {
                    defaultPort = true;
                } else {
                    customPort = true;
                }
                if (result.getQuery() == null) {
                    nullQuery = true;
                } else {
                    withQuery = true;
                }
                if (result.getRef() == null) {
                    nullFragment = true;
                } else {
                    withFragment = true;
                }
            }
        });
    }

    private void  verifyURL(
            final URL url) {
        assertNotNull(url.getProtocol());
        assertNotNull(url.getHost());
        assertNotNull(url.getPath());
        assertThrows(NotImplementedException.class, () -> {
            url.openConnection();
        });
    }

    /**
     * Unit test for {@link URLGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final URLGenerator generator = new URLGenerator();
        verifyURLGeneration(generator::randomValue);
    }
}
