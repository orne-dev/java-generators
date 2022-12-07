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

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.GeneratorsTestUtils;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code PathGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see PathGenerator
 */
@Tag("ut")
class PathGeneratorTest {

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        assertTrue(Generators.supports(Path.class));
        assertEquals(PathGenerator.class, Generators.getGenerator(Path.class).getClass());
    }

    /**
     * Unit test for {@link PathGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.NATIVE_GENERATORS, new PathGenerator().getPriority());
    }

    /**
     * Unit test for {@link PathGenerator#supports()}
     */
    @Test
    void testSupports() {
        final PathGenerator generator = new PathGenerator();
        assertTrue(generator.supports(Path.class));
        assertFalse(generator.supports(Path[].class));
    }

    /**
     * Unit test for {@link PathGenerator#removeLastSeparator(String)}
     */
    @Test
    void testRemoveLastSeparator() {
        assertEquals(PathGenerator.SEPARATOR, FileSystems.getDefault().getSeparator());
        String expected = "test";
        assertEquals(expected, PathGenerator.removeLastSeparator(
                expected));
        assertEquals(expected, PathGenerator.removeLastSeparator(
                expected + PathGenerator.SEPARATOR));
        expected = PathGenerator.SEPARATOR + "test";
        assertEquals(expected, PathGenerator.removeLastSeparator(
                expected));
        assertEquals(expected, PathGenerator.removeLastSeparator(
                expected + PathGenerator.SEPARATOR));
        expected = "test" + PathGenerator.SEPARATOR + "multiple";
        assertEquals(expected, PathGenerator.removeLastSeparator(
                expected));
        assertEquals(expected, PathGenerator.removeLastSeparator(
                expected + PathGenerator.SEPARATOR));
        expected = PathGenerator.SEPARATOR + "test" + PathGenerator.SEPARATOR + "multiple";
        assertEquals(expected, PathGenerator.removeLastSeparator(
                expected));
        assertEquals(expected, PathGenerator.removeLastSeparator(
                expected + PathGenerator.SEPARATOR));
    }

    /**
     * Unit test for {@link PathGenerator#workingPath()}
     */
    @Test
    void testWorkingPath() {
        final String sysProp = System.getProperty(PathGenerator.WORKING_DIR_PROP);
        final String expected = PathGenerator.removeLastSeparator(sysProp);
        assertEquals(expected, PathGenerator.workingPath());
    }

    /**
     * Unit test for {@link PathGenerator#workingDir()}
     */
    @Test
    void testWorkingDir() {
        final String sysProp = System.getProperty(PathGenerator.WORKING_DIR_PROP);
        final String expected = PathGenerator.removeLastSeparator(sysProp);
        assertEquals(Paths.get(expected), PathGenerator.workingDir());
    }

    /**
     * Unit test for {@link PathGenerator#userPath()}
     */
    @Test
    void testUserPath() {
        final String sysProp = System.getProperty(PathGenerator.USER_DIR_PROP);
        final String expected = PathGenerator.removeLastSeparator(sysProp);
        assertEquals(expected, PathGenerator.userPath());
    }

    /**
     * Unit test for {@link PathGenerator#userDir()}
     */
    @Test
    void testUserDir() {
        final String sysProp = System.getProperty(PathGenerator.USER_DIR_PROP);
        final String expected = PathGenerator.removeLastSeparator(sysProp);
        assertEquals(Paths.get(expected), PathGenerator.userDir());
    }

    /**
     * Unit test for {@link PathGenerator#tmpPath()}
     */
    @Test
    void testTmpPath() {
        final String sysProp = System.getProperty(PathGenerator.TMP_DIR_PROP);
        final String expected = PathGenerator.removeLastSeparator(sysProp);
        assertEquals(expected, PathGenerator.tmpPath());
    }

    /**
     * Unit test for {@link PathGenerator#tmpDir()}
     */
    @Test
    void testTmpDir() {
        final String sysProp = System.getProperty(PathGenerator.TMP_DIR_PROP);
        final String expected = PathGenerator.removeLastSeparator(sysProp);
        assertEquals(Paths.get(expected), PathGenerator.tmpDir());
    }

    /**
     * Unit test for {@link PathGenerator#randomPathSegment()}
     */
    @Test
    void testRandomPathSegment() {
        final String result = PathGenerator.randomPathSegment();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.matches("[a-zA-Z0-9]+"));
    }

    /**
     * Unit test for {@link PathGenerator#randomPath()}
     */
    @Test
    void testRandomPath() {
        final String result = PathGenerator.randomPath();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertFalse(result.startsWith(PathGenerator.SEPARATOR));
        final String[] segments = result.split("\\" + PathGenerator.SEPARATOR);
        assertTrue(segments.length > 0);
        for (int i = 0; i < segments.length; i++) {
            assertTrue(segments[i].matches("[a-zA-Z0-9]+"));
        }
    }

    /**
     * Unit test for {@link PathGenerator#defaultValue()}
     */
    @Test
    void testDefaultValue() {
        final PathGenerator generator = new PathGenerator();
        assertEquals(PathGenerator.workingDir(), generator.defaultValue());
    }

    /**
     * Unit test for {@link PathGenerator#randomValue()}
     */
    @Test
    void testRandomValue() {
        final PathGenerator generator = new PathGenerator();
        final Set<Path> results = GeneratorsTestUtils.assertRandomGeneration(generator, 50, 2);
        for (final Path result : results) {
            assertFalse(result.isAbsolute());
        }
    }
}
