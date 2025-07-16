# Proyecto Flux y Mono - Programación Reactiva

Este proyecto demuestra el uso de **Mono** y **Flux** con **Project Reactor** para programación reactiva en Java usando **Gradle 8.2**.

## Estructura del Proyecto

```
fluxymono/
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   ├── FluxYMonoMain.java       # Clase principal con todas las actividades
│   │   │   └── EjemplosAdicionales.java # Ejemplos adicionales
│   │   └── resources/
│   │       └── logback.xml              # Configuración de logging
│   └── test/
│       └── java/com/example/
│           └── FluxYMonoTest.java       # Tests unitarios
├── gradle/
│   └── wrapper/                         # Gradle wrapper
├── build.gradle                         # Configuración Gradle
├── settings.gradle                      # Configuración del proyecto
├── gradlew                              # Script Gradle para Unix/Linux
├── gradlew.bat                          # Script Gradle para Windows
└── README.md                            # Este archivo
```

## Dependencias

- **Project Reactor Core**: Para Mono y Flux
- **SLF4J + Logback**: Para logging
- **JUnit 5**: Para tests unitarios
- **Reactor Test**: Para testing reactivo

## Actividades Implementadas

### 1. Flux con Filter y Map
Crea un Flux que emite números del 1 al 10, filtra solo los números pares y los multiplica por 2.

```java
Flux.range(1, 10)
    .filter(numero -> numero % 2 == 0)  // Solo pares
    .map(numero -> numero * 2)          // Multiplicar por 2
    .subscribe(resultado -> System.out.println("Resultado: " + resultado));
```

**Salida esperada:** 4, 8, 12, 16, 20

### 2. Flux con Reduce
Crea un Flux que emite números del 1 al 10 y usa reduce para calcular la suma total.

```java
Flux.range(1, 10)
    .reduce(Integer::sum)
    .subscribe(suma -> System.out.println("Suma total: " + suma));
```

**Salida esperada:** 55

### 3. Mono con Manejo de Errores
Crea un Mono que simula una operación que puede fallar (división por cero) y maneja el error.

```java
Mono.fromCallable(() -> {
    if (divisor == 0) {
        throw new ArithmeticException("División por cero no permitida");
    }
    return dividendo / divisor;
})
.onErrorResume(ArithmeticException.class, error -> Mono.just(-1));
```

### 4. Análisis de Código Complejo
Explica el comportamiento del código proporcionado paso a paso.

## Análisis del Código Proporcionado

```java
Flux<Integer> numbers = Flux.range(1, 10)
    .map(i -> {
        if (i % 2 == 0) {
            throw new RuntimeException("Número par no permitido");
        }
        return i;
    })
    .onErrorResume(RuntimeException.class, error -> {
        log.error("Error: {}", error.getMessage());
        return Mono.just(0);
    })
    .filter(number -> number > 5)
    .flatMap(number -> {
        return Mono.just(number * 2)
                .delayElements(Duration.ofMillis(100));
    })
    .subscribe(/*...*/);
```

### Explicación Paso a Paso:

1. **`Flux.range(1, 10)`**: Emite números del 1 al 10
2. **`map(i -> {...})`**: 
   - Si el número es par, lanza `RuntimeException`
   - Si es impar, lo deja pasar
3. **`onErrorResume`**: 
   - Captura la excepción para números pares
   - Devuelve `Mono.just(0)` como reemplazo
4. **`filter(number > 5)`**: 
   - Solo deja pasar números mayores a 5
   - Los números impares 7 y 9 pasan
   - El 0 (valor de error) se descarta
5. **`flatMap`**: 
   - Multiplica por 2 cada número que pasa
   - Añade delay de 100ms

### Resultado Final:
- **Números procesados**: 14 y 18 (7×2 y 9×2)
- **Comportamiento**: Solo los números impares mayores a 5 llegan al final

## Cómo Ejecutar

### Requisitos
- Java 17 o superior
- Gradle 8.2 (incluido via wrapper)

### Ejecutar la aplicación principal
```bash
./gradlew run
# En Windows:
.\gradlew run
```

### Ejecutar los ejemplos adicionales
```bash
./gradlew runExamples
# En Windows:
.\gradlew runExamples
```

### Ejecutar los tests
```bash
./gradlew test
# En Windows:
.\gradlew test
```

### Compilar el proyecto
```bash
./gradlew build
# En Windows:
.\gradlew build
```

## Conceptos Clave de Programación Reactiva

### Mono vs Flux
- **Mono**: Representa 0 o 1 elemento
- **Flux**: Representa 0 a N elementos

### Operadores Principales
- **map**: Transforma cada elemento
- **filter**: Filtra elementos según condición
- **flatMap**: Aplana Mono/Flux anidados
- **reduce**: Combina todos los elementos en uno
- **onErrorResume**: Maneja errores y proporciona alternativa

### Características
- **Lazy Evaluation**: Solo se ejecuta cuando hay suscripción
- **Backpressure**: Manejo de presión en flujos de datos
- **Asíncrono**: Operaciones no bloqueantes
- **Composable**: Operadores se pueden encadenar

## Logging
El proyecto usa SLF4J con Logback para mostrar información detallada sobre el manejo de errores y el flujo de ejecución.
