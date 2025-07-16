# Manual: Cómo Crear un Proyecto Flux y Mono desde Cero

Este manual te guiará paso a paso para crear un proyecto completo de programación reactiva con **Mono** y **Flux** usando **Project Reactor** y **Gradle 8.2** desde cero.

## 📋 Prerrequisitos

Antes de comenzar, asegúrate de tener instalado:

- **Java 17 o superior** - [Descargar aquí](https://www.oracle.com/java/technologies/downloads/)
- **VS Code** con la extensión de Java - [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
- **Git** (opcional) - Para control de versiones

### Verificar instalación de Java

```bash
java -version
javac -version
```

Deberías ver algo como:
```
java version "17.0.6" 2023-01-17 LTS
Java(TM) SE Runtime Environment (build 17.0.6+9-LTS-190)
```

## 🚀 Paso 1: Crear la estructura del proyecto

### 1.1 Crear el directorio principal

```bash
# Crear directorio del proyecto
mkdir fluxymono
cd fluxymono
```

### 1.2 Crear la estructura de directorios

```bash
# Crear estructura de directorios Maven/Gradle estándar
mkdir -p src\main\java\com\example
mkdir -p src\main\resources
mkdir -p src\test\java\com\example
mkdir -p gradle\wrapper
```

En **Windows PowerShell**:
```powershell
# Crear estructura de directorios
New-Item -ItemType Directory -Path "src\main\java\com\example" -Force
New-Item -ItemType Directory -Path "src\main\resources" -Force
New-Item -ItemType Directory -Path "src\test\java\com\example" -Force
New-Item -ItemType Directory -Path "gradle\wrapper" -Force
```

## 🔧 Paso 2: Configurar Gradle

### 2.1 Crear `settings.gradle`

```gradle
rootProject.name = 'fluxymono'
```

### 2.2 Crear `build.gradle`

```gradle
plugins {
    id 'java'
    id 'application'
}

group = 'com.example'
version = '1.0.0'
description = 'Ejemplos de programación reactiva con Project Reactor'

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application {
    mainClass = 'com.example.FluxYMonoMain'
}

repositories {
    mavenCentral()
}

dependencies {
    // Project Reactor Core
    implementation 'io.projectreactor:reactor-core:3.5.0'
    
    // SLF4J API
    implementation 'org.slf4j:slf4j-api:2.0.7'
    
    // Logback implementation
    implementation 'ch.qos.logback:logback-classic:1.4.7'
    
    // JUnit 5 para tests
    testImplementation 'org.junit.jupiter:junit-jupiter:5.9.2'
    
    // Reactor Test
    testImplementation 'io.projectreactor:reactor-test:3.5.0'
}

test {
    useJUnitPlatform()
}

// Task personalizada para ejecutar los ejemplos adicionales
task runExamples(type: JavaExec) {
    group = 'application'
    description = 'Ejecuta los ejemplos adicionales de Mono y Flux'
    classpath = sourceSets.main.runtimeClasspath
    mainClass = 'com.example.EjemplosAdicionales'
}

// Configuración para encoding UTF-8
compileJava {
    options.encoding = 'UTF-8'
}

compileTestJava {
    options.encoding = 'UTF-8'
}
```

### 2.3 Configurar Gradle Wrapper

#### 2.3.1 Crear `gradle/wrapper/gradle-wrapper.properties`

```properties
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.2-bin.zip
networkTimeout=10000
validateDistributionUrl=true
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
```

#### 2.3.2 Descargar el JAR del wrapper

```bash
# PowerShell
powershell -Command "$ProgressPreference = 'SilentlyContinue'; Invoke-WebRequest -Uri 'https://github.com/gradle/gradle/raw/v8.2.1/gradle/wrapper/gradle-wrapper.jar' -OutFile 'gradle\wrapper\gradle-wrapper.jar'"
```

#### 2.3.3 Crear `gradlew.bat` (para Windows)

```batch
@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  Gradle startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%"=="0" goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line
set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar

@rem Execute Gradle
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*

:end
@rem End local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" endlocal

:fail
rem Set variable GRADLE_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd_ return code when the batch returns a non-zero code and return it.
if "%GRADLE_EXIT_CONSOLE%"=="true" (
  exit /b %ERRORLEVEL%
)
exit %ERRORLEVEL%
```

## 📝 Paso 3: Configurar Logging

### 3.1 Crear `src/main/resources/logback.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <root level="INFO">
        <appender-ref ref="STDOUT"/>
    </root>
</configuration>
```

## 💻 Paso 4: Crear la clase principal

### 4.1 Crear `src/main/java/com/example/FluxYMonoMain.java`

```java
package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

/**
 * Clase principal que demuestra el uso de Mono y Flux con Project Reactor
 */
public class FluxYMonoMain {
    
    private static final Logger log = LoggerFactory.getLogger(FluxYMonoMain.class);
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== EJEMPLOS DE MONO Y FLUX ===\n");
        
        // Actividad 1: Flux con filter y map
        System.out.println("1. FLUX CON FILTER Y MAP (números pares multiplicados por 2):");
        actividad1();
        
        Thread.sleep(1000); // Pausa para mejor visualización
        
        // Actividad 2: Flux con reduce
        System.out.println("\n2. FLUX CON REDUCE (suma total):");
        actividad2();
        
        Thread.sleep(1000);
        
        // Actividad 3: Mono con manejo de errores
        System.out.println("\n3. MONO CON MANEJO DE ERRORES:");
        actividad3();
        
        Thread.sleep(1000);
        
        // Actividad 4: Análisis del código proporcionado
        System.out.println("\n4. ANÁLISIS DEL CÓDIGO PROPORCIONADO:");
        actividad4();
        
        // Esperar a que terminen las operaciones asíncronas
        Thread.sleep(3000);
        System.out.println("\n=== FIN DE EJEMPLOS ===");
    }
    
    /**
     * Actividad 1: Flux que emite números del 1 al 10, filtra pares y los multiplica por 2
     */
    private static void actividad1() {
        Flux.range(1, 10)
            .filter(numero -> numero % 2 == 0) // Filtrar solo números pares
            .map(numero -> numero * 2)         // Multiplicar por 2
            .subscribe(resultado -> System.out.println("Resultado: " + resultado));
    }
    
    /**
     * Actividad 2: Flux que suma todos los números del 1 al 10 usando reduce
     */
    private static void actividad2() {
        Flux.range(1, 10)
            .reduce(Integer::sum)  // Suma todos los elementos
            .subscribe(suma -> System.out.println("Suma total: " + suma));
    }
    
    /**
     * Actividad 3: Mono que simula una operación que puede fallar con manejo de errores
     */
    private static void actividad3() {
        // Caso 1: División exitosa
        System.out.println("Caso exitoso:");
        divisionSegura(10, 2)
            .subscribe(resultado -> System.out.println("Resultado división: " + resultado));
        
        // Caso 2: División por cero (error)
        System.out.println("Caso con error (división por cero):");
        divisionSegura(10, 0)
            .subscribe(resultado -> System.out.println("Resultado división: " + resultado));
    }
    
    /**
     * Método auxiliar que simula una división que puede fallar
     */
    private static Mono<Integer> divisionSegura(int dividendo, int divisor) {
        return Mono.fromCallable(() -> {
            if (divisor == 0) {
                throw new ArithmeticException("División por cero no permitida");
            }
            return dividendo / divisor;
        })
        .onErrorResume(ArithmeticException.class, error -> {
            log.error("Error en división: {}", error.getMessage());
            return Mono.just(-1); // Valor predeterminado
        });
    }
    
    /**
     * Actividad 4: Reproducir y explicar el comportamiento del código proporcionado
     */
    private static void actividad4() throws InterruptedException {
        System.out.println("Ejecutando el código de análisis...");
        
        CountDownLatch latch = new CountDownLatch(1);
        
        Flux<Integer> numbers = Flux.range(1, 10)
            .map(i -> {
                System.out.println("Procesando número: " + i);
                if (i % 2 == 0) {
                    throw new RuntimeException("Número par no permitido: " + i);
                }
                return i;
            })
            .onErrorResume(RuntimeException.class, error -> {
                log.error("Error: {}", error.getMessage());
                return Mono.just(0);
            })
            .filter(number -> {
                System.out.println("Filtrando número: " + number + " (> 5? " + (number > 5) + ")");
                return number > 5;
            })
            .flatMap(number -> {
                System.out.println("Aplicando flatMap a: " + number);
                // Simular una operación asíncrona
                return Mono.just(number * 2)
                        .delayElement(Duration.ofMillis(100));
            });
        
        numbers.subscribe(
            number -> System.out.println("Número procesado: " + number),
            error -> log.error("Error general: {}", error),
            () -> {
                System.out.println("Completado");
                latch.countDown();
            }
        );
        
        // Esperar a que termine
        latch.await();
        
        // Explicación del comportamiento
        System.out.println("\n--- EXPLICACIÓN DEL COMPORTAMIENTO ---");
        explicarComportamiento();
    }
    
    /**
     * Explica paso a paso qué sucede en el código de análisis
     */
    private static void explicarComportamiento() {
        System.out.println("""
        EXPLICACIÓN PASO A PASO:
        
        1. Flux.range(1, 10): Emite números del 1 al 10
        
        2. map(i -> {...}): Para cada número:
           - Si es par, lanza RuntimeException
           - Si es impar, lo deja pasar
           
        3. onErrorResume: Cuando se produce un error (número par):
           - Captura la RuntimeException
           - Registra el error en el log
           - Devuelve Mono.just(0) como valor de reemplazo
           
        4. filter(number -> number > 5): Filtra números mayores a 5
           - Los números impares 1, 3, 5 se descartan
           - Los números impares 7, 9 pasan el filtro
           - El 0 (valor de error) se descarta
           
        5. flatMap: Para cada número que pasa el filtro:
           - Lo multiplica por 2
           - Añade un delay de 100ms
           
        RESULTADO ESPERADO:
        - Números que causan error: 2, 4, 6, 8, 10 (pares) → se convierten en 0
        - Números que pasan: 1, 3, 5, 7, 9 (impares)
        - Después del filtro > 5: solo 7 y 9
        - Después de flatMap: 14 y 18 (con delay)
        
        SALIDA FINAL: "Número procesado: 14" y "Número procesado: 18"
        """);
    }
}
```

## 🧪 Paso 5: Crear tests unitarios

### 5.1 Crear `src/test/java/com/example/FluxYMonoTest.java`

```java
package com.example;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Tests unitarios para demostrar el comportamiento de Mono y Flux
 */
public class FluxYMonoTest {
    
    @Test
    public void testFluxFilterAndMap() {
        // Actividad 1: Test del Flux con filter y map
        Flux<Integer> resultado = Flux.range(1, 10)
            .filter(numero -> numero % 2 == 0)
            .map(numero -> numero * 2);
        
        StepVerifier.create(resultado)
            .expectNext(4, 8, 12, 16, 20) // 2*2, 4*2, 6*2, 8*2, 10*2
            .verifyComplete();
    }
    
    @Test
    public void testFluxReduce() {
        // Actividad 2: Test del Flux con reduce
        Mono<Integer> suma = Flux.range(1, 10)
            .reduce(Integer::sum);
        
        StepVerifier.create(suma)
            .expectNext(55) // 1+2+3+4+5+6+7+8+9+10 = 55
            .verifyComplete();
    }
    
    @Test
    public void testMonoExitoso() {
        // Actividad 3: Test del Mono exitoso
        Mono<Integer> resultado = divisionSegura(10, 2);
        
        StepVerifier.create(resultado)
            .expectNext(5)
            .verifyComplete();
    }
    
    @Test
    public void testMonoConError() {
        // Actividad 3: Test del Mono con error manejado
        Mono<Integer> resultado = divisionSegura(10, 0);
        
        StepVerifier.create(resultado)
            .expectNext(-1) // Valor predeterminado cuando hay error
            .verifyComplete();
    }
    
    @Test
    public void testCodigoAnalisis() {
        // Actividad 4: Test del código de análisis simplificado
        // Como onErrorResume reemplaza todo el stream cuando ocurre el primer error,
        // solo tendremos los valores hasta que ocurra el primer error (número 2)
        Flux<Integer> numbers = Flux.range(1, 10)
            .map(i -> {
                if (i % 2 == 0) {
                    throw new RuntimeException("Número par no permitido");
                }
                return i;
            })
            .onErrorResume(RuntimeException.class, error -> Mono.just(0))
            .filter(number -> number > 5);
        
        // Solo se procesan: 1 (pasa), luego 2 (error -> 0), stream termina
        // Como 1 y 0 no son > 5, no se emite nada
        StepVerifier.create(numbers)
            .verifyComplete();
    }
    
    /**
     * Método auxiliar para tests - versión simplificada del método principal
     */
    private Mono<Integer> divisionSegura(int dividendo, int divisor) {
        return Mono.fromCallable(() -> {
            if (divisor == 0) {
                throw new ArithmeticException("División por cero no permitida");
            }
            return dividendo / divisor;
        })
        .onErrorResume(ArithmeticException.class, error -> Mono.just(-1));
    }
}
```

## 🎯 Paso 6: Crear ejemplos adicionales

### 6.1 Crear `src/main/java/com/example/EjemplosAdicionales.java`

```java
package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * Ejemplos adicionales de operaciones con Mono y Flux
 */
public class EjemplosAdicionales {
    
    private static final Logger log = LoggerFactory.getLogger(EjemplosAdicionales.class);
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== EJEMPLOS ADICIONALES DE MONO Y FLUX ===\n");
        
        ejemplosMono();
        Thread.sleep(1000);
        
        ejemplosFlux();
        Thread.sleep(1000);
        
        ejemplosOperadores();
        Thread.sleep(3000);
    }
    
    /**
     * Ejemplos básicos de Mono
     */
    private static void ejemplosMono() {
        System.out.println("--- EJEMPLOS DE MONO ---");
        
        // Mono vacío
        System.out.println("1. Mono vacío:");
        Mono.empty()
            .subscribe(
                value -> System.out.println("Valor: " + value),
                error -> System.out.println("Error: " + error),
                () -> System.out.println("Mono vacío completado")
            );
        
        // Mono con valor
        System.out.println("\n2. Mono con valor:");
        Mono.just("Hola Mundo")
            .subscribe(value -> System.out.println("Valor: " + value));
        
        // Mono con transformación
        System.out.println("\n3. Mono con transformación:");
        Mono.just(5)
            .map(x -> x * x)
            .subscribe(resultado -> System.out.println("5² = " + resultado));
        
        // Mono con error
        System.out.println("\n4. Mono con error manejado:");
        Mono.error(new RuntimeException("Error simulado"))
            .onErrorReturn("Valor por defecto")
            .subscribe(value -> System.out.println("Resultado: " + value));
    }
    
    /**
     * Ejemplos básicos de Flux
     */
    private static void ejemplosFlux() {
        System.out.println("\n--- EJEMPLOS DE FLUX ---");
        
        // Flux desde lista
        System.out.println("1. Flux desde lista:");
        List<String> nombres = Arrays.asList("Ana", "Bob", "Carlos", "Diana");
        Flux.fromIterable(nombres)
            .subscribe(nombre -> System.out.println("Nombre: " + nombre));
        
        // Flux con intervalo
        System.out.println("\n2. Flux con intervalo (primeros 5 elementos):");
        Flux.interval(Duration.ofMillis(200))
            .take(5)
            .subscribe(numero -> System.out.println("Número: " + numero));
        
        // Esperar un poco para ver el resultado del interval
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Flux con generación
        System.out.println("\n3. Flux generado (números del 1 al 5):");
        Flux.generate(
            () -> 1, // Estado inicial
            (state, sink) -> {
                sink.next("Elemento " + state);
                if (state >= 5) {
                    sink.complete();
                }
                return state + 1;
            }
        ).subscribe(elemento -> System.out.println(elemento));
    }
    
    /**
     * Ejemplos de operadores avanzados
     */
    private static void ejemplosOperadores() throws InterruptedException {
        System.out.println("\n--- OPERADORES AVANZADOS ---");
        
        // FlatMap vs Map
        System.out.println("1. Diferencia entre map y flatMap:");
        
        System.out.println("Con map (devuelve Mono<String>):");
        Flux.range(1, 3)
            .map(i -> "Elemento " + i)
            .subscribe(elemento -> System.out.println("  " + elemento));
        
        System.out.println("Con flatMap (aplana Mono<String> a String):");
        Flux.range(1, 3)
            .flatMap(i -> Mono.just("Elemento " + i).delayElement(Duration.ofMillis(100)))
            .subscribe(elemento -> System.out.println("  " + elemento));
        
        Thread.sleep(500);
        
        // Combinación de Flux
        System.out.println("\n2. Combinación de Flux:");
        Flux<String> flux1 = Flux.just("A", "B", "C");
        Flux<String> flux2 = Flux.just("1", "2", "3");
        
        Flux.zip(flux1, flux2, (a, b) -> a + b)
            .subscribe(combinado -> System.out.println("Combinado: " + combinado));
        
        // Manejo de errores avanzado
        System.out.println("\n3. Manejo de errores con retry:");
        Flux.range(1, 5)
            .map(i -> {
                if (i == 3) {
                    throw new RuntimeException("Error en el elemento 3");
                }
                return i;
            })
            .retry(2) // Reintenta hasta 2 veces
            .onErrorResume(error -> {
                log.error("Error después de reintentos: {}", error.getMessage());
                return Flux.just(-1);
            })
            .subscribe(
                numero -> System.out.println("Número: " + numero),
                error -> System.out.println("Error final: " + error.getMessage()),
                () -> System.out.println("Completado con reintentos")
            );
        
        Thread.sleep(100);
        
        // Backpressure
        System.out.println("\n4. Ejemplo de backpressure:");
        Flux.range(1, 1000)
            .onBackpressureDrop(dropped -> System.out.println("Elemento descartado: " + dropped))
            .take(5)
            .subscribe(numero -> System.out.println("Procesado: " + numero));
    }
}
```

## 🔧 Paso 7: Compilar y ejecutar

### 7.1 Compilar el proyecto

```bash
# Windows
.\gradlew build

# Linux/Mac
./gradlew build
```

### 7.2 Ejecutar la aplicación principal

```bash
# Windows
.\gradlew run

# Linux/Mac
./gradlew run
```

### 7.3 Ejecutar los ejemplos adicionales

```bash
# Windows
.\gradlew runExamples

# Linux/Mac
./gradlew runExamples
```

### 7.4 Ejecutar los tests

```bash
# Windows
.\gradlew test

# Linux/Mac
./gradlew test
```

## 📁 Estructura final del proyecto

```
fluxymono/
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           ├── FluxYMonoMain.java
│   │   │           └── EjemplosAdicionales.java
│   │   └── resources/
│   │       └── logback.xml
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── FluxYMonoTest.java
├── build.gradle
├── settings.gradle
├── gradlew
├── gradlew.bat
└── README.md
```

## 🎯 Conceptos clave implementados

### **Mono** (0 o 1 elemento)
- ✅ Mono vacío (`Mono.empty()`)
- ✅ Mono con valor (`Mono.just()`)
- ✅ Mono con error (`Mono.error()`)
- ✅ Manejo de errores (`onErrorResume`, `onErrorReturn`)

### **Flux** (0 a N elementos)
- ✅ Flux desde rango (`Flux.range()`)
- ✅ Flux desde lista (`Flux.fromIterable()`)
- ✅ Flux con intervalo (`Flux.interval()`)
- ✅ Flux generado (`Flux.generate()`)

### **Operadores Reactivos**
- ✅ **Transformación**: `map()`, `flatMap()`
- ✅ **Filtrado**: `filter()`
- ✅ **Agregación**: `reduce()`
- ✅ **Combinación**: `zip()`
- ✅ **Limitación**: `take()`
- ✅ **Tiempo**: `delayElement()`
- ✅ **Errores**: `onErrorResume()`, `retry()`
- ✅ **Backpressure**: `onBackpressureDrop()`

### **Testing Reactivo**
- ✅ **StepVerifier**: Para verificar flujos reactivos
- ✅ **Expectativas**: `expectNext()`, `verifyComplete()`

## 🚀 Próximos pasos

1. **Experimenta** con diferentes operadores
2. **Modifica** los ejemplos para ver cómo cambia el comportamiento
3. **Agrega** más tests unitarios
4. **Explora** la documentación oficial de [Project Reactor](https://projectreactor.io/)
5. **Aprende** sobre WebFlux para aplicaciones web reactivas

## 📚 Recursos adicionales

- [Documentación oficial de Project Reactor](https://projectreactor.io/docs)
- [Reactor Reference Guide](https://projectreactor.io/docs/core/release/reference/)
- [Reactive Streams Specification](https://www.reactive-streams.org/)
- [Spring WebFlux Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)

---

¡Felicidades! 🎉 Has creado tu primer proyecto completo de programación reactiva con Mono y Flux usando Gradle 8.2.
