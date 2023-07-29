# Orne random value generators

Provides utilities for generation of random values.

## Status

[![License][status.license.badge]][status.license]
[![Latest version][status.maven.badge]][status.maven]
[![Javadoc][status.javadoc.badge]][javadoc]
[![Maven site][status.site.badge]][site]

| Latest Release | Develop |
| :------------: | :-------------: |
| [![Build Status][status.latest.ci.badge]][status.latest.ci] | [![Build Status][status.dev.ci.badge]][status.dev.ci] |
| [![Coverage][status.latest.cov.badge]][status.latest.cov] | [![Coverage][status.dev.cov.badge]][status.dev.cov] |

## Features

The library provides the following features (unchecked features are planned and
unimplemented):

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

## Usage

The binaries can be obtained from [Maven Central][status.maven] with the
`dev.orne.test:generators` coordinates:

```xml
<dependency>
  <groupId>dev.orne.test</groupId>
  <artifactId>generators</artifactId>
  <version>0.2.0</version>
</dependency>
```

To generate random values of a supported type just use the `Generators` class
static methods:

```java
String value = Generators.randomValue(String.class);
```

### Add support for new types

To add support for new types simply implement the `dev.orne.test.rnd.Generator`
interface and register the new implementations through SPI in a
`META-INF/services/dev.orne.test.rnd.Generator` file:

```
org.example.MyNewGenerator
org.example.AnotherNewGenerator
```

For simple type generators use the `dev.orne.test.rnd.AbstractTypedGenerator`
class:

```java
public class MyGenerator
extends AbstractTypedGenerator<MyType> {

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull MyType defaultValue() {
        return new MyType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull MyType randomValue() {
        final MyType instance = new MyType();
        // Generate random values for bean properties
        instance.setName(Generators.randomValue(String.class));
        return instance;
    }
}
```

### Overriding built-in types generation

Registered generators are ordered based on their optional
`dev.orne.test.rnd.Priority` annotation. To override a built-in generator
simply provide a new generator implementation with a higher priority than the
built-in generator.

```java
@Priority(Priority.DEFAULT)
public class MyImprovedGenerator ... {
    ...
}
```

All the built-in generators have a priority lower than the default priority, so
any registered generator without a priority annotation will have higher
priority than the built-in ones.

### Experimental features

The following features are in an experimental state and both API and
implementations may change in future versions.

#### Annotated method based generation

Automatic bean generation support can be added annotating a bean constructor
or factory method with the `dev.orne.test.rnd.GeneratorMethod` annotation:

```java
public class MyConstrutorBean {
    ...
    private String name;
    ...
    @GeneratorMethod
    public MyBean(String name) {
        this.name = name;
    }
    ...
}
public class MyFactoryBean {
    ...
    private String name;
    ...
    @GeneratorMethod
    public static MyBean factoryMethod(String name) {
        MyBean instance = new MyBean();
        instance.name = name;
        return instance;
    }
    ...
}
assertNotNull(Generators.defaultValue(MyConstrutorBean.class));
assertNotNull(Generators.randomValue(MyConstrutorBean.class));
assertNotNull(Generators.defaultValue(MyFactoryBean.class));
assertNotNull(Generators.randomValue(MyFactoryBean.class));
```

#### Generation parameters

Generators can support optional generation parameters implementing the
`dev.orne.test.rnd.params.ParameterizableGenerator` interface.
This allows fine-tuning the generated values:

```java
Generators.randomValue(
    String.class,
    GenerationParameters.forNullables().withNullable(false),
    GenerationParameters.forSizes().withMinSize(10));
Generators.randomValue(
    List.class,
    GenerationParameters.forSimpleGenerics().withElementsType(Integer.class),
    GenerationParameters.forSizes().withMaxSize(5));
```

Current built-in generation parameters:

| Type | Supported parameters types | Required parameters types |
| --- | --- | --- |
| java.lang.CharSequence | Nullable, Size |  |
| java.lang.String | Nullable, Size |  |
| java.lang.Collection | Nullable, Size | SimpleGenerics |
| java.lang.List | Nullable, Size | SimpleGenerics |
| java.lang.Set | Nullable, Size | SimpleGenerics |
| java.lang.Map | Nullable, Size | KeyValueGenerics |

#### Targeted generators

Targeted generators are generator instances that generate values for a target
usage. Generators targeting bean properties, constructor parameters, method
parameters and method return types are supported.

When a targeted generator generates values of a type whose registered generator
is a parameterizable generator uses the runtime information of the target to
populate the generation parameters. Currently the runtime `ParameterizedType`
of the target and validation constraints are supported.

```java
// Property
TargetedGenerator<String> generator = Generators.forProperty(
        TestType.class,
        "name")
String value = generator.randomValue(MyValidationGroup.class);
// Constructor parameter
TargetedGenerator<String> generator = Generators.forParameter(
        MyBean.class,
        new Class<?>[] { String.class },
        0)
String value = generator.randomValue(MyValidationGroup.class);
// Method parameter
TargetedGenerator<String> generator = Generators.forParameter(
        MyBean.class,
        "myMethod",
        new Class<?>[] { String.class },
        0)
String value = generator.randomValue(MyValidationGroup.class);
// Method return type
TargetedGenerator<String> generator = Generators.forReturnType(
        MyBean.class,
        "myMethod",
        new Class<?>[] { String.class })
String value = generator.randomValue(MyValidationGroup.class);
```

Extraction of generation parameters from runtime information is performed
through registered instances of
`dev.orne.test.rnd.params.ParametersSourceExtractor`.

See [Javadoc][javadoc] for implementation details.

## Further information

For further information refer to the [Javadoc][javadoc]
and [Maven Site][site].

[site]: https://orne-dev.github.io/java-generators/
[javadoc]: https://javadoc.io/doc/dev.orne.test/generators
[status.license]: http://www.gnu.org/licenses/gpl-3.0.txt
[status.license.badge]: https://img.shields.io/github/license/orne-dev/java-generators
[status.maven]: https://search.maven.org/artifact/dev.orne.test/generators
[status.maven.badge]: https://img.shields.io/maven-central/v/dev.orne.test/generators.svg?label=Maven%20Central
[status.javadoc.badge]: https://javadoc.io/badge2/dev.orne.test/generators/javadoc.svg
[status.site.badge]: https://img.shields.io/website?url=https%3A%2F%2Forne-dev.github.io%2Fjava-generators%2F
[status.latest.ci]: https://github.com/orne-dev/java-generators/actions/workflows/release.yml
[status.latest.ci.badge]: https://github.com/orne-dev/java-generators/actions/workflows/release.yml/badge.svg?branch=master
[status.latest.cov]: https://sonarcloud.io/dashboard?id=orne-dev_java-generators
[status.latest.cov.badge]: https://sonarcloud.io/api/project_badges/measure?project=orne-dev_java-generators&metric=coverage
[status.dev.ci]: https://github.com/orne-dev/java-generators/actions/workflows/build.yml
[status.dev.ci.badge]: https://github.com/orne-dev/java-generators/actions/workflows/build.yml/badge.svg?branch=develop
[status.dev.cov]: https://sonarcloud.io/dashboard?id=orne-dev_java-generators&branch=develop
[status.dev.cov.badge]: https://sonarcloud.io/api/project_badges/measure?project=orne-dev_java-generators&metric=coverage&branch=develop
