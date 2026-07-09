package com.example.secure_shield_backend.controller;

import com.example.secure_shield_backend.entity.AuditLog;
import com.example.secure_shield_backend.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping
    public List<AuditLog> getAllLogs() {

        return auditLogService.getAllLogs();
    }
}