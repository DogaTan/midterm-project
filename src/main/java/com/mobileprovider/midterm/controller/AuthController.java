package com.mobileprovider.midterm.controller;

import com.mobileprovider.midterm.entity.AppUser;
import com.mobileprovider.midterm.repository.AppUserRepository;
import com.mobileprovider.midterm.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Login and token generation")
public class AuthController {

    private final AppUserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthController(AppUserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    @Operation(summary = "Login and receive JWT token")
    public ResponseEntity<?> login(
            @Parameter(description = "Kullanıcı adı") @RequestParam String username,
            @Parameter(description = "Şifre") @RequestParam String password) {

        Optional<AppUser> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(password)) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }

        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(Map.of("token", "Bearer " + token));
    }
}
