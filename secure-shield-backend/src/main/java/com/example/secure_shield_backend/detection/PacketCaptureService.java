package com.example.secure_shield_backend.detection;

import com.example.secure_shield_backend.detection.model.Packet;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
@RequiredArgsConstructor
public class PacketCaptureService {

    private final PacketAnalyser packetAnalyzer;
    private final DetectionEngine detectionEngine;

    @PostConstruct
    public void startCapture() {

        new Thread(() -> {

            try {
                System.out.println("Starting Tshark....");
                ProcessBuilder processBuilder = new ProcessBuilder(

                        "C:\\Program Files\\Wireshark\\tshark.exe",
                        "-i",
                        "8",

                        "-l",

                        "-T", "fields",

                        "-e", "ip.src",

                        "-e", "ip.dst",

                        "-e", "tcp.dstport",

                        "-e", "tcp.flags.syn"

                );
                processBuilder.redirectErrorStream(true);

                Process process = processBuilder.start();
                System.out.println("Tshark Started");
                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        process.getInputStream()
                                )
                        );

                String line;

                while ((line = reader.readLine()) != null) {


                    Packet packet =
                            packetAnalyzer.parse(line);

                    if(packet != null){

                       detectionEngine.analyze(packet);
                    }

                }

            } catch (Exception e) {

                e.printStackTrace();

            }

        }).start();

    }

}