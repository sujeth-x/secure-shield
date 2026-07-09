package com.example.secure_shield_backend.service;

import com.example.secure_shield_backend.detection.SQLInjectionDetector;
import com.example.secure_shield_backend.dto.AuthResponse;
import com.example.secure_shield_backend.dto.LoginRequest;
import com.example.secure_shield_backend.dto.RegisterRequest;
import com.example.secure_shield_backend.entity.Role;
import com.example.secure_shield_backend.entity.Threat;
import com.example.secure_shield_backend.entity.User;
import com.example.secure_shield_backend.repository.UserRepository;
import com.example.secure_shield_backend.security.JwtUtil;
import com.example.secure_shield_backend.util.ThreatSeverityUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ThreatService threatService;
    private final AuditLogService auditLogService;
    private final SQLInjectionDetector sqlInjectionDetector;

    // Track failed login attempts
    private final Map<String, Integer> failedAttempts = new HashMap<>();

    // ==========================
    // REGISTER USER
    // ==========================
    public AuthResponse register(RegisterRequest request) {

        if (repo.existsByEmail(request.getEmail())) {

            return AuthResponse.builder()
                    .message("Email already exists")
                    .build();
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(Role.USER)
                .build();

        repo.save(user);

        auditLogService.saveLog(
                "USER_REGISTERED",
                request.getEmail(),
                "SYSTEM",
                "SUCCESS"
        );

        String token =
                jwtUtil.generateToken(user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .message("User Registered Successfully")
                .build();
    }

    // ==========================
    // LOGIN USER
    // ==========================
    public AuthResponse login(
            LoginRequest request,
            HttpServletRequest httpRequest) {

        sqlInjectionDetector.analyze(request, httpRequest);
        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Wrong Password
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            int attempts =
                    failedAttempts.getOrDefault(
                            request.getEmail(),
                            0);

            attempts++;

            failedAttempts.put(
                    request.getEmail(),
                    attempts
            );

            auditLogService.saveLog(
                    "LOGIN_FAILED",
                    request.getEmail(),
                    getClientIp(httpRequest),
                    "FAILED"
            );

            if (attempts >= 3) {

                System.out.println("🔥 BRUTE FORCE DETECTED");

                Threat threat = Threat.builder()
                        .threatType("Brute Force Attempt")
                        .severity(
                                ThreatSeverityUtil.calculateSeverity(attempts)
                        )
                        .sourceIp(
                                getClientIp(httpRequest)
                        )
                        .description(
                                "Multiple failed login attempts detected for email: "
                                        + request.getEmail()
                        )
                        .build();

                threatService.createThreat(threat);

                System.out.println("Threat Saved Successfully");
            }

            throw new RuntimeException("Invalid password");
        }

        // Correct Password

        failedAttempts.remove(request.getEmail());

        auditLogService.saveLog(
                "LOGIN_SUCCESS",
                request.getEmail(),
                getClientIp(httpRequest),
                "SUCCESS"
        );

        String token =
                jwtUtil.generateToken(
                        user.getEmail()
                );

        return AuthResponse.builder()
                .token(token)
                .message("Login Successful")
                .build();
    }

    // ==========================
    // CLIENT IP
    // ==========================
    private String getClientIp(
            HttpServletRequest request) {

        String header =
                request.getHeader("X-Forwarded-For");

        if (header == null) {

            return request.getRemoteAddr();
        }

        return header.split(",")[0];
    }
}