package com.example.secure_shield_backend.controller;

import com.example.secure_shield_backend.entity.Threat;
import com.example.secure_shield_backend.service.ThreatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/threats")
@RequiredArgsConstructor
public class ThreatController {

    private final ThreatService threatService;

    // CREATE THREAT
    @PostMapping
    public Threat createThreat(
            @RequestBody Threat threat) {

        return threatService.createThreat(threat);
    }

    // GET ALL THREATS
    @GetMapping
    public List<Threat> getAllThreats() {

        return threatService.getAllThreats();
    }
}