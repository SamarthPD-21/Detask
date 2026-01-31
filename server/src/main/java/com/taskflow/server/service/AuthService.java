package com.taskflow.server.service;

import com.taskflow.server.dto.request.LoginRequest;
import com.taskflow.server.dto.request.RegisterRequest;
import com.taskflow.server.dto.response.AuthResponse;
import com.taskflow.server.dto.response.UserResponse;
import com.taskflow.server.exception.BadRequestException;
import com.taskflow.server.exception.ConflictException;
import com.taskflow.server.model.Role;
import com.taskflow.server.model.User;
import com.taskflow.server.repository.UserRepository;
import com.taskflow.server.security.JwtTokenProvider;
import com.taskflow.server.security.UserPrincipal;
import com.taskflow.server.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final MapperUtils mapperUtils;
    private final EmailService emailService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email is already registered");
        }

        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ConflictException("Username is already taken");
        }

        // Create new user
        User user = User.builder()
                .email(request.getEmail().toLowerCase())
                .username(request.getUsername().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .roles(Set.of(Role.ROLE_USER))
                .enabled(true)
                .emailVerified(false)
                .verificationToken(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .build();

        user = userRepository.save(user);
        log.info("New user registered: {}", user.getEmail());

        // Send verification email (async)
        emailService.sendWelcomeEmail(user.getEmail(), user.getFullName());

        // Generate tokens
        String accessToken = tokenProvider.generateAccessToken(user.getId());
        String refreshToken = tokenProvider.generateRefreshToken(user.getId());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(tokenProvider.getJwtExpiration())
                .user(mapperUtils.toUserResponse(user))
                .build();
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmailOrUsername().toLowerCase(),
                        request.getPassword()
                )
        );

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        // Update last login
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new BadRequestException("User not found"));
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        // Generate tokens
        String accessToken = tokenProvider.generateAccessToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(userPrincipal.getId());

        log.info("User logged in: {}", user.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(tokenProvider.getJwtExpiration())
                .user(mapperUtils.toUserResponse(user))
                .build();
    }

    public AuthResponse refreshToken(String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new BadRequestException("Invalid refresh token");
        }

        String userId = tokenProvider.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        String newAccessToken = tokenProvider.generateAccessToken(userId);
        String newRefreshToken = tokenProvider.generateRefreshToken(userId);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .tokenType("Bearer")
                .expiresIn(tokenProvider.getJwtExpiration())
                .user(mapperUtils.toUserResponse(user))
                .build();
    }

    public UserResponse getCurrentUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));
        return mapperUtils.toUserResponse(user);
    }
}
