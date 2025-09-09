package com.emf.ingestion.controller;

import com.emf.common.model.RawTelemetry;
import com.emf.common.model.StandardTelemetry;
import com.emf.ingestion.service.TransformService;
import com.emf.ingestion.service.ValidationService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/telemetry")
public class TelemetryController {

    private final ValidationService validation;
    private final TransformService transform;

    public TelemetryController(ValidationService validation, TransformService transform) {
        this.validation = validation;
        this.transform = transform;
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<StandardTelemetry> ingest(@Valid @RequestBody RawTelemetry raw) {
        // Bean validation runs because of @Valid; business checks next:
        validation.validate(raw);

        // Normalize to the internal schema:
        StandardTelemetry std = transform.toStandard(raw);

        // Later we’ll persist + publish; for now we return 202 with normalized body so you can verify.
        return ResponseEntity.accepted().body(std);
    }
}
