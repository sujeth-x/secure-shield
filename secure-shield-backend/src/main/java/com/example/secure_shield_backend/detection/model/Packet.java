package com.example.secure_shield_backend.detection.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public  class Packet {

    private String sourceIp;

    private String destinationIp;

    private int destinationPort;

    private String protocol;

    private boolean syn;

    private LocalDateTime timestamp;

}