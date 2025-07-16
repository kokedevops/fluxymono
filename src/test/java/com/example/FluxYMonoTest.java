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
