package com.taskflow.server.controller;

import com.taskflow.server.dto.response.ApiResponse;
import com.taskflow.server.dto.response.NotificationResponse;
import com.taskflow.server.dto.response.PageResponse;
import com.taskflow.server.service.NotificationService;
import com.taskflow.server.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "Notification management endpoints")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @Operation(summary = "Get user notifications")
    public ResponseEntity<ApiResponse<PageResponse<NotificationResponse>>> getNotifications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        String userId = SecurityUtils.getCurrentUserId();
        PageResponse<NotificationResponse> response = notificationService.getUserNotifications(userId, page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/unread")
    @Operation(summary = "Get unread notifications")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getUnreadNotifications() {
        String userId = SecurityUtils.getCurrentUserId();
        List<NotificationResponse> response = notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/count")
    @Operation(summary = "Get unread notification count")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getUnreadCount() {
        String userId = SecurityUtils.getCurrentUserId();
        long count = notificationService.getUnreadCount(userId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("count", count)));
    }

    @PostMapping("/{notificationId}/read")
    @Operation(summary = "Mark notification as read")
    public ResponseEntity<ApiResponse<NotificationResponse>> markAsRead(@PathVariable String notificationId) {
        NotificationResponse response = notificationService.markAsRead(notificationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/read-all")
    @Operation(summary = "Mark all notifications as read")
    public ResponseEntity<ApiResponse<Void>> markAllAsRead() {
        String userId = SecurityUtils.getCurrentUserId();
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok(ApiResponse.success("All notifications marked as read", null));
    }

    @DeleteMapping("/{notificationId}")
    @Operation(summary = "Delete notification")
    public ResponseEntity<ApiResponse<Void>> deleteNotification(@PathVariable String notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok(ApiResponse.success("Notification deleted", null));
    }
}
