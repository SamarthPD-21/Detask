package com.taskflow.server.controller;

import com.taskflow.server.dto.response.ActivityResponse;
import com.taskflow.server.dto.response.ApiResponse;
import com.taskflow.server.dto.response.PageResponse;
import com.taskflow.server.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Activities", description = "Activity log endpoints")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/boards/{boardId}/activities")
    @Operation(summary = "Get board activities")
    public ResponseEntity<ApiResponse<PageResponse<ActivityResponse>>> getBoardActivities(
            @PathVariable String boardId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PageResponse<ActivityResponse> response = activityService.getBoardActivities(boardId, page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/cards/{cardId}/activities")
    @Operation(summary = "Get card activities")
    public ResponseEntity<ApiResponse<PageResponse<ActivityResponse>>> getCardActivities(
            @PathVariable String cardId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PageResponse<ActivityResponse> response = activityService.getCardActivities(cardId, page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/boards/{boardId}/activities/recent")
    @Operation(summary = "Get recent board activities")
    public ResponseEntity<ApiResponse<List<ActivityResponse>>> getRecentActivities(
            @PathVariable String boardId,
            @RequestParam(defaultValue = "24") int hours) {
        List<ActivityResponse> response = activityService.getRecentBoardActivities(boardId, hours);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
