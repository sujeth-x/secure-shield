package com.example.secure_shield_backend.service;

import com.example.secure_shield_backend.entity.AuditLog;
import com.example.secure_shield_backend.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    // Save log
    public void saveLog(
            String action,
            String email,
            String ipAddress,
            String status
    ) {

        AuditLog log = AuditLog.builder()
                .action(action)
                .email(email)
                .ipAddress(ipAddress)
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();

        auditLogRepository.save(log);
    }

    // Get all logs
    public List<AuditLog> getAllLogs() {

        return auditLogRepository.findAll();
    }
}