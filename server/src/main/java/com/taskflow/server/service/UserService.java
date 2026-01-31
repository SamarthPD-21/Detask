package com.taskflow.server.service;

import com.taskflow.server.dto.request.UpdateProfileRequest;
import com.taskflow.server.dto.response.PageResponse;
import com.taskflow.server.dto.response.UserResponse;
import com.taskflow.server.exception.ResourceNotFoundException;
import com.taskflow.server.model.User;
import com.taskflow.server.repository.UserRepository;
import com.taskflow.server.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MapperUtils mapperUtils;

    @Cacheable(value = "users", key = "#userId")
    public UserResponse getUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return mapperUtils.toUserResponse(user);
    }

    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return mapperUtils.toUserResponse(user);
    }

    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username.toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        return mapperUtils.toUserResponse(user);
    }

    @Transactional
    @CacheEvict(value = "users", key = "#userId")
    public UserResponse updateProfile(String userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getBio() != null) {
            user.setBio(request.getBio());
        }
        if (request.getAvatarUrl() != null) {
            user.setAvatarUrl(request.getAvatarUrl());
        }

        user = userRepository.save(user);
        log.info("User profile updated: {}", userId);

        return mapperUtils.toUserResponse(user);
    }

    public List<UserResponse> searchUsers(String query) {
        List<User> users = userRepository
                .findByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCase(query, query);
        return users.stream()
                .map(mapperUtils::toUserResponse)
                .collect(Collectors.toList());
    }

    public PageResponse<UserResponse> getAllUsers(int page, int size) {
        Page<User> userPage = userRepository.findAll(PageRequest.of(page, size));
        List<UserResponse> users = userPage.getContent().stream()
                .map(mapperUtils::toUserResponse)
                .collect(Collectors.toList());

        return PageResponse.<UserResponse>builder()
                .content(users)
                .page(page)
                .size(size)
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .first(userPage.isFirst())
                .last(userPage.isLast())
                .build();
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email.toLowerCase());
    }
}
