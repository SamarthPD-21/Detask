package com.taskflow.server.service;

import com.taskflow.server.exception.BadRequestException;
import com.taskflow.server.model.Attachment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(
            "jpg", "jpeg", "png", "gif", "pdf", "doc", "docx", "xls", "xlsx", "txt", "zip"
    );

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB

    public Attachment uploadFile(MultipartFile file, String userId) {
        validateFile(file);

        try {
            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            // Generate unique filename
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String extension = getFileExtension(originalFilename);
            String newFilename = UUID.randomUUID().toString() + "." + extension;

            // Copy file to upload directory
            Path targetLocation = uploadPath.resolve(newFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            log.info("File uploaded: {} -> {}", originalFilename, newFilename);

            return Attachment.builder()
                    .id(UUID.randomUUID().toString())
                    .fileName(originalFilename)
                    .fileUrl("/uploads/" + newFilename)
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .uploadedBy(userId)
                    .uploadedAt(LocalDateTime.now())
                    .build();

        } catch (IOException e) {
            log.error("Failed to upload file", e);
            throw new BadRequestException("Failed to upload file: " + e.getMessage());
        }
    }

    public void deleteFile(String fileUrl) {
        try {
            String filename = fileUrl.replace("/uploads/", "");
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Files.deleteIfExists(filePath);
            log.info("File deleted: {}", filename);
        } catch (IOException e) {
            log.error("Failed to delete file: {}", fileUrl, e);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BadRequestException("File is empty");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BadRequestException("File size exceeds maximum allowed size of 10MB");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new BadRequestException("Invalid filename");
        }

        String extension = getFileExtension(originalFilename).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new BadRequestException("File type not allowed. Allowed types: " + String.join(", ", ALLOWED_EXTENSIONS));
        }
    }

    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return "";
        }
        return filename.substring(lastDotIndex + 1);
    }
}
