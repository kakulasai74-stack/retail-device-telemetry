package com.emf.ingestion.service;

import com.emf.common.model.RawTelemetry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ValidationService {
    public void validate(RawTelemetry raw) {
        if (raw == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Body is required");
        }
        if (raw.getProtocol() == null ||
                !(raw.getProtocol().equalsIgnoreCase("MODBUS") || raw.getProtocol().equalsIgnoreCase("BACNET"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "protocol must be MODBUS or BACNET");
        }
        if (raw.getValue() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "value is required");
        }
        if (raw.getTimestamp() == null || raw.getTimestamp().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "timestamp is required (ISO-8601)");
        }
    }
}
