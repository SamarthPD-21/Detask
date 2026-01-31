package com.taskflow.server.dto.response;

import com.taskflow.server.model.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private String id;
    private String title;
    private String message;
    private NotificationType type;
    private String boardId;
    private String cardId;
    private boolean read;
    private LocalDateTime createdAt;
}
