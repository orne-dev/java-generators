# Random values generation for testing purposes

## Random values generation

The most basic value generation requires only passing the value type to the
`Generators.randomValue()` method:

```java
String value = Generators.randomValue(String.class);
assertNotNull(value);
```

If null values are acceptable call `Generators.nullableRandomValue()` method:

```java
String value = Generators.nullableRandomValue(String.class);
// The generated value can be null or a random value
```

### Parameterized values generation

Generators can support optional generation parameters implementing the
`dev.orne.test.rnd.params.ParameterizableGenerator` interface.
This allows fine-tuning the generated values:

```java
String value = Generators.randomValue(
    String.class,
    GenerationParameters.forNullables().withNullable(false),
    GenerationParameters.forSizes().withMinSize(10));
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

Collections generation requires, at least, the generic types parameters to
success: 

```java
List<String> list = Generators.randomValue(
    List.class,
    GenerationParameters.forSimpleGenerics().withElementsType(String.class));
Map<String, Locale> map = Generators.randomValue(
    Map.class,
    GenerationParameters.forKeyValueGenerics()
        .withKeysType(String.class)
        .withValuesType(Locale.class),
    GenerationParameters.forSizes().withMaxSize(5));
Map<String, Map<Integer, String>> nestedMap = Generators.randomValue(
    Map.class,
    GenerationParameters.forKeyValueGenerics()
        .withKeysType(String.class)
        .withValuesType(TypeUtils.parameterize(Map.class, Integer.class, String.class)),
    GenerationParameters.forSizes().withMaxSize(5));
```

### Targeted value generation

Targeted generators are generator instances that generate values for a target
usage. Generators targeting bean properties, constructor parameters, method
parameters and method return types are supported.

When a targeted generator generates values of a type whose registered generator
is a parameterizable generator uses the runtime information of the target to
populate the generation parameters. Currently the runtime `ParameterizedType`
of the target and following validation constraints are supported:

- `javax.validation.constraints.NotNull`
- `javax.validation.constraints.Size`
- `javax.validation.constraints.Min`
- `javax.validation.constraints.Max`
- `javax.validation.constraints.PositiveOrZero`
- `javax.validation.constraints.Positive`

Examples:

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

### Annotation based random values generation in JUnit 5 tests

The library provides a JUnit 5 extension to inject random values based in
targeted value generation system. Add the `@Random` annotation to any field,
constructor parameter, test life-cycle callback parameter, or test method
parameter.

See [targeted value generation](#targeted-value-generation) and
[parameterized values generation](#parameterized-values-generation) for
supported generation parameters extraction.

```java
class MyTest {

    // Generated on @BeforeAll phase
    private static @Random Year staticNullableValue;
    // Generated on @BeforeAll phase
    private static @Random @NotNull Year staticNonNullValue;
    // Generated once per @BeforeEach phase
    private @Random Year instanceNullableValue;
    // Generated once per @BeforeEach phase
    private @Random @NotNull Year instanceNonNullValue;

    @BeforeAll
    static void beforeAllCallback(
            // Generated on invocation
            @Random String nullableValue,
            @Random @NotNull String nonNullValue) {
        assertNotNull(nonNullValue);
        assertNotNull(staticNonNullValue);
    }

    @AfterAll
    static void afterAllCallback(
            // Generated on invocation
            @Random String nullableValue,
            @Random @NotNull String nonNullValue) {
        assertNotNull(nonNullValue);
    }

    @BeforeEach
    void beforeEachCallback(
            // Generated on invocation
            @Random String nullableValue,
            @Random @NotNull String nonNullValue) {
        assertNotNull(nonNullValue);
        assertNotNull(instanceNonNullValue);
    }

    @AfterEach
    void afterEachCallback(
            // Generated on invocation
            @Random String nullableValue,
            @Random @NotNull String nonNullValue) {
        assertNotNull(nonNullValue);
        instanceValue = null;
    }

    RandomValueExtensionTest(
            // Generated on class instantation
            @Random String nullableValue,
            @Random @NotNull String nonNullValue) {
        assertNotNull(nonNullValue);
    }

    @Test
    void testSomething(
            // Generated once per invocation
            @Random String nullableValue,
            @Random @NotNull String nonNullValue) {
        assertNotNull(nonNullValue);
    }
}
```

## Supported out-of-the-box types

- Primitives and wrapper types
    - `java.lang.Boolean`
    - `java.lang.Byte`
    - `java.lang.Short`
    - `java.lang.Integer`
    - `java.lang.Long`
    - `java.lang.Float`
    - `java.lang.Double`
    - `java.lang.Character`
- Common JRE types
    - `java.lang.CharSequence`
    - `java.lang.Number`
    - `java.lang.String`
    - `java.io.File`
    - `java.math.BigInteger`
    - `java.math.BigDecimal`
    - `java.nio.charset.Charset`
    - `java.nio.file.Path`
    - `java.util.Calendar`
    - `java.util.Currency`
    - `java.util.Date`
    - `java.util.Locale`
    - `java.util.TimeZone`
    - `java.util.URI`
    - `java.util.URL`
    - `java.util.UUID`
    - `java.time.Clock`
    - `java.time.Duration`
    - `java.time.Instant`
    - `java.time.LocalDate`
    - `java.time.LocalDateTime`
    - `java.time.LocalTime`
    - `java.time.MonthDay`
    - `java.time.OffsetDateTime`
    - `java.time.OffsetTime`
    - `java.time.Period`
    - `java.time.Year`
    - `java.time.YearMonth`
    - `java.time.ZonedDateTime`
    - `java.time.ZoneId`
    - `java.time.ZoneOffset`
    - `java.time.chrono.Chronology`
- Arrays and collections
    - `java.lang.Array`
    - `java.util.Collection`
    - `java.util.List`
    - `java.util.Set`
    - `java.util.Map`
- Enumerations
- Annotated beans
    - Annotated constructor based bean generator
    - Annotated method based bean generator

## Generic beans generation

Not every bean type requires a dedicated generator. 
For simple bean types annotation based generation is supported.

### Annotated constructor based bean generation

Beans with a constructor annotated with `@GeneratorMethod` will be generated
out-of-the-box calling the annotated constructor with targeted random arguments:

```java
public class MyType {
    @GeneratorMethod
    public MyType(
            Integer paramA,
            String paramB) {
        super();
        // Populate bean
    }
}
```

### Annotated method based bean generation

Alternatively, beans can annotate a factory method with `@GeneratorMethod` will
be generated out-of-the-box calling the annotated method with targeted random
arguments:

```java
public class MyType {
    @GeneratorMethod
    public static @NotNull MyType factory(
            Integer paramA,
            String paramB) {
        MyType bean = new MyType();
        // Populate bean
        return bean;
    }
}
```
