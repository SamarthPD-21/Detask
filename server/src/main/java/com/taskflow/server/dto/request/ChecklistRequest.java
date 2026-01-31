package com.taskflow.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class ChecklistRequest {

    @NotBlank(message = "Checklist title is required")
    @Size(min = 1, max = 100, message = "Checklist title must be between 1 and 100 characters")
    private String title;

    public ChecklistRequest() {
    }

    public ChecklistRequest(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChecklistRequest that = (ChecklistRequest) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "ChecklistRequest{" +
                "title='" + title + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String title;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public ChecklistRequest build() {
            return new ChecklistRequest(title);
        }
    }
}
