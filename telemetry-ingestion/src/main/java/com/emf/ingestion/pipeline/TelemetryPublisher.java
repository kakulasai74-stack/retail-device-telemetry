package com.emf.ingestion.pipeline;

import com.emf.common.model.StandardTelemetry;

public interface TelemetryPublisher {
    void publish(StandardTelemetry std);
}
