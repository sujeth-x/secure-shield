package com.example.secure_shield_backend.repository;

import com.example.secure_shield_backend.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository
        extends JpaRepository<AuditLog, Long> {
}