package com.taskflow.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class ChecklistItemRequest {

    @NotBlank(message = "Item text is required")
    @Size(min = 1, max = 500, message = "Item text must be between 1 and 500 characters")
    private String text;

    public ChecklistItemRequest() {
    }

    public ChecklistItemRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChecklistItemRequest that = (ChecklistItemRequest) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "ChecklistItemRequest{" +
                "text='" + text + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String text;

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public ChecklistItemRequest build() {
            return new ChecklistItemRequest(text);
        }
    }
}
