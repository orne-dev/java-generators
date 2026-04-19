# Orne Java random value generator for tests - Changelog

## 0.3.0 - *Unreleased*

- **Maven coordinates change.**

### Changed

- Changed Maven coordinates from `dev.orne.test:generators` to `dev.orne.test:orne-test-generators`.

## dev.orne.test:generators - 0.2.0 - 2023-12-14

### Added

- Add support for retrieval of targeted generators based on `Field` and `Parameter`
    - `dev.orne.test.rnd.params.ParameterTypeGenerator` class
    - `ConstraintIntrospector.findParameterConstrains(Parameter, Class...)` method
    - `ConstraintIntrospector.findParameterConstrains(Validator, Parameter, Class...)` method
    - `Generators.forField(Field)` method
    - `Generators.forField(Class, Field)` method
    - `Generators.forParameter(Parameter)` method
- Add **experimental** JUnit 5 (Jupiter) random value injection extension
    - `dev.orne.test.rnd.junit.Random` annotation
    - `dev.orne.test.rnd.junit.RandomValueExtension` class

## dev.orne.test:generators - 0.1.3 - 2023-10-07

### Changed

- Improve `CharsetGenerator`.

    Prevent generation of charsets that don't support encoding.
    Causes unexpected random errors in multiple tests.
    If a test must validate the code behavior with decode-only charsets use
    `CharsetGenerator.randomDecodeOnlyValue()`.

## dev.orne.test:generators - 0.1.2 - 2023-09-09

### Fixed

- Fix `URIGenerator` maximum port number (65535).

## dev.orne.test:generators - 0.1.1 - 2022-12-11

### Fixed

- Fix `CurrencyGenerator` default value.

    Default locale may have not country code.
    Use "EUR" as default value in such cases.

## dev.orne.test:generators - 0.1.0 - 2022-12-10

### Added

- Add JPMS default module name `dev.orne.test.generators`.
- Add main API.
    - `dev.orne.test.rnd.GenerationException` exception
    - `dev.orne.test.rnd.UnsupportedValueTypeException` exception
    - `dev.orne.test.rnd.GeneratorNotFoundException` exception
    - `dev.orne.test.rnd.Priority` annotation
    - `dev.orne.test.rnd.Generator` interface
    - `dev.orne.test.rnd.AbstractGenerator` class
    - `dev.orne.test.rnd.TypedGenerator` interface
    - `dev.orne.test.rnd.AbstractTypedGenerator` class
    - `dev.orne.test.rnd.Generators` class
- Add **experimental** generation parameters system
    - `dev.orne.test.rnd.params.GenerationParameters` interface
    - `dev.orne.test.rnd.params.NullableParameters` interface
    - `dev.orne.test.rnd.params.NullableParametersImpl` class
    - `dev.orne.test.rnd.params.NumberParameters` interface
    - `dev.orne.test.rnd.params.NumberParametersImpl` class
    - `dev.orne.test.rnd.params.SizeParameters` interface
    - `dev.orne.test.rnd.params.SizeParametersImpl` class
    - `dev.orne.test.rnd.params.ParameterizableGenerator` interface
    - `dev.orne.test.rnd.params.TypedParameterizableGenerator` interface
    - `dev.orne.test.rnd.params.ParametersExtractor` interface
    - `dev.orne.test.rnd.params.ParametersSourceExtractor` interface
    - `dev.orne.test.rnd.params.AbstractParametersSourceExtractor` class
    - `dev.orne.test.rnd.params.DefaultParametersExtractor` class
    - `dev.orne.test.rnd.params.ParametersExtractors` class
    - `dev.orne.test.rnd.params.AbstractParameterizableGenerator` class
    - `dev.orne.test.rnd.params.AbstractTypedParameterizableGenerator` class
    - `dev.orne.test.rnd.params.GeneratorNotParameterizableException` exception
    - Add built-in parameter source extractors
        - `dev.orne.test.rnd.params.NullableParametersExtractor` class
        - `dev.orne.test.rnd.params.NotNullConstraintExtractor` class
        - `dev.orne.test.rnd.params.NumberParametersExtractor` class
        - `dev.orne.test.rnd.params.MinConstraintExtractor` class
        - `dev.orne.test.rnd.params.MaxConstraintExtractor` class
        - `dev.orne.test.rnd.params.PositiveConstraintExtractor` class
        - `dev.orne.test.rnd.params.PositiveOrZeroConstraintExtractor` class
        - `dev.orne.test.rnd.params.SizeParametersExtractor` class
        - `dev.orne.test.rnd.params.SizeConstraintExtractor` class
    - Add generic classes generation support
        - `dev.orne.test.rnd.params.TypeDeclaration` class
        - `dev.orne.test.rnd.params.SimpleGenericParameters` interface
        - `dev.orne.test.rnd.params.SimpleGenericParametersImpl` class
        - `dev.orne.test.rnd.params.SimpleGenericParametersExtractor` class
        - `dev.orne.test.rnd.params.SimpleGenericParametersTypeExtractor` class
        - `dev.orne.test.rnd.params.KeyValueGenericParameters` interface
        - `dev.orne.test.rnd.params.KeyValueGenericParametersImpl` class
        - `dev.orne.test.rnd.params.KeyValueGenericParametersExtractor` class
        - `dev.orne.test.rnd.params.KeyValueGenericParametersTypeExtractor` class
    - Add targeted generators system
        - `dev.orne.test.rnd.params.TargetedGenerator` interface
        - `dev.orne.test.rnd.params.ConstraintIntrospector` class
        - `dev.orne.test.rnd.params.AbstractTargetedGenerator` class
        - `dev.orne.test.rnd.params.PropertyTypeGenerator` class
        - `dev.orne.test.rnd.params.MethodReturnTypeGenerator` class
        - `dev.orne.test.rnd.params.MethodParameterTypeGenerator` class
        - `dev.orne.test.rnd.params.ConstructorParameterTypeGenerator` class
        - `dev.orne.test.rnd.params.ExecutableGenerator` class
        - `dev.orne.test.rnd.params.ConstructorGenerator` class
        - `dev.orne.test.rnd.params.FactoryMethodGenerator` class
- Add built-in generators
    - `dev.orne.test.rnd.generators.AbstractPrimitiveGenerator` class
    - `dev.orne.test.rnd.generators.BooleanGenerator` class
    - `dev.orne.test.rnd.generators.ByteGenerator` class
    - `dev.orne.test.rnd.generators.ShortGenerator` class
    - `dev.orne.test.rnd.generators.IntegerGenerator` class
    - `dev.orne.test.rnd.generators.LongGenerator` class
    - `dev.orne.test.rnd.generators.CharacterGenerator` class
    - `dev.orne.test.rnd.params.StringGenerationParameters` class
    - `dev.orne.test.rnd.generators.StringGenerator` class
    - `dev.orne.test.rnd.generators.BigIntegerGenerator` class
    - `dev.orne.test.rnd.generators.BigDecimalGenerator` class
    - `dev.orne.test.rnd.generators.DateGenerator` class
    - `dev.orne.test.rnd.generators.LocaleGenerator` class
    - `dev.orne.test.rnd.generators.CharsetGenerator` class
    - `dev.orne.test.rnd.generators.TimeZoneGenerator` class
    - `dev.orne.test.rnd.generators.CurrencyGenerator` class
    - `dev.orne.test.rnd.generators.CalendarGenerator` class
    - `dev.orne.test.rnd.generators.URIGenerator` class
    - `dev.orne.test.rnd.generators.URLGenerator` class
    - `dev.orne.test.rnd.generators.UUIDGenerator` class
    - `dev.orne.test.rnd.generators.ClockGeneratorTest` class
    - `dev.orne.test.rnd.generators.ChronologyGenerator` class
    - `dev.orne.test.rnd.generators.DurationGenerator` class
    - `dev.orne.test.rnd.generators.InstantGenerator` class
    - `dev.orne.test.rnd.generators.LocalDateGenerator` class
    - `dev.orne.test.rnd.generators.LocalDateTimeGenerator` class
    - `dev.orne.test.rnd.generators.LocalTimeGenerator` class
    - `dev.orne.test.rnd.generators.MonthDayGenerator` class
    - `dev.orne.test.rnd.generators.OffsetDateTimeGenerator` class
    - `dev.orne.test.rnd.generators.OffsetTimeGenerator` class
    - `dev.orne.test.rnd.generators.PeriodGenerator` class
    - `dev.orne.test.rnd.generators.YearGenerator` class
    - `dev.orne.test.rnd.generators.YearMonthGenerator` class
    - `dev.orne.test.rnd.generators.ZonedDateTimeGenerator` class
    - `dev.orne.test.rnd.generators.ZoneIdGenerator` class
    - `dev.orne.test.rnd.generators.ZoneOffsetGenerator` class
    - `dev.orne.test.rnd.generators.EnumGenerator` class
    - `dev.orne.test.rnd.generators.FileGenerator` class
    - `dev.orne.test.rnd.generators.PathGenerator` class
- Add collection generators
    - `dev.orne.test.rnd.generators.CollectionGeneratorUtils` class
    - `dev.orne.test.rnd.generators.ArrayGenerator` class
    - `dev.orne.test.rnd.params.CollectionGenerationParameters` class
    - `dev.orne.test.rnd.generators.ListGenerator` class
    - `dev.orne.test.rnd.generators.SetGenerator` class
    - `dev.orne.test.rnd.params.MapGenerationParameters` class
    - `dev.orne.test.rnd.generators.MapGenerator` class
- Add annotation based generation
    - `dev.orne.test.rnd.GeneratorMethod` annotation
    - `dev.orne.test.rnd.generators.AnnotatedMethodGenerator` class
