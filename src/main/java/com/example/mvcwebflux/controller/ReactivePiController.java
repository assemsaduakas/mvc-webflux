package com.example.mvcwebflux.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.MathContext;

@RestController
public class ReactivePiController {

    @GetMapping("/reactive/calculate/pi")
    public Mono<String> calculatePi(@RequestParam(defaultValue = "10000") int digits) {
        if (digits <= 0 || digits > 100000) { // Ограничение на разумный диапазон
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Number of digits must be between 1 and 100,000"));
        }

        return Mono.fromCallable(() -> {
            long startTime = System.currentTimeMillis();
            BigDecimal pi = calculatePiChudnovsky(digits);
            long endTime = System.currentTimeMillis();
            return String.format("Calculated π to %d digits in %d ms", digits, (endTime - startTime));
        });
    }

    private BigDecimal calculatePiChudnovsky(int digits) {
        MathContext mc = new MathContext(digits);
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal factor = BigDecimal.ONE;
        BigDecimal multiplier = BigDecimal.valueOf(-262537412640768000L);

        for (int k = 0; k < digits; k++) {
            BigDecimal kValue = BigDecimal.valueOf(k);
            BigDecimal numerator = factorial(6 * k).multiply(BigDecimal.valueOf(13591409 + 545140134L * k));
            BigDecimal denominator = factorial(3 * k).multiply(factor.pow(3)).multiply(multiplier.pow(k));
            sum = sum.add(numerator.divide(denominator, mc));
            factor = factor.multiply(kValue.equals(BigDecimal.ZERO) ? BigDecimal.ONE : kValue);
        }

        BigDecimal constant = BigDecimal.valueOf(426880).multiply(BigDecimal.valueOf(Math.sqrt(10005)));
        return constant.divide(sum, mc);
    }

    private BigDecimal factorial(int n) {
        BigDecimal result = BigDecimal.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigDecimal.valueOf(i));
        }
        return result;
    }
}
