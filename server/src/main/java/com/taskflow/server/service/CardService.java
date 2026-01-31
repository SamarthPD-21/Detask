package com.taskflow.server.service;

import com.taskflow.server.dto.request.*;
import com.taskflow.server.dto.response.CardResponse;
import com.taskflow.server.dto.response.PageResponse;
import com.taskflow.server.exception.ResourceNotFoundException;
import com.taskflow.server.model.*;
import com.taskflow.server.repository.BoardRepository;
import com.taskflow.server.repository.CardRepository;
import com.taskflow.server.repository.TaskListRepository;
import com.taskflow.server.repository.UserRepository;
import com.taskflow.server.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final TaskListRepository listRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardService boardService;
    private final ActivityService activityService;
    private final NotificationService notificationService;
    private final MapperUtils mapperUtils;

    @Transactional
    @CacheEvict(value = {"boards", "cards"}, allEntries = true)
    public CardResponse createCard(String listId, CardRequest request, String userId) {
        TaskList list = listRepository.findById(listId)
                .orElseThrow(() -> new ResourceNotFoundException("List", "id", listId));

        Board board = boardService.findBoardAndCheckAccess(list.getBoardId(), userId);

        int position = request.getPosition() != null ? request.getPosition() :
                (int) cardRepository.countByListIdAndArchivedFalse(listId);

        Card card = Card.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .boardId(list.getBoardId())
                .listId(listId)
                .position(position)
                .priority(request.getPriority() != null ? request.getPriority() : Priority.MEDIUM)
                .dueDate(request.getDueDate())
                .startDate(request.getStartDate())
                .assigneeIds(request.getAssigneeIds() != null ? request.getAssigneeIds() : new HashSet<>())
                .labelIds(request.getLabelIds() != null ? request.getLabelIds() : new HashSet<>())
                .coverColor(request.getCoverColor())
                .attachments(new ArrayList<>())
                .checklists(new ArrayList<>())
                .comments(new ArrayList<>())
                .watcherIds(new HashSet<>())
                .createdAt(LocalDateTime.now())
                .createdBy(userId)
                .build();

        card = cardRepository.save(card);

        // Update list's card order
        list.getCardOrder().add(card.getId());
        listRepository.save(list);

        log.info("Card created: {} in list: {}", card.getId(), listId);

        activityService.logActivity(list.getBoardId(), card.getId(), listId, ActivityType.CARD_CREATED,
                "created card \"" + card.getTitle() + "\"");

        return mapperUtils.toCardResponseWithAssignees(card, board.getLabels());
    }

    public CardResponse getCardById(String cardId, String userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", cardId));

        Board board = boardService.findBoardAndCheckAccess(card.getBoardId(), userId);

        return mapperUtils.toCardResponseWithAssignees(card, board.getLabels());
    }

    public PageResponse<CardResponse> getCardsByBoard(String boardId, String userId, int page, int size,
                                                       String sortBy, String sortDir) {
        Board board = boardService.findBoardAndCheckAccess(boardId, userId);

        Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Card> cardPage = cardRepository.findByBoardIdAndArchivedFalse(boardId, pageable);

        List<CardResponse> cards = cardPage.getContent().stream()
                .map(card -> mapperUtils.toCardResponse(card, board.getLabels()))
                .collect(Collectors.toList());

        return PageResponse.<CardResponse>builder()
                .content(cards)
                .page(page)
                .size(size)
                .totalElements(cardPage.getTotalElements())
                .totalPages(cardPage.getTotalPages())
                .first(cardPage.isFirst())
                .last(cardPage.isLast())
                .build();
    }

    public PageResponse<CardResponse> getMyCards(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "dueDate"));
        Page<Card> cardPage = cardRepository.findByAssigneeId(userId, pageable);

        List<CardResponse> cards = cardPage.getContent().stream()
                .map(card -> {
                    Board board = boardRepository.findById(card.getBoardId()).orElse(null);
                    return mapperUtils.toCardResponse(card, board != null ? board.getLabels() : null);
                })
                .collect(Collectors.toList());

        return PageResponse.<CardResponse>builder()
                .content(cards)
                .page(page)
                .size(size)
                .totalElements(cardPage.getTotalElements())
                .totalPages(cardPage.getTotalPages())
                .first(cardPage.isFirst())
                .last(cardPage.isLast())
                .build();
    }

    @Transactional
    @CacheEvict(value = {"boards", "cards"}, allEntries = true)
    public CardResponse updateCard(String cardId, CardRequest request, String userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", cardId));

        Board board = boardService.findBoardAndCheckAccess(card.getBoardId(), userId);

        if (request.getTitle() != null) {
            card.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            card.setDescription(request.getDescription());
        }
        if (request.getPriority() != null) {
            card.setPriority(request.getPriority());
        }
        if (request.getDueDate() != null) {
            card.setDueDate(request.getDueDate());
            activityService.logActivity(card.getBoardId(), cardId, card.getListId(),
                    ActivityType.CARD_DUE_DATE_SET, "set due date");
        }
        if (request.getStartDate() != null) {
            card.setStartDate(request.getStartDate());
        }
        if (request.getCoverColor() != null) {
            card.setCoverColor(request.getCoverColor());
        }
        if (request.getLabelIds() != null) {
            card.setLabelIds(request.getLabelIds());
        }

        card.setUpdatedAt(LocalDateTime.now());
        card = cardRepository.save(card);

        log.info("Card updated: {}", cardId);

        activityService.logActivity(card.getBoardId(), cardId, card.getListId(),
                ActivityType.CARD_UPDATED, "updated card \"" + card.getTitle() + "\"");

        return mapperUtils.toCardResponseWithAssignees(card, board.getLabels());
    }

    @Transactional
    @CacheEvict(value = {"boards", "cards"}, allEntries = true)
    public CardResponse moveCard(String cardId, CardMoveRequest request, String userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", cardId));

        boardService.findBoardAndCheckAccess(card.getBoardId(), userId);

        TaskList sourceList = listRepository.findById(card.getListId())
                .orElseThrow(() -> new ResourceNotFoundException("List", "id", card.getListId()));

        TaskList targetList = listRepository.findById(request.getTargetListId())
                .orElseThrow(() -> new ResourceNotFoundException("List", "id", request.getTargetListId()));

        // Remove from source list
        sourceList.getCardOrder().remove(cardId);
        listRepository.save(sourceList);

        // Add to target list at position
        card.setListId(request.getTargetListId());
        card.setPosition(request.getPosition());

        if (request.getPosition() >= targetList.getCardOrder().size()) {
            targetList.getCardOrder().add(cardId);
        } else {
            targetList.getCardOrder().add(request.getPosition(), cardId);
        }
        listRepository.save(targetList);

        card.setUpdatedAt(LocalDateTime.now());
        card = cardRepository.save(card);

        log.info("Card moved: {} from {} to {}", cardId, sourceList.getId(), targetList.getId());

        activityService.logActivity(card.getBoardId(), cardId, targetList.getId(),
                ActivityType.CARD_MOVED,
                "moved card \"" + card.getTitle() + "\" from " + sourceList.getName() + " to " + targetList.getName());

        Board board = boardRepository.findById(card.getBoardId()).orElse(null);
        return mapperUtils.toCardResponseWithAssignees(card, board != null ? board.getLabels() : null);
    }

    @Transactional
    @CacheEvict(value = {"boards", "cards"}, allEntries = true)
    public CardResponse toggleComplete(String cardId, String userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", cardId));

        Board board = boardService.findBoardAndCheckAccess(card.getBoardId(), userId);

        card.setCompleted(!card.isCompleted());
        if (card.isCompleted()) {
            card.setCompletedAt(LocalDateTime.now());
            activityService.logActivity(card.getBoardId(), cardId, card.getListId(),
                    ActivityType.CARD_COMPLETED, "completed card \"" + card.getTitle() + "\"");
        } else {
            card.setCompletedAt(null);
            activityService.logActivity(card.getBoardId(), cardId, card.getListId(),
                    ActivityType.CARD_REOPENED, "reopened card \"" + card.getTitle() + "\"");
        }

        card.setUpdatedAt(LocalDateTime.now());
        card = cardRepository.save(card);

        return mapperUtils.toCardResponseWithAssignees(card, board.getLabels());
    }

    @Transactional
    @CacheEvict(value = {"boards", "cards"}, allEntries = true)
    public CardResponse assignUser(String cardId, String assigneeId, String userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", cardId));

        Board board = boardService.findBoardAndCheckAccess(card.getBoardId(), userId);

        User assignee = userRepository.findById(assigneeId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", assigneeId));

        if (card.getAssigneeIds() == null) {
            card.setAssigneeIds(new HashSet<>());
        }

        if (card.getAssigneeIds().contains(assigneeId)) {
            card.getAssigneeIds().remove(assigneeId);
            activityService.logActivity(card.getBoardId(), cardId, card.getListId(),
                    ActivityType.CARD_UNASSIGNED,
                    "unassigned " + assignee.getFullName() + " from card \"" + card.getTitle() + "\"");
        } else {
            card.getAssigneeIds().add(assigneeId);
            activityService.logActivity(card.getBoardId(), cardId, card.getListId(),
                    ActivityType.CARD_ASSIGNED,
                    "assigned " + assignee.getFullName() + " to card \"" + card.getTitle() + "\"");

            // Send notification
            notificationService.createNotification(
                    assigneeId,
                    "You've been assigned to a card",
                    "You were assigned to \"" + card.getTitle() + "\"",
                    NotificationType.CARD_ASSIGNED,
                    card.getBoardId(),
                    cardId
            );
        }

        card.setUpdatedAt(LocalDateTime.now());
        card = cardRepository.save(card);

        return mapperUtils.toCardResponseWithAssignees(card, board.getLabels());
    }

    @Transactional
    @CacheEvict(value = {"boards", "cards"}, allEntries = true)
    public CardResponse addComment(String cardId, CommentRequest request, String userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", cardId));

        Board board = boardService.findBoardAndCheckAccess(card.getBoardId(), userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Comment comment = Comment.builder()
                .id(UUID.randomUUID().toString())
                .text(request.getText())
                .authorId(userId)
                .authorName(user.getFullName())
                .authorAvatar(user.getAvatarUrl())
                .createdAt(LocalDateTime.now())
                .build();

        if (card.getComments() == null) {
            card.setComments(new ArrayList<>());
        }
        card.getComments().add(comment);
        card.setUpdatedAt(LocalDateTime.now());
        card = cardRepository.save(card);

        activityService.logActivity(card.getBoardId(), cardId, card.getListId(),
                ActivityType.COMMENT_ADDED,
                "commented on card \"" + card.getTitle() + "\"");

        // Notify watchers and assignees
        Set<String> toNotify = new HashSet<>();
        if (card.getWatcherIds() != null) toNotify.addAll(card.getWatcherIds());
        if (card.getAssigneeIds() != null) toNotify.addAll(card.getAssigneeIds());
        toNotify.remove(userId);

        for (String notifyUserId : toNotify) {
            notificationService.createNotification(
                    notifyUserId,
                    "New comment on card",
                    user.getFullName() + " commented on \"" + card.getTitle() + "\"",
                    NotificationType.CARD_COMMENTED,
                    card.getBoardId(),
                    cardId
            );
        }

        return mapperUtils.toCardResponseWithAssignees(card, board.getLabels());
    }

    @Transactional
    public CardResponse updateComment(String cardId, String commentId, CommentRequest request, String userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", cardId));

        Board board = boardService.findBoardAndCheckAccess(card.getBoardId(), userId);

        Comment comment = card.getComments().stream()
                .filter(c -> c.getId().equals(commentId) && c.getAuthorId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        comment.setText(request.getText());
        comment.setUpdatedAt(LocalDateTime.now());
        comment.setEdited(true);

        card.setUpdatedAt(LocalDateTime.now());
        card = cardRepository.save(card);

        return mapperUtils.toCardResponseWithAssignees(card, board.getLabels());
    }

    @Transactional
    public CardResponse deleteComment(String cardId, String commentId, String userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", cardId));

        Board board = boardService.findBoardAndCheckAccess(card.getBoardId(), userId);

        card.getComments().removeIf(c -> c.getId().equals(commentId) && c.getAuthorId().equals(userId));
        card.setUpdatedAt(LocalDateTime.now());
        card = cardRepository.save(card);

        activityService.logActivity(card.getBoardId(), cardId, card.getListId(),
                ActivityType.COMMENT_DELETED, "deleted a comment");

        return mapperUtils.toCardResponseWithAssignees(card, board.getLabels());
    }

    @Transactional
    public CardResponse addChecklist(String cardId, ChecklistRequest request, String userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", cardId));

        Board board = boardService.findBoardAndCheckAccess(card.getBoardId(), userId);

        Checklist checklist = Checklist.builder()
                .id(UUID.randomUUID().toString())
                .title(request.getTitle())
                .items(new ArrayList<>())
                .build();

        if (card.getChecklists() == null) {
            card.setChecklists(new ArrayList<>());
        }
        card.getChecklists().add(checklist);
        card.setUpdatedAt(LocalDateTime.now());
        card = cardRepository.save(card);

        activityService.logActivity(card.getBoardId(), cardId, card.getListId(),
                ActivityType.CHECKLIST_ADDED,
                "added checklist \"" + checklist.getTitle() + "\" to card \"" + card.getTitle() + "\"");

        return mapperUtils.toCardResponseWithAssignees(card, board.getLabels());
    }

    @Transactional
    public CardResponse addChecklistItem(String cardId, String checklistId, ChecklistItemRequest request, String userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", cardId));

        Board board = boardService.findBoardAndCheckAccess(card.getBoardId(), userId);

        Checklist checklist = card.getChecklists().stream()
                .filter(c -> c.getId().equals(checklistId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Checklist", "id", checklistId));

        ChecklistItem item = ChecklistItem.builder()
                .id(UUID.randomUUID().toString())
                .text(request.getText())
                .completed(false)
                .build();

        checklist.getItems().add(item);
        card.setUpdatedAt(LocalDateTime.now());
        card = cardRepository.save(card);

        return mapperUtils.toCardResponseWithAssignees(card, board.getLabels());
    }

    @Transactional
    public CardResponse toggleChecklistItem(String cardId, String checklistId, String itemId, String userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", cardId));

        Board board = boardService.findBoardAndCheckAccess(card.getBoardId(), userId);

        Checklist checklist = card.getChecklists().stream()
                .filter(c -> c.getId().equals(checklistId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Checklist", "id", checklistId));

        ChecklistItem item = checklist.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("ChecklistItem", "id", itemId));

        item.setCompleted(!item.isCompleted());
        if (item.isCompleted()) {
            item.setCompletedAt(LocalDateTime.now());
            item.setCompletedBy(userId);
            activityService.logActivity(card.getBoardId(), cardId, card.getListId(),
                    ActivityType.CHECKLIST_ITEM_COMPLETED,
                    "completed \"" + item.getText() + "\" on card \"" + card.getTitle() + "\"");
        } else {
            item.setCompletedAt(null);
            item.setCompletedBy(null);
            activityService.logActivity(card.getBoardId(), cardId, card.getListId(),
                    ActivityType.CHECKLIST_ITEM_UNCOMPLETED,
                    "marked \"" + item.getText() + "\" incomplete on card \"" + card.getTitle() + "\"");
        }

        card.setUpdatedAt(LocalDateTime.now());
        card = cardRepository.save(card);

        return mapperUtils.toCardResponseWithAssignees(card, board.getLabels());
    }

    @Transactional
    public CardResponse deleteChecklist(String cardId, String checklistId, String userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", cardId));

        Board board = boardService.findBoardAndCheckAccess(card.getBoardId(), userId);

        card.getChecklists().removeIf(c -> c.getId().equals(checklistId));
        card.setUpdatedAt(LocalDateTime.now());
        card = cardRepository.save(card);

        return mapperUtils.toCardResponseWithAssignees(card, board.getLabels());
    }

    @Transactional
    public CardResponse toggleWatch(String cardId, String userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", cardId));

        Board board = boardService.findBoardAndCheckAccess(card.getBoardId(), userId);

        if (card.getWatcherIds() == null) {
            card.setWatcherIds(new HashSet<>());
        }

        if (card.getWatcherIds().contains(userId)) {
            card.getWatcherIds().remove(userId);
            card.setWatchersCount(card.getWatchersCount() - 1);
        } else {
            card.getWatcherIds().add(userId);
            card.setWatchersCount(card.getWatchersCount() + 1);
        }

        card.setUpdatedAt(LocalDateTime.now());
        card = cardRepository.save(card);

        return mapperUtils.toCardResponseWithAssignees(card, board.getLabels());
    }

    @Transactional
    @CacheEvict(value = {"boards", "cards"}, allEntries = true)
    public CardResponse archiveCard(String cardId, String userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", cardId));

        Board board = boardService.findBoardAndCheckAccess(card.getBoardId(), userId);

        card.setArchived(true);
        card.setUpdatedAt(LocalDateTime.now());
        card = cardRepository.save(card);

        // Remove from list's card order
        TaskList list = listRepository.findById(card.getListId()).orElse(null);
        if (list != null) {
            list.getCardOrder().remove(cardId);
            listRepository.save(list);
        }

        activityService.logActivity(card.getBoardId(), cardId, card.getListId(),
                ActivityType.CARD_ARCHIVED, "archived card \"" + card.getTitle() + "\"");

        return mapperUtils.toCardResponseWithAssignees(card, board.getLabels());
    }

    @Transactional
    @CacheEvict(value = {"boards", "cards"}, allEntries = true)
    public void deleteCard(String cardId, String userId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", cardId));

        boardService.findBoardAndCheckAccess(card.getBoardId(), userId);

        // Remove from list's card order
        TaskList list = listRepository.findById(card.getListId()).orElse(null);
        if (list != null) {
            list.getCardOrder().remove(cardId);
            listRepository.save(list);
        }

        cardRepository.delete(card);
        log.info("Card deleted: {}", cardId);
    }

    // Search and Filter methods
    public List<CardResponse> searchCardsInBoard(String boardId, String query, String userId) {
        Board board = boardService.findBoardAndCheckAccess(boardId, userId);
        List<Card> cards = cardRepository.searchInBoard(boardId, query);

        return cards.stream()
                .map(card -> mapperUtils.toCardResponse(card, board.getLabels()))
                .collect(Collectors.toList());
    }

    public List<CardResponse> filterByPriority(String boardId, Priority priority, String userId) {
        Board board = boardService.findBoardAndCheckAccess(boardId, userId);
        List<Card> cards = cardRepository.findByBoardIdAndPriority(boardId, priority);

        return cards.stream()
                .map(card -> mapperUtils.toCardResponse(card, board.getLabels()))
                .collect(Collectors.toList());
    }

    public List<CardResponse> filterByLabel(String boardId, String labelId, String userId) {
        Board board = boardService.findBoardAndCheckAccess(boardId, userId);
        List<Card> cards = cardRepository.findByBoardIdAndLabelId(boardId, labelId);

        return cards.stream()
                .map(card -> mapperUtils.toCardResponse(card, board.getLabels()))
                .collect(Collectors.toList());
    }

    public List<CardResponse> filterByAssignee(String boardId, String assigneeId, String userId) {
        Board board = boardService.findBoardAndCheckAccess(boardId, userId);
        List<Card> cards = cardRepository.findByBoardIdAndAssigneeId(boardId, assigneeId);

        return cards.stream()
                .map(card -> mapperUtils.toCardResponse(card, board.getLabels()))
                .collect(Collectors.toList());
    }
}
