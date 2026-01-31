package com.taskflow.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChecklistRequest {

    @NotBlank(message = "Checklist title is required")
    @Size(min = 1, max = 100, message = "Checklist title must be between 1 and 100 characters")
    private String title;
}
