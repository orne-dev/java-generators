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

import java.io.File;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.AbstractTypedGenerator;
import dev.orne.test.rnd.Priority;

/**
 * Generator of {@code File} values.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
@Priority(Priority.NATIVE_GENERATORS)
public class FileGenerator
extends AbstractTypedGenerator<File> {

    /** System property for working directory path. */
    public static final String WORKING_DIR_PROP = "user.dir";
    /** System property for user home directory path. */
    public static final String USER_DIR_PROP = "user.home";
    /** System property for temporary files directory path. */
    public static final String TMP_DIR_PROP = "java.io.tmpdir";

    /**
     * Creates a new instance.
     */
    public FileGenerator() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull File defaultValue() {
        return workingDir();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull File randomValue() {
        return new File(randomPath());
    }

    /**
     * Returns the current working directory.
     * 
     * @return The current working directory
     */
    public static @NotNull File workingDir() {
        return new File(workingPath());
    }

    /**
     * Returns the current working directory path.
     * 
     * @return The current working directory path
     */
    public static @NotNull String workingPath() {
        return removeLastSeparator(System.getProperty(WORKING_DIR_PROP));
    }

    /**
     * Returns the current user's home directory.
     * 
     * @return The current user's home directory
     */
    public static @NotNull File userDir() {
        return new File(userPath());
    }

    /**
     * Returns the current user's home directory path.
     * 
     * @return The current user's home directory path
     */
    public static @NotNull String userPath() {
        return removeLastSeparator(System.getProperty(USER_DIR_PROP));
        
    }

    /**
     * Returns the default temporary file directory.
     * 
     * @return The default temporary file directory
     */
    public static @NotNull File tmpDir() {
        return new File(tmpPath());
    }

    /**
     * Returns the default temporary file directory path.
     * 
     * @return The default temporary file directory path
     */
    public static @NotNull String tmpPath() {
        return removeLastSeparator(System.getProperty(TMP_DIR_PROP));
    }

    /**
     * Generate a random path.
     * 
     * @return The generated random path
     */
    public static @NotNull String randomPath() {
        final int segments = RandomUtils.nextInt(1, 5);
        final StringBuilder path = new StringBuilder()
                .append(randomPathSegment());
        for (int i = 1; i < segments; i++) {
            path.append(File.separator)
                .append(randomPathSegment());
        }
        return path.toString();
    }

    /**
     * Generate a random path segment.
     * 
     * @return The generated random path
     */
    public static @NotNull String randomPathSegment() {
        return RandomStringUtils.randomAlphanumeric(5);
    }

    /**
     * Removes the last file path separator from the path if exists.
     * <p>
     * In some environments some paths end with separator while other
     * paths don't. This methods removes de incoherence.
     * 
     * @param path The path
     * @return The path without trailing separator
     */
    protected static @NotNull String removeLastSeparator(
            final @NotNull String path) {
        final String result;
        if (path.endsWith(File.separator)) {
            result = path.substring(0, path.length() - File.separator.length());
        } else {
            result = path;
        }
        return result;
    }
}
