package com.taskflow.server.security;

import com.taskflow.server.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private String id;
    private String email;
    private String username;
    private String password;
    private String fullName;
    private String avatarUrl;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean enabled;

    public UserPrincipal() {}

    public UserPrincipal(String id, String email, String username, String password, String fullName, 
                         String avatarUrl, Collection<? extends GrantedAuthority> authorities, boolean enabled) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
        this.authorities = authorities;
        this.enabled = enabled;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) { this.authorities = authorities; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public static UserPrincipalBuilder builder() { return new UserPrincipalBuilder(); }

    public static class UserPrincipalBuilder {
        private String id;
        private String email;
        private String username;
        private String password;
        private String fullName;
        private String avatarUrl;
        private Collection<? extends GrantedAuthority> authorities;
        private boolean enabled;

        public UserPrincipalBuilder id(String id) { this.id = id; return this; }
        public UserPrincipalBuilder email(String email) { this.email = email; return this; }
        public UserPrincipalBuilder username(String username) { this.username = username; return this; }
        public UserPrincipalBuilder password(String password) { this.password = password; return this; }
        public UserPrincipalBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public UserPrincipalBuilder avatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; return this; }
        public UserPrincipalBuilder authorities(Collection<? extends GrantedAuthority> authorities) { this.authorities = authorities; return this; }
        public UserPrincipalBuilder enabled(boolean enabled) { this.enabled = enabled; return this; }
        public UserPrincipal build() {
            return new UserPrincipal(id, email, username, password, fullName, avatarUrl, authorities, enabled);
        }
    }

    public static UserPrincipal create(User user) {
        return UserPrincipal.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .avatarUrl(user.getAvatarUrl())
                .authorities(user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.name()))
                        .collect(Collectors.toList()))
                .enabled(user.isEnabled())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
