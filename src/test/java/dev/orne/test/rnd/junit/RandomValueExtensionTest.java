package dev.orne.test.rnd.junit;

/*-
 * #%L
 * Orne Test Generators
 * %%
 * Copyright (C) 2023 Orne Developments
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

import java.lang.reflect.Field;
import java.util.Map;

import javax.validation.constraints.Size;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterResolutionException;

/**
 * Unit tests for {@code @Random} and {@code RandomValueExtension}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2021-03
 * @since 0.1
 * @see Random
 * @see RandomValueExtension
 */
@Tag("ut")
class RandomValueExtensionTest {

    /** Random static injected value. */
    private static @Random String staticText;
    /** Random static injected generic value. */
    private static @Random @Size(min = 2) Map<String, Number> staticGeneric;
    /** Random instance injected value. */
    private @Random String instanceText;
    /** Random instance injected generic value. */
    private @Random @Size(min = 2) Map<String, Number> instanceGeneric;

    /**
     * {@code @BeforeAll} callback parameters injection test.
     * 
     * @param text The random injected parameter value.
     * @param generic The random injected generic parameter value.
     */
    @BeforeAll
    static void beforeAllCallback(
            final @Random String text,
            final @Random @Size(min = 2) Map<String, Number> generic) {
        assertNotNull(text);
        assertNotNull(generic);
        assertFalse(generic.isEmpty());
        assertTrue(generic.size() > 1);
        for (final Map.Entry<String, Number> entry : generic.entrySet()) {
            assertInstanceOf(String.class, entry.getKey());
            assertInstanceOf(Number.class, entry.getValue());
        }
    }

    /**
     * {@code @AfterAll} callback parameters injection test.
     * 
     * @param text The random injected parameter value.
     * @param generic The random injected generic parameter value.
     */
    @AfterAll
    static void afterAllCallback(
            final @Random String text,
            final @Random @Size(min = 2) Map<String, Number> generic) {
        assertNotNull(text);
        assertNotNull(generic);
        assertFalse(generic.isEmpty());
        assertTrue(generic.size() > 1);
        for (final Map.Entry<String, Number> entry : generic.entrySet()) {
            assertInstanceOf(String.class, entry.getKey());
            assertInstanceOf(Number.class, entry.getValue());
        }
    }

    /**
     * {@code @BeforeEach} callback parameters injection test.
     * 
     * @param text The random injected parameter value.
     * @param generic The random injected generic parameter value.
     */
    @BeforeEach
    void beforeEachCallback(
            final @Random String text,
            final @Random @Size(min = 2) Map<String, Number> generic) {
        assertNotNull(text);
        assertNotNull(generic);
        assertFalse(generic.isEmpty());
        assertTrue(generic.size() > 1);
        for (final Map.Entry<String, Number> entry : generic.entrySet()) {
            assertInstanceOf(String.class, entry.getKey());
            assertInstanceOf(Number.class, entry.getValue());
        }
    }

    /**
     * {@code @AfterAll} callback parameters injection test.
     * 
     * @param text The random injected parameter value.
     * @param generic The random injected generic parameter value.
     */
    @AfterEach
    void afterEachCallback(
            final @Random String text,
            final @Random @Size(min = 2) Map<String, Number> generic) {
        assertNotNull(text);
        assertNotNull(generic);
        assertFalse(generic.isEmpty());
        assertTrue(generic.size() > 1);
        for (final Map.Entry<String, Number> entry : generic.entrySet()) {
            assertInstanceOf(String.class, entry.getKey());
            assertInstanceOf(Number.class, entry.getValue());
        }
    }

    /**
     * Constructor parameters injection test.
     * 
     * @param text The random injected parameter value.
     * @param generic The random injected generic parameter value.
     */
    RandomValueExtensionTest(
            final @Random String text,
            final @Random @Size(min = 2) Map<String, Number> generic) {
        assertNotNull(text);
        assertNotNull(generic);
        assertFalse(generic.isEmpty());
        assertTrue(generic.size() > 1);
        for (final Map.Entry<String, Number> entry : generic.entrySet()) {
            assertInstanceOf(String.class, entry.getKey());
            assertInstanceOf(Number.class, entry.getValue());
        }
    }

    /**
     * Test method parameters injection test.
     * 
     * @param text The random injected parameter value.
     * @param generic The random injected generic parameter value.
     */
    @Test
    void testParameterInyection(
            final @Random String text,
            final @Random @Size(min = 2) Map<String, Number> generic) {
        assertNotNull(text);
        assertNotNull(generic);
        assertFalse(generic.isEmpty());
        assertTrue(generic.size() > 1);
        for (final Map.Entry<String, Number> entry : generic.entrySet()) {
            assertInstanceOf(String.class, entry.getKey());
            assertInstanceOf(Number.class, entry.getValue());
        }
    }

    /**
     * Unit test for {@link RandomValueExtension#injectFields(Class, Object)}
     */
    @Test
    void testInjectFields_Static() {
        final RandomValueExtension extension = new RandomValueExtension();
        TestTarget.staticField = TestTarget.DEFAULT_VALUE;
        extension.injectFields(TestTarget.class, null);
        assertNotEquals(TestTarget.DEFAULT_VALUE, TestTarget.staticField);
    }

    /**
     * Unit test for {@link RandomValueExtension#injectFields(Class, Object)}
     */
    @Test
    void testInjectFields_Instance() {
        final RandomValueExtension extension = new RandomValueExtension();
        TestTarget.staticField = TestTarget.DEFAULT_VALUE;
        final TestTarget instance = new TestTarget();
        extension.injectFields(TestTarget.class, instance);
        assertEquals(TestTarget.DEFAULT_VALUE, TestTarget.staticField);
        assertNotEquals(TestTarget.DEFAULT_VALUE, instance.instanceField);
    }

    /**
     * Unit test for {@link RandomValueExtension#injectField(Class, Field, Object, Object)}
     */
    @Test
    void testInjectField()
    throws ReflectiveOperationException {
        final RandomValueExtension extension = new RandomValueExtension();
        final TestTarget instance = new TestTarget();
        final Field  invalidStaticField = TestTarget.class.getDeclaredField("invalidStaticField");
        final Field  staticField = TestTarget.class.getDeclaredField("staticField");
        final Field  invalidInstanceField = TestTarget.class.getDeclaredField("invalidInstanceField");
        final Field  instanceField = TestTarget.class.getDeclaredField("instanceField");
        assertThrows(ParameterResolutionException.class, () -> {
            extension.injectField(RandomValueExtension.class, invalidStaticField, null, "Won't change");
        });
        TestTarget.staticField = TestTarget.DEFAULT_VALUE;
        extension.injectField(RandomValueExtension.class, staticField, null, "New value");
        assertEquals("New value", TestTarget.staticField);
        assertThrows(ParameterResolutionException.class, () -> {
            extension.injectField(RandomValueExtension.class, invalidInstanceField, instance, "Won't change");
        });
        extension.injectField(RandomValueExtension.class, instanceField, instance, "New value");
        assertEquals("New value", instance.instanceField);
    }

    @SuppressWarnings("unused")
    private static class TestTarget {
        private static final String DEFAULT_VALUE = "Can be injected";
        private static final String invalidStaticField = "Cannot be injected";
        private static @Random String staticField = DEFAULT_VALUE;
        private final String invalidInstanceField = "Cannot be injected";
        private @Random String instanceField = DEFAULT_VALUE;
    }
}
