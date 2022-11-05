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

import java.net.URI;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code URIGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see URIGenerator
 */
@Tag("ut")
class URIGeneratorTest {

    private static final String SCHEME_REGEXP = "[a-zA-Z0-9\\+\\-\\.]+";
    private static final String TOP_LABEL_REGEXP = "[a-zA-Z]([a-zA-Z0-9\\-]*[a-zA-Z0-9])?";
    private static final String DOMAIN_LABEL_REGEXP = "[a-zA-Z0-9]([a-zA-Z0-9\\-]*[a-zA-Z0-9])?";
    private static final String HOSTNAME_REGEXP = "(" + DOMAIN_LABEL_REGEXP + "\\.)*" + TOP_LABEL_REGEXP + "\\.?";
    private static final String IP4_REGEXP = "\\d+\\.\\d+\\.\\d+\\.\\d+";
    private static final String IP6_PIECE_REGEXP = "[0-9a-fA-F]{1,4}";
    private static final String IP6_FULL_REGEXP = IP6_PIECE_REGEXP + "(:" + IP6_PIECE_REGEXP + "){7}";
    private static final String IP6_ABRV_REGEXP = "(" + IP6_PIECE_REGEXP + "(:" + IP6_PIECE_REGEXP + ")*)?"
            + "::"
            + "(" + IP6_PIECE_REGEXP + "(:" + IP6_PIECE_REGEXP + ")*)?";
    private static final String IP6_REGEXP = "((" + IP6_FULL_REGEXP + ")|(" + IP6_ABRV_REGEXP + "))";
    private static final String IP6_HOST_REGEXP = "\\[" + IP6_REGEXP + "\\]";
    private static final String HOST_REGEXP = "((" + HOSTNAME_REGEXP + ")|(" + IP4_REGEXP + ")|(" + IP6_HOST_REGEXP + "))";
    private static final String SERVER_AUTHORITY_REGEXP = "([^@]*\\@)?" + HOST_REGEXP + "(:\\d+)?";

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(URI.class));
        assertEquals(URIGenerator.class, Generators.getGenerator(URI.class).getClass());
    }

    /**
     * Unit test for {@link URIGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new URIGenerator().getPriority());
    }

    /**
     * Unit test for {@link URIGenerator#supports()}
     */
    @Test
    void testSupports() {
        final URIGenerator generator = new URIGenerator();
        assertTrue(generator.supports(URI.class));
        assertFalse(generator.supports(URI[].class));
    }

    /**
     * Unit test for {@link URIGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final URIGenerator generator = new URIGenerator();
        assertEquals(URIGenerator.DEFAULT_VALUE, generator.defaultValue());
    }

    /**
     * Unit test for {@link URIGenerator#randomScheme()}
     */
    @Test
    void testRandomScheme() {
        verifySchemeGeneration(URIGenerator::randomScheme);
    }

    private void  verifySchemeGeneration(
            final Supplier<String> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final Set<Integer> lengths = new HashSet<>();
            while (lengths.size() < 5) {
                final String result = supplier.get();
                verifyScheme(result);
                lengths.add(result.length());
            }
        });
    }

    private void  verifyScheme(
            final String scheme) {
        assertMatches(scheme, SCHEME_REGEXP);
        final URI uri = assertDoesNotThrow(() -> {
            return new URI(scheme, "test/", null);
        });
        assertEquals(scheme, uri.getScheme());
    }

    /**
     * Unit test for {@link URIGenerator#randomUserInfo()}
     */
    @Test
    void testRandomUserInfo() {
        verifyUserInfoGeneration(URIGenerator::randomUserInfo);
    }

    /**
     * Unit test for {@link URIGenerator#randomUserInfo()}
     */
    @Test
    void testRandomUserInfoSimple() {
        verifyUserInfo(URIGenerator.randomUserInfo());
    }

    private void  verifyUserInfoGeneration(
            final Supplier<String> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final Set<Integer> lengths = new HashSet<>();
            while (lengths.size() < 5) {
                final String result = supplier.get();
                verifyUserInfo(result);
                lengths.add(result.length());
            }
        });
    }

    /**
     * Unit test for {@link URIGenerator#randomOptionalUserInfo()}
     */
    @Test
    void testRandomOptionalUserInfo() {
        verifyOptionalUserInfoGeneration(URIGenerator::randomOptionalUserInfo);
    }

    private void  verifyOptionalUserInfoGeneration(
            final Supplier<String> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean nullValues = false;
            final Set<Integer> lengths = new HashSet<>();
            while (lengths.size() < 5 && !nullValues) {
                final String result = supplier.get();
                if (result == null) {
                    nullValues = true;
                } else {
                    verifyUserInfo(result);
                    lengths.add(result.length());
                }
            }
        });
    }

    private void  verifyUserInfo(
            final String userInfo) {
        final URI uri = assertDoesNotThrow(() -> {
            return new URI("http", userInfo, "test", -1, "/", null, null);
        });
        assertEquals(userInfo, uri.getUserInfo());
    }

    /**
     * Unit test for {@link URIGenerator#randomDomainLabel()}
     */
    @Test
    void testRandomDomainLabel() {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final Set<Integer> lengths = new HashSet<>();
            while (lengths.size() < 10) {
                final String result = URIGenerator.randomDomainLabel();
                assertMatches(result, DOMAIN_LABEL_REGEXP);
                lengths.add(result.length());
            }
        });
    }

    /**
     * Unit test for {@link URIGenerator#randomTopLabel()}
     */
    @Test
    void testRandomTopLabel() {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final Set<Integer> lengths = new HashSet<>();
            while (lengths.size() < 5) {
                final String result = URIGenerator.randomTopLabel();
                assertMatches(result, TOP_LABEL_REGEXP);
                lengths.add(result.length());
            }
        });
    }

    /**
     * Unit test for {@link URIGenerator#randomHostName()}
     */
    @Test
    void testRandomHostName() {
        verifyHostNameGeneration(URIGenerator::randomHostName);
    }

    private void verifyHostNameGeneration(
            final Supplier<String> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final Set<Integer> lengths = new HashSet<>();
            while (lengths.size() < 5) {
                final String result = supplier.get();
                verifyHostName(result);
                lengths.add(result.split("\\.").length);
            }
        });
    }

    private void verifyHostName(
            final String hostname) {
        assertMatches(hostname, HOSTNAME_REGEXP);
        final URI uri = assertDoesNotThrow(() -> {
            return new URI("http", hostname, "/", null);
        });
        assertEquals(hostname, uri.getHost());
    }

    /**
     * Unit test for {@link URIGenerator#randomIp4Address()}
     */
    @Test
    void testRandomIp4Address() {
        final String result = URIGenerator.randomIp4Address();
        assertMatches(result, IP4_REGEXP);
        final String[] parts = result.split("\\.");
        assertEquals(4, parts.length);
        for (int i = 0; i < parts.length; i++) {
            int part = Integer.parseInt(parts[i]);
            assertTrue(0 <= part);
            assertTrue(255 >= part);
        }
    }

    /**
     * Unit test for {@link URIGenerator#randomIp6Piece(StringBuilder)}
     */
    @Test
    void testRandomIp6Piece() {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final Set<Integer> lengths = new HashSet<>();
            while (lengths.size() < 4) {
                final String result = URIGenerator.randomIp6Piece();
                assertMatches(result, IP6_PIECE_REGEXP);
                lengths.add(result.length());
            }
        });
    }

    /**
     * Unit test for {@link URIGenerator#randomFullIp6Address()}
     */
    @Test
    void testRandomFullIp6Address() {
        verifyFullIp6AddressGeneration(URIGenerator::randomFullIp6Address);
    }

    private void verifyFullIp6AddressGeneration(
            final Supplier<String> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final Set<String> results = new HashSet<>();
            while (results.size() < 20) {
                final String result = supplier.get();
                verifyFullIp6Address(result);
                results.add(result);
            }
        });
    }

    private void verifyFullIp6Address(
            final String address) {
        assertMatches(address, IP6_FULL_REGEXP);
        final String[] parts = address.split("\\:", -1);
        assertEquals(8, parts.length);
        for (int i = 0; i < parts.length; i++) {
            assertMatches(parts[i], IP6_PIECE_REGEXP);
        }
    }

    /**
     * Unit test for {@link URIGenerator#randomAbbreviatedIp6Address()}
     */
    @Test
    void testRandomAbbreviatedIp6Address() {
        verifyAbbreviatedIp6AddressGeneration(URIGenerator::randomAbbreviatedIp6Address);
    }

    private void verifyAbbreviatedIp6AddressGeneration(
            final Supplier<String> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final Set<Integer> lengths = new HashSet<>();
            boolean start = false;
            boolean middle = false;
            boolean end = false;
            boolean empty = false;
            while (lengths.size() < 4 && !start && !middle && !end && !empty) {
                final String result = supplier.get();
                verifyAbbreviatedIp6Address(result);
                final String[] parts = result.split("\\:", -1);
                if (parts.length == 3) {
                    assertTrue(parts[1].isEmpty());
                    if (parts[0].isEmpty() && parts[2].isEmpty()) {
                        empty = true;
                    }
                } else if (parts[0].isEmpty()) {
                    end = true;
                } else if (parts[parts.length - 1].isEmpty()) {
                    start = true;
                } else {
                    middle = true;
                }
            }
        });
    }

    private void verifyAbbreviatedIp6Address(
            final String address) {
        assertMatches(address, IP6_ABRV_REGEXP);
        final String[] parts = address.split("\\:", -1);
        if (parts.length > 9) {
            throw new AssertionError(String.format(
                    "Too many IP6 parts: %s -> %d %s",
                    address,
                    parts.length,
                    Arrays.toString(parts)));
        }
        int emptyParts = 0;
        for (int i = 0; i < parts.length; i++) {
            final String part = parts[i];
            if (part.isEmpty()) {
                emptyParts++;
            } else {
                assertMatches(parts[i], IP6_PIECE_REGEXP);
            }
        }
        if (emptyParts == parts.length) {
            assertEquals(3, parts.length);
        } else if (parts[0].isEmpty()) {
            assertEquals(2, emptyParts);
        } else if (parts[parts.length - 1].isEmpty()) {
            assertEquals(2, emptyParts);
        } else {
            assertEquals(1, emptyParts);
        }
    }

    /**
     * Unit test for {@link URIGenerator#randomIp6Address()}
     */
    @Test
    void testRandomIp6Address() {
        verifyIp6AddressGeneration(URIGenerator::randomIp6Address);
    }

    private void verifyIp6AddressGeneration(
            final Supplier<String> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean full = false;
            boolean abbreviated = false;
            final Set<String> results = new HashSet<>();
            while (results.size() < 4 && !full && !abbreviated) {
                final String result = supplier.get();
                verifyIp6Address(result);
                results.add(result);
                if (result.matches(IP6_FULL_REGEXP)) {
                    full = true;
                } else {
                    abbreviated = true;
                }
            }
        });
    }

    private void verifyIp6Address(
            final String address) {
        assertMatches(address, IP6_REGEXP);
        if (address.matches(IP6_FULL_REGEXP)) {
            verifyFullIp6Address(address);
        } else {
            verifyAbbreviatedIp6Address(address);
        }
    }

    /**
     * Unit test for {@link URIGenerator#randomHost()}
     */
    @Test
    void testRandomHost() {
        verifyHostGeneration(URIGenerator::randomHost);
    }

    private void verifyHostGeneration(
            final Supplier<String> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean ip4 = false;
            boolean ip6 = false;
            boolean hostname = false;
            while (!ip4 || !ip6 || !hostname) {
                final String result = supplier.get();
                verifyHost(result);
                if (result.matches(IP4_REGEXP)) {
                    ip4 = true;
                } else if (result.matches(IP6_HOST_REGEXP)) {
                    ip6 = true;
                } else {
                    hostname = true;
                }
            }
        });
    }

    private void verifyHost(
            final String host) {
        assertTrue(host.matches(IP4_REGEXP) || host.matches(IP6_HOST_REGEXP) || host.matches(HOSTNAME_REGEXP),
                "Unexpected host: " + host);
        final URI uri = assertDoesNotThrow(() -> {
            return new URI("http", host, "/", null);
        });
        assertEquals(host, uri.getHost());
    }

    /**
     * Unit test for {@link URIGenerator#randomPort()}
     */
    @Test
    void testRandomPort() {
        verifyPortGeneration(URIGenerator::randomPort);
    }

    private void  verifyPortGeneration(
            final Supplier<Integer> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final Set<Integer> values = new HashSet<>();
            while (values.size() < 100) {
                final Integer result = supplier.get();
                verifyPort(result);
                values.add(result);
            }
        });
    }

    /**
     * Unit test for {@link URIGenerator#randomOptionalPort()}
     */
    @Test
    void testRandomOptionalPort() {
        verifyOptionalPortGeneration(URIGenerator::randomOptionalPort);
    }

    private void  verifyOptionalPortGeneration(
            final Supplier<Integer> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean defaultValues = false;
            final Set<Integer> values = new HashSet<>();
            while (values.size() < 100 && !defaultValues) {
                final Integer result = supplier.get();
                if (result == -1) {
                    defaultValues = true;
                } else {
                    verifyPort(result);
                }
                values.add(result);
            }
        });
    }

    private void verifyPort(
            final int port) {
        assertTrue(1 <= port);
        assertTrue(999999 >= port);
    }

    /**
     * Unit test for {@link URIGenerator#randomServerAuthority()}
     */
    @Test
    void testRandomServerAuthority() {
        verifyServerAuthorityGeneration(URIGenerator::randomServerAuthority);
    }

    private void  verifyServerAuthorityGeneration(
            final Supplier<String> supplier) {
        final AtomicBoolean ip4NoPort = new AtomicBoolean();
        final AtomicBoolean ip4WithPort = new AtomicBoolean();
        final AtomicBoolean ip6NoPort = new AtomicBoolean();
        final AtomicBoolean ip6WithPort = new AtomicBoolean();
        final AtomicBoolean hostnameNoPort = new AtomicBoolean();
        final AtomicBoolean hostnameWithPort = new AtomicBoolean();
        final AtomicBoolean noUserInfo = new AtomicBoolean();
        final AtomicBoolean withUserInfo = new AtomicBoolean();
        try {
            assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
                while (!ip4NoPort.get() || !ip4WithPort.get()
                        || !ip6NoPort.get() || !ip6WithPort.get()
                        || !hostnameNoPort.get() || !hostnameWithPort.get()
                        || !noUserInfo.get() || !withUserInfo.get()) {
                    final String result = supplier.get();
                    verifyServerAuthority(result);
                    String host = result;
                    String userInfo = null;
                    int userInfoEndIndx = host.indexOf('@');
                    if (userInfoEndIndx != -1) {
                        userInfo = result.substring(0, userInfoEndIndx);
                        host = host.substring(userInfoEndIndx + 1);
                    }
                    int portIndx = host.lastIndexOf(':');
                    int ip6Indx = host.lastIndexOf(']');
                    int port = -1;
                    if (portIndx != -1 && ip6Indx < portIndx) {
                        port = Integer.parseInt(host.substring(portIndx + 1));
                        host = host.substring(0, portIndx);
                    }
                    if (host.matches(IP4_REGEXP)) {
                        if (port == -1) {
                            ip4NoPort.set(true);
                        } else {
                            ip4WithPort.set(true);
                        }
                    } else if (host.matches(IP6_HOST_REGEXP)) {
                        if (port == -1) {
                            ip6NoPort.set(true);
                        } else {
                            ip6WithPort.set(true);
                        }
                    } else {
                        if (port == -1) {
                            hostnameNoPort.set(true);
                        } else {
                            hostnameWithPort.set(true);
                        }
                    }
                    if (userInfo == null) {
                        noUserInfo.set(true);
                    } else {
                        withUserInfo.set(true);
                    }
                }
            });
        } catch (AssertionFailedError e) {
            final AssertionError err = new AssertionError(String.format(
                    "Faile in state: [%s, %s, %s, %s, %s, %s, %s, %s]",
                    ip4NoPort.get(),
                    ip4WithPort.get(),
                    ip6NoPort.get(),
                    ip6WithPort.get(),
                    hostnameNoPort.get(),
                    hostnameWithPort.get(),
                    noUserInfo.get(),
                    withUserInfo.get()));
            err.initCause(e);
            throw err;
        }
    }

    private void verifyServerAuthority(
            final String authority) {
        assertMatches(authority, SERVER_AUTHORITY_REGEXP);
        String host = authority;
        String userInfo = null;
        int userInfoEndIndx = host.indexOf('@');
        if (userInfoEndIndx != -1) {
            userInfo = authority.substring(0, userInfoEndIndx);
            host = host.substring(userInfoEndIndx + 1);
        }
        int portIndx = host.lastIndexOf(':');
        int ip6Indx = host.lastIndexOf(']');
        int port = -1;
        if (portIndx != -1 && ip6Indx < portIndx) {
            port = Integer.parseInt(host.substring(portIndx + 1));
            host = host.substring(0, portIndx);
        }
        final URI uri = assertDoesNotThrow(() -> {
            return new URI("http", authority, "/", null, null);
        });
        assertEquals(authority, uri.getAuthority());
        assertEquals(userInfo, uri.getUserInfo());
        assertEquals(host, uri.getHost());
        assertEquals(port, uri.getPort());
    }

    /**
     * Unit test for {@link URIGenerator#randomRegistryBasedNamedAuthority()}
     */
    @Test
    void testRandomRegistryBasedNamedAuthority() {
        verifyRegistryBasedNamedAuthority(URIGenerator::randomRegistryBasedNamedAuthority);
    }

    private void  verifyRegistryBasedNamedAuthority(
            final Supplier<String> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final Set<Integer> lengths = new HashSet<>();
            while (lengths.size() < 20) {
                final String result = supplier.get();
                verifyRegistryBasedNamedAuthority(result);
                lengths.add(result.length());
            }
        });
    }

    private void verifyRegistryBasedNamedAuthority(
            final String authority) {
        final URI uri = assertDoesNotThrow(() -> {
            return new URI("http", authority, "/", null, null);
        });
        assertEquals(authority, uri.getAuthority());
    }

    /**
     * Unit test for {@link URIGenerator#randomAuthority()}
     */
    @Test
    void testRandomAuthority() {
        verifyAuthorityGeneration(URIGenerator::randomAuthority);
    }

    private void verifyAuthorityGeneration(
            final Supplier<String> supplier) {
        final AtomicBoolean regName = new AtomicBoolean();
        final AtomicBoolean ip4NoPort = new AtomicBoolean();
        final AtomicBoolean ip4WithPort = new AtomicBoolean();
        final AtomicBoolean ip6NoPort = new AtomicBoolean();
        final AtomicBoolean ip6WithPort = new AtomicBoolean();
        final AtomicBoolean hostnameNoPort = new AtomicBoolean();
        final AtomicBoolean hostnameWithPort = new AtomicBoolean();
        final AtomicBoolean noUserInfo = new AtomicBoolean();
        final AtomicBoolean withUserInfo = new AtomicBoolean();
        try {
            assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
                while (!regName.get()
                        || !ip4NoPort.get() || !ip4WithPort.get()
                        || !ip6NoPort.get() || !ip6WithPort.get()
                        || !hostnameNoPort.get() || !hostnameWithPort.get()
                        || !noUserInfo.get() || !withUserInfo.get()) {
                    final String result = supplier.get();
                    verifyAuthority(result);
                    final URI uri = new URI("http", result, "/", null, null);
                    assertEquals(result, uri.getAuthority());
                    if (uri.getHost() == null) {
                        regName.set(true);
                    } else {
                        if (uri.getHost().matches(IP4_REGEXP)) {
                            if (uri.getPort() == -1) {
                                ip4NoPort.set(true);
                            } else {
                                ip4WithPort.set(true);
                            }
                        } else if (uri.getHost().matches(IP6_HOST_REGEXP)) {
                            if (uri.getPort() == -1) {
                                ip6NoPort.set(true);
                            } else {
                                ip6WithPort.set(true);
                            }
                        } else {
                            if (uri.getPort() == -1) {
                                hostnameNoPort.set(true);
                            } else {
                                hostnameWithPort.set(true);
                            }
                        }
                        if (uri.getRawUserInfo() == null) {
                            noUserInfo.set(true);
                        } else {
                            withUserInfo.set(true);
                        }
                    }
                }
            });
        } catch (AssertionFailedError e) {
            final AssertionError err = new AssertionError(String.format(
                    "Faile in state: [%s, %s, %s, %s, %s, %s, %s, %s, %s]",
                    regName.get(),
                    ip4NoPort.get(),
                    ip4WithPort.get(),
                    ip6NoPort.get(),
                    ip6WithPort.get(),
                    hostnameNoPort.get(),
                    hostnameWithPort.get(),
                    noUserInfo.get(),
                    withUserInfo.get()));
            err.initCause(e);
            throw err;
        }
    }

    private void verifyAuthority(
            final String authority) {
        final URI uri = assertDoesNotThrow(() -> {
            return new URI("http", authority, "/", null, null);
        });
        assertEquals(authority, uri.getAuthority());
        if (authority.matches(SERVER_AUTHORITY_REGEXP)) {
            verifyServerAuthority(authority);
        } else {
            verifyRegistryBasedNamedAuthority(authority);
        }
    }

    /**
     * Unit test for {@link URIGenerator#randomPathSegment()}
     */
    @Test
    void testRandomPathSegment() {
        verifyPathSegmentGeneration(URIGenerator::randomPathSegment);
    }

    private void verifyPathSegmentGeneration(
            final Supplier<String> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean withoutParam = false;
            boolean withParam = false;
            final Set<Integer> lengths = new HashSet<>();
            while (lengths.size() < 5 && !withParam && !withoutParam) {
                final String result = supplier.get();
                if (result.contains(";")) {
                    withParam = true;
                    lengths.add(result.split(";")[0].length());
                } else {
                    withoutParam = true;
                    lengths.add(result.length());
                }
            }
        });
    }

    /**
     * Unit test for {@link URIGenerator#randomAbsolutePath()}
     */
    @Test
    void testRandomAbsolutePath() {
        verifyAbsolutePathGeneration(URIGenerator::randomAbsolutePath);
    }

    private void  verifyAbsolutePathGeneration(
            final Supplier<String> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean withoutParam = false;
            boolean withParam = false;
            final Set<Integer> lengths = new HashSet<>();
            while (lengths.size() < 5 && !withParam && !withoutParam) {
                final String result = supplier.get();
                verifyAbsolutePath(result);
                final String[] segments = result.split("/");
                lengths.add(segments.length);
                for (int i = 0; i < segments.length; i++) {
                    if (segments[i].contains(";")) {
                        withParam = true;
                    } else {
                        withoutParam = true;
                    }
                }
            }
        });
    }

    private void  verifyAbsolutePath(
            final String path) {
        assertTrue(path.startsWith("/"));
        final URI uri = assertDoesNotThrow(() -> {
            return new URI("http", "test", path, null, null);
        });
        assertEquals(path, uri.getPath());
        assertTrue(uri.isAbsolute());
    }

    /**
     * Unit test for {@link URIGenerator#randomRelativePath()}
     */
    @Test
    void testRandomRelativePath() {
        verifyRelativePathGeneration(URIGenerator::randomRelativePath);
    }

    private void verifyRelativePathGeneration(
            final Supplier<String> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean withoutParam = false;
            boolean withParam = false;
            final Set<Integer> lengths = new HashSet<>();
            while (lengths.size() < 5 && !withParam && !withoutParam) {
                final String result = supplier.get();
                verifyRelativePath(result);
                final String[] segments = result.split("/", -1);
                lengths.add(segments.length);
                for (int i = 0; i < segments.length; i++) {
                    if (segments[i].contains(";")) {
                        withParam = true;
                    } else {
                        withoutParam = true;
                    }
                }
            }
        });
    }

    private void verifyRelativePath(
            final String path) {
        final URI uri = assertDoesNotThrow(() -> {
            return new URI(null, null, path, null, null);
        });
        assertEquals(path, uri.getPath());
        assertFalse(uri.isAbsolute());
    }

    /**
     * Unit test for {@link URIGenerator#randomPath()}
     */
    @Test
    void testRandomPath() {
        verifyPathGeneration(URIGenerator::randomPath);
    }

    private void verifyPathGeneration(
            final Supplier<String> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean absolute = false;
            boolean relative = false;
            boolean withoutParam = false;
            boolean withParam = false;
            final Set<Integer> lengths = new HashSet<>();
            while (lengths.size() < 5 && !withParam && !withoutParam
                    && !absolute && !relative) {
                final String result = supplier.get();
                verifyPath(result);
                if (result.startsWith("/")) {
                    absolute = true;
                } else {
                    relative = true;
                }
                final String[] segments = result.split("/", -1);
                lengths.add(segments.length);
                for (int i = 0; i < segments.length; i++) {
                    if (segments[i].contains(";")) {
                        withParam = true;
                    } else {
                        withoutParam = true;
                    }
                }
            }
        });
    }

    private void verifyPath(
            final String path) {
        if (path.startsWith("/")) {
            verifyAbsolutePath(path);
        } else {
            verifyRelativePath(path);
        }
    }

    /**
     * Unit test for {@link URIGenerator#randomQuery()}
     */
    @Test
    void testRandomQuery() {
        verifyQueryGeneration(URIGenerator::randomQuery);
    }

    private void verifyQueryGeneration(
            final Supplier<String> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final Set<Integer> lengths = new HashSet<>();
            while (lengths.size() < 20) {
                final String result = supplier.get();
                verifyQuery(result);
                lengths.add(result.length());
            }
        });
    }

    /**
     * Unit test for {@link URIGenerator#randomOptionalQuery()}
     */
    @Test
    void testRandomOptionalQuery() {
        verifyOptionalQueryGeneration(URIGenerator::randomOptionalQuery);
    }

    private void  verifyOptionalQueryGeneration(
            final Supplier<String> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean nullValues = false;
            final Set<Integer> lengths = new HashSet<>();
            while (lengths.size() < 5 && !nullValues) {
                final String result = supplier.get();
                if (result == null) {
                    nullValues = true;
                } else {
                    verifyQuery(result);
                    lengths.add(result.length());
                }
            }
        });
    }

    private void verifyQuery(
            final String query) {
        final URI uri = assertDoesNotThrow(() -> {
            return new URI(null, null, null, -1, "/", query, null);
        });
        assertEquals(query, uri.getQuery());
    }

    /**
     * Unit test for {@link URIGenerator#randomFragment()}
     */
    @Test
    void testRandomFragment() {
        verifyFragmentGeneration(URIGenerator::randomFragment);
    }

    private void verifyFragmentGeneration(
            final Supplier<String> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final Set<Integer> lengths = new HashSet<>();
            while (lengths.size() < 20) {
                final String result = supplier.get();
                verifyFragment(result);
                lengths.add(result.length());
            }
        });
    }

    /**
     * Unit test for {@link URIGenerator#randomOptionalFragment()}
     */
    @Test
    void testRandomOptionalFragment() {
        verifyOptionalFragmentGeneration(URIGenerator::randomOptionalFragment);
    }

    private void  verifyOptionalFragmentGeneration(
            final Supplier<String> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean nullValues = false;
            final Set<Integer> lengths = new HashSet<>();
            while (lengths.size() < 5 && !nullValues) {
                final String result = supplier.get();
                if (result == null) {
                    nullValues = true;
                } else {
                    verifyFragment(result);
                    lengths.add(result.length());
                }
            }
        });
    }

    private void verifyFragment(
            final String fragment) {
        final URI uri = assertDoesNotThrow(() -> {
            return new URI(null, null, null, -1, "/", null, fragment);
        });
        assertEquals(fragment, uri.getFragment());
    }

    /**
     * Unit test for {@link URIGenerator#randomAbsoluteURI()}
     */
    @Test
    void testRandomRelativeURI() {
        verifyRelativeURIGeneration(URIGenerator::randomRelativeURI);
    }

    private void  verifyRelativeURIGeneration(
            final Supplier<URI> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean absolutePath = false;
            boolean relativePath = false;
            boolean nullQuery = false;
            boolean withQuery = false;
            boolean nullFragment = false;
            boolean withFragment = false;
            final Set<URI> values = new HashSet<>();
            while (values.size() < 20
                    && !absolutePath && !relativePath
                    && !nullQuery && !withQuery
                    && !nullFragment && !withFragment) {
                final URI result = supplier.get();
                verifyRelativeURI(result);
                values.add(result);
                if (result.getPath().startsWith("/")) {
                    absolutePath = true;
                } else {
                    relativePath = true;
                }
                if (result.getQuery() == null) {
                    nullQuery = true;
                } else {
                    withQuery = true;
                }
                if (result.getFragment() == null) {
                    nullFragment = true;
                } else {
                    withFragment = true;
                }
            }
        });
    }

    private void  verifyRelativeURI(
            final URI uri) {
        assertNull(uri.getScheme());
        assertNull(uri.getAuthority());
        assertNull(uri.getUserInfo());
        assertNull(uri.getHost());
        assertEquals(-1, uri.getPort());
        assertNotNull(uri.getPath());
        assertFalse(uri.isAbsolute());
    }

    /**
     * Unit test for {@link URIGenerator#randomAbsoluteURI()}
     */
    @Test
    void testRandomAbsoluteURI() {
        verifyAbsoluteURIGeneration(URIGenerator::randomAbsoluteURI);
    }

    private void  verifyAbsoluteURIGeneration(
            final Supplier<URI> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean nullUserInfo = false;
            boolean withUserInfo = false;
            boolean defaultPort = false;
            boolean customPort = false;
            boolean nullQuery = false;
            boolean withQuery = false;
            boolean nullFragment = false;
            boolean withFragment = false;
            final Set<URI> values = new HashSet<>();
            while (values.size() < 20
                    && !nullUserInfo && !withUserInfo
                    && !defaultPort && !customPort
                    && !nullQuery && !withQuery
                    && !nullFragment && !withFragment) {
                final URI result = supplier.get();
                verifyAbsoluteURI(result);
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
                if (result.getFragment() == null) {
                    nullFragment = true;
                } else {
                    withFragment = true;
                }
            }
        });
    }

    private void  verifyAbsoluteURI(
            final URI uri) {
        assertNotNull(uri.getScheme());
        assertNotNull(uri.getHost());
        assertNotNull(uri.getPath());
        assertTrue(uri.getPath().startsWith("/"));
        assertTrue(uri.isAbsolute());
    }

    /**
     * Unit test for {@link URIGenerator#randomURI()}
     */
    @Test
    void testRandomURI() {
        verifyURIGeneration(URIGenerator::randomURI);
    }

    private void  verifyURIGeneration(
            final Supplier<URI> supplier) {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean absolute = false;
            boolean relative = false;
            boolean nullUserInfo = false;
            boolean withUserInfo = false;
            boolean defaultPort = false;
            boolean customPort = false;
            boolean nullQuery = false;
            boolean withQuery = false;
            boolean nullFragment = false;
            boolean withFragment = false;
            final Set<URI> values = new HashSet<>();
            while (values.size() < 20
                    && !absolute && !relative
                    && !nullUserInfo && !withUserInfo
                    && !defaultPort && !customPort
                    && !nullQuery && !withQuery
                    && !nullFragment && !withFragment) {
                final URI result = supplier.get();
                verifyURI(result);
                values.add(result);
                if (result.isAbsolute()) {
                    absolute = true;
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
                } else {
                    relative = true;
                    
                }
                if (result.getQuery() == null) {
                    nullQuery = true;
                } else {
                    withQuery = true;
                }
                if (result.getFragment() == null) {
                    nullFragment = true;
                } else {
                    withFragment = true;
                }
            }
        });
    }

    private void  verifyURI(
            final URI uri) {
        if (uri.isAbsolute()) {
            verifyAbsoluteURI(uri);
        } else {
            verifyRelativeURI(uri);
        }
    }

    /**
     * Unit test for {@link URIGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final URIGenerator generator = new URIGenerator();
        verifyURIGeneration(generator::randomValue);
    }

    private void assertMatches(
            final @NotNull String value,
            final @NotNull String regexp) {
        if (!value.matches(regexp)) {
            fail("Invalid result: " + value);
        }
    }
}
