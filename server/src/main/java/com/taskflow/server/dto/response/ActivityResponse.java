package com.taskflow.server.dto.response;

import com.taskflow.server.model.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
