package com.taskflow.server.dto.response;

import com.taskflow.server.model.NotificationType;

import java.time.LocalDateTime;

public class NotificationResponse {

    private String id;
    private String title;
    private String message;
    private NotificationType type;
    private String boardId;
    private String cardId;
    private boolean read;
    private LocalDateTime createdAt;

    public NotificationResponse() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public NotificationType getType() { return type; }
    public void setType(NotificationType type) { this.type = type; }
    public String getBoardId() { return boardId; }
    public void setBoardId(String boardId) { this.boardId = boardId; }
    public String getCardId() { return cardId; }
    public void setCardId(String cardId) { this.cardId = cardId; }
    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static NotificationResponseBuilder builder() { return new NotificationResponseBuilder(); }

    public static class NotificationResponseBuilder {
        private final NotificationResponse r = new NotificationResponse();
        public NotificationResponseBuilder id(String id) { r.id = id; return this; }
        public NotificationResponseBuilder title(String title) { r.title = title; return this; }
        public NotificationResponseBuilder message(String message) { r.message = message; return this; }
        public NotificationResponseBuilder type(NotificationType type) { r.type = type; return this; }
        public NotificationResponseBuilder boardId(String boardId) { r.boardId = boardId; return this; }
        public NotificationResponseBuilder cardId(String cardId) { r.cardId = cardId; return this; }
        public NotificationResponseBuilder read(boolean read) { r.read = read; return this; }
        public NotificationResponseBuilder createdAt(LocalDateTime createdAt) { r.createdAt = createdAt; return this; }
        public NotificationResponse build() { return r; }
    }
}
