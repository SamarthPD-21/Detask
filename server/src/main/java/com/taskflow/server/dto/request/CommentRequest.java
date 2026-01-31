package com.taskflow.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class CommentRequest {

    @NotBlank(message = "Comment text is required")
    @Size(min = 1, max = 2000, message = "Comment must be between 1 and 2000 characters")
    private String text;

    public CommentRequest() {
    }

    public CommentRequest(String text) {
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
        CommentRequest that = (CommentRequest) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "CommentRequest{" +
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

        public CommentRequest build() {
            return new CommentRequest(text);
        }
    }
}
