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
- [ ] Generators
    - [ ] TypedGenerators
    - [ ] ParametrizableGenerators
    - [ ] ParametrizableTypedGenerators
- [ ] Javax Validation 2.1 based parametrizable generators
- [ ] Jakarta Validation 3.0 based parametrizable generators
- [ ] Java primitives and wrapper types generators
    - [ ] `java.lang.Boolean` generator
    - [ ] `java.lang.Character` generator
    - [ ] `java.lang.Byte` generator
    - [ ] `java.lang.Short` generator
    - [ ] `java.lang.Integer` generator
    - [ ] `java.lang.Long` generator
    - [ ] `java.lang.Float` generator
    - [ ] `java.lang.Double` generator
- [ ] `java.lang.Number` generator
- [ ] `java.lang.String` and `java.lang.CharSequence` generator
- [ ] Enumerations generator
- [ ] `java.io.File` generator
- [ ] `java.math.BigInteger` generator
- [ ] `java.math.BigDecimal` generator
- [ ] `java.nio.charset.Charset` generator
- [ ] `java.nio.file.Path` generator
- [ ] `java.util.Calendar` generator
- [ ] `java.util.Currency` generator
- [ ] `java.util.Date` generator
- [ ] `java.util.Locale` generator
- [ ] `java.util.TimeZone` generator
- [ ] `java.util.URI` generator
- [ ] `java.util.UUID` generator
- [ ] `java.time` generators
    - [ ] `java.time.Clock` generator
    - [ ] `java.time.Duration` generator
    - [ ] `java.time.Instant` generator
    - [ ] `java.time.LocalDate` generator
    - [ ] `java.time.LocalDateTime` generator
    - [ ] `java.time.LocalTime` generator
    - [ ] `java.time.MonthDay` generator
    - [ ] `java.time.OffsetDateTime` generator
    - [ ] `java.time.OffsetTime` generator
    - [ ] `java.time.Period` generator
    - [ ] `java.time.Year` generator
    - [ ] `java.time.YearMonth` generator
    - [ ] `java.time.ZonedDateTime` generator
    - [ ] `java.time.ZoneId` generator
    - [ ] `java.time.ZoneOffset` generator
    - [ ] `java.time.chrono.Chronology` generator
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
