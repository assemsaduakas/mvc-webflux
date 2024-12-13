package com.example.mvcwebflux.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prime/number")
public class PrimeNumberController {

    @GetMapping
    public ResponseEntity<String> stressTestMvc(@RequestParam(defaultValue = "50000") int number) {
        if (number <= 0) {
            return ResponseEntity.badRequest().body("Number must be a positive integer");
        }

        long startTime = System.currentTimeMillis();
        boolean isPrime = isPrime(number);
        long endTime = System.currentTimeMillis();

        String result = isPrime
                ? String.format("Number %d is prime. Computed in %d ms.", number, (endTime - startTime))
                : String.format("Number %d is not prime. Computed in %d ms.", number, (endTime - startTime));

        return ResponseEntity.ok(result);
    }

    private boolean isPrime(int number) {
        if (number <= 1) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
}
