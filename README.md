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

- [ ] Identities
- [ ] References
- [ ] Javax Validation 2.1 support
- [ ] Jakarta Validation 3.0 support
- [ ] Jackson support
- [ ] JAXB support
- [ ] Spring Data Binding support
- [ ] Apache Commons BeanUtils converters

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

For further information refer to the [project Wiki][wiki]
and [Maven Site][site].

[site]: https://orne-dev.github.io/java-test-generators/
[wiki]: https://github.com/orne-dev/java-test-generators/wiki
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
