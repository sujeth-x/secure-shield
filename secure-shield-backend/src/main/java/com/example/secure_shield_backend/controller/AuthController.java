package com.example.secure_shield_backend.controller;

import com.example.secure_shield_backend.dto.AuthResponse;
import com.example.secure_shield_backend.dto.LoginRequest;
import com.example.secure_shield_backend.dto.RegisterRequest;
import com.example.secure_shield_backend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest){
        return authService.login(request,httpRequest);
    }
}
