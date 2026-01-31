package com.taskflow.server.dto.response;

import com.taskflow.server.model.Role;

import java.time.LocalDateTime;
import java.util.Set;

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

    public UserResponse() {}

    public UserResponse(String id, String email, String username, String fullName, String avatarUrl,
                        String bio, Set<Role> roles, boolean emailVerified, LocalDateTime createdAt, LocalDateTime lastLoginAt) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
        this.roles = roles;
        this.emailVerified = emailVerified;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
    public boolean isEmailVerified() { return emailVerified; }
    public void setEmailVerified(boolean emailVerified) { this.emailVerified = emailVerified; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getLastLoginAt() { return lastLoginAt; }
    public void setLastLoginAt(LocalDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; }

    public static UserResponseBuilder builder() { return new UserResponseBuilder(); }

    public static class UserResponseBuilder {
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

        public UserResponseBuilder id(String id) { this.id = id; return this; }
        public UserResponseBuilder email(String email) { this.email = email; return this; }
        public UserResponseBuilder username(String username) { this.username = username; return this; }
        public UserResponseBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public UserResponseBuilder avatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; return this; }
        public UserResponseBuilder bio(String bio) { this.bio = bio; return this; }
        public UserResponseBuilder roles(Set<Role> roles) { this.roles = roles; return this; }
        public UserResponseBuilder emailVerified(boolean emailVerified) { this.emailVerified = emailVerified; return this; }
        public UserResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public UserResponseBuilder lastLoginAt(LocalDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; return this; }
        public UserResponse build() {
            return new UserResponse(id, email, username, fullName, avatarUrl, bio, roles, emailVerified, createdAt, lastLoginAt);
        }
    }
}
