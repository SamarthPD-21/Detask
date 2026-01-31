package com.taskflow.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
