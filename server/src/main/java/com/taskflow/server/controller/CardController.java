package com.taskflow.server.controller;

import com.taskflow.server.dto.request.*;
import com.taskflow.server.dto.response.ApiResponse;
import com.taskflow.server.dto.response.CardResponse;
import com.taskflow.server.dto.response.PageResponse;
import com.taskflow.server.model.Attachment;
import com.taskflow.server.model.Priority;
import com.taskflow.server.service.CardService;
import com.taskflow.server.service.FileService;
import com.taskflow.server.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Cards", description = "Card management endpoints")
public class CardController {

    private final CardService cardService;
    private final FileService fileService;

    @PostMapping("/lists/{listId}/cards")
    @Operation(summary = "Create a new card in a list")
    public ResponseEntity<ApiResponse<CardResponse>> createCard(
            @PathVariable String listId,
            @Valid @RequestBody CardRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        CardResponse response = cardService.createCard(listId, request, userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Card created successfully", response));
    }

    @GetMapping("/cards/{cardId}")
    @Operation(summary = "Get card by ID")
    public ResponseEntity<ApiResponse<CardResponse>> getCardById(@PathVariable String cardId) {
        String userId = SecurityUtils.getCurrentUserId();
        CardResponse response = cardService.getCardById(cardId, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/boards/{boardId}/cards")
    @Operation(summary = "Get all cards in a board with pagination")
    public ResponseEntity<ApiResponse<PageResponse<CardResponse>>> getCardsByBoard(
            @PathVariable String boardId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "position") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        String userId = SecurityUtils.getCurrentUserId();
        PageResponse<CardResponse> response = cardService.getCardsByBoard(boardId, userId, page, size, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/cards/my")
    @Operation(summary = "Get cards assigned to current user")
    public ResponseEntity<ApiResponse<PageResponse<CardResponse>>> getMyCards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        String userId = SecurityUtils.getCurrentUserId();
        PageResponse<CardResponse> response = cardService.getMyCards(userId, page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/cards/{cardId}")
    @Operation(summary = "Update card")
    public ResponseEntity<ApiResponse<CardResponse>> updateCard(
            @PathVariable String cardId,
            @Valid @RequestBody CardRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        CardResponse response = cardService.updateCard(cardId, request, userId);
        return ResponseEntity.ok(ApiResponse.success("Card updated successfully", response));
    }

    @PostMapping("/cards/{cardId}/move")
    @Operation(summary = "Move card to another list")
    public ResponseEntity<ApiResponse<CardResponse>> moveCard(
            @PathVariable String cardId,
            @Valid @RequestBody CardMoveRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        CardResponse response = cardService.moveCard(cardId, request, userId);
        return ResponseEntity.ok(ApiResponse.success("Card moved successfully", response));
    }

    @PostMapping("/cards/{cardId}/complete")
    @Operation(summary = "Toggle card completion")
    public ResponseEntity<ApiResponse<CardResponse>> toggleComplete(@PathVariable String cardId) {
        String userId = SecurityUtils.getCurrentUserId();
        CardResponse response = cardService.toggleComplete(cardId, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/cards/{cardId}/assign/{assigneeId}")
    @Operation(summary = "Assign/unassign user to card")
    public ResponseEntity<ApiResponse<CardResponse>> assignUser(
            @PathVariable String cardId,
            @PathVariable String assigneeId) {
        String userId = SecurityUtils.getCurrentUserId();
        CardResponse response = cardService.assignUser(cardId, assigneeId, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/cards/{cardId}/watch")
    @Operation(summary = "Toggle watch card")
    public ResponseEntity<ApiResponse<CardResponse>> toggleWatch(@PathVariable String cardId) {
        String userId = SecurityUtils.getCurrentUserId();
        CardResponse response = cardService.toggleWatch(cardId, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // Comments
    @PostMapping("/cards/{cardId}/comments")
    @Operation(summary = "Add comment to card")
    public ResponseEntity<ApiResponse<CardResponse>> addComment(
            @PathVariable String cardId,
            @Valid @RequestBody CommentRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        CardResponse response = cardService.addComment(cardId, request, userId);
        return ResponseEntity.ok(ApiResponse.success("Comment added successfully", response));
    }

    @PutMapping("/cards/{cardId}/comments/{commentId}")
    @Operation(summary = "Update comment")
    public ResponseEntity<ApiResponse<CardResponse>> updateComment(
            @PathVariable String cardId,
            @PathVariable String commentId,
            @Valid @RequestBody CommentRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        CardResponse response = cardService.updateComment(cardId, commentId, request, userId);
        return ResponseEntity.ok(ApiResponse.success("Comment updated successfully", response));
    }

    @DeleteMapping("/cards/{cardId}/comments/{commentId}")
    @Operation(summary = "Delete comment")
    public ResponseEntity<ApiResponse<CardResponse>> deleteComment(
            @PathVariable String cardId,
            @PathVariable String commentId) {
        String userId = SecurityUtils.getCurrentUserId();
        CardResponse response = cardService.deleteComment(cardId, commentId, userId);
        return ResponseEntity.ok(ApiResponse.success("Comment deleted successfully", response));
    }

    // Checklists
    @PostMapping("/cards/{cardId}/checklists")
    @Operation(summary = "Add checklist to card")
    public ResponseEntity<ApiResponse<CardResponse>> addChecklist(
            @PathVariable String cardId,
            @Valid @RequestBody ChecklistRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        CardResponse response = cardService.addChecklist(cardId, request, userId);
        return ResponseEntity.ok(ApiResponse.success("Checklist added successfully", response));
    }

    @PostMapping("/cards/{cardId}/checklists/{checklistId}/items")
    @Operation(summary = "Add item to checklist")
    public ResponseEntity<ApiResponse<CardResponse>> addChecklistItem(
            @PathVariable String cardId,
            @PathVariable String checklistId,
            @Valid @RequestBody ChecklistItemRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        CardResponse response = cardService.addChecklistItem(cardId, checklistId, request, userId);
        return ResponseEntity.ok(ApiResponse.success("Item added successfully", response));
    }

    @PostMapping("/cards/{cardId}/checklists/{checklistId}/items/{itemId}/toggle")
    @Operation(summary = "Toggle checklist item")
    public ResponseEntity<ApiResponse<CardResponse>> toggleChecklistItem(
            @PathVariable String cardId,
            @PathVariable String checklistId,
            @PathVariable String itemId) {
        String userId = SecurityUtils.getCurrentUserId();
        CardResponse response = cardService.toggleChecklistItem(cardId, checklistId, itemId, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/cards/{cardId}/checklists/{checklistId}")
    @Operation(summary = "Delete checklist")
    public ResponseEntity<ApiResponse<CardResponse>> deleteChecklist(
            @PathVariable String cardId,
            @PathVariable String checklistId) {
        String userId = SecurityUtils.getCurrentUserId();
        CardResponse response = cardService.deleteChecklist(cardId, checklistId, userId);
        return ResponseEntity.ok(ApiResponse.success("Checklist deleted successfully", response));
    }

    // File Upload
    @PostMapping(value = "/cards/{cardId}/attachments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload attachment to card")
    public ResponseEntity<ApiResponse<Attachment>> uploadAttachment(
            @PathVariable String cardId,
            @RequestParam("file") MultipartFile file) {
        String userId = SecurityUtils.getCurrentUserId();
        Attachment attachment = fileService.uploadFile(file, userId);
        return ResponseEntity.ok(ApiResponse.success("File uploaded successfully", attachment));
    }

    // Archive & Delete
    @PostMapping("/cards/{cardId}/archive")
    @Operation(summary = "Archive card")
    public ResponseEntity<ApiResponse<CardResponse>> archiveCard(@PathVariable String cardId) {
        String userId = SecurityUtils.getCurrentUserId();
        CardResponse response = cardService.archiveCard(cardId, userId);
        return ResponseEntity.ok(ApiResponse.success("Card archived successfully", response));
    }

    @DeleteMapping("/cards/{cardId}")
    @Operation(summary = "Delete card")
    public ResponseEntity<ApiResponse<Void>> deleteCard(@PathVariable String cardId) {
        String userId = SecurityUtils.getCurrentUserId();
        cardService.deleteCard(cardId, userId);
        return ResponseEntity.ok(ApiResponse.success("Card deleted successfully", null));
    }

    // Search & Filter
    @GetMapping("/boards/{boardId}/cards/search")
    @Operation(summary = "Search cards in board")
    public ResponseEntity<ApiResponse<List<CardResponse>>> searchCards(
            @PathVariable String boardId,
            @RequestParam String query) {
        String userId = SecurityUtils.getCurrentUserId();
        List<CardResponse> response = cardService.searchCardsInBoard(boardId, query, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/boards/{boardId}/cards/filter/priority")
    @Operation(summary = "Filter cards by priority")
    public ResponseEntity<ApiResponse<List<CardResponse>>> filterByPriority(
            @PathVariable String boardId,
            @RequestParam Priority priority) {
        String userId = SecurityUtils.getCurrentUserId();
        List<CardResponse> response = cardService.filterByPriority(boardId, priority, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/boards/{boardId}/cards/filter/label/{labelId}")
    @Operation(summary = "Filter cards by label")
    public ResponseEntity<ApiResponse<List<CardResponse>>> filterByLabel(
            @PathVariable String boardId,
            @PathVariable String labelId) {
        String userId = SecurityUtils.getCurrentUserId();
        List<CardResponse> response = cardService.filterByLabel(boardId, labelId, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/boards/{boardId}/cards/filter/assignee/{assigneeId}")
    @Operation(summary = "Filter cards by assignee")
    public ResponseEntity<ApiResponse<List<CardResponse>>> filterByAssignee(
            @PathVariable String boardId,
            @PathVariable String assigneeId) {
        String userId = SecurityUtils.getCurrentUserId();
        List<CardResponse> response = cardService.filterByAssignee(boardId, assigneeId, userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
