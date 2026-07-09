package com.example.secure_shield_backend.service;

import com.example.secure_shield_backend.entity.Threat;
import com.example.secure_shield_backend.repository.ThreatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ThreatService {

    private final ThreatRepository threatRepository;

    // Create Threat
    public Threat createThreat(Threat threat) {

        threat.setDetectedAt(LocalDateTime.now());

        return threatRepository.save(threat);
    }

    // Get All Threats
    public List<Threat> getAllThreats() {

        return threatRepository.findAll();
    }
}