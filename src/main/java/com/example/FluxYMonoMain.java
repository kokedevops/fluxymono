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
