package com.example.secure_shield_backend.detection;

import com.example.secure_shield_backend.detection.model.Packet;
import com.example.secure_shield_backend.service.ThreatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.secure_shield_backend.entity.Threat;
import com.example.secure_shield_backend.service.ThreatService;
import lombok.RequiredArgsConstructor;
import java.util.*;

@RequiredArgsConstructor
@Service
public class PortScanDetector {

    private final ThreatService threatService;

    private final Set<String> alreadyDetected =
            new HashSet<>();

    private final Map<String, Set<Integer>> scannedPorts =
            new HashMap<>();

  public void analyze(Packet packet) {

    try {

        System.out.println(">>> PortScanDetector called");

        System.out.println("SYN = " + packet.isSyn());

        if (!packet.isSyn()) {
            return;
        }


        System.out.println("IP = " + packet.getSourceIp());

        scannedPorts.putIfAbsent(
                packet.getSourceIp(),
                new HashSet<>()
        );



        scannedPorts.get(packet.getSourceIp())
                .add(packet.getDestinationPort());

        System.out.println("Port Added");

        int count = scannedPorts
                .get(packet.getSourceIp())
                .size();

        System.out.println("COUNT = " + count);

        if (count >= 20 &&
                !alreadyDetected.contains(packet.getSourceIp())) {

            alreadyDetected.add(packet.getSourceIp());

            Threat threat = Threat.builder()
                    .threatType("Port Scan")
                    .severity("HIGH")
                    .sourceIp(packet.getSourceIp())
                    .description("Nmap Port Scan Detected")
                    .build();

            System.out.println("Creating Port Scan Threat...");

            threatService.createThreat(threat);

            System.out.println("Threat Saved");
            System.out.println("🚨 PORT SCAN DETECTED");
        }

    } catch (Exception e) {

        e.printStackTrace();

    }

}

}

