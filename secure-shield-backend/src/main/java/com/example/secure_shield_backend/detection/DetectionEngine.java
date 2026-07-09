package com.example.secure_shield_backend.detection;

import com.example.secure_shield_backend.detection.model.Packet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DetectionEngine {

    private final PortScanDetector portScanDetector;

    public void analyze(Packet packet) {

        System.out.println(">>> DetectionEngine called");

        portScanDetector.analyze(packet);
    }
}