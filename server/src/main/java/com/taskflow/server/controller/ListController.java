package com.taskflow.server.controller;

import com.taskflow.server.dto.request.ListRequest;
import com.taskflow.server.dto.response.ApiResponse;
import com.taskflow.server.dto.response.ListResponse;
import com.taskflow.server.service.ListService;
import com.taskflow.server.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Lists", description = "List management endpoints")
public class ListController {

    private final ListService listService;

    @PostMapping("/boards/{boardId}/lists")
    @Operation(summary = "Create a new list in a board")
    public ResponseEntity<ApiResponse<ListResponse>> createList(
            @PathVariable String boardId,
            @Valid @RequestBody ListRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        ListResponse response = listService.createList(boardId, request, userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("List created successfully", response));
    }

    @GetMapping("/boards/{boardId}/lists")
    @Operation(summary = "Get all lists in a board")
    public ResponseEntity<ApiResponse<List<ListResponse>>> getListsByBoard(@PathVariable String boardId) {
        String userId = SecurityUtils.getCurrentUserId();
        List<ListResponse> response = listService.getListsByBoard(boardId, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/lists/{listId}")
    @Operation(summary = "Get list by ID")
    public ResponseEntity<ApiResponse<ListResponse>> getListById(@PathVariable String listId) {
        String userId = SecurityUtils.getCurrentUserId();
        ListResponse response = listService.getListById(listId, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/lists/{listId}")
    @Operation(summary = "Update list")
    public ResponseEntity<ApiResponse<ListResponse>> updateList(
            @PathVariable String listId,
            @Valid @RequestBody ListRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        ListResponse response = listService.updateList(listId, request, userId);
        return ResponseEntity.ok(ApiResponse.success("List updated successfully", response));
    }

    @PutMapping("/boards/{boardId}/lists/reorder")
    @Operation(summary = "Reorder lists in a board")
    public ResponseEntity<ApiResponse<Void>> reorderLists(
            @PathVariable String boardId,
            @RequestBody List<String> listOrder) {
        String userId = SecurityUtils.getCurrentUserId();
        listService.updateListOrder(boardId, listOrder, userId);
        return ResponseEntity.ok(ApiResponse.success("Lists reordered successfully", null));
    }

    @PostMapping("/lists/{listId}/archive")
    @Operation(summary = "Archive list")
    public ResponseEntity<ApiResponse<ListResponse>> archiveList(@PathVariable String listId) {
        String userId = SecurityUtils.getCurrentUserId();
        ListResponse response = listService.archiveList(listId, userId);
        return ResponseEntity.ok(ApiResponse.success("List archived successfully", response));
    }

    @DeleteMapping("/lists/{listId}")
    @Operation(summary = "Delete list")
    public ResponseEntity<ApiResponse<Void>> deleteList(@PathVariable String listId) {
        String userId = SecurityUtils.getCurrentUserId();
        listService.deleteList(listId, userId);
        return ResponseEntity.ok(ApiResponse.success("List deleted successfully", null));
    }

    @PostMapping("/lists/{listId}/copy")
    @Operation(summary = "Copy list")
    public ResponseEntity<ApiResponse<ListResponse>> copyList(
            @PathVariable String listId,
            @RequestParam(required = false) String name) {
        String userId = SecurityUtils.getCurrentUserId();
        ListResponse response = listService.copyList(listId, name, userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("List copied successfully", response));
    }
}
