package com.emf.ingestion.controller;

import com.emf.common.model.RawTelemetry;
import com.emf.common.model.StandardTelemetry;
import com.emf.ingestion.service.TransformService;
import com.emf.ingestion.service.ValidationService;
import com.emf.ingestion.validation.SchemaValidationService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.emf.ingestion.service.PersistService;

@RestController
@RequestMapping("/api/v1/telemetry")
public class TelemetryController {

    private final ValidationService validation;
    private final TransformService transform;
    private final PersistService persist;
    private final SchemaValidationService schemaValidationService;

    public TelemetryController(ValidationService validation, TransformService transform, PersistService persist,  SchemaValidationService schemaValidationService) {
        this.validation = validation;
        this.transform = transform;
        this.persist = persist;
        this.schemaValidationService = schemaValidationService;
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<StandardTelemetry> ingest(
            @Valid @RequestBody RawTelemetry raw,
            @RequestHeader(value = "Content-Type", required = false) String contentType
    ) {
        // business checks
        validation.validate(raw);

        // schema checks
        if (contentType != null && contentType.toLowerCase().contains("xml")) {
            schemaValidationService.validateXml(raw);
        } else {
            schemaValidationService.validateJson(raw);
        }

        // transform + persist (already implemented)
        StandardTelemetry std = transform.toStandard(raw);
        persist.saveRawAndStandard(raw, std);

        return ResponseEntity.accepted().body(std);
    }
}
