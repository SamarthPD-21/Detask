package com.taskflow.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private String id;
    private String text;
    private String authorId;
    private String authorName;
    private String authorAvatar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean edited;
}
