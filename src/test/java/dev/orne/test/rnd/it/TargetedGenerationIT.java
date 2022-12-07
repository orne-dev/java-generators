package dev.orne.test.rnd.it;

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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.params.TargetedGenerator;

/**
 * Integration test for targeted generators.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 */
@Tag("it")
class TargetedGenerationIT {

    /**
     * Test for generator for bean property.
     */
    @Test
    void testTargetPropertyGeneration() {
        final TargetedGenerator<String> generator = Generators.forProperty(
                TestType.class,
                "name");
        assertGeneratedValues(generator);
    }

    /**
     * Test for generator for constructor parameter.
     */
    @Test
    void testTargetConstructorParameterGeneration() {
        final TargetedGenerator<String> generator = Generators.forParameter(
                TestType.class,
                new Class<?>[] { String.class },
                0);
        assertGeneratedValues(generator);
    }

    /**
     * Test for generator for method parameter.
     */
    @Test
    void testTargetMethodParameterGeneration() {
        final TargetedGenerator<String> generator = Generators.forParameter(
                TestType.class,
                "testMethod",
                new Class<?>[] { String.class },
                0);
        assertGeneratedValues(generator);
    }

    /**
     * Test for generator for method return type.
     */
    @Test
    void testTargetMethodReturnTypeGeneration() {
        final TargetedGenerator<String> generator = Generators.forReturnType(
                TestType.class,
                "testMethod",
                new Class<?>[] { String.class });
        assertGeneratedValues(generator);
    }

    private void assertGeneratedValues(
            final TargetedGenerator<String> generator) {
        String result;
        result = generator.defaultValue();
        assertNotNull(result);
        result = generator.defaultValue(TestType.NotNullMode.class);
        assertNotNull(result);
        result = generator.defaultValue(TestType.NotBlankMode.class);
        assertNotNull(result);
        result = generator.defaultValue(TestType.SizeMode.class);
        assertNotNull(result);
        result = generator.nullableDefaultValue();
        assertNull(result);
        result = generator.nullableDefaultValue(TestType.NotNullMode.class);
        assertNotNull(result);
        result = generator.nullableDefaultValue(TestType.NotBlankMode.class);
        assertNull(result);
        result = generator.nullableDefaultValue(TestType.SizeMode.class);
        assertNull(result);
        result = generator.randomValue();
        assertNotNull(result);
        result = generator.defaultValue(TestType.NotNullMode.class);
        assertNotNull(result);
        result = generator.defaultValue(TestType.NotBlankMode.class);
        assertNotNull(result);
        result = generator.defaultValue(TestType.SizeMode.class);
        assertNotNull(result);
        result = generator.nullableRandomValue();
        result = generator.nullableRandomValue(TestType.NotNullMode.class);
        assertNotNull(result);
        result = generator.nullableRandomValue(TestType.NotBlankMode.class);
        result = generator.nullableRandomValue(TestType.SizeMode.class);
        if (result != null) {
            assertTrue(result.length() >= 3);
            assertTrue(result.length() <= 8);
        }
    }

    public static class TestType {
        @NotNull(groups = NotNullMode.class)
        @NotBlank(groups = NotBlankMode.class)
        @Size(groups = SizeMode.class, min = 3, max = 8)
        private String name;
        public TestType() {
            super();
        }
        public TestType(
                @NotNull(groups = NotNullMode.class)
                @NotBlank(groups = NotBlankMode.class)
                @Size(groups = SizeMode.class, min = 3, max = 8)
                String name) {
            super();
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        @NotNull(groups = NotNullMode.class)
        @NotBlank(groups = NotBlankMode.class)
        @Size(groups = SizeMode.class, min = 3, max = 8)
        public String testMethod(
                @NotNull(groups = NotNullMode.class)
                @NotBlank(groups = NotBlankMode.class)
                @Size(groups = SizeMode.class, min = 3, max = 8)
                String name) {
            return name;
        }
        interface NotNullMode extends Default  {}
        interface NotBlankMode extends Default  {}
        interface SizeMode extends Default  {}
    }
}
