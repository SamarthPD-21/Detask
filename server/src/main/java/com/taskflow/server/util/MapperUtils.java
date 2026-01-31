package com.taskflow.server.util;

import com.taskflow.server.dto.response.*;
import com.taskflow.server.model.*;
import com.taskflow.server.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MapperUtils {

    private final UserRepository userRepository;

    public MapperUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse toUserResponse(User user) {
        if (user == null) return null;
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .avatarUrl(user.getAvatarUrl())
                .bio(user.getBio())
                .roles(user.getRoles())
                .emailVerified(user.isEmailVerified())
                .createdAt(user.getCreatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }

    public BoardResponse toBoardResponse(Board board) {
        if (board == null) return null;
        return BoardResponse.builder()
                .id(board.getId())
                .name(board.getName())
                .description(board.getDescription())
                .backgroundColor(board.getBackgroundColor())
                .backgroundImage(board.getBackgroundImage())
                .ownerId(board.getOwnerId())
                .memberIds(board.getMemberIds())
                .listOrder(board.getListOrder())
                .archived(board.isArchived())
                .visibility(board.getVisibility())
                .labels(board.getLabels())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .build();
    }

    public BoardResponse toBoardResponseWithMembers(Board board) {
        BoardResponse response = toBoardResponse(board);
        if (response == null) return null;

        // Load owner
        userRepository.findById(board.getOwnerId())
                .ifPresent(owner -> response.setOwner(toUserResponse(owner)));

        // Load members
        if (board.getMemberIds() != null && !board.getMemberIds().isEmpty()) {
            List<User> members = userRepository.findByIdIn(new ArrayList<>(board.getMemberIds()));
            response.setMembers(members.stream().map(this::toUserResponse).collect(Collectors.toList()));
        }

        return response;
    }

    public ListResponse toListResponse(TaskList list) {
        if (list == null) return null;
        return ListResponse.builder()
                .id(list.getId())
                .name(list.getName())
                .boardId(list.getBoardId())
                .position(list.getPosition())
                .cardOrder(list.getCardOrder())
                .archived(list.isArchived())
                .createdAt(list.getCreatedAt())
                .updatedAt(list.getUpdatedAt())
                .build();
    }

    public ListResponse toListResponseWithCards(TaskList list, List<Card> cards, Set<Label> boardLabels) {
        ListResponse response = toListResponse(list);
        if (response == null) return null;

        if (cards != null) {
            response.setCards(cards.stream()
                    .map(card -> toCardResponse(card, boardLabels))
                    .collect(Collectors.toList()));
        }

        return response;
    }

    public CardResponse toCardResponse(Card card, Set<Label> boardLabels) {
        if (card == null) return null;

        CardResponse response = CardResponse.builder()
                .id(card.getId())
                .title(card.getTitle())
                .description(card.getDescription())
                .boardId(card.getBoardId())
                .listId(card.getListId())
                .position(card.getPosition())
                .priority(card.getPriority())
                .dueDate(card.getDueDate())
                .startDate(card.getStartDate())
                .completed(card.isCompleted())
                .completedAt(card.getCompletedAt())
                .assigneeIds(card.getAssigneeIds())
                .labelIds(card.getLabelIds())
                .attachments(card.getAttachments())
                .checklists(card.getChecklists())
                .comments(card.getComments())
                .commentsCount(card.getComments() != null ? card.getComments().size() : 0)
                .attachmentsCount(card.getAttachments() != null ? card.getAttachments().size() : 0)
                .coverColor(card.getCoverColor())
                .coverImage(card.getCoverImage())
                .watchersCount(card.getWatchersCount())
                .archived(card.isArchived())
                .createdAt(card.getCreatedAt())
                .updatedAt(card.getUpdatedAt())
                .createdBy(card.getCreatedBy())
                .build();

        // Calculate checklist progress
        if (card.getChecklists() != null && !card.getChecklists().isEmpty()) {
            int totalItems = 0;
            int completedItems = 0;
            for (Checklist checklist : card.getChecklists()) {
                if (checklist.getItems() != null) {
                    totalItems += checklist.getItems().size();
                    completedItems += checklist.getItems().stream()
                            .filter(ChecklistItem::isCompleted)
                            .count();
                }
            }
            response.setChecklistProgress(totalItems > 0 ? (completedItems * 100) / totalItems : 0);
        }

        // Map labels
        if (card.getLabelIds() != null && boardLabels != null) {
            List<Label> cardLabels = boardLabels.stream()
                    .filter(label -> card.getLabelIds().contains(label.getId()))
                    .collect(Collectors.toList());
            response.setLabels(cardLabels);
        }

        // Check if current user is watching
        String currentUserId = SecurityUtils.getCurrentUserId();
        if (currentUserId != null && card.getWatcherIds() != null) {
            response.setWatching(card.getWatcherIds().contains(currentUserId));
        }

        return response;
    }

    public CardResponse toCardResponseWithAssignees(Card card, Set<Label> boardLabels) {
        CardResponse response = toCardResponse(card, boardLabels);
        if (response == null) return null;

        if (card.getAssigneeIds() != null && !card.getAssigneeIds().isEmpty()) {
            List<User> assignees = userRepository.findByIdIn(new ArrayList<>(card.getAssigneeIds()));
            response.setAssignees(assignees.stream().map(this::toUserResponse).collect(Collectors.toList()));
        }

        if (card.getCreatedBy() != null) {
            userRepository.findById(card.getCreatedBy())
                    .ifPresent(creator -> response.setCreator(toUserResponse(creator)));
        }

        return response;
    }

    public ActivityResponse toActivityResponse(Activity activity) {
        if (activity == null) return null;
        return ActivityResponse.builder()
                .id(activity.getId())
                .boardId(activity.getBoardId())
                .cardId(activity.getCardId())
                .listId(activity.getListId())
                .userId(activity.getUserId())
                .userName(activity.getUserName())
                .userAvatar(activity.getUserAvatar())
                .type(activity.getType())
                .description(activity.getDescription())
                .createdAt(activity.getCreatedAt())
                .build();
    }

    public NotificationResponse toNotificationResponse(Notification notification) {
        if (notification == null) return null;
        return NotificationResponse.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .type(notification.getType())
                .boardId(notification.getBoardId())
                .cardId(notification.getCardId())
                .read(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
