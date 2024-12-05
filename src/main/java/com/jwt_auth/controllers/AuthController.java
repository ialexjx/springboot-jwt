package com.jwt_auth.controllers;

import com.jwt_auth.models.requests.LoginRequest;
import com.jwt_auth.models.requests.SignupRequest;
import com.jwt_auth.models.responses.ApiResponse;
import com.jwt_auth.models.responses.LoginResponse;
import com.jwt_auth.repositories.UserTokensRepository;
import com.jwt_auth.services.AuthService;
import com.jwt_auth.services.JWTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserTokensRepository userTokensRepository;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) throws Exception {
        log.info("Inside the signup method in auth controller {}", request);
        ApiResponse<?> response = authService.signup(request);
        if (response.getCode() == 200) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        System.out.println("inside the controller for login with request" + request);
        LoginResponse loginResponse = authService.login(request);
        return ResponseEntity.ok(loginResponse);
    }
}
