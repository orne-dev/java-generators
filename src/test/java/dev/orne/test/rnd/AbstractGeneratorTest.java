package dev.orne.test.rnd;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code AbstractGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2021-03
 * @since 0.1
 * @see AbstractGenerator
 */
@Tag("ut")
class AbstractGeneratorTest {

    /**
     * Unit test for {@link AbstractGenerator#setNullProbability(float)}
     */
    @Test
    void testSetNullProbability() {
        final AbstractGenerator generator = spy(AbstractGenerator.class);
        assertEquals(AbstractGenerator.DEFAULT_NULL_PROBABILITY, generator.getNullProbability());
        float newProb = RandomUtils.nextFloat(0, 1);
        generator.setNullProbability(newProb);
        assertEquals(newProb, generator.getNullProbability());
        assertThrows(IllegalArgumentException.class, () -> {
            generator.setNullProbability(-0.001f);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            generator.setNullProbability(1.001f);
        });
        newProb = 0;
        generator.setNullProbability(newProb);
        assertEquals(newProb, generator.getNullProbability());
        newProb = 1;
        generator.setNullProbability(newProb);
        assertEquals(newProb, generator.getNullProbability());
    }

    /**
     * Unit test for {@link AbstractGenerator#assertSupported(Class)}
     */
    @Test
    void testAssertSupported() {
        final AbstractGenerator generator = spy(AbstractGenerator.class);
        willReturn(true).given(generator).supports(Object.class);
        assertDoesNotThrow(() -> {
            generator.assertSupported(Object.class);
        });
        then(generator).should().supports(Object.class);
    }

    /**
     * Unit test for {@link AbstractGenerator#assertSupported(Class)}
     */
    @Test
    void testAssertSupported_Unsupported() {
        final AbstractGenerator generator = spy(AbstractGenerator.class);
        willReturn(false).given(generator).supports(Object.class);
        assertThrows(IllegalArgumentException.class, () -> {
            generator.assertSupported(Object.class);
        });
        then(generator).should().supports(Object.class);
    }

    /**
     * Unit test for {@link AbstractGenerator#assertSupported(Class)}
     */
    @Test
    void testAssertSupported_Null() {
        final AbstractGenerator generator = spy(AbstractGenerator.class);
        final NullPointerException mockEx = new NullPointerException();
        willThrow(mockEx).given(generator).supports(null);
        final NullPointerException result = assertThrows(NullPointerException.class, () -> {
            generator.assertSupported(null);
        });
        assertSame(mockEx, result);
        then(generator).should().supports(null);
    }

    /**
     * Unit test for {@link AbstractGenerator#nullableDefaultValue(Class)}
     */
    @Test
    void testNullableDefaultValue() {
        final AbstractGenerator generator = spy(AbstractGenerator.class);
        willReturn(true).given(generator).supports(Object.class);
        assertNull(generator.nullableDefaultValue(Object.class));
        then(generator).should().supports(Object.class);
    }

    /**
     * Unit test for {@link AbstractGenerator#nullableDefaultValue(Class)}
     */
    @Test
    void testNullableDefaultValue_Unsupported() {
        final AbstractGenerator generator = spy(AbstractGenerator.class);
        willReturn(false).given(generator).supports(Object.class);
        assertThrows(IllegalArgumentException.class, () -> {
            generator.nullableDefaultValue(Object.class);
        });
        then(generator).should().supports(Object.class);
    }

    /**
     * Unit test for {@link AbstractGenerator#nullableRandomValue(Class)}
     */
    @Test
    void testNullableRandomValue() {
        final AbstractGenerator generator = spy(AbstractGenerator.class);
        final Object mockResult = new Object();
        willReturn(true).given(generator).supports(Object.class);
        willReturn(mockResult).given(generator).randomValue(Object.class);
        generator.setNullProbability(1);
        assertNull(generator.nullableRandomValue(Object.class));
        then(generator).should(times(1)).supports(Object.class);
        then(generator).should(never()).randomValue(Object.class);
        generator.setNullProbability(0);
        assertSame(mockResult, generator.nullableRandomValue(Object.class));
        then(generator).should(times(2)).supports(Object.class);
        then(generator).should().randomValue(Object.class);
    }

    /**
     * Unit test for {@link AbstractGenerator#nullableRandomValue(Class)}
     */
    @Test
    void testNullableRandomValue_Unsupported() {
        final AbstractGenerator generator = spy(AbstractGenerator.class);
        willReturn(false).given(generator).supports(Object.class);
        assertThrows(IllegalArgumentException.class, () -> {
            generator.nullableRandomValue(Object.class);
        });
        then(generator).should().supports(Object.class);
    }
}
