package com.taskflow.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardMoveRequest {

    @NotBlank(message = "Target list ID is required")
    private String targetListId;

    private int position;
}
