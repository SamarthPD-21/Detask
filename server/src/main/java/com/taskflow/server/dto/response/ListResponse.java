package com.taskflow.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListResponse {

    private String id;
    private String name;
    private String boardId;
    private int position;
    private List<String> cardOrder;
    private List<CardResponse> cards;
    private boolean archived;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
