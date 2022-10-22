package dev.orne.test.rnd;

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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code AbstractTypedGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 * @see AbstractTypedGenerator
 */
@Tag("ut")
class PriorityTest {

    /**
     * Test for {@link Priority#COMPARATOR}.
     */
    @Test
    void testComparator() {
        final Generator importantGenerator = new ImportantGenerator();
        final Generator defaultGenerator = new DefaultGenerator();
        final Generator noAnnotationGenerator = new NoAnnotationGenerator();
        final Generator fallbackGenerator = new FallbackGenerator();
        final List<Generator> list = Arrays.asList(
                defaultGenerator,
                importantGenerator,
                fallbackGenerator,
                noAnnotationGenerator);
        final List<Generator> expected = Arrays.asList(
                importantGenerator,
                defaultGenerator,
                noAnnotationGenerator,
                fallbackGenerator);
        Collections.sort(list, Priority.COMPARATOR);
        assertEquals(expected, list);
    }

    @Priority(Priority.MAX)
    private static class ImportantGenerator
    extends MockGenerator {}
    @Priority(Priority.DEFAULT)
    private static class DefaultGenerator
    extends MockGenerator {}
    private static class NoAnnotationGenerator
    extends MockGenerator {}
    @Priority(Priority.MIN)
    private static class FallbackGenerator
    extends MockGenerator {}
}
