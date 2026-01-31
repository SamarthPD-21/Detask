package com.taskflow.server.dto.request;

import com.taskflow.server.model.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardRequest {

    @NotBlank(message = "Card title is required")
    @Size(min = 1, max = 200, message = "Card title must be between 1 and 200 characters")
    private String title;

    @Size(max = 5000, message = "Description must be less than 5000 characters")
    private String description;

    private Priority priority;

    private LocalDateTime dueDate;

    private LocalDateTime startDate;

    private Set<String> assigneeIds;

    private Set<String> labelIds;

    private String coverColor;

    private Integer position;
}
