package com.emf.ingestion.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("ingestion-service: OK");
    }
}
