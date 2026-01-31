package com.taskflow.server.controller;

import com.taskflow.server.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Health", description = "Health check endpoints")
public class HealthController {

    @GetMapping("/health")
    @Operation(summary = "Health check")
    public ResponseEntity<ApiResponse<Map<String, Object>>> health() {
        Map<String, Object> healthData = Map.of(
                "status", "UP",
                "timestamp", LocalDateTime.now(),
                "service", "TaskFlow API",
                "version", "1.0.0"
        );
        return ResponseEntity.ok(ApiResponse.success(healthData));
    }
}
