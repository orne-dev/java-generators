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
import static org.mockito.BDDMockito.*;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.Chronology;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generator;
import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.GeneratorsTestUtils;
import dev.orne.test.rnd.Priority;

/**
 * Unit tests for {@code ArrayGenerator}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-11
 * @since 0.1
 * @see ArrayGenerator
 */
@Tag("ut")
class ArrayGeneratorTest {

    /**
     * The built in supported types
     */
    private static final Class<?>[] SUPPORTED_TYPES = new Class<?>[] {
        boolean[].class,
        byte[].class,
        short[].class,
        int[].class,
        long[].class,
        float[].class,
        double[].class,
        char[].class,
        Boolean[].class,
        Byte[].class,
        Short[].class,
        Integer[].class,
        Long[].class,
        Float[].class,
        Double[].class,
        Character[].class,
        CharSequence[].class,
        Number[].class,
        String[].class,
        File[].class,
        BigInteger[].class,
        BigDecimal[].class,
        Charset[].class,
        Path[].class,
        Calendar[].class,
        Currency[].class,
        Date[].class,
        Locale[].class,
        TimeZone[].class,
        URI[].class,
        URL[].class,
        UUID[].class,
        Chronology[].class,
        Clock[].class,
        Duration[].class,
        Instant[].class,
        LocalDate[].class,
        LocalDateTime[].class,
        LocalTime[].class,
        OffsetDateTime[].class,
        OffsetTime[].class,
        Period[].class,
        Year[].class,
        YearMonth[].class,
        ZonedDateTime[].class,
        ZoneId[].class,
        ZoneOffset[].class,
    };

    /**
     * Integration test for automatic registration in {@code Generators}.
     * 
     * @see Generators
     */
    @Test
    void testAutomaticRegistration() {
        for (final Class<?> supportedType : SUPPORTED_TYPES) {
            assertTrue(Generators.supports(supportedType));
            assertEquals(ArrayGenerator.class, Generators.getGenerator(supportedType).getClass());
        }
    }

    /**
     * Unit test for {@link ArrayGenerator#getPriority()}
     */
    @Test
    void testPriority() {
        assertEquals(Priority.GENERIC_GENERATORS, new ArrayGenerator().getPriority());
    }

    /**
     * Unit test for {@link ArrayGenerator#supports(Class)}
     */
    @Test
    void testSupports() {
        final ArrayGenerator generator = new ArrayGenerator();
        for (final Class<?> supportedType : SUPPORTED_TYPES) {
            assertTrue(generator.supports(supportedType));
        }
        assertFalse(generator.supports(Long.class));
        assertFalse(generator.supports(long.class));
        assertFalse(generator.supports(UnsupportedType[].class));
    }

    /**
     * Unit test for {@link ArrayGenerator#nullableDefaultValue(Class)}
     */
    @Test
    void testNullableDefaultValue() {
        final ArrayGenerator generator = new ArrayGenerator();
        for (final Class<?> supportedType : SUPPORTED_TYPES) {
            assertNull(generator.nullableDefaultValue(supportedType));
        }
    }

    /**
     * Unit test for {@link ArrayGenerator#defaultValue(Class)}
     */
    @Test
    void testDefaultValue() {
        final ArrayGenerator generator = new ArrayGenerator();
        assertArrayEquals(new boolean[0], generator.defaultValue(boolean[].class));
        assertArrayEquals(new byte[0], generator.defaultValue(byte[].class));
        assertArrayEquals(new short[0], generator.defaultValue(short[].class));
        assertArrayEquals(new int[0], generator.defaultValue(int[].class));
        assertArrayEquals(new long[0], generator.defaultValue(long[].class));
        assertArrayEquals(new float[0], generator.defaultValue(float[].class));
        assertArrayEquals(new double[0], generator.defaultValue(double[].class));
        assertArrayEquals(new char[0], generator.defaultValue(char[].class));
        assertArrayEquals(new Boolean[0], generator.defaultValue(Boolean[].class));
        assertArrayEquals(new Byte[0], generator.defaultValue(Byte[].class));
        assertArrayEquals(new Short[0], generator.defaultValue(Short[].class));
        assertArrayEquals(new Integer[0], generator.defaultValue(Integer[].class));
        assertArrayEquals(new Long[0], generator.defaultValue(Long[].class));
        assertArrayEquals(new Float[0], generator.defaultValue(Float[].class));
        assertArrayEquals(new Double[0], generator.defaultValue(Double[].class));
        assertArrayEquals(new Character[0], generator.defaultValue(Character[].class));
        assertArrayEquals(new CharSequence[0], generator.defaultValue(CharSequence[].class));
        assertArrayEquals(new Number[0], generator.defaultValue(Number[].class));
        assertArrayEquals(new String[0], generator.defaultValue(String[].class));
        assertArrayEquals(new File[0], generator.defaultValue(File[].class));
        assertArrayEquals(new BigInteger[0], generator.defaultValue(BigInteger[].class));
        assertArrayEquals(new BigDecimal[0], generator.defaultValue(BigDecimal[].class));
        assertArrayEquals(new Charset[0], generator.defaultValue(Charset[].class));
        assertArrayEquals(new Path[0], generator.defaultValue(Path[].class));
        assertArrayEquals(new Calendar[0], generator.defaultValue(Calendar[].class));
        assertArrayEquals(new Currency[0], generator.defaultValue(Currency[].class));
        assertArrayEquals(new Date[0], generator.defaultValue(Date[].class));
        assertArrayEquals(new Locale[0], generator.defaultValue(Locale[].class));
        assertArrayEquals(new TimeZone[0], generator.defaultValue(TimeZone[].class));
        assertArrayEquals(new UUID[0], generator.defaultValue(UUID[].class));
    }

    /**
     * Unit test for {@link ArrayGenerator#randomArray(Class, Generator)}
     */
    @Test
    void testRandomArray() {
        final Generator compGenerator = mock(Generator.class);
        final ArrayGenerator generator = new ArrayGenerator();
        final UnsupportedType value = mock(UnsupportedType.class);
        willReturn(value).given(compGenerator).randomValue(UnsupportedType.class);
        final UnsupportedType[] result = (UnsupportedType[]) generator.randomArray(
                UnsupportedType.class,
                compGenerator);
        assertNotNull(result);
        assertTrue(result.length > 0);
        then(compGenerator).should(times(result.length)).randomValue(UnsupportedType.class);
    }

    /**
     * Unit test for {@link ArrayGenerator#randomArray(Class, Generator)}
     */
    @Test
    void testRandomArray_Primitives() {
        final ArrayGenerator generator = new ArrayGenerator();
        final Generator intGenerator = Generators.getGenerator(int.class);
        final int[] result = (int[]) generator.randomArray(int.class, intGenerator);
        assertNotNull(result);
        assertTrue(result.length > 0);
    }

    /**
     * Unit test for {@link ArrayGenerator#randomArray(Class, Generator)}
     */
    @Test
    void testRandomArray_Sizes() {
        final ArrayGenerator generator = new ArrayGenerator();
        final int sizes = ArrayGenerator.MAX_SIZE - ArrayGenerator.MIN_SIZE;
        final Generator intGenerator = Generators.getGenerator(int.class);
        GeneratorsTestUtils.assertRandomGeneration(
                () -> {
                    final Integer[] value = (Integer[]) generator.randomArray(
                            Integer.class,
                            intGenerator);
                    return value.length;
                },
                sizes,
                2);
    }

    /**
     * Unit test for {@link ArrayGenerator#randomValue(Class)}
     */
    @Test
    void testRandomValue() {
        final ArrayGenerator generator = spy(new ArrayGenerator());
        final long[] mockResult = new long[1];
        willReturn(mockResult).given(generator).randomArray(
                long.class,
                Generators.getGenerator(long.class));
        final long[] result = generator.randomValue(long[].class);
        assertSame(mockResult, result);
        then(generator).should().randomArray(
                long.class,
                Generators.getGenerator(long.class));
    }

    /**
     * Unit test for {@link ArrayGenerator#randomNullablesArray(Class, Generator)}
     */
    @Test
    void testRandomNullablesArray() {
        final Generator compGenerator = mock(Generator.class);
        final ArrayGenerator generator = new ArrayGenerator();
        final UnsupportedType value = mock(UnsupportedType.class);
        willReturn(value).given(compGenerator).nullableRandomValue(UnsupportedType.class);
        final UnsupportedType[] result = (UnsupportedType[]) generator.randomNullablesArray(
                UnsupportedType.class,
                compGenerator);
        assertNotNull(result);
        assertTrue(result.length > 0);
        then(compGenerator).should(times(result.length)).nullableRandomValue(UnsupportedType.class);
    }

    /**
     * Unit test for {@link ArrayGenerator#randomNullablesArray(Class, Generator)}
     */
    @Test
    void testRandomNullablesArray_Sizes() {
        final ArrayGenerator generator = new ArrayGenerator();
        final int sizes = ArrayGenerator.MAX_SIZE - ArrayGenerator.MIN_SIZE;
        final Generator intGenerator = Generators.getGenerator(Integer.class);
        GeneratorsTestUtils.assertNullableRandomGeneration(
                () -> {
                    final Integer[] value = (Integer[]) generator.randomNullablesArray(
                            Integer.class,
                            intGenerator);
                    return value.length;
                },
                sizes,
                2);
    }

    /**
     * Unit test for {@link ArrayGenerator#nullableRandomValue(Class)}
     */
    @Test
    void testNullableRandomValue() {
        final ArrayGenerator generator = spy(new ArrayGenerator());
        final Long[] mockResult = new Long[1];
        willReturn(mockResult).given(generator).randomNullablesArray(
                Long.class,
                Generators.getGenerator(Long.class));
        generator.setNullProbability(1);
        assertNull(generator.nullableRandomValue(Long[].class));
        then(generator).should(never()).randomNullablesArray(
                Long.class,
                Generators.getGenerator(Long.class));
        generator.setNullProbability(0);
        final Long[] result = generator.nullableRandomValue(Long[].class);
        assertSame(mockResult, result);
        then(generator).should().randomNullablesArray(
                Long.class,
                Generators.getGenerator(Long.class));
    }

    private static class UnsupportedType {}
}
