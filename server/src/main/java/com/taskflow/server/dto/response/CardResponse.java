package com.taskflow.server.dto.response;

import com.taskflow.server.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardResponse {

    private String id;
    private String title;
    private String description;
    private String boardId;
    private String listId;
    private int position;
    private Priority priority;
    private LocalDateTime dueDate;
    private LocalDateTime startDate;
    private boolean completed;
    private LocalDateTime completedAt;
    private Set<String> assigneeIds;
    private List<UserResponse> assignees;
    private Set<String> labelIds;
    private List<Label> labels;
    private List<Attachment> attachments;
    private List<Checklist> checklists;
    private List<Comment> comments;
    private int commentsCount;
    private int attachmentsCount;
    private int checklistProgress;
    private String coverColor;
    private String coverImage;
    private int watchersCount;
    private boolean isWatching;
    private boolean archived;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private UserResponse creator;
}
