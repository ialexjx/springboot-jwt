package com.jwt_auth.controllers;

import com.jwt_auth.models.requests.LoginRequest;
import com.jwt_auth.models.requests.SignupRequest;
import com.jwt_auth.models.responses.ApiResponse;
import com.jwt_auth.models.responses.AuthResponse;
import com.jwt_auth.models.tables.Users;
import com.jwt_auth.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        ApiResponse<Users> response = authService.signup(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        ApiResponse<AuthResponse> apiResponse = new ApiResponse<>(200, authResponse, "LoginSuccessful");
        return ResponseEntity.ok(apiResponse);
    }
}
