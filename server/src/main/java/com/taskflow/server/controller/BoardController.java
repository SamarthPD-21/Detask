package com.taskflow.server.controller;

import com.taskflow.server.dto.request.BoardRequest;
import com.taskflow.server.dto.request.InviteMemberRequest;
import com.taskflow.server.dto.request.LabelRequest;
import com.taskflow.server.dto.response.*;
import com.taskflow.server.service.BoardService;
import com.taskflow.server.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
@Tag(name = "Boards", description = "Board management endpoints")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    @Operation(summary = "Create a new board")
    public ResponseEntity<ApiResponse<BoardResponse>> createBoard(@Valid @RequestBody BoardRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        BoardResponse response = boardService.createBoard(request, userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Board created successfully", response));
    }

    @GetMapping
    @Operation(summary = "Get all boards for current user")
    public ResponseEntity<ApiResponse<PageResponse<BoardResponse>>> getUserBoards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "updatedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        String userId = SecurityUtils.getCurrentUserId();
        PageResponse<BoardResponse> response = boardService.getUserBoards(userId, page, size, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{boardId}")
    @Operation(summary = "Get board by ID")
    public ResponseEntity<ApiResponse<BoardResponse>> getBoardById(@PathVariable String boardId) {
        String userId = SecurityUtils.getCurrentUserId();
        BoardResponse response = boardService.getBoardById(boardId, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{boardId}/detail")
    @Operation(summary = "Get board detail with lists and cards")
    public ResponseEntity<ApiResponse<BoardDetailResponse>> getBoardDetail(@PathVariable String boardId) {
        String userId = SecurityUtils.getCurrentUserId();
        BoardDetailResponse response = boardService.getBoardDetail(boardId, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{boardId}")
    @Operation(summary = "Update board")
    public ResponseEntity<ApiResponse<BoardResponse>> updateBoard(
            @PathVariable String boardId,
            @Valid @RequestBody BoardRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        BoardResponse response = boardService.updateBoard(boardId, request, userId);
        return ResponseEntity.ok(ApiResponse.success("Board updated successfully", response));
    }

    @DeleteMapping("/{boardId}")
    @Operation(summary = "Delete board")
    public ResponseEntity<ApiResponse<Void>> deleteBoard(@PathVariable String boardId) {
        String userId = SecurityUtils.getCurrentUserId();
        boardService.deleteBoard(boardId, userId);
        return ResponseEntity.ok(ApiResponse.success("Board deleted successfully", null));
    }

    @PostMapping("/{boardId}/archive")
    @Operation(summary = "Archive board")
    public ResponseEntity<ApiResponse<BoardResponse>> archiveBoard(@PathVariable String boardId) {
        String userId = SecurityUtils.getCurrentUserId();
        BoardResponse response = boardService.archiveBoard(boardId, userId);
        return ResponseEntity.ok(ApiResponse.success("Board archived successfully", response));
    }

    @PostMapping("/{boardId}/members")
    @Operation(summary = "Add member to board")
    public ResponseEntity<ApiResponse<BoardResponse>> addMember(
            @PathVariable String boardId,
            @Valid @RequestBody InviteMemberRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        BoardResponse response = boardService.addMember(boardId, request.getEmail(), userId);
        return ResponseEntity.ok(ApiResponse.success("Member added successfully", response));
    }

    @DeleteMapping("/{boardId}/members/{memberId}")
    @Operation(summary = "Remove member from board")
    public ResponseEntity<ApiResponse<BoardResponse>> removeMember(
            @PathVariable String boardId,
            @PathVariable String memberId) {
        String userId = SecurityUtils.getCurrentUserId();
        BoardResponse response = boardService.removeMember(boardId, memberId, userId);
        return ResponseEntity.ok(ApiResponse.success("Member removed successfully", response));
    }

    @PostMapping("/{boardId}/labels")
    @Operation(summary = "Add label to board")
    public ResponseEntity<ApiResponse<BoardResponse>> addLabel(
            @PathVariable String boardId,
            @Valid @RequestBody LabelRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        BoardResponse response = boardService.addLabel(boardId, request, userId);
        return ResponseEntity.ok(ApiResponse.success("Label added successfully", response));
    }

    @PutMapping("/{boardId}/labels/{labelId}")
    @Operation(summary = "Update label")
    public ResponseEntity<ApiResponse<BoardResponse>> updateLabel(
            @PathVariable String boardId,
            @PathVariable String labelId,
            @Valid @RequestBody LabelRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        BoardResponse response = boardService.updateLabel(boardId, labelId, request, userId);
        return ResponseEntity.ok(ApiResponse.success("Label updated successfully", response));
    }

    @DeleteMapping("/{boardId}/labels/{labelId}")
    @Operation(summary = "Delete label")
    public ResponseEntity<ApiResponse<BoardResponse>> deleteLabel(
            @PathVariable String boardId,
            @PathVariable String labelId) {
        String userId = SecurityUtils.getCurrentUserId();
        BoardResponse response = boardService.deleteLabel(boardId, labelId, userId);
        return ResponseEntity.ok(ApiResponse.success("Label deleted successfully", response));
    }

    @GetMapping("/search")
    @Operation(summary = "Search boards")
    public ResponseEntity<ApiResponse<PageResponse<BoardResponse>>> searchBoards(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        String userId = SecurityUtils.getCurrentUserId();
        PageResponse<BoardResponse> response = boardService.searchBoards(query, userId, page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
