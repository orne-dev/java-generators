# Internal implementation technical details

## Generators

The generators system provide abstract implementations for generic
(based on received target class) and typed generation, both simple and
parameterizable:

```mermaid
classDiagram
    direction LR
    class Generator {
        <<interface>>
        supports(Class~?~ type) boolean
        defaultValue(Class~T~ type) T
        nullableDefaultValue(Class~T~ type) T
        randomValue(Class~T~ type) T
        nullableRandomValue(Class~T~ type) T
        getPriority() int
        asParameterizable() ParameterizableGenerator
    }
    class TypedGenerator~T~ {
        <<interface>>
        defaultValue() T
        nullableDefaultValue() T
        randomValue() T
        nullableRandomValue() T
    }
    class ParameterizableGenerator {
        <<interface>>
        defaultValue(Class~T~ type, Object[] params) T
        nullableDefaultValue(Class~T~ type, Object[] params) T
        randomValue(Class~T~ type, Object[] params) T
        nullableRandomValue(Class~T~ type, Object[] params) T
    }
    class TypedParameterizableGenerator~T~ {
        <<interface>>
        defaultValue(Object[] params) T
        nullableDefaultValue(Object[] params) T
        randomValue(Object[] params) T
        nullableRandomValue(Object[] params) T
    }
    class AbstractGenerator {
        <<abstract>>
        defaultValue(Class~T~ type) T*
        nullableDefaultValue(Class~T~ type) T
        randomValue(Class~T~ type) T*
        nullableRandomValue(Class~T~ type) T
        getNullProbability() float
        setNullProbability(float prob)
        randomNull(Class~?~ type) boolean
    }
    class AbstractTypedGenerator~T~ {
        <<abstract>>
        getValueType() Class~T~
        supports(Class~?~ type) boolean
        defaultValue(Class~V~ type) V
        defaultValue() T*
        nullableDefaultValue() T
        randomValue(Class~V~ type) V
        randomValue() T*
        nullableRandomValue() T
    }
    class AbstractParameterizableGenerator~P extends GenerationParameters~ {
        <<abstract>>
        getParametersType() Class~P~
        getExtractor() ParametersExtractor~P~
        supports(Class~?~ type) boolean*
        defaultValue(Class~T~ type) T
        defaultValue(Class~T~ type, Object[] params) T
        defaultValue(Class~T~ type, P parameters) T*
        nullableDefaultValue(Class~T~ type) T
        nullableDefaultValue(Class~T~ type, Object[] params) T
        nullableDefaultValue(Class~T~ type, P parameters) T
        randomValue(Class~T~ type) T
        randomValue(Class~T~ type, Object[] params) T
        randomValue(Class~T~ type, P parameters) T*
        nullableRandomValue(Class~T~ type) T
        nullableRandomValue(Class~T~ type, Object[] params) T
        nullableRandomValue(Class~T~ type, P parameters) T
        createEmptyParams() P
        createParams(Object[] sources) P
    }
    class AbstractTypedParameterizableGenerator~T, P extends GenerationParameters~ {
        <<abstract>>
        getValueType() Class~T~
        supports(Class~?~ type) boolean
        defaultValue() T
        defaultValue(Object[] params) T
        defaultValue(Class~V~ type, P parameters) V
        defaultValue(P parameters) T*
        nullableDefaultValue() T
        nullableDefaultValue(Object[] params) T
        nullableDefaultValue(P parameters) T*
        randomValue() T
        randomValue(Object[] params) T
        randomValue(Class~V~ type, P parameters) V
        randomValue(P parameters) T*
        nullableRandomValue() T
        nullableRandomValue(Object[] params) T
        nullableRandomValue(P parameters) T*
    }
    Generator <|-- TypedGenerator
    Generator <|.. AbstractGenerator
    TypedGenerator <|-- TypedParameterizableGenerator
    TypedGenerator <|.. AbstractTypedGenerator
    AbstractGenerator <|-- AbstractTypedGenerator
    Generator <|-- ParameterizableGenerator
    ParameterizableGenerator <|.. AbstractParameterizableGenerator
    AbstractGenerator <|-- AbstractParameterizableGenerator
    ParameterizableGenerator <|-- TypedParameterizableGenerator
    TypedParameterizableGenerator <|.. AbstractTypedParameterizableGenerator
    AbstractParameterizableGenerator <|-- AbstractTypedParameterizableGenerator
```
