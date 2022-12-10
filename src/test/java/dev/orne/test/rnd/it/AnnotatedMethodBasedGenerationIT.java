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

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.GeneratorMethod;
import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.GeneratorsTestUtils;

/**
 * Integration test for annotated method based generation.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 */
@Tag("it")
class AnnotatedMethodBasedGenerationIT {

    /**
     * Test for annotated constructor based built-in generation.
     */
    @Test
    void testImmutableTypeGeneration() {
        ImmutableType result;
        result = Generators.defaultValue(ImmutableType.class);
        assertNotNull(result);
        assertEquals(0, result.code);
        assertNull(result.name);
        result = Generators.nullableDefaultValue(ImmutableType.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(ImmutableType.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(ImmutableType.class, 100, 2);
    }

    /**
     * Test for annotated factory method based built-in generation.
     * <p>
     * The second parameter has a {@code NotNull} annotation, so no null values
     * should be generated.
     */
    @Test
    void testDerivedTypeGeneration() {
        DerivedType result;
        result = Generators.defaultValue(DerivedType.class);
        assertNotNull(result);
        assertEquals("0-null", result.codename);
        result = Generators.nullableDefaultValue(DerivedType.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(ImmutableType.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(ImmutableType.class, 100, 2);
    }

    public static class ImmutableType {
        private final int code;
        private final String name;
        @GeneratorMethod
        public ImmutableType(
                final int code,
                final String name) {
            super();
            this.code = code;
            this.name = name;
        }
        public int getCode() {
            return this.code;
        }
        public String getName() {
            return this.name;
        }
        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }
        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }
    }

    public static class DerivedType {
        private final @NotNull String codename;
        private DerivedType(
                final @NotNull String codename) {
            super();
            this.codename = codename;
        }
        @GeneratorMethod
        public static DerivedType of(
                final int code,
                final @NotNull String name) {
            return new DerivedType(
                    String.format("%d-%s", code, name));
        }
        public @NotNull String getCodename() {
            return codename;
        }
        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }
        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }
    }
}
