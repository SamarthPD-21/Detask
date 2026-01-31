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
public class ChecklistItem {
    private String id;
    private String text;
    @Builder.Default
    private boolean completed = false;
    private LocalDateTime completedAt;
    private String completedBy;
}
