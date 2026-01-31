package com.taskflow.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    private String id;
    private String fileName;
    private String fileUrl;
    private String fileType;
    private long fileSize;
    private String uploadedBy;
    private LocalDateTime uploadedAt;
}
