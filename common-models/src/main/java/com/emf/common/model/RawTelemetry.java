package com.emf.common.model;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "telemetry")  // makes XML <telemetry> root tag
@JsonInclude(JsonInclude.Include.NON_NULL)       // ignore null fields when serializing
public class RawTelemetry {

    @NotBlank
    private String deviceId;   // Example: POS-123, FREEZER-001

    @NotBlank
    private String protocol;   // MODBUS or BACNET

    @NotBlank
    private String metricType; // temperature | powerUsage | transactionCount

    @NotNull
    private Double value;      // actual numeric reading

    @NotBlank
    private String timestamp;  // ISO 8601 string: 2025-09-07T12:00:00Z

    // Getters & Setters
    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public String getProtocol() { return protocol; }
    public void setProtocol(String protocol) { this.protocol = protocol; }

    public String getMetricType() { return metricType; }
    public void setMetricType(String metricType) { this.metricType = metricType; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
