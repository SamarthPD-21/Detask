package com.taskflow.server.dto.response;

import com.taskflow.server.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String id;
    private String email;
    private String username;
    private String fullName;
    private String avatarUrl;
    private String bio;
    private Set<Role> roles;
    private boolean emailVerified;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
}
