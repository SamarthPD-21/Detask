package com.taskflow.server.dto.request;

import com.taskflow.server.model.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class CardRequest {

    @NotBlank(message = "Card title is required")
    @Size(min = 1, max = 200, message = "Card title must be between 1 and 200 characters")
    private String title;

    @Size(max = 5000, message = "Description must be less than 5000 characters")
    private String description;

    private Priority priority;

    private LocalDateTime dueDate;

    private LocalDateTime startDate;

    private Set<String> assigneeIds;

    private Set<String> labelIds;

    private String coverColor;

    private Integer position;

    public CardRequest() {
    }

    public CardRequest(String title, String description, Priority priority, LocalDateTime dueDate,
                       LocalDateTime startDate, Set<String> assigneeIds, Set<String> labelIds,
                       String coverColor, Integer position) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.startDate = startDate;
        this.assigneeIds = assigneeIds;
        this.labelIds = labelIds;
        this.coverColor = coverColor;
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Set<String> getAssigneeIds() {
        return assigneeIds;
    }

    public void setAssigneeIds(Set<String> assigneeIds) {
        this.assigneeIds = assigneeIds;
    }

    public Set<String> getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(Set<String> labelIds) {
        this.labelIds = labelIds;
    }

    public String getCoverColor() {
        return coverColor;
    }

    public void setCoverColor(String coverColor) {
        this.coverColor = coverColor;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardRequest that = (CardRequest) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "CardRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String title;
        private String description;
        private Priority priority;
        private LocalDateTime dueDate;
        private LocalDateTime startDate;
        private Set<String> assigneeIds;
        private Set<String> labelIds;
        private String coverColor;
        private Integer position;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder priority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public Builder dueDate(LocalDateTime dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Builder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder assigneeIds(Set<String> assigneeIds) {
            this.assigneeIds = assigneeIds;
            return this;
        }

        public Builder labelIds(Set<String> labelIds) {
            this.labelIds = labelIds;
            return this;
        }

        public Builder coverColor(String coverColor) {
            this.coverColor = coverColor;
            return this;
        }

        public Builder position(Integer position) {
            this.position = position;
            return this;
        }

        public CardRequest build() {
            return new CardRequest(title, description, priority, dueDate, startDate,
                    assigneeIds, labelIds, coverColor, position);
        }
    }
}
