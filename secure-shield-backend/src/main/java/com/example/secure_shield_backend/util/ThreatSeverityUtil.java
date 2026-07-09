package com.example.secure_shield_backend.util;

public class ThreatSeverityUtil {

    public static String calculateSeverity(int attempts) {

        if (attempts >= 7) {
            return "HIGH";
        }

        if (attempts >= 5) {
            return "MEDIUM";
        }

        return "LOW";
    }
}