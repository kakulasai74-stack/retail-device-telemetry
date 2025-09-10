package com.emf.ingestion.service;

import com.emf.common.model.RawTelemetry;
import com.emf.common.model.StandardTelemetry;
import com.emf.ingestion.persistence.entity.TelemetryEntity;
import com.emf.ingestion.persistence.repo.TelemetryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersistService {

    private final TelemetryRepository repo;

    public PersistService(TelemetryRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public TelemetryEntity saveRawAndStandard(RawTelemetry raw, StandardTelemetry std) {
        TelemetryEntity e = new TelemetryEntity();
        e.setDeviceId(std.getDeviceId());
        e.setMetricType(std.getMetricType());
        e.setValue(std.getValue());
        e.setTimestampIso(std.getTimestamp().toString()); // ISO-8601
        e.setProtocol(raw.getProtocol());
        return repo.save(e);
    }
}
