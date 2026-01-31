package com.taskflow.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cards")
@CompoundIndex(name = "board_list_idx", def = "{'boardId': 1, 'listId': 1}")
public class Card {

    @Id
    private String id;

    private String title;

    private String description;

    @Indexed
    private String boardId;

    @Indexed
    private String listId;

    private int position;

    @Builder.Default
    private Priority priority = Priority.MEDIUM;

    private LocalDateTime dueDate;

    private LocalDateTime startDate;

    @Builder.Default
    private boolean completed = false;

    private LocalDateTime completedAt;

    @Builder.Default
    private Set<String> assigneeIds = new HashSet<>();

    @Builder.Default
    private Set<String> labelIds = new HashSet<>();

    @Builder.Default
    private List<Attachment> attachments = new ArrayList<>();

    @Builder.Default
    private List<Checklist> checklists = new ArrayList<>();

    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    private String coverColor;

    private String coverImage;

    @Builder.Default
    private int watchersCount = 0;

    @Builder.Default
    private Set<String> watcherIds = new HashSet<>();

    @Builder.Default
    private boolean archived = false;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private String createdBy;
}
