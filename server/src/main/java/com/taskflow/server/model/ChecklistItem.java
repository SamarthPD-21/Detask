package com.taskflow.server.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class ChecklistItem {
    private String id;
    private String text;
    private boolean completed = false;
    private LocalDateTime completedAt;
    private String completedBy;

    public ChecklistItem() {
    }

    public ChecklistItem(String id, String text, boolean completed, LocalDateTime completedAt, String completedBy) {
        this.id = id;
        this.text = text;
        this.completed = completed;
        this.completedAt = completedAt;
        this.completedBy = completedBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChecklistItem that = (ChecklistItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ChecklistItem{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", completed=" + completed +
                ", completedAt=" + completedAt +
                ", completedBy='" + completedBy + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String text;
        private boolean completed = false;
        private LocalDateTime completedAt;
        private String completedBy;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder completed(boolean completed) {
            this.completed = completed;
            return this;
        }

        public Builder completedAt(LocalDateTime completedAt) {
            this.completedAt = completedAt;
            return this;
        }

        public Builder completedBy(String completedBy) {
            this.completedBy = completedBy;
            return this;
        }

        public ChecklistItem build() {
            return new ChecklistItem(id, text, completed, completedAt, completedBy);
        }
    }
}
