# :package: 0.1.1

01. :bug: Fixed `CurrencyGenerator` default value.

    Default locale may have not country code.
    Use "EUR" as default value in such cases.

# :package: 0.1.0

01. :gift: Added exception `dev.orne.test.rnd.GenerationException`
01. :gift: Added exception `dev.orne.test.rnd.UnsupportedValueTypeException`
01. :gift: Added annotation `dev.orne.test.rnd.Priority`
01. :gift: Added interface `dev.orne.test.rnd.Generator`
01. :gift: Added class `dev.orne.test.rnd.AbstractGenerator`
01. :gift: Added interface `dev.orne.test.rnd.TypedGenerator`
01. :gift: Added class `dev.orne.test.rnd.AbstractTypedGenerator`
01. :gift: Added exception `dev.orne.test.rnd.GeneratorNotFoundException`
01. :gift: Added class `dev.orne.test.rnd.Generators`
01. :gift: Added **experimental** generation parameters system
    01. Added interface `dev.orne.test.rnd.params.GenerationParameters`
    01. Added interface `dev.orne.test.rnd.params.NullableParameters`
    01. Added class `dev.orne.test.rnd.params.NullableParametersImpl`
    01. Added interface `dev.orne.test.rnd.params.NumberParameters`
    01. Added class `dev.orne.test.rnd.params.NumberParametersImpl`
    01. Added interface `dev.orne.test.rnd.params.SizeParameters`
    01. Added class `dev.orne.test.rnd.params.SizeParametersImpl`
    01. Added interface `dev.orne.test.rnd.params.ParameterizableGenerator`
    01. Added interface `dev.orne.test.rnd.params.TypedParameterizableGenerator`
    01. Added interface `dev.orne.test.rnd.params.ParametersExtractor`
    01. Added interface `dev.orne.test.rnd.params.ParametersSourceExtractor`
    01. Added class `dev.orne.test.rnd.params.AbstractParametersSourceExtractor`
    01. Added class `dev.orne.test.rnd.params.DefaultParametersExtractor`
    01. Added class `dev.orne.test.rnd.params.ParametersExtractors`
    01. Added class `dev.orne.test.rnd.params.AbstractParameterizableGenerator`
    01. Added class `dev.orne.test.rnd.params.AbstractTypedParameterizableGenerator`
    01. Added exception `dev.orne.test.rnd.params.GeneratorNotParameterizableException`
    01. Added built-in parameter source extractors
        01. Added class `dev.orne.test.rnd.params.NullableParametersExtractor`
        01. Added class `dev.orne.test.rnd.params.NotNullConstraintExtractor`
        01. Added class `dev.orne.test.rnd.params.NumberParametersExtractor`
        01. Added class `dev.orne.test.rnd.params.MinConstraintExtractor`
        01. Added class `dev.orne.test.rnd.params.MaxConstraintExtractor`
        01. Added class `dev.orne.test.rnd.params.PositiveConstraintExtractor`
        01. Added class `dev.orne.test.rnd.params.PositiveOrZeroConstraintExtractor`
        01. Added class `dev.orne.test.rnd.params.SizeParametersExtractor`
        01. Added class `dev.orne.test.rnd.params.SizeConstraintExtractor`
    01. Added generic classes generation support
        01. Added class `dev.orne.test.rnd.params.TypeDeclaration`
        01. Added interface `dev.orne.test.rnd.params.SimpleGenericParameters`
        01. Added class `dev.orne.test.rnd.params.SimpleGenericParametersImpl`
        01. Added class `dev.orne.test.rnd.params.SimpleGenericParametersExtractor`
        01. Added class `dev.orne.test.rnd.params.SimpleGenericParametersTypeExtractor`
        01. Added interface `dev.orne.test.rnd.params.KeyValueGenericParameters`
        01. Added class `dev.orne.test.rnd.params.KeyValueGenericParametersImpl`
        01. Added class `dev.orne.test.rnd.params.KeyValueGenericParametersExtractor`
        01. Added class `dev.orne.test.rnd.params.KeyValueGenericParametersTypeExtractor`
    01. Added targeted generators system
        01. Added interface `dev.orne.test.rnd.params.TargetedGenerator`
        01. Added class `dev.orne.test.rnd.params.ConstraintIntrospector`
        01. Added class `dev.orne.test.rnd.params.AbstractTargetedGenerator`
        01. Added class `dev.orne.test.rnd.params.PropertyTypeGenerator`
        01. Added class `dev.orne.test.rnd.params.MethodReturnTypeGenerator`
        01. Added class `dev.orne.test.rnd.params.MethodParameterTypeGenerator`
        01. Added class `dev.orne.test.rnd.params.ConstructorParameterTypeGenerator`
        01. Added class `dev.orne.test.rnd.params.ExecutableGenerator`
        01. Added class `dev.orne.test.rnd.params.ConstructorGenerator`
        01. Added class `dev.orne.test.rnd.params.FactoryMethodGenerator`
01. :gift: Added built-in generators
    01. Added class `dev.orne.test.rnd.generators.AbstractPrimitiveGenerator`
    01. Added class `dev.orne.test.rnd.generators.BooleanGenerator`
    01. Added class `dev.orne.test.rnd.generators.ByteGenerator`
    01. Added class `dev.orne.test.rnd.generators.ShortGenerator`
    01. Added class `dev.orne.test.rnd.generators.IntegerGenerator`
    01. Added class `dev.orne.test.rnd.generators.LongGenerator`
    01. Added class `dev.orne.test.rnd.generators.CharacterGenerator`
    01. Added class `dev.orne.test.rnd.params.StringGenerationParameters`
    01. Added class `dev.orne.test.rnd.generators.StringGenerator`
    01. Added class `dev.orne.test.rnd.generators.BigIntegerGenerator`
    01. Added class `dev.orne.test.rnd.generators.BigDecimalGenerator`
    01. Added class `dev.orne.test.rnd.generators.DateGenerator`
    01. Added class `dev.orne.test.rnd.generators.LocaleGenerator`
    01. Added class `dev.orne.test.rnd.generators.CharsetGenerator`
    01. Added class `dev.orne.test.rnd.generators.TimeZoneGenerator`
    01. Added class `dev.orne.test.rnd.generators.CurrencyGenerator`
    01. Added class `dev.orne.test.rnd.generators.CalendarGenerator`
    01. Added class `dev.orne.test.rnd.generators.URIGenerator`
    01. Added class `dev.orne.test.rnd.generators.URLGenerator`
    01. Added class `dev.orne.test.rnd.generators.UUIDGenerator`
    01. Added class `dev.orne.test.rnd.generators.ClockGeneratorTest`
    01. Added class `dev.orne.test.rnd.generators.ChronologyGenerator`
    01. Added class `dev.orne.test.rnd.generators.DurationGenerator`
    01. Added class `dev.orne.test.rnd.generators.InstantGenerator`
    01. Added class `dev.orne.test.rnd.generators.LocalDateGenerator`
    01. Added class `dev.orne.test.rnd.generators.LocalDateTimeGenerator`
    01. Added class `dev.orne.test.rnd.generators.LocalTimeGenerator`
    01. Added class `dev.orne.test.rnd.generators.MonthDayGenerator`
    01. Added class `dev.orne.test.rnd.generators.OffsetDateTimeGenerator`
    01. Added class `dev.orne.test.rnd.generators.OffsetTimeGenerator`
    01. Added class `dev.orne.test.rnd.generators.PeriodGenerator`
    01. Added class `dev.orne.test.rnd.generators.YearGenerator`
    01. Added class `dev.orne.test.rnd.generators.YearMonthGenerator`
    01. Added class `dev.orne.test.rnd.generators.ZonedDateTimeGenerator`
    01. Added class `dev.orne.test.rnd.generators.ZoneIdGenerator`
    01. Added class `dev.orne.test.rnd.generators.ZoneOffsetGenerator`
    01. Added class `dev.orne.test.rnd.generators.EnumGenerator`
    01. Added class `dev.orne.test.rnd.generators.FileGenerator`
    01. Added class `dev.orne.test.rnd.generators.PathGenerator`
01. :gift: Added collection generators
    01. Added class `dev.orne.test.rnd.generators.CollectionGeneratorUtils`
    01. Added class `dev.orne.test.rnd.generators.ArrayGenerator`
    01. Added class `dev.orne.test.rnd.params.CollectionGenerationParameters`
    01. Added class `dev.orne.test.rnd.generators.ListGenerator`
    01. Added class `dev.orne.test.rnd.generators.SetGenerator`
    01. Added class `dev.orne.test.rnd.params.MapGenerationParameters`
    01. Added class `dev.orne.test.rnd.generators.MapGenerator`
01. :gift: Added annotation based generation
    01. Added annotation `dev.orne.test.rnd.GeneratorMethod`
    01. Added class `dev.orne.test.rnd.generators.AnnotatedMethodGenerator`
