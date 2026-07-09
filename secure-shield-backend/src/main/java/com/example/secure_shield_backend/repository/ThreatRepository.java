package com.example.secure_shield_backend.repository;

import com.example.secure_shield_backend.entity.Threat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreatRepository
        extends JpaRepository<Threat, Long> {
}