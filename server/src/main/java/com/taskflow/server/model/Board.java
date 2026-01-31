package com.taskflow.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
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
@Document(collection = "boards")
public class Board {

    @Id
    private String id;

    private String name;

    private String description;

    private String backgroundColor;

    private String backgroundImage;

    @Indexed
    private String ownerId;

    @Builder.Default
    private Set<String> memberIds = new HashSet<>();

    @Builder.Default
    private List<String> listOrder = new ArrayList<>();

    @Builder.Default
    private boolean archived = false;

    @Builder.Default
    private Visibility visibility = Visibility.PRIVATE;

    @Builder.Default
    private Set<Label> labels = new HashSet<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
