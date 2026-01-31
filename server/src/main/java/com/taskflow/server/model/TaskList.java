package com.taskflow.server.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "lists")
public class TaskList {

    @Id
    private String id;

    private String name;

    @Indexed
    private String boardId;

    private int position;

    private List<String> cardOrder = new ArrayList<>();

    private boolean archived = false;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public TaskList() {
    }

    public TaskList(String id, String name, String boardId, int position, List<String> cardOrder,
                    boolean archived, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.boardId = boardId;
        this.position = position;
        this.cardOrder = cardOrder != null ? cardOrder : new ArrayList<>();
        this.archived = archived;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<String> getCardOrder() {
        return cardOrder;
    }

    public void setCardOrder(List<String> cardOrder) {
        this.cardOrder = cardOrder;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskList taskList = (TaskList) o;
        return Objects.equals(id, taskList.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TaskList{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", boardId='" + boardId + '\'' +
                ", position=" + position +
                ", archived=" + archived +
                ", createdAt=" + createdAt +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String name;
        private String boardId;
        private int position;
        private List<String> cardOrder = new ArrayList<>();
        private boolean archived = false;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder boardId(String boardId) {
            this.boardId = boardId;
            return this;
        }

        public Builder position(int position) {
            this.position = position;
            return this;
        }

        public Builder cardOrder(List<String> cardOrder) {
            this.cardOrder = cardOrder != null ? cardOrder : new ArrayList<>();
            return this;
        }

        public Builder archived(boolean archived) {
            this.archived = archived;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public TaskList build() {
            return new TaskList(id, name, boardId, position, cardOrder, archived, createdAt, updatedAt);
        }
    }
}
