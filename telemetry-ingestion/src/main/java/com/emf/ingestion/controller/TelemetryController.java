package com.emf.ingestion.controller;

import com.emf.common.model.RawTelemetry;
import com.emf.common.model.StandardTelemetry;
import com.emf.ingestion.service.TransformService;
import com.emf.ingestion.service.ValidationService;
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

    public TelemetryController(ValidationService validation, TransformService transform, PersistService persist) {
        this.validation = validation;
        this.transform = transform;
        this.persist = persist;
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StandardTelemetry> ingest(@Valid @RequestBody RawTelemetry raw) {
        validation.validate(raw);
        StandardTelemetry std = transform.toStandard(raw);
        persist.saveRawAndStandard(raw, std);  // <-- save to DB
        return ResponseEntity.accepted().body(std);
    }
}
