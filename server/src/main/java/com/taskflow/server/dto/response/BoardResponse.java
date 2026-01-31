package com.taskflow.server.dto.response;

import com.taskflow.server.model.Label;
import com.taskflow.server.model.Visibility;
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
public class BoardResponse {

    private String id;
    private String name;
    private String description;
    private String backgroundColor;
    private String backgroundImage;
    private String ownerId;
    private UserResponse owner;
    private Set<String> memberIds;
    private List<UserResponse> members;
    private List<String> listOrder;
    private boolean archived;
    private Visibility visibility;
    private Set<Label> labels;
    private int totalCards;
    private int completedCards;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
