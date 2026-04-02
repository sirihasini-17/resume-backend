package com.resumiq.backend.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
class AuthController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}

@Data @Builder @AllArgsConstructor @NoArgsConstructor
class AuthenticationRequest {
    private String email;
    private String password;
}

@Data @Builder @AllArgsConstructor @NoArgsConstructor
class RegisterRequest {
    private String username;
    private String email;
    private String password;
}

@Data @Builder @AllArgsConstructor @NoArgsConstructor
class AuthenticationResponse {
    private String token;
    private Long userId;
    private String username;
    private String email;
}
