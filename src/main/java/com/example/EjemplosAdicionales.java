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
        
        // Mono vacio
        System.out.println("1. Mono vacio:");
        Mono.empty()
            .doOnNext(value -> System.out.println("Valor: " + value))
            .subscribe(
                value -> System.out.println("Valor: " + value),
                error -> System.out.println("Error: " + error),
                () -> System.out.println("Mono vacio completado")
            );
        
        // Mono con valor
        System.out.println("\n2. Mono con valor:");
        Mono.just("Hola Mundo")
            .subscribe(value -> System.out.println("Valor: " + value));
        
        // Mono con transformación
        System.out.println("\n3. Mono con transformacion:");
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
            .subscribe(numero -> System.out.println("Numero: " + numero));
        
        // Esperar un poco para ver el resultado del interval
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Flux con generación
        System.out.println("\n3. Flux generado (numeros del 1 al 5):");
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
        System.out.println("\n2. Combinacion de Flux:");
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
                numero -> System.out.println("Numero: " + numero),
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
