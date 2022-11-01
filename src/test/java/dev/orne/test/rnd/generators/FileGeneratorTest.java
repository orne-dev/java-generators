package dev.orne.test.rnd.generators;

/*-
 * #%L
 * Orne Test Generators
 * %%
 * Copyright (C) 2021 Orne Developments
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

import java.io.File;
import java.time.Duration;
import java.util.HashSet;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code FileGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2021-11
 * @since 0.1
 * @see FileGenerator
 */
@Tag("ut")
class FileGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(File.class));
        assertEquals(FileGenerator.class, Generators.getGenerator(File.class).getClass());
    }

    /**
     * Unit test for {@link FileGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new FileGenerator().getPriority());
    }

    /**
     * Unit test for {@link FileGenerator#supports()}
     */
    @Test
    void testSupports() {
        final FileGenerator generator = new FileGenerator();
        assertTrue(generator.supports(File.class));
        assertFalse(generator.supports(File[].class));
    }

    /**
     * Unit test for {@link FileGenerator#removeLastSeparator(String)}
     */
    @Test
    void testRemoveLastSeparator() {
        String expected = "test";
        assertEquals(expected, FileGenerator.removeLastSeparator(
                expected));
        assertEquals(expected, FileGenerator.removeLastSeparator(
                expected + File.separator));
        expected = File.separator + "test";
        assertEquals(expected, FileGenerator.removeLastSeparator(
                expected));
        assertEquals(expected, FileGenerator.removeLastSeparator(
                expected + File.separator));
        expected = "test" + File.separator + "multiple";
        assertEquals(expected, FileGenerator.removeLastSeparator(
                expected));
        assertEquals(expected, FileGenerator.removeLastSeparator(
                expected + File.separator));
        expected = File.separator + "test" + File.separator + "multiple";
        assertEquals(expected, FileGenerator.removeLastSeparator(
                expected));
        assertEquals(expected, FileGenerator.removeLastSeparator(
                expected + File.separator));
    }

    /**
     * Unit test for {@link FileGenerator#workingPath()}
     */
    @Test
    void testWorkingPath() {
        final String sysProp = System.getProperty(FileGenerator.WORKING_DIR_PROP);
        final String expected = FileGenerator.removeLastSeparator(sysProp);
        assertEquals(expected, FileGenerator.workingPath());
    }

    /**
     * Unit test for {@link FileGenerator#workingDir()}
     */
    @Test
    void testWorkingDir() {
        final String sysProp = System.getProperty(FileGenerator.WORKING_DIR_PROP);
        final String expected = FileGenerator.removeLastSeparator(sysProp);
        final File result = FileGenerator.workingDir();
        assertEquals(expected, result.getPath());
    }

    /**
     * Unit test for {@link FileGenerator#userPath()}
     */
    @Test
    void testUserPath() {
        final String sysProp = System.getProperty(FileGenerator.USER_DIR_PROP);
        final String expected = FileGenerator.removeLastSeparator(sysProp);
        assertEquals(expected, FileGenerator.userPath());
    }

    /**
     * Unit test for {@link FileGenerator#userDir()}
     */
    @Test
    void testUserDir() {
        final String sysProp = System.getProperty(FileGenerator.USER_DIR_PROP);
        final String expected = FileGenerator.removeLastSeparator(sysProp);
        final File result = FileGenerator.userDir();
        assertEquals(expected, result.getPath());
    }

    /**
     * Unit test for {@link FileGenerator#tmpPath()}
     */
    @Test
    void testTmpPath() {
        final String sysProp = System.getProperty(FileGenerator.TMP_DIR_PROP);
        final String expected = FileGenerator.removeLastSeparator(sysProp);
        assertEquals(expected, FileGenerator.tmpPath());
    }

    /**
     * Unit test for {@link FileGenerator#tmpDir()}
     */
    @Test
    void testTmpDir() {
        final String sysProp = System.getProperty(FileGenerator.TMP_DIR_PROP);
        final String expected = FileGenerator.removeLastSeparator(sysProp);
        final File result = FileGenerator.tmpDir();
        assertEquals(expected, result.getPath());
    }

    /**
     * Unit test for {@link FileGenerator#randomPathSegment()}
     */
    @Test
    void testRandomPathSegment() {
        final String result = FileGenerator.randomPathSegment();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.matches("[a-zA-Z0-9]+"));
    }

    /**
     * Unit test for {@link FileGenerator#randomPath()}
     */
    @Test
    void testRandomPath() {
        final String result = FileGenerator.randomPath();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertFalse(result.startsWith(File.separator));
        final String[] segments = result.split("\\" + File.separator);
        assertTrue(segments.length > 0);
        for (int i = 0; i < segments.length; i++) {
            assertTrue(segments[i].matches("[a-zA-Z0-9]+"));
        }
    }

    /**
     * Unit test for {@link FileGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final FileGenerator generator = new FileGenerator();
        assertEquals(FileGenerator.workingDir(), generator.defaultValue());
    }

    /**
     * Unit test for {@link FileGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final FileGenerator generator = new FileGenerator();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HashSet<String> results = new HashSet<>(); 
            // We just check that there is some result variety
            while (results.size() < 50) {
                final File value = generator.randomValue();
                assertNotNull(value);
                assertFalse(value.isAbsolute());
                results.add(value.getPath());
            }
        });
    }
}
