package com.taskflow.server.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Objects;

@Document(collection = "activities")
public class Activity {

    @Id
    private String id;

    @Indexed
    private String boardId;

    private String cardId;

    private String listId;

    @Indexed
    private String userId;

    private String userName;

    private String userAvatar;

    private ActivityType type;

    private String description;

    private Object oldValue;

    private Object newValue;

    @CreatedDate
    private LocalDateTime createdAt;

    public Activity() {
    }

    public Activity(String id, String boardId, String cardId, String listId, String userId,
                    String userName, String userAvatar, ActivityType type, String description,
                    Object oldValue, Object newValue, LocalDateTime createdAt) {
        this.id = id;
        this.boardId = boardId;
        this.cardId = cardId;
        this.listId = listId;
        this.userId = userId;
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.type = type;
        this.description = description;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }

    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(id, activity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id='" + id + '\'' +
                ", boardId='" + boardId + '\'' +
                ", cardId='" + cardId + '\'' +
                ", listId='" + listId + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String boardId;
        private String cardId;
        private String listId;
        private String userId;
        private String userName;
        private String userAvatar;
        private ActivityType type;
        private String description;
        private Object oldValue;
        private Object newValue;
        private LocalDateTime createdAt;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder boardId(String boardId) {
            this.boardId = boardId;
            return this;
        }

        public Builder cardId(String cardId) {
            this.cardId = cardId;
            return this;
        }

        public Builder listId(String listId) {
            this.listId = listId;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder userAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
            return this;
        }

        public Builder type(ActivityType type) {
            this.type = type;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder oldValue(Object oldValue) {
            this.oldValue = oldValue;
            return this;
        }

        public Builder newValue(Object newValue) {
            this.newValue = newValue;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Activity build() {
            return new Activity(id, boardId, cardId, listId, userId, userName, userAvatar,
                    type, description, oldValue, newValue, createdAt);
        }
    }
}
