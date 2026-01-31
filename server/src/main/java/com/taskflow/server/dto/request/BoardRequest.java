package com.taskflow.server.dto.request;

import com.taskflow.server.model.Visibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class BoardRequest {

    @NotBlank(message = "Board name is required")
    @Size(min = 1, max = 100, message = "Board name must be between 1 and 100 characters")
    private String name;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    private String backgroundColor;

    private String backgroundImage;

    private Visibility visibility;

    public BoardRequest() {
    }

    public BoardRequest(String name, String description, String backgroundColor, String backgroundImage, Visibility visibility) {
        this.name = name;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.backgroundImage = backgroundImage;
        this.visibility = visibility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardRequest that = (BoardRequest) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "BoardRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", backgroundColor='" + backgroundColor + '\'' +
                ", visibility=" + visibility +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String description;
        private String backgroundColor;
        private String backgroundImage;
        private Visibility visibility;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder backgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder backgroundImage(String backgroundImage) {
            this.backgroundImage = backgroundImage;
            return this;
        }

        public Builder visibility(Visibility visibility) {
            this.visibility = visibility;
            return this;
        }

        public BoardRequest build() {
            return new BoardRequest(name, description, backgroundColor, backgroundImage, visibility);
        }
    }
}
