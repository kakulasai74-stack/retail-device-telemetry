package com.emf.ingestion.pipeline;

import com.emf.common.model.StandardTelemetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Profile("dev") // use in dev; we'll add Event Hubs publisher for prod later
@Service
public class KafkaTelemetryPublisher implements TelemetryPublisher {

    private final KafkaTemplate<String, StandardTelemetry> kafkaTemplate;
    private final String topic;

    public KafkaTelemetryPublisher(
            KafkaTemplate<String, StandardTelemetry> kafkaTemplate,
            @Value("${telemetry.kafka.topic:emf.telemetry.ingested}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(StandardTelemetry std) {
        // key by deviceId for better partitioning
        kafkaTemplate.send(topic, std.getDeviceId(), std);
    }
}
