package com.example.secure_shield_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "threats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Threat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String threatType;

    private String severity;

    private String sourceIp;

    private String description;

    private LocalDateTime detectedAt;
}