package com.example.secure_shield_backend.detection;

import com.example.secure_shield_backend.dto.LoginRequest;
import com.example.secure_shield_backend.entity.Threat;
import com.example.secure_shield_backend.service.ThreatService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class SQLInjectionDetector {

    private final ThreatService threatService;

    public void analyze(
            LoginRequest request,
            HttpServletRequest httpRequest) {

        String input =
                (request.getEmail() + " " + request.getPassword())
                        .toLowerCase();

        if (input.contains("'")
                || input.contains(" or ")
                || input.contains("--")
                || input.contains("union")
                || input.contains("select")) {

            Threat threat = Threat.builder()
                    .threatType("SQL Injection Attempt")
                    .severity("HIGH")
                    .sourceIp(httpRequest.getRemoteAddr())
                    .description(
                            "SQL Injection payload detected during login"
                    )
                    .build();

            threatService.createThreat(threat);

            System.out.println("🚨 SQL Injection Login Attempt");
        }
    }
}