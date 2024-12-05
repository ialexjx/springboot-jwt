package com.jwt_auth.services;

import com.jwt_auth.enums.Role;
import com.jwt_auth.models.requests.LoginRequest;
import com.jwt_auth.models.requests.SignupRequest;
import com.jwt_auth.models.responses.ApiResponse;
import com.jwt_auth.models.responses.LoginResponse;
import com.jwt_auth.models.tables.UserTokens;
import com.jwt_auth.models.tables.Users;
import com.jwt_auth.repositories.UserTokensRepository;
import com.jwt_auth.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private UserTokensRepository userTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    public ApiResponse<?> signup(SignupRequest request) throws Exception {
        // Check if username or email already exists
        ApiResponse<?> response = validateRequest(request);
        if (response != null) return response;

        // Create new user
        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(request.getRole());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return new ApiResponse<>(200, user, "Registration SuccessFul for the user");
    }

    private ApiResponse<?> validateRequest(SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            ApiResponse<?> response = new ApiResponse<>();
            response.setCode(400);
            response.setMessage("This username is already occupied");
            return response;
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            ApiResponse<?> response = new ApiResponse<>();
            response.setCode(400);
            response.setMessage("This email is already present");
            return response;
        }
        return null;
    }

    public LoginResponse login(LoginRequest request) {
        System.out.println("inside the service for login with the request :- " + request);
        //Authenticate the user

        System.out.println("Going to match the password");
        String hashedPassword = userRepository.findByEmail("akshathrx6393@gmail.com").get().getPassword();
        System.out.println("password is :- " + hashedPassword);
        boolean matches = passwordEncoder.matches("Akshat@123", hashedPassword);
        System.out.println("Password matches: " + matches);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            System.out.println("Authentication successful for: " + request.getEmail());
        } catch (BadCredentialsException e) {
            System.err.println("Authentication failed: " + e.toString());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        Users user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        System.out.println("User can access");
        String token = jwtService.generateToken(user);

        System.out.println("Token is :- " + token);
        UserTokens userToken = new UserTokens();
        userToken.setUsers(user);
        userToken.setToken(token);
        userToken.setCreatedAt(LocalDateTime.now());
        userToken.setExpiresAt(LocalDateTime.now().plusHours(1));
        userTokenRepository.save(userToken);

        System.out.println("returning from the service");
        return new LoginResponse(token, jwtService.getExpirationTime());
    }
}
