package com.taskflow.server.dto.response;

import com.taskflow.server.model.Label;
import com.taskflow.server.model.Visibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDetailResponse {

    private String id;
    private String name;
    private String description;
    private String backgroundColor;
    private String backgroundImage;
    private String ownerId;
    private UserResponse owner;
    private List<UserResponse> members;
    private Visibility visibility;
    private Set<Label> labels;
    private List<ListResponse> lists;
    private int totalCards;
    private int completedCards;
}
