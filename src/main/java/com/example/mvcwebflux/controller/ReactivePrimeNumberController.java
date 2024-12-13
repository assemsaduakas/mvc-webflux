package com.example.mvcwebflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive/prime/number")
public class ReactivePrimeNumberController {

    @GetMapping
    public Mono<String> stressTestWebFlux(@RequestParam(defaultValue = "50000") int number) {
        if (number <= 0) {
            return Mono.just("Number must be a positive integer");
        }

        return Mono.fromCallable(() -> {
            long startTime = System.currentTimeMillis();
            boolean isPrime = isPrime(number);
            long endTime = System.currentTimeMillis();

            return isPrime
                    ? String.format("Number %d is prime. Computed in %d ms.", number, (endTime - startTime))
                    : String.format("Number %d is not prime. Computed in %d ms.", number, (endTime - startTime));
        });
    }

    private boolean isPrime(int number) {
        if (number <= 1) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
}
