package com.example.secure_shield_backend.detection;

import com.example.secure_shield_backend.detection.model.Packet;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PacketAnalyser {

    public Packet parse(String line) {

        String[] data = line.split("\\t");

        if (data.length < 4) {
            return null;
        }

        try {

            Packet packet = new Packet();

            packet.setSourceIp(data[0]);
            packet.setDestinationIp(data[1]);
            packet.setDestinationPort(Integer.parseInt(data[2]));
            packet.setProtocol("TCP");
            //packet.setSyn(Boolean.parseBoolean(data[3]));
            packet.setTimestamp(LocalDateTime.now());

            packet.setSyn(Boolean.parseBoolean(data[3]));

            System.out.println("RAW SYN = " + data[3]);
            System.out.println("PACKET SYN = " + packet.isSyn());

            return packet;

        } catch (Exception e) {

            return null;
        }
    }
}