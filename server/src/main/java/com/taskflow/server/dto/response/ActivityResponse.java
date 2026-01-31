package com.taskflow.server.dto.response;

import com.taskflow.server.model.ActivityType;

import java.time.LocalDateTime;

public class ActivityResponse {

    private String id;
    private String boardId;
    private String cardId;
    private String listId;
    private String userId;
    private String userName;
    private String userAvatar;
    private ActivityType type;
    private String description;
    private LocalDateTime createdAt;

    public ActivityResponse() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getBoardId() { return boardId; }
    public void setBoardId(String boardId) { this.boardId = boardId; }
    public String getCardId() { return cardId; }
    public void setCardId(String cardId) { this.cardId = cardId; }
    public String getListId() { return listId; }
    public void setListId(String listId) { this.listId = listId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserAvatar() { return userAvatar; }
    public void setUserAvatar(String userAvatar) { this.userAvatar = userAvatar; }
    public ActivityType getType() { return type; }
    public void setType(ActivityType type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static ActivityResponseBuilder builder() { return new ActivityResponseBuilder(); }

    public static class ActivityResponseBuilder {
        private final ActivityResponse r = new ActivityResponse();
        public ActivityResponseBuilder id(String id) { r.id = id; return this; }
        public ActivityResponseBuilder boardId(String boardId) { r.boardId = boardId; return this; }
        public ActivityResponseBuilder cardId(String cardId) { r.cardId = cardId; return this; }
        public ActivityResponseBuilder listId(String listId) { r.listId = listId; return this; }
        public ActivityResponseBuilder userId(String userId) { r.userId = userId; return this; }
        public ActivityResponseBuilder userName(String userName) { r.userName = userName; return this; }
        public ActivityResponseBuilder userAvatar(String userAvatar) { r.userAvatar = userAvatar; return this; }
        public ActivityResponseBuilder type(ActivityType type) { r.type = type; return this; }
        public ActivityResponseBuilder description(String description) { r.description = description; return this; }
        public ActivityResponseBuilder createdAt(LocalDateTime createdAt) { r.createdAt = createdAt; return this; }
        public ActivityResponse build() { return r; }
    }
}
