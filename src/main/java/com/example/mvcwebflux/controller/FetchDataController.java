package com.example.mvcwebflux.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@RestController
@RequestMapping("/fetch/data")
public class FetchDataController {

    @GetMapping
    public ResponseEntity<StreamingResponseBody> fetchDataMvc() {
        String filePath = "src/main/resources/bankdataset.csv"; // Corrected to CSV for streaming readability.

        StreamingResponseBody stream = outputStream -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    outputStream.write((line + "\n").getBytes());
                    outputStream.flush();
                }
            } catch (IOException e) {
                throw new RuntimeException("Error reading the file", e);
            }
        };

        return ResponseEntity.ok()
                .header("Content-Type", "text/plain")
                .body(stream);
    }
}
