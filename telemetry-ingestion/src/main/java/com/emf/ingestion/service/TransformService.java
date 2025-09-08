package com.emf.ingestion.service;

import com.emf.common.model.RawTelemetry;
import com.emf.common.model.StandardTelemetry;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TransformService {
    public StandardTelemetry toStandard(RawTelemetry raw) {
        return new StandardTelemetry(
                raw.getDeviceId(),
                raw.getMetricType(),
                raw.getValue(),
                Instant.parse(raw.getTimestamp()) // expects ISO-8601 e.g. 2025-09-07T23:00:00Z
        );
    }
}
