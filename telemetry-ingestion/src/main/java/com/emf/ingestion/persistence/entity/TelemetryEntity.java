package com.emf.ingestion.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;
@Entity
@Table(name = "telemetry")
public class TelemetryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String deviceId;

    @Column(nullable = false)
    private String metricType;

    @Column(name = "metric_value", nullable = false)  // fixed reserved keyword
    private Double value;

    @Column(nullable = false)
    private String timestampIso;

    @Column(nullable = false)
    private String protocol;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public String getMetricType() { return metricType; }
    public void setMetricType(String metricType) { this.metricType = metricType; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public String getTimestampIso() { return timestampIso; }
    public void setTimestampIso(String timestampIso) { this.timestampIso = timestampIso; }

    public String getProtocol() { return protocol; }
    public void setProtocol(String protocol) { this.protocol = protocol; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
