package com.emf.ingestion.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class TelemetryMetrics {

    private final Counter telemetryReceived;

    public TelemetryMetrics(MeterRegistry registry) {
        this.telemetryReceived = Counter.builder("telemetry_ingestion_requests_total")
                .description("Total number of telemetry messages ingested")
                .register(registry);
    }

    public void increment() {
        telemetryReceived.increment();
    }
}
