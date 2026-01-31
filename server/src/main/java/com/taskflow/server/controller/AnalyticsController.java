package com.taskflow.server.controller;

import com.taskflow.server.dto.response.AnalyticsResponse;
import com.taskflow.server.dto.response.ApiResponse;
import com.taskflow.server.service.AnalyticsService;
import com.taskflow.server.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics", description = "Analytics and reporting endpoints")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping
    @Operation(summary = "Get user analytics")
    public ResponseEntity<ApiResponse<AnalyticsResponse>> getUserAnalytics() {
        String userId = SecurityUtils.getCurrentUserId();
        AnalyticsResponse response = analyticsService.getUserAnalytics(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/boards/{boardId}")
    @Operation(summary = "Get board analytics")
    public ResponseEntity<ApiResponse<AnalyticsResponse>> getBoardAnalytics(@PathVariable String boardId) {
        String userId = SecurityUtils.getCurrentUserId();
        AnalyticsResponse response = analyticsService.getBoardAnalytics(boardId, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
