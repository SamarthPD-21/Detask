package com.taskflow.server.security;

import com.taskflow.server.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private String id;
    private String email;
    private String username;
    private String password;
    private String fullName;
    private String avatarUrl;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean enabled;

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
