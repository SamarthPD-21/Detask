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
public class ChecklistItemRequest {

    @NotBlank(message = "Item text is required")
    @Size(min = 1, max = 500, message = "Item text must be between 1 and 500 characters")
    private String text;
}
