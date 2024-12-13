package com.example.mvcwebflux.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.BaseStream;

@RestController
@RequestMapping("/reactive/fetch/data")
public class ReactiveFetchDataController {

    @GetMapping
    public ResponseEntity<Flux<String>> fetchDataWebFlux() {
        String filePath = "src/main/resources/bankdataset.csv"; // Corrected to CSV for consistency.

        Flux<String> fileContent = Flux.using(
                () -> Files.lines(Paths.get(filePath)),
                Flux::fromStream,
                BaseStream::close
        );

        return ResponseEntity.ok()
                .header("Content-Type", "text/plain")
                .body(fileContent.map(line -> line + "\n")); // Add newline for consistency.
    }
}
