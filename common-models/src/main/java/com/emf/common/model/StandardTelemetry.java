package com.emf.common.model;

import java.time.Instant;

public class StandardTelemetry {
    private String deviceId;
    private String metricType;
    private double value;
    private Instant timestamp;

    public StandardTelemetry() {}

    public StandardTelemetry(String deviceId, String metricType, double value, Instant timestamp) {
        this.deviceId = deviceId;
        this.metricType = metricType;
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public String getMetricType() { return metricType; }
    public void setMetricType(String metricType) { this.metricType = metricType; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
