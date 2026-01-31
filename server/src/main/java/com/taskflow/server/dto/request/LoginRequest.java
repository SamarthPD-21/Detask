package com.taskflow.server.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class LoginRequest {

    @NotBlank(message = "Email or username is required")
    private String emailOrUsername;

    @NotBlank(message = "Password is required")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String emailOrUsername, String password) {
        this.emailOrUsername = emailOrUsername;
        this.password = password;
    }

    public String getEmailOrUsername() {
        return emailOrUsername;
    }

    public void setEmailOrUsername(String emailOrUsername) {
        this.emailOrUsername = emailOrUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginRequest that = (LoginRequest) o;
        return Objects.equals(emailOrUsername, that.emailOrUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailOrUsername);
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "emailOrUsername='" + emailOrUsername + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String emailOrUsername;
        private String password;

        public Builder emailOrUsername(String emailOrUsername) {
            this.emailOrUsername = emailOrUsername;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public LoginRequest build() {
            return new LoginRequest(emailOrUsername, password);
        }
    }
}
