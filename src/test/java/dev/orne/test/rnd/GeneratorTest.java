package dev.orne.test.rnd;

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

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.params.GeneratorNotParameterizableException;
import dev.orne.test.rnd.params.ParameterizableGenerator;

/**
 * Unit tests for {@code Generator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2023-11
 * @since 0.2
 * @see Generator
 */
@Tag("ut")
class GeneratorTest {

    /**
     * Unit test for {@link Generator#getPriority()}
     */
    @Test
    void testGetPriority() {
        assertEquals(Priority.DEFAULT, new DefaultTestGenerator().getPriority());
        assertEquals(PriorityTestGenerator.PRIORITY, new PriorityTestGenerator().getPriority());
    }

    /**
     * Unit test for {@link Generator#asParameterizable()}
     */
    @Test
    void testAsParameterizable() {
        assertThrows(GeneratorNotParameterizableException.class, () -> {
            new DefaultTestGenerator().asParameterizable();
        });
        final ParameterizableTestGenerator pGenerator = new ParameterizableTestGenerator();
        assertSame(pGenerator, pGenerator.asParameterizable());
    }

    private static class DefaultTestGenerator
    implements Generator {
        @Override
        public boolean supports(@NotNull Class<?> type) {
            throw new UnsupportedOperationException();
        }
        @Override
        public <T> @NotNull T defaultValue(@NotNull Class<T> type) {
            throw new UnsupportedOperationException();
        }
        @Override
        public <T> T nullableDefaultValue(@NotNull Class<T> type) {
            throw new UnsupportedOperationException();
        }
        @Override
        public <T> @NotNull T randomValue(@NotNull Class<T> type) {
            throw new UnsupportedOperationException();
        }
        @Override
        public <T> T nullableRandomValue(@NotNull Class<T> type) {
            throw new UnsupportedOperationException();
        }
    }
    @Priority(PriorityTestGenerator.PRIORITY)
    private static class PriorityTestGenerator
    implements Generator {
        private static final int PRIORITY = 123;
        @Override
        public boolean supports(@NotNull Class<?> type) {
            throw new UnsupportedOperationException();
        }
        @Override
        public <T> @NotNull T defaultValue(@NotNull Class<T> type) {
            throw new UnsupportedOperationException();
        }
        @Override
        public <T> T nullableDefaultValue(@NotNull Class<T> type) {
            throw new UnsupportedOperationException();
        }
        @Override
        public <T> @NotNull T randomValue(@NotNull Class<T> type) {
            throw new UnsupportedOperationException();
        }
        @Override
        public <T> T nullableRandomValue(@NotNull Class<T> type) {
            throw new UnsupportedOperationException();
        }
    }
    private static class ParameterizableTestGenerator
    implements ParameterizableGenerator {
        @Override
        public boolean supports(@NotNull Class<?> type) {
            throw new UnsupportedOperationException();
        }
        @Override
        public <T> @NotNull T defaultValue(@NotNull Class<T> type) {
            throw new UnsupportedOperationException();
        }
        @Override
        public <T> T nullableDefaultValue(@NotNull Class<T> type) {
            throw new UnsupportedOperationException();
        }
        @Override
        public <T> @NotNull T randomValue(@NotNull Class<T> type) {
            throw new UnsupportedOperationException();
        }
        @Override
        public <T> T nullableRandomValue(@NotNull Class<T> type) {
            throw new UnsupportedOperationException();
        }
        @Override
        public <T> @NotNull T defaultValue(@NotNull Class<T> type, @NotNull Object... params) {
            throw new UnsupportedOperationException();
        }
        @Override
        public <T> T nullableDefaultValue(@NotNull Class<T> type, @NotNull Object... params) {
            throw new UnsupportedOperationException();
        }
        @Override
        public <T> @NotNull T randomValue(@NotNull Class<T> type, @NotNull Object... params) {
            throw new UnsupportedOperationException();
        }
        @Override
        public <T> T nullableRandomValue(@NotNull Class<T> type, @NotNull Object... params) {
            throw new UnsupportedOperationException();
        }
    }
}
