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

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.Chronology;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.GeneratorsTestUtils;
import dev.orne.test.rnd.params.GenerationParameters;
import dev.orne.test.rnd.params.KeyValueGenericParameters;
import dev.orne.test.rnd.params.SimpleGenericParameters;

/**
 * Integration test for custom generators registration.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @since 0.1
 */
@Tag("it")
class BuildInGeneratorsIT {

    /**
     * Test for {@code boolean} generation.
     */
    @Test
    void testNativeBooleanGeneration() {
        boolean result;
        result = Generators.defaultValue(boolean.class);
        assertEquals(false, result);
        result = Generators.nullableDefaultValue(boolean.class);
        assertEquals(false, result);
        GeneratorsTestUtils.assertRandomGeneration(boolean.class, 2, 2);
        GeneratorsTestUtils.assertRandomGeneration(
                () -> Generators.nullableRandomValue(boolean.class),
                2,
                2);
    }

    /**
     * Test for {@code Boolean} generation.
     */
    @Test
    void testBooleanGeneration() {
        Boolean result;
        result = Generators.defaultValue(Boolean.class);
        assertEquals(false, result);
        result = Generators.nullableDefaultValue(Boolean.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Boolean.class, 2, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Boolean.class, 2, 2);
    }

    /**
     * Test for {@code byte} generation.
     */
    @Test
    void testNativeByteGeneration() {
        byte result;
        result = Generators.defaultValue(byte.class);
        assertEquals((byte) 0, result);
        result = Generators.nullableDefaultValue(byte.class);
        assertEquals((byte) 0, result);
        GeneratorsTestUtils.assertRandomGeneration(byte.class, 100, 2);
        GeneratorsTestUtils.assertRandomGeneration(
                () -> Generators.nullableRandomValue(byte.class),
                100,
                2);
    }

    /**
     * Test for {@code Byte} generation.
     */
    @Test
    void testByteGeneration() {
        Byte result;
        result = Generators.defaultValue(Byte.class);
        assertEquals((byte) 0, result);
        result = Generators.nullableDefaultValue(Byte.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Byte.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Byte.class, 100, 2);
    }

    /**
     * Test for {@code short} generation.
     */
    @Test
    void testNativeShortGeneration() {
        short result;
        result = Generators.defaultValue(short.class);
        assertEquals((short) 0, result);
        result = Generators.nullableDefaultValue(short.class);
        assertEquals((short) 0, result);
        GeneratorsTestUtils.assertRandomGeneration(short.class, 100, 2);
        GeneratorsTestUtils.assertRandomGeneration(
                () -> Generators.nullableRandomValue(short.class),
                100,
                2);
    }

    /**
     * Test for {@code Short} generation.
     */
    @Test
    void testShortGeneration() {
        Short result;
        result = Generators.defaultValue(Short.class);
        assertEquals((short) 0, result);
        result = Generators.nullableDefaultValue(Short.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Short.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Short.class, 100, 2);
    }

    /**
     * Test for {@code int} generation.
     */
    @Test
    void testNativeIntegerGeneration() {
        int result;
        result = Generators.defaultValue(int.class);
        assertEquals(0, result);
        result = Generators.nullableDefaultValue(int.class);
        assertEquals(0, result);
        GeneratorsTestUtils.assertRandomGeneration(int.class, 100, 2);
        GeneratorsTestUtils.assertRandomGeneration(
                () -> Generators.nullableRandomValue(int.class),
                100,
                2);
    }

    /**
     * Test for {@code Integer} generation.
     */
    @Test
    void testIntegerGeneration() {
        Integer result;
        result = Generators.defaultValue(Integer.class);
        assertEquals(0, result);
        result = Generators.nullableDefaultValue(Integer.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Integer.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Integer.class, 100, 2);
    }

    /**
     * Test for {@code long} generation.
     */
    @Test
    void testNativeLongGeneration() {
        long result;
        result = Generators.defaultValue(long.class);
        assertEquals(0L, result);
        result = Generators.nullableDefaultValue(long.class);
        assertEquals(0L, result);
        GeneratorsTestUtils.assertRandomGeneration(long.class, 100, 2);
        GeneratorsTestUtils.assertRandomGeneration(
                () -> Generators.nullableRandomValue(long.class),
                100,
                2);
    }

    /**
     * Test for {@code Long} generation.
     */
    @Test
    void testLongGeneration() {
        Long result;
        result = Generators.defaultValue(Long.class);
        assertEquals(0L, result);
        result = Generators.nullableDefaultValue(Long.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Long.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Long.class, 100, 2);
    }

    /**
     * Test for {@code float} generation.
     */
    @Test
    void testNativeFloatGeneration() {
        float result;
        result = Generators.defaultValue(float.class);
        assertEquals(0.0f, result);
        result = Generators.nullableDefaultValue(float.class);
        assertEquals(0.0f, result);
        GeneratorsTestUtils.assertRandomGeneration(float.class, 100, 2);
        GeneratorsTestUtils.assertRandomGeneration(
                () -> Generators.nullableRandomValue(float.class),
                100,
                2);
    }

    /**
     * Test for {@code Float} generation.
     */
    @Test
    void testFloatGeneration() {
        Float result;
        result = Generators.defaultValue(Float.class);
        assertEquals(0.0f, result);
        result = Generators.nullableDefaultValue(Float.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Float.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Float.class, 100, 2);
    }

    /**
     * Test for {@code double} generation.
     */
    @Test
    void testNativeDoubleGeneration() {
        double result;
        result = Generators.defaultValue(double.class);
        assertEquals(0.0d, result);
        result = Generators.nullableDefaultValue(double.class);
        assertEquals(0.0d, result);
        GeneratorsTestUtils.assertRandomGeneration(double.class, 100, 2);
        GeneratorsTestUtils.assertRandomGeneration(
                () -> Generators.nullableRandomValue(double.class),
                100,
                2);
    }

    /**
     * Test for {@code Double} generation.
     */
    @Test
    void testDoubleGeneration() {
        Double result;
        result = Generators.defaultValue(Double.class);
        assertEquals(0.0d, result);
        result = Generators.nullableDefaultValue(Double.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Double.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Double.class, 100, 2);
    }

    /**
     * Test for {@code char} generation.
     */
    @Test
    void testNativeCharacterGeneration() {
        char result;
        result = Generators.defaultValue(char.class);
        assertEquals((char) 0, result);
        result = Generators.nullableDefaultValue(char.class);
        assertEquals((char) 0, result);
        GeneratorsTestUtils.assertRandomGeneration(char.class, 100, 2);
        GeneratorsTestUtils.assertRandomGeneration(
                () -> Generators.nullableRandomValue(char.class),
                100,
                2);
    }

    /**
     * Test for {@code Character} generation.
     */
    @Test
    void testCharacterGeneration() {
        Character result;
        result = Generators.defaultValue(Character.class);
        assertEquals((char) 0, result);
        result = Generators.nullableDefaultValue(Character.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Character.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Character.class, 100, 2);
    }

    /**
     * Test for {@code CharSequence} generation.
     */
    @Test
    void testCharSequenceGeneration() {
        CharSequence result;
        result = Generators.defaultValue(CharSequence.class);
        assertEquals("", result);
        result = Generators.nullableDefaultValue(CharSequence.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(CharSequence.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(CharSequence.class, 100, 2);
    }

    /**
     * Test for {@code Number} generation.
     */
    @Test
    void testNumberGeneration() {
        Number result;
        result = Generators.defaultValue(Number.class);
        assertEquals(BigDecimal.ZERO, result);
        result = Generators.nullableDefaultValue(Number.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Number.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Number.class, 100, 2);
    }

    /**
     * Test for {@code String} generation.
     */
    @Test
    void testStringGeneration() {
        String result;
        result = Generators.defaultValue(String.class);
        assertEquals("", result);
        result = Generators.nullableDefaultValue(String.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(String.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(String.class, 100, 2);
    }

    /**
     * Test for {@code File} generation.
     */
    @Test
    void testFileGeneration() {
        File result;
        result = Generators.defaultValue(File.class);
        assertEquals(new File(System.getProperty("user.dir")), result);
        result = Generators.nullableDefaultValue(File.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(File.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(File.class, 100, 2);
    }

    /**
     * Test for {@code BigInteger} generation.
     */
    @Test
    void testBigIntegerGeneration() {
        BigInteger result;
        result = Generators.defaultValue(BigInteger.class);
        assertEquals(BigInteger.ZERO, result);
        result = Generators.nullableDefaultValue(BigInteger.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(BigInteger.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(BigInteger.class, 100, 2);
    }

    /**
     * Test for {@code BigDecimal} generation.
     */
    @Test
    void testBigDecimalGeneration() {
        BigDecimal result;
        result = Generators.defaultValue(BigDecimal.class);
        assertEquals(BigDecimal.ZERO, result);
        result = Generators.nullableDefaultValue(BigDecimal.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(BigDecimal.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(BigDecimal.class, 100, 2);
    }

    /**
     * Test for {@code Charset} generation.
     */
    @Test
    void testCharsetGeneration() {
        Charset result;
        result = Generators.defaultValue(Charset.class);
        assertEquals(Charset.defaultCharset(), result);
        result = Generators.nullableDefaultValue(Charset.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Charset.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Charset.class, 100, 2);
    }

    /**
     * Test for {@code Path} generation.
     */
    @Test
    void testPathGeneration() {
        Path result;
        result = Generators.defaultValue(Path.class);
        assertEquals(new File(System.getProperty("user.dir")).toPath(), result);
        result = Generators.nullableDefaultValue(Path.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Path.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Path.class, 100, 2);
    }

    /**
     * Test for {@code Calendar} generation.
     */
    @Test
    void testCalendarGeneration() {
        Calendar result;
        result = Generators.defaultValue(Calendar.class);
        final Calendar defaultValue = Calendar.getInstance();
        defaultValue.setTimeInMillis(0);
        assertEquals(defaultValue, result);
        result = Generators.nullableDefaultValue(Calendar.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Calendar.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Calendar.class, 100, 2);
    }

    /**
     * Test for {@code Currency} generation.
     */
    @Test
    void testCurrencyGeneration() {
        Currency result;
        result = Generators.defaultValue(Currency.class);
        assertNotNull(result);
        result = Generators.nullableDefaultValue(Currency.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Currency.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Currency.class, 100, 2);
    }

    /**
     * Test for {@code Date} generation.
     */
    @Test
    void testDateGeneration() {
        Date result;
        result = Generators.defaultValue(Date.class);
        assertEquals(new Date(0), result);
        result = Generators.nullableDefaultValue(Date.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Date.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Date.class, 100, 2);
    }

    /**
     * Test for {@code Locale} generation.
     */
    @Test
    void testLocaleGeneration() {
        Locale result;
        result = Generators.defaultValue(Locale.class);
        assertEquals(Locale.getDefault(), result);
        result = Generators.nullableDefaultValue(Locale.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Locale.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Locale.class, 100, 2);
    }

    /**
     * Test for {@code TimeZone} generation.
     */
    @Test
    void testTimeZoneGeneration() {
        TimeZone result;
        result = Generators.defaultValue(TimeZone.class);
        assertEquals(TimeZone.getDefault(), result);
        result = Generators.nullableDefaultValue(TimeZone.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(TimeZone.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(TimeZone.class, 100, 2);
    }

    /**
     * Test for {@code URI} generation.
     */
    @Test
    void testUriGeneration() {
        URI result;
        result = Generators.defaultValue(URI.class);
        assertEquals(URI.create("/"), result);
        result = Generators.nullableDefaultValue(URI.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(URI.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(URI.class, 100, 2);
    }

    /**
     * Test for {@code URL} generation.
     */
    @Test
    void testUrlGeneration()
    throws MalformedURLException {
        URL result;
        result = Generators.defaultValue(URL.class);
        assertEquals("http://localhost/", result.toString());
        result = Generators.nullableDefaultValue(URL.class);
        assertNull(result);
        // Slow generation
        GeneratorsTestUtils.assertRandomGeneration(URL.class, 10, 4);
        GeneratorsTestUtils.assertNullableRandomGeneration(URL.class, 10, 4);
    }

    /**
     * Test for {@code UUID} generation.
     */
    @Test
    void testUuidGeneration() {
        UUID result;
        result = Generators.defaultValue(UUID.class);
        assertEquals(UUID.fromString("00000000-0000-0000-00-000000000000"), result);
        result = Generators.nullableDefaultValue(UUID.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(UUID.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(UUID.class, 100, 2);
    }

    /**
     * Test for {@code Clock} generation.
     */
    @Test
    void testClockGeneration() {
        Clock result;
        result = Generators.defaultValue(Clock.class);
        assertEquals(Clock.systemUTC(), result);
        result = Generators.nullableDefaultValue(Clock.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Clock.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Clock.class, 100, 2);
    }

    /**
     * Test for {@code DayOfWeek} generation.
     */
    @Test
    void testDayOfWeekGeneration() {
        DayOfWeek result;
        result = Generators.defaultValue(DayOfWeek.class);
        assertEquals(DayOfWeek.MONDAY, result);
        result = Generators.nullableDefaultValue(DayOfWeek.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(DayOfWeek.class, 7, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(DayOfWeek.class, 7, 2);
    }

    /**
     * Test for {@code Duration} generation.
     */
    @Test
    void testDurationGeneration() {
        Duration result;
        result = Generators.defaultValue(Duration.class);
        assertEquals(Duration.ZERO, result);
        result = Generators.nullableDefaultValue(Duration.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Duration.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Duration.class, 100, 2);
    }

    /**
     * Test for {@code Instant} generation.
     */
    @Test
    void testInstantGeneration() {
        Instant result;
        result = Generators.defaultValue(Instant.class);
        assertEquals(Instant.EPOCH, result);
        result = Generators.nullableDefaultValue(Instant.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Instant.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Instant.class, 100, 2);
    }

    /**
     * Test for {@code LocalDate} generation.
     */
    @Test
    void testLocalDateGeneration() {
        LocalDate result;
        result = Generators.defaultValue(LocalDate.class);
        assertEquals(LocalDate.ofEpochDay(0), result);
        result = Generators.nullableDefaultValue(LocalDate.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(LocalDate.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(LocalDate.class, 100, 2);
    }

    /**
     * Test for {@code LocalDateTime} generation.
     */
    @Test
    void testLocalDateTimeGeneration() {
        LocalDateTime result;
        result = Generators.defaultValue(LocalDateTime.class);
        assertEquals(LocalDateTime.ofEpochSecond(0L, 0, ZoneOffset.UTC), result);
        result = Generators.nullableDefaultValue(LocalDateTime.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(LocalDateTime.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(LocalDateTime.class, 100, 2);
    }

    /**
     * Test for {@code LocalTime} generation.
     */
    @Test
    void testLocalTimeGeneration() {
        LocalTime result;
        result = Generators.defaultValue(LocalTime.class);
        assertEquals(LocalTime.ofNanoOfDay(0), result);
        result = Generators.nullableDefaultValue(LocalTime.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(LocalTime.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(LocalTime.class, 100, 2);
    }

    /**
     * Test for {@code Month} generation.
     */
    @Test
    void testMonthGeneration() {
        Month result;
        result = Generators.defaultValue(Month.class);
        assertEquals(Month.JANUARY, result);
        result = Generators.nullableDefaultValue(Month.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Month.class, 12, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Month.class, 12, 2);
    }

    /**
     * Test for {@code MonthDay} generation.
     */
    @Test
    void testMonthDayGeneration() {
        MonthDay result;
        result = Generators.defaultValue(MonthDay.class);
        assertEquals(MonthDay.of(1, 1), result);
        result = Generators.nullableDefaultValue(MonthDay.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(MonthDay.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(MonthDay.class, 100, 2);
    }

    /**
     * Test for {@code OffsetDateTime} generation.
     */
    @Test
    void testOffsetDateTimeGeneration() {
        OffsetDateTime result;
        result = Generators.defaultValue(OffsetDateTime.class);
        assertEquals(OffsetDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC), result);
        result = Generators.nullableDefaultValue(OffsetDateTime.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(OffsetDateTime.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(OffsetDateTime.class, 100, 2);
    }

    /**
     * Test for {@code OffsetTime} generation.
     */
    @Test
    void testOffsetTimeGeneration() {
        OffsetTime result;
        result = Generators.defaultValue(OffsetTime.class);
        assertEquals(OffsetTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC), result);
        result = Generators.nullableDefaultValue(OffsetTime.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(OffsetTime.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(OffsetTime.class, 100, 2);
    }

    /**
     * Test for {@code Period} generation.
     */
    @Test
    void testPeriodGeneration() {
        Period result;
        result = Generators.defaultValue(Period.class);
        assertEquals(Period.ofDays(0), result);
        result = Generators.nullableDefaultValue(Period.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Period.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Period.class, 100, 2);
    }

    /**
     * Test for {@code Year} generation.
     */
    @Test
    void testYearGeneration() {
        Year result;
        result = Generators.defaultValue(Year.class);
        assertEquals(Year.of(0), result);
        result = Generators.nullableDefaultValue(Year.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Year.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Year.class, 100, 2);
    }

    /**
     * Test for {@code YearMonth} generation.
     */
    @Test
    void testYearMonthGeneration() {
        YearMonth result;
        result = Generators.defaultValue(YearMonth.class);
        assertEquals(YearMonth.of(0, 1), result);
        result = Generators.nullableDefaultValue(YearMonth.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(YearMonth.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(YearMonth.class, 100, 2);
    }

    /**
     * Test for {@code ZonedDateTime} generation.
     */
    @Test
    void testZonedDateTimeGeneration() {
        ZonedDateTime result;
        result = Generators.defaultValue(ZonedDateTime.class);
        assertEquals(
                ZonedDateTime.of(
                    LocalDateTime.ofEpochSecond(0L, 0, ZoneOffset.UTC),
                    ZoneId.systemDefault()),
                result);
        result = Generators.nullableDefaultValue(ZonedDateTime.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(ZonedDateTime.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(ZonedDateTime.class, 100, 2);
    }

    /**
     * Test for {@code ZoneId} generation.
     */
    @Test
    void testZoneIdGeneration() {
        ZoneId result;
        result = Generators.defaultValue(ZoneId.class);
        assertEquals(ZoneId.systemDefault(), result);
        result = Generators.nullableDefaultValue(ZoneId.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(ZoneId.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(ZoneId.class, 100, 2);
    }

    /**
     * Test for {@code ZoneOffset} generation.
     */
    @Test
    void testZoneOffsetGeneration() {
        ZoneOffset result;
        result = Generators.defaultValue(ZoneOffset.class);
        assertEquals(ZoneOffset.UTC, result);
        result = Generators.nullableDefaultValue(ZoneOffset.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(ZoneOffset.class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(ZoneOffset.class, 100, 2);
    }

    /**
     * Test for {@code Chronology} generation.
     */
    @Test
    void testChronologyGeneration() {
        Chronology result;
        result = Generators.defaultValue(Chronology.class);
        assertEquals(Chronology.ofLocale(Locale.getDefault()), result);
        result = Generators.nullableDefaultValue(Chronology.class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Chronology.class, 5, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(Chronology.class, 5, 2);
    }

    /**
     * Test for {@code Array} generation.
     */
    @Test
    void testArrayGeneration() {
        int[] result;
        result = Generators.defaultValue(int[].class);
        assertNotNull(result);
        assertEquals(0, result.length);
        result = Generators.nullableDefaultValue(int[].class);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(int[].class, 100, 2);
        GeneratorsTestUtils.assertNullableRandomGeneration(int[].class, 100, 2);
    }

    /**
     * Test for {@code List} generation.
     */
    @Test
    void testListGeneration() {
        final SimpleGenericParameters params = GenerationParameters
                .forSimpleGenerics()
                .withElementsType(Integer.class);
        List<?> result;
        result = Generators.defaultValue(List.class, params);
        assertEquals(new ArrayList<Integer>(), result);
        result = Generators.nullableDefaultValue(List.class, params);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(List.class, 100, 2, params);
        GeneratorsTestUtils.assertNullableRandomGeneration(List.class, 100, 2, params);
    }

    /**
     * Test for {@code Collection} generation.
     */
    @Test
    void testCollectionGeneration() {
        final SimpleGenericParameters params = GenerationParameters
                .forSimpleGenerics()
                .withElementsType(Integer.class);
        Collection<?> result;
        result = Generators.defaultValue(Collection.class, params);
        assertEquals(new ArrayList<Integer>(), result);
        result = Generators.nullableDefaultValue(Collection.class, params);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Collection.class, 100, 2, params);
        GeneratorsTestUtils.assertNullableRandomGeneration(Collection.class, 100, 2, params);
    }

    /**
     * Test for {@code Set} generation.
     */
    @Test
    void testSetGeneration() {
        final SimpleGenericParameters params = GenerationParameters
                .forSimpleGenerics()
                .withElementsType(Integer.class);
        Set<?> result;
        result = Generators.defaultValue(Set.class, params);
        assertEquals(new HashSet<Integer>(), result);
        result = Generators.nullableDefaultValue(Set.class, params);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Set.class, 100, 2, params);
        GeneratorsTestUtils.assertNullableRandomGeneration(Set.class, 100, 2, params);
    }

    /**
     * Test for {@code Map} generation.
     */
    @Test
    void testMapGeneration() {
        final KeyValueGenericParameters params = GenerationParameters
                .forKeyValueGenerics()
                .withKeysType(Integer.class)
                .withValuesType(String.class);
        Map<?, ?> result;
        result = Generators.defaultValue(Map.class, params);
        assertEquals(new HashMap<Integer, String>(), result);
        result = Generators.nullableDefaultValue(Map.class, params);
        assertNull(result);
        GeneratorsTestUtils.assertRandomGeneration(Map.class, 100, 2, params);
        GeneratorsTestUtils.assertNullableRandomGeneration(Map.class, 100, 2, params);
    }
}
