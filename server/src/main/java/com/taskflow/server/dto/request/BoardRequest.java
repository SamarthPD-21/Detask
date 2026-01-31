package com.taskflow.server.dto.request;

import com.taskflow.server.model.Visibility;
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
public class BoardRequest {

    @NotBlank(message = "Board name is required")
    @Size(min = 1, max = 100, message = "Board name must be between 1 and 100 characters")
    private String name;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    private String backgroundColor;

    private String backgroundImage;

    private Visibility visibility;
}
