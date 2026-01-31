package com.taskflow.server.dto.request;

import jakarta.validation.constraints.Size;

import java.util.Objects;

public class UpdateProfileRequest {

    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullName;

    @Size(max = 500, message = "Bio must be less than 500 characters")
    private String bio;

    private String avatarUrl;

    public UpdateProfileRequest() {
    }

    public UpdateProfileRequest(String fullName, String bio, String avatarUrl) {
        this.fullName = fullName;
        this.bio = bio;
        this.avatarUrl = avatarUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateProfileRequest that = (UpdateProfileRequest) o;
        return Objects.equals(fullName, that.fullName) && Objects.equals(bio, that.bio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, bio);
    }

    @Override
    public String toString() {
        return "UpdateProfileRequest{" +
                "fullName='" + fullName + '\'' +
                ", bio='" + bio + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String fullName;
        private String bio;
        private String avatarUrl;

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder bio(String bio) {
            this.bio = bio;
            return this;
        }

        public Builder avatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
            return this;
        }

        public UpdateProfileRequest build() {
            return new UpdateProfileRequest(fullName, bio, avatarUrl);
        }
    }
}
