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

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.AbstractTypedGenerator;
import dev.orne.test.rnd.GenerationException;
import dev.orne.test.rnd.Priority;

/**
 * Generator of {@code UUID} values.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
@Priority(Priority.NATIVE_GENERATORS)
public class URIGenerator
extends AbstractTypedGenerator<URI> {

    /** The default value. */
    public static final URI DEFAULT_VALUE = URI.create("/");
    
    private static final String LOWALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA = LOWALPHA + UPALPHA;
    private static final String DIGIT = "0123456789";
    private static final String ALPHANUM = ALPHA + DIGIT;
    private static final String RESERVED = ";/?:@&=+$,[]";
    private static final String MARK = "-_.!~*'()";
    private static final String UNRESERVED  = ALPHANUM + MARK;
    private static final String HEXDIG = DIGIT + "ABCDEF";
    private static final float ESCAPED_CHAR_P = 0.05f;
    private static final String UNESCAPED_URIC = RESERVED + UNRESERVED;
    private static final int SCHEME_MIN_LENGTH = 1;
    private static final int SCHEME_MAX_LENGTH = 10;
    private static final String SCHEME_REST_C = ALPHANUM + "+-.";
    private static final int USERINFO_MIN_LENGTH = 0;
    private static final int USERINFO_MAX_LENGTH = 20;
    private static final String UNESCAPED_USERINFO_C = UNRESERVED + ";:&=+$,";
    private static final int DOMAIN_LABEL_MIN_LENGTH = 1;
    private static final int DOMAIN_LABEL_MAX_LENGTH = 20;
    private static final String DOMAIN_LABEL_MIDDLE_C = ALPHANUM + "-";
    private static final int TOP_LABEL_MIN_LENGTH = 1;
    private static final int TOP_LABEL_MAX_LENGTH = 5;
    private static final String TOP_LABEL_MIDDLE_C = ALPHANUM + "-";
    private static final int HOSTNAME_MIN_DOMAIN_LEVELS = 1;
    private static final int HOSTNAME_MAX_DOMAIN_LEVELS = 5;
    private static final char DOMAIN_LABEL_SEPARATOR = '.';
    private static final float TOP_LABEL_SUFFIX_P = 0.01f;
    private static final char TOP_LABEL_SUFFIX = '.';
    private static final int IP4_ADDRESS_MIN_NUM = 0;
    private static final int IP4_ADDRESS_MAX_NUM = 255;
    private static final int IP6_PIECE_MIN_LENGTH = 1;
    private static final int IP6_PIECE_MAX_LENGTH = 4;
    private static final int IP6_ADDRESS_MIN_LENGTH = 0;
    private static final int IP6_ADDRESS_MAX_LENGTH = 8;
    private static final float IP6_ABRV_P = 0.3f;
    private static final char IP6_ADDRESS_SEPARATOR = ':';
    private static final char IP6_ADDRESS_PREFIX = '[';
    private static final char IP6_ADDRESS_SUFFIX = ']';
    private static final float HOSTNAME_P = 0.8f;
    private static final float IP4_P = 0.5f;
    private static final int PORT_MIN = 1;
    private static final int PORT_MAX = 999999;
    private static final float PORT_P = 0.2f;
    private static final char PORT_PREFIX = ':';
    private static final char USERINFO_SUFFIX = '@';
    private static final float USERINFO_P = 0.2f;
    private static final int REG_NAME_MIN_LENGTH = 1;
    private static final int REG_NAME_MAX_LENGTH = 30;
    private static final String UNESCAPED_REG_NAME_C = UNRESERVED + "$,;:@&=+";
    private static final float SERVER_P = 0.9f;
    private static final String UNESCAPED_PATH_C = UNRESERVED + ":@&=+$,";
    private static final int PARAM_MIN_LENGTH = 1;
    private static final int PARAM_MAX_LENGTH = 10;
    private static final int SEGMENT_PATH_MIN_LENGTH = 1;
    private static final int SEGMENT_PATH_MAX_LENGTH = 20;
    private static final float SEGMENT_PARAM_P = 0.1f;
    private static final char SEGMENT_PARAM_SEPARATOR = ';';
    private static final int PATH_SEGMENTS_MIN_SEGMENTS = 0;
    private static final int PATH_SEGMENTS_MAX_SEGMENTS = 10;
    private static final char PATH_SEGMENTS_SEPARATOR = '/';
    private static final char ABSOLUTE_PATH_PREFIX = '/';
    private static final int QUERY_MIN_LENGTH = 1;
    private static final int QUERY_MAX_LENGTH = 50;
    private static final float QUERY_P = 0.5f;
    private static final float PATH_ABSOLUTE_P = 0.8f;
    private static final int FRAGMENT_MIN_LENGTH = 1;
    private static final int FRAGMENT_MAX_LENGTH = 20;
    private static final String UNESCAPED_RELATIVE_SEGMENT_C = UNRESERVED + ";@&=+$,";
    private static final int RELATIVE_SEGMENT_MIN_LENGTH = 1;
    private static final int RELATIVE_SEGMENT_MAX_LENGTH = 20;
    private static final float RELATIVE_PATH_ABSOLUTE_PATH_P = 0.6f;
    private static final float FRAGMENT_P = 0.2f;

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull URI defaultValue() {
        return DEFAULT_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull URI randomValue() {
        return randomURI();
    }

    /**
     * Returns a random scheme.
     * 
     * @return The random scheme
     */
    public static @NotNull String randomScheme() {
        final int length = RandomUtils.nextInt(
                SCHEME_MIN_LENGTH,
                SCHEME_MAX_LENGTH + 1);
        final StringBuilder buffer = new StringBuilder();
        buffer.append(RandomStringUtils.randomAlphabetic(1));
        buffer.append(RandomStringUtils.random(length - 1, SCHEME_REST_C));
        return buffer.toString();
    }

    /**
     * Returns a random user info part of an URI.
     * 
     * @return The random user info
     */
    public static @NotNull String randomUserInfo() {
        final int length = RandomUtils.nextInt(
                USERINFO_MIN_LENGTH,
                USERINFO_MAX_LENGTH + 1);
        final StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (RandomUtils.nextFloat(0, 1) < ESCAPED_CHAR_P) {
                buffer.append((char) RandomUtils.nextInt('\u0080', '\uFFFF'));
            } else {
                buffer.append(RandomStringUtils.random(1, UNESCAPED_USERINFO_C));
            }
        }
        return buffer.toString();
    }

    /**
     * Returns a random optional user info part of an URI.
     * 
     * @return The random user info, or {@code null}
     */
    public static String randomOptionalUserInfo() {
        if (RandomUtils.nextFloat(0, 1) < USERINFO_P) {
            return randomUserInfo();
        } else {
            return null;
        }
    }

    /**
     * Returns a random non top domain label.
     * 
     * @return The non top domain label
     */
    protected static @NotNull String randomDomainLabel() {
        final StringBuilder buffer = new StringBuilder();
        final int length = RandomUtils.nextInt(
                DOMAIN_LABEL_MIN_LENGTH,
                DOMAIN_LABEL_MAX_LENGTH + 1);
        buffer.append(RandomStringUtils.randomAlphanumeric(1));
        if (length > 1) {
            if (length > 2) {
                buffer.append(RandomStringUtils.random(length - 2, DOMAIN_LABEL_MIDDLE_C));
            }
            buffer.append(RandomStringUtils.randomAlphanumeric(1));
        }
        return buffer.toString();
    }

    /**
     * Returns a random top domain label.
     * 
     * @returns The top domain label
     */
    protected static @NotNull String randomTopLabel() {
        final StringBuilder buffer = new StringBuilder();
        final int length = RandomUtils.nextInt(
                TOP_LABEL_MIN_LENGTH,
                TOP_LABEL_MAX_LENGTH + 1);
        buffer.append(RandomStringUtils.randomAlphabetic(1));
        if (length > 1) {
            if (length > 2) {
                buffer.append(RandomStringUtils.random(length - 2, TOP_LABEL_MIDDLE_C));
            }
            buffer.append(RandomStringUtils.randomAlphanumeric(1));
        }
        return buffer.toString();
    }

    /**
     * Returns a random host name.
     * 
     * @return The host name
     */
    public static @NotNull String randomHostName() {
        final StringBuilder buffer = new StringBuilder();
        final int domainLevels = RandomUtils.nextInt(
                HOSTNAME_MIN_DOMAIN_LEVELS,
                HOSTNAME_MAX_DOMAIN_LEVELS + 1);
        for (int i = 0; i < domainLevels; i++) {
            buffer.append(randomDomainLabel()).append(DOMAIN_LABEL_SEPARATOR);
        }
        buffer.append(randomTopLabel());
        if (RandomUtils.nextFloat(0, 1) < TOP_LABEL_SUFFIX_P) {
            buffer.append(TOP_LABEL_SUFFIX);
        }
        return buffer.toString();
    }

    /**
     * Returns a random IP4 address.
     * 
     * @return The IP4 address
     */
    public static @NotNull String randomIp4Address() {
        return String.format(
                "%d.%d.%d.%d",
                RandomUtils.nextInt(IP4_ADDRESS_MIN_NUM, IP4_ADDRESS_MAX_NUM + 1),
                RandomUtils.nextInt(IP4_ADDRESS_MIN_NUM, IP4_ADDRESS_MAX_NUM + 1),
                RandomUtils.nextInt(IP4_ADDRESS_MIN_NUM, IP4_ADDRESS_MAX_NUM + 1),
                RandomUtils.nextInt(IP4_ADDRESS_MIN_NUM, IP4_ADDRESS_MAX_NUM + 1));
    }

    /**
     * Returns a random IP6 address piece.
     * 
     * @return The random IP6 address piece
     */
    protected static @NotNull String randomIp6Piece() {
        final int length = RandomUtils.nextInt(
                IP6_PIECE_MIN_LENGTH,
                IP6_PIECE_MAX_LENGTH + 1);
        return RandomStringUtils.random(length, HEXDIG);
    }

    /**
     * Returns a random full IP6 address.
     * 
     * @return The full IP6 address
     */
    public static @NotNull String randomFullIp6Address() {
        return String.format(
                "%s:%s:%s:%s:%s:%s:%s:%s",
                randomIp6Piece(),
                randomIp6Piece(),
                randomIp6Piece(),
                randomIp6Piece(),
                randomIp6Piece(),
                randomIp6Piece(),
                randomIp6Piece(),
                randomIp6Piece());
    }

    /**
     * Returns a random abbreviated IP6 address.
     * 
     * @return The abbreviated IP6 address
     */
    public static @NotNull String randomAbbreviatedIp6Address() {
        final StringBuilder buffer = new StringBuilder();
        final int pieces = RandomUtils.nextInt(
                IP6_ADDRESS_MIN_LENGTH,
                IP6_ADDRESS_MAX_LENGTH);
        if (pieces == 0) {
            buffer.append(IP6_ADDRESS_SEPARATOR)
                    .append(IP6_ADDRESS_SEPARATOR);
        } else {
            final int prePieces = RandomUtils.nextInt(0, pieces + 1);
            if (prePieces > 0) {
                buffer.append(randomIp6Piece());
                for (int i = 1 ; i < prePieces; i++) {
                    buffer.append(IP6_ADDRESS_SEPARATOR).append(randomIp6Piece());
                }
            }
            buffer.append(IP6_ADDRESS_SEPARATOR);
            final int postPieces = pieces - prePieces;
            if (postPieces == 0) {
                buffer.append(IP6_ADDRESS_SEPARATOR);
            } else {
                for (int i = 0 ; i < postPieces; i++) {
                    buffer.append(IP6_ADDRESS_SEPARATOR).append(randomIp6Piece());
                }
            }
        }
        return buffer.toString();
    }

    /**
     * Returns a random IP6 address.
     * 
     * @return The IP6 address
     */
    public static @NotNull String randomIp6Address() {
        if (RandomUtils.nextFloat(0, 1) < IP6_ABRV_P) {
            return randomAbbreviatedIp6Address();
        } else {
            return randomFullIp6Address();
        }
    }

    /**
     * Returns a random host.
     * 
     * @return The host part
     */
    public static @NotNull String randomHost() {
        if (RandomUtils.nextFloat(0, 1) < HOSTNAME_P) {
            return randomHostName();
        } else if (RandomUtils.nextFloat(0, 1) < IP4_P) {
            return randomIp4Address();
        } else {
            return IP6_ADDRESS_PREFIX + randomIp6Address() + IP6_ADDRESS_SUFFIX;
        }
    }

    /**
     * Returns a random port.
     * 
     * @return The port
     */
    public static int randomPort() {
        return RandomUtils.nextInt(PORT_MIN, PORT_MAX + 1);
    }

    /**
     * Returns a random optional port.
     * 
     * @return The port, or {@code -1}
     */
    public static int randomOptionalPort() {
        if (RandomUtils.nextFloat(0, 1) < PORT_P) {
            return randomPort();
        } else {
            return -1;
        }
    }

    /**
     * Returns a random server authority part.
     * 
     * @return The server authority part
     */
    public static @NotNull String randomServerAuthority() {
        final StringBuilder buffer = new StringBuilder();
        final String userInfo = randomOptionalUserInfo();
        final String host = randomHost();
        final int port = randomOptionalPort();
        if (userInfo != null) {
            buffer.append(userInfo).append(USERINFO_SUFFIX);
        }
        buffer.append(host);
        if (port != -1) {
            buffer.append(PORT_PREFIX).append(port);
        }
        return buffer.toString();
    }

    /**
     * Returns a random registry based named authority part.
     * 
     * @return The registry based named authority part
     */
    public static @NotNull String randomRegistryBasedNamedAuthority() {
        final StringBuilder buffer = new StringBuilder();
        final int length = RandomUtils.nextInt(
                REG_NAME_MIN_LENGTH,
                REG_NAME_MAX_LENGTH + 1);
        for (int i = 0; i < length; i++) {
            if (RandomUtils.nextFloat(0, 1) < ESCAPED_CHAR_P) {
                buffer.append((char) RandomUtils.nextInt('\u0080', '\uFFFF'));
            } else {
                buffer.append(RandomStringUtils.random(1, UNESCAPED_REG_NAME_C));
            }
        }
        return buffer.toString();
    }

    /**
     * Returns a random authority part.
     * 
     * @return The authority part
     */
    public static @NotNull String randomAuthority() {
        if (RandomUtils.nextFloat(0, 1) < SERVER_P) {
            return randomServerAuthority();
        } else {
            return randomRegistryBasedNamedAuthority();
        }
    }

    /**
     * Returns a random path segment.
     * 
     * @return The path segment
     */
    protected static @NotNull String randomPathSegment() {
        final StringBuilder buffer = new StringBuilder();
        final int length = RandomUtils.nextInt(
                SEGMENT_PATH_MIN_LENGTH,
                SEGMENT_PATH_MAX_LENGTH + 1);
        buffer.append(RandomStringUtils.randomAlphabetic(1));
        for (int i = 1; i < length; i++) {
            if (RandomUtils.nextFloat(0, 1) < ESCAPED_CHAR_P) {
                buffer.append((char) RandomUtils.nextInt('\u0080', '\uFFFF'));
            } else {
                buffer.append(RandomStringUtils.random(1, UNESCAPED_PATH_C));
            }
        }
        if (RandomUtils.nextFloat(0, 1) < SEGMENT_PARAM_P) {
            buffer.append(SEGMENT_PARAM_SEPARATOR);
            final int paramLength = RandomUtils.nextInt(
                    PARAM_MIN_LENGTH,
                    PARAM_MAX_LENGTH + 1);
            for (int i = 0; i < paramLength; i++) {
                if (RandomUtils.nextFloat(0, 1) < ESCAPED_CHAR_P) {
                    buffer.append((char) RandomUtils.nextInt('\u0080', '\uFFFF'));
                } else {
                    buffer.append(RandomStringUtils.random(1, UNESCAPED_PATH_C));
                }
            }
        }
        return buffer.toString();
    }

    /**
     * Returns a random absolute path.
     * 
     * @return The absolute path
     */
    public static @NotNull String randomAbsolutePath() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(ABSOLUTE_PATH_PREFIX);
        final int length = RandomUtils.nextInt(
                PATH_SEGMENTS_MIN_SEGMENTS,
                PATH_SEGMENTS_MAX_SEGMENTS + 1);
        if (length > 0) {
            buffer.append(randomPathSegment());
            for (int i = 1; i < length; i++) {
                buffer.append(PATH_SEGMENTS_SEPARATOR).append(randomPathSegment());
            }
        }
        return buffer.toString();
    }

    /**
     * Returns a random relative path.
     * 
     * @return The relative path
     */
    public static @NotNull String randomRelativePath() {
        final StringBuilder buffer = new StringBuilder();
        final int length = RandomUtils.nextInt(
                RELATIVE_SEGMENT_MIN_LENGTH,
                RELATIVE_SEGMENT_MAX_LENGTH + 1);
        for (int i = 0; i < length; i++) {
            if (RandomUtils.nextFloat(0, 1) < ESCAPED_CHAR_P) {
                buffer.append((char) RandomUtils.nextInt('\u0080', '\uFFFF'));
            } else {
                buffer.append(RandomStringUtils.random(1, UNESCAPED_RELATIVE_SEGMENT_C));
            }
        }
        if (RandomUtils.nextFloat(0, 1) < RELATIVE_PATH_ABSOLUTE_PATH_P) {
            buffer.append(randomAbsolutePath());
        }
        return buffer.toString();
    }

    /**
     * Returns a random path part of an URI.
     * The path can be relative or absolute.
     * 
     * @return The path part
     */
    public static @NotNull String randomPath() {
        if (RandomUtils.nextFloat(0, 1) < PATH_ABSOLUTE_P) {
            return randomAbsolutePath();
        } else {
            return randomRelativePath();
        }
    }

    /**
     * Returns a random query part of an URI.
     * 
     * @return The query part
     */
    public static @NotNull String randomQuery() {
        final StringBuilder buffer = new StringBuilder();
        final int length = RandomUtils.nextInt(
                QUERY_MIN_LENGTH,
                QUERY_MAX_LENGTH + 1);
        for (int i = 0; i < length; i++) {
            if (RandomUtils.nextFloat(0, 1) < ESCAPED_CHAR_P) {
                buffer.append((char) RandomUtils.nextInt('\u0080', '\uFFFF'));
            } else {
                buffer.append(RandomStringUtils.random(1, UNESCAPED_URIC));
            }
        }
        return buffer.toString();
    }

    /**
     * Returns a random optional query part of an URI.
     * 
     * @return The query part, or {@code null}
     */
    public static String randomOptionalQuery() {
        if (RandomUtils.nextFloat(0, 1) < QUERY_P) {
            return randomQuery();
        } else {
            return null;
        }
    }

    /**
     * Returns a random fragment part of an URI.
     * 
     * @return The fragment part
     */
    public static @NotNull String randomFragment() {
        final StringBuilder buffer = new StringBuilder();
        final int length = RandomUtils.nextInt(
                FRAGMENT_MIN_LENGTH,
                FRAGMENT_MAX_LENGTH + 1);
        for (int i = 0; i < length; i++) {
            if (RandomUtils.nextFloat(0, 1) < ESCAPED_CHAR_P) {
                buffer.append((char) RandomUtils.nextInt('\u0080', '\uFFFF'));
            } else {
                buffer.append(RandomStringUtils.random(1, UNESCAPED_URIC));
            }
        }
        return buffer.toString();
    }

    /**
     * Returns a random optional fragment part of an URI.
     * 
     * @return The fragment part, or {@code null}
     */
    public static String randomOptionalFragment() {
        if (RandomUtils.nextFloat(0, 1) < FRAGMENT_P) {
            return randomFragment();
        } else {
            return null;
        }
    }

    /**
     * Returns a random absolute URI.
     * 
     * @return The absolute URI
     */
    public static @NotNull URI randomRelativeURI() {
        final String path;
        if (RandomUtils.nextFloat(0, 1) < PATH_ABSOLUTE_P) {
            path = randomAbsolutePath();
        } else {
            path = randomRelativePath();
        }
        try {
            return new URI(
                    null,
                    null,
                    path,
                    randomOptionalQuery(),
                    randomOptionalFragment());
        } catch (final URISyntaxException e) {
            throw new GenerationException("Error generating URI", e);
        }
    }

    /**
     * Returns a random absolute URI.
     * 
     * @return The absolute URI
     */
    public static @NotNull URI randomAbsoluteURI() {
        try {
            return new URI(
                    randomScheme(),
                    randomAuthority(),
                    randomAbsolutePath(),
                    randomOptionalQuery(),
                    randomOptionalFragment());
        } catch (final URISyntaxException e) {
            throw new GenerationException("Error generating URI", e);
        }
    }

    /**
     * Returns a random URI (absolute or not).
     * 
     * @return The URI
     */
    public static @NotNull URI randomURI() {
        if (RandomUtils.nextFloat(0, 1) < PATH_ABSOLUTE_P) {
            return randomAbsoluteURI();
        } else {
            return randomRelativeURI();
        }
    }
}
