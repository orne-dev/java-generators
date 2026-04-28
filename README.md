# Orne test data random value generators

Provides utilities for generation of random values for testing purposes

**Warning:** This library is intended for testing purposes only and the
generated values must not be used in production code.

## Status

[![License][status.license.badge]][status.license]
[![Latest version][status.maven.badge]][status.maven]
[![Javadoc][status.javadoc.badge]][javadoc]
[![Maven site][status.site.badge]][site]

| Branch | CI Status | Quality | Coverage |
| :------------: | :-------------: | :-------------: | :-------------: |
| Main | [![Build Status][status.latest.ci.badge]][status.latest.ci] | [![Quality][status.sonar.quality.badge]][status.sonar] | [![Coverage][status.sonar.cov.badge]][status.sonar] |
| Develop | [![Build Status][status.dev.ci.badge]][status.dev.ci] | | |

## Features

The library provides the following features (unchecked features are planned and
unimplemented):

- [X] SPI generators discovery
    - [X] Annotation based generators ordering
- [X] Generators
    - [X] Typed generators
    - [X] Parameterizable generators
        - [X] Generic classes generation support 
        - [ ] JSpecify based parameters extractors
        - [X] Jakarta Validation 2.0 based parameters extractors
            - [X] `NotNull` extractor
            - [X] `Size` extractor
            - [X] `Min` extractor
            - [X] `Max` extractor
        - [ ] Jakarta Validation 3.0 based parameters extractors
            - [ ] `NotNull` extractor
            - [ ] `Size` extractor
            - [ ] `Min` extractor
            - [ ] `Max` extractor
        - [X] Parameterizable typed generators
        - [X] Targeted generators
            - [X] Property type generator
            - [X] Method argument type generator
            - [X] Method return type generator
            - [X] Constructor argument type generator
            - [X] Constructor based generator
            - [X] Factory method based generator
- [X] Primitives and wrapper types generators
    - [X] `java.lang.Boolean` generator
    - [X] `java.lang.Byte` generator
    - [X] `java.lang.Short` generator
    - [X] `java.lang.Integer` generator
    - [X] `java.lang.Long` generator
    - [X] `java.lang.Float` generator
    - [X] `java.lang.Double` generator
    - [X] `java.lang.Character` generator
- [X] Common types generators
    - [X] `java.lang.CharSequence` generator
    - [X] `java.lang.Number` generator
    - [X] `java.lang.String` generator
    - [X] `java.io.File` generator
    - [X] `java.math.BigInteger` generator
    - [X] `java.math.BigDecimal` generator
    - [X] `java.nio.charset.Charset` generator
    - [X] `java.nio.file.Path` generator
    - [X] `java.util.Calendar` generator
    - [X] `java.util.Currency` generator
    - [X] `java.util.Date` generator
    - [X] `java.util.Locale` generator
    - [ ] `java.util.Optional` generator
    - [X] `java.util.TimeZone` generator
    - [X] `java.util.URI` generator
    - [X] `java.util.URL` generator
    - [X] `java.util.UUID` generator
    - [X] `java.time.Clock` generator
    - [X] `java.time.Duration` generator
    - [X] `java.time.Instant` generator
    - [X] `java.time.LocalDate` generator
    - [X] `java.time.LocalDateTime` generator
    - [X] `java.time.LocalTime` generator
    - [X] `java.time.MonthDay` generator
    - [X] `java.time.OffsetDateTime` generator
    - [X] `java.time.OffsetTime` generator
    - [X] `java.time.Period` generator
    - [X] `java.time.Year` generator
    - [X] `java.time.YearMonth` generator
    - [X] `java.time.ZonedDateTime` generator
    - [X] `java.time.ZoneId` generator
    - [X] `java.time.ZoneOffset` generator
    - [X] `java.time.chrono.Chronology` generator
- [X] Arrays and collections generators
    - [X] `java.lang.Array` generator
    - [X] `java.util.Collection` generator
    - [X] `java.util.List` generator
    - [X] `java.util.Set` generator
    - [X] `java.util.Map` generator
- [ ] General generators
    - [X] Enumerations generator
    - [X] Annotated constructor based bean generator
    - [X] Annotated method based bean generator
    - [ ] Annotated properties based bean generator
    - [ ] Introspection based bean generator
- [X] JUnit 5 extension
    - [X] Annotation based test values generation

## Usage

The binaries can be obtained from [Maven Central][status.maven] with the
`dev.orne.test:orne-test-generators` coordinates:

```xml
<dependency>
  <groupId>dev.orne.test</groupId>
  <artifactId>orne-test-generators</artifactId>
  <version>0.3.0</version>
</dependency>
```

## Further information

For further information refer to the [Maven Site][site] and [Javadoc][javadoc].

[site]: https://orne-dev.github.io/java-generators/
[javadoc]: https://javadoc.io/doc/dev.orne.test/orne-test-generators
[status.license]: https://www.gnu.org/licenses/gpl-3.0.txt
[status.license.badge]: https://img.shields.io/github/license/orne-dev/java-generators
[status.maven]: https://central.sonatype.com/artifact/dev.orne.test/orne-test-generators
[status.maven.badge]: https://img.shields.io/maven-central/v/dev.orne.test/orne-test-generators.svg?label=Maven%20Central
[status.javadoc.badge]: https://javadoc.io/badge2/dev.orne.test/orne-test-generators/javadoc.svg
[status.site.badge]: https://img.shields.io/website?url=https%3A%2F%2Forne-dev.github.io%2Fjava-generators%2F
[status.latest.ci]: https://github.com/orne-dev/java-generators/actions/workflows/release.yml
[status.latest.ci.badge]: https://github.com/orne-dev/java-generators/actions/workflows/release.yml/badge.svg?branch=master
[status.dev.ci]: https://github.com/orne-dev/java-generators/actions/workflows/build.yml
[status.dev.ci.badge]: https://github.com/orne-dev/java-generators/actions/workflows/build.yml/badge.svg?branch=develop
[status.sonar]: https://sonarcloud.io/dashboard?id=orne-dev_java-generators
[status.sonar.quality.badge]: https://sonarcloud.io/api/project_badges/measure?project=orne-dev_java-generators&metric=alert_status
[status.sonar.cov.badge]: https://sonarcloud.io/api/project_badges/measure?project=orne-dev_java-generators&metric=coverage
