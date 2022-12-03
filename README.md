# Orne random value generators

Provides utilities for generation of random values.

## Status

[![License][status.license.badge]][status.license]
[![Latest version][status.maven.badge]][status.maven]
[![Javadoc][status.javadoc.badge]][status.javadoc]
[![Maven site][status.site.badge]][status.site.site]

| Latest Release | Develop |
| :------------: | :-------------: |
| [![Build Status][status.latest.ci.badge]][status.latest.ci] | [![Build Status][status.dev.ci.badge]][status.dev.ci] |
| [![Coverage][status.latest.cov.badge]][status.latest.cov] | [![Coverage][status.dev.cov.badge]][status.dev.cov] |

## Features provided (or planned)

- [X] SPI generators discovery
    - [X] Annotation based generators ordering
- [X] Generators
    - [X] Typed generators
    - [X] Parameterizable generators
        - [X] Generic classes generation support 
        - [X] Java Validation 2.1 based parameters extractors
            - [X] `NotNull` extractor
            - [X] `Size` extractor
            - [X] `Min` extractor
            - [X] `Max` extractor
        - [X] Parameterizable typed generators
        - [X] Targeted generators
                - [X] Property type generator
                - [X] Method argument type generator
                - [X] Method return type generator
                - [X] Constructor argument type generator
- [X] Primitives and wrapper types generators
    - [X] `java.lang.Boolean` generator
    - [X] `java.lang.Byte` generator
    - [X] `java.lang.Short` generator
    - [X] `java.lang.Integer` generator
    - [X] `java.lang.Long` generator
    - [X] `java.lang.Float` generator
    - [X] `java.lang.Double` generator
    - [X] `java.lang.Character` generator
- [X] Arrays and collections generators
    - [X] `java.lang.Array` generator
    - [X] `java.util.Collection` generator
    - [X] `java.util.List` generator
    - [X] `java.util.Set` generator
    - [X] `java.util.Map` generator
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
- [ ] General generators
    - [X] Enumerations generator
    - [ ] Annotation based bean generator
    - [ ] Proxyed interface generator
    - [ ] Introspection based bean generator

## Usage and further information

The binaries can be obtained from [Maven Central][status.maven] with the
`dev.orne:rnd-generators` coordinates:

```xml
<dependency>
  <groupId>dev.orne.test</groupId>
  <artifactId>generators</artifactId>
  <version>0.1.0</version>
</dependency>
```

To generate random values of a supported type just use the `Generators` class
static methods:

```java
String value = Generators.randomValue(String.class);
```

For further information refer to the [project Wiki][wiki]
and [Maven Site][site].

[site]: https://orne-dev.github.io/java-generators/
[wiki]: https://github.com/orne-dev/java-generators/wiki
[status.license]: http://www.gnu.org/licenses/gpl-3.0.txt
[status.license.badge]: https://img.shields.io/github/license/orne-dev/java-test-generators
[status.maven]: https://search.maven.org/artifact/dev.orne.test/generators
[status.maven.badge]: https://img.shields.io/maven-central/v/dev.orne.test/generators.svg?label=Maven%20Central
[status.javadoc]: https://javadoc.io/doc/dev.orne.test/generators
[status.javadoc.badge]: https://javadoc.io/badge2/dev.orne.test/generators/javadoc.svg
[status.site]: https://orne-dev.github.io/java-test-generators/
[status.site.badge]: https://img.shields.io/website?url=https%3A%2F%2Forne-dev.github.io%2Fjava-test-generators%2F
[status.latest.ci]: https://github.com/orne-dev/java-generators/actions/workflows/release.yml
[status.latest.ci.badge]: https://github.com/orne-dev/java-test-generators/actions/workflows/release.yml/badge.svg?branch=master
[status.latest.cov]: https://sonarcloud.io/dashboard?id=dev.orne.test%3Agenerators
[status.latest.cov.badge]: https://sonarcloud.io/api/project_badges/measure?project=dev.orne.test%3Agenerators&metric=coverage
[status.dev.ci]: https://github.com/orne-dev/java-test-generators/actions/workflows/build.yml
[status.dev.ci.badge]: https://github.com/orne-dev/java-test-generators/actions/workflows/build.yml/badge.svg?branch=develop
[status.dev.cov]: https://sonarcloud.io/dashboard?id=dev.orne.test%3Agenerators&branch=develop
[status.dev.cov.badge]: https://sonarcloud.io/api/project_badges/measure?project=dev.orne.test%3Agenerators&metric=coverage&branch=develop
