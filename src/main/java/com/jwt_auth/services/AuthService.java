package com.jwt_auth.services;

import com.jwt_auth.models.requests.LoginRequest;
import com.jwt_auth.models.requests.SignupRequest;
import com.jwt_auth.models.responses.ApiResponse;
import com.jwt_auth.models.responses.AuthResponse;
import com.jwt_auth.models.tables.UserTokens;
import com.jwt_auth.models.tables.Users;
import com.jwt_auth.repositories.UserTokensRepository;
import com.jwt_auth.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository userRepository;
    private final UserTokensRepository userTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public ApiResponse<Users> signup(SignupRequest request) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create new user
        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return new ApiResponse<>(200, user, "Registration SuccessFul for the user");
    }

    public AuthResponse login(LoginRequest request) {
        Users user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }

        String token = jwtService.generateToken(user.getUsername());

        UserTokens userToken = new UserTokens();
        userToken.setUsers(user);
        userToken.setToken(token);
        userToken.setCreatedAt(LocalDateTime.now());
        userToken.setExpiresAt(LocalDateTime.now().plusDays(1));
        userTokenRepository.save(userToken);

        return new AuthResponse(token, user.getUsername());
    }
}
