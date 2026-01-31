package com.taskflow.server.service;

import com.taskflow.server.dto.request.ListRequest;
import com.taskflow.server.dto.response.ListResponse;
import com.taskflow.server.exception.ForbiddenException;
import com.taskflow.server.exception.ResourceNotFoundException;
import com.taskflow.server.model.*;
import com.taskflow.server.repository.BoardRepository;
import com.taskflow.server.repository.CardRepository;
import com.taskflow.server.repository.TaskListRepository;
import com.taskflow.server.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListService {

    private final TaskListRepository listRepository;
    private final BoardRepository boardRepository;
    private final CardRepository cardRepository;
    private final BoardService boardService;
    private final ActivityService activityService;
    private final MapperUtils mapperUtils;

    @Transactional
    @CacheEvict(value = "boards", key = "#boardId")
    public ListResponse createList(String boardId, ListRequest request, String userId) {
        Board board = boardService.findBoardAndCheckAccess(boardId, userId);

        int position = request.getPosition() != null ? request.getPosition() :
                listRepository.countByBoardIdAndArchivedFalse(boardId);

        TaskList list = TaskList.builder()
                .name(request.getName())
                .boardId(boardId)
                .position(position)
                .cardOrder(new ArrayList<>())
                .createdAt(LocalDateTime.now())
                .build();

        list = listRepository.save(list);

        // Update board's list order
        board.getListOrder().add(list.getId());
        boardRepository.save(board);

        log.info("List created: {} in board: {}", list.getId(), boardId);

        activityService.logActivity(boardId, null, list.getId(), ActivityType.LIST_CREATED,
                "created list \"" + list.getName() + "\"");

        return mapperUtils.toListResponse(list);
    }

    public List<ListResponse> getListsByBoard(String boardId, String userId) {
        boardService.findBoardAndCheckAccess(boardId, userId);
        Board board = boardRepository.findById(boardId).orElse(null);

        List<TaskList> lists = listRepository.findByBoardIdAndArchivedFalseOrderByPositionAsc(boardId);

        return lists.stream()
                .map(list -> {
                    List<Card> cards = cardRepository.findByListIdAndArchivedFalseOrderByPositionAsc(list.getId());
                    return mapperUtils.toListResponseWithCards(list, cards, 
                            board != null ? board.getLabels() : null);
                })
                .collect(Collectors.toList());
    }

    public ListResponse getListById(String listId, String userId) {
        TaskList list = listRepository.findById(listId)
                .orElseThrow(() -> new ResourceNotFoundException("List", "id", listId));

        boardService.findBoardAndCheckAccess(list.getBoardId(), userId);
        Board board = boardRepository.findById(list.getBoardId()).orElse(null);

        List<Card> cards = cardRepository.findByListIdAndArchivedFalseOrderByPositionAsc(listId);
        return mapperUtils.toListResponseWithCards(list, cards, 
                board != null ? board.getLabels() : null);
    }

    @Transactional
    @CacheEvict(value = "boards", key = "#result.boardId")
    public ListResponse updateList(String listId, ListRequest request, String userId) {
        TaskList list = listRepository.findById(listId)
                .orElseThrow(() -> new ResourceNotFoundException("List", "id", listId));

        boardService.findBoardAndCheckAccess(list.getBoardId(), userId);

        if (request.getName() != null) {
            list.setName(request.getName());
        }
        if (request.getPosition() != null) {
            list.setPosition(request.getPosition());
        }

        list.setUpdatedAt(LocalDateTime.now());
        list = listRepository.save(list);

        log.info("List updated: {}", listId);

        activityService.logActivity(list.getBoardId(), null, list.getId(), ActivityType.LIST_UPDATED,
                "updated list \"" + list.getName() + "\"");

        return mapperUtils.toListResponse(list);
    }

    @Transactional
    public void updateListOrder(String boardId, List<String> newOrder, String userId) {
        Board board = boardService.findBoardAndCheckAccess(boardId, userId);

        // Update positions based on new order
        for (int i = 0; i < newOrder.size(); i++) {
            String listId = newOrder.get(i);
            listRepository.findById(listId).ifPresent(list -> {
                list.setPosition(newOrder.indexOf(listId));
                listRepository.save(list);
            });
        }

        board.setListOrder(newOrder);
        boardRepository.save(board);

        log.info("List order updated for board: {}", boardId);
    }

    @Transactional
    @CacheEvict(value = "boards", key = "#result.boardId")
    public ListResponse archiveList(String listId, String userId) {
        TaskList list = listRepository.findById(listId)
                .orElseThrow(() -> new ResourceNotFoundException("List", "id", listId));

        boardService.findBoardAndCheckAccess(list.getBoardId(), userId);

        list.setArchived(true);
        list.setUpdatedAt(LocalDateTime.now());
        list = listRepository.save(list);

        // Remove from board's list order
        Board board = boardRepository.findById(list.getBoardId()).orElse(null);
        if (board != null) {
            board.getListOrder().remove(listId);
            boardRepository.save(board);
        }

        activityService.logActivity(list.getBoardId(), null, list.getId(), ActivityType.LIST_ARCHIVED,
                "archived list \"" + list.getName() + "\"");

        return mapperUtils.toListResponse(list);
    }

    @Transactional
    public void deleteList(String listId, String userId) {
        TaskList list = listRepository.findById(listId)
                .orElseThrow(() -> new ResourceNotFoundException("List", "id", listId));

        Board board = boardService.findBoardAndCheckAccess(list.getBoardId(), userId);

        // Delete all cards in the list
        cardRepository.deleteByListId(listId);

        // Remove from board's list order
        board.getListOrder().remove(listId);
        boardRepository.save(board);

        listRepository.delete(list);

        log.info("List deleted: {}", listId);
    }

    @Transactional
    public ListResponse copyList(String listId, String newName, String userId) {
        TaskList originalList = listRepository.findById(listId)
                .orElseThrow(() -> new ResourceNotFoundException("List", "id", listId));

        Board board = boardService.findBoardAndCheckAccess(originalList.getBoardId(), userId);

        // Create new list
        TaskList newList = TaskList.builder()
                .name(newName != null ? newName : originalList.getName() + " (Copy)")
                .boardId(originalList.getBoardId())
                .position(listRepository.countByBoardIdAndArchivedFalse(originalList.getBoardId()))
                .cardOrder(new ArrayList<>())
                .createdAt(LocalDateTime.now())
                .build();

        newList = listRepository.save(newList);

        // Copy all cards
        List<Card> originalCards = cardRepository.findByListIdAndArchivedFalseOrderByPositionAsc(listId);
        for (Card originalCard : originalCards) {
            Card newCard = Card.builder()
                    .title(originalCard.getTitle())
                    .description(originalCard.getDescription())
                    .boardId(originalList.getBoardId())
                    .listId(newList.getId())
                    .position(originalCard.getPosition())
                    .priority(originalCard.getPriority())
                    .dueDate(originalCard.getDueDate())
                    .labelIds(originalCard.getLabelIds())
                    .createdAt(LocalDateTime.now())
                    .createdBy(userId)
                    .build();
            newCard = cardRepository.save(newCard);
            newList.getCardOrder().add(newCard.getId());
        }

        newList = listRepository.save(newList);

        // Update board's list order
        board.getListOrder().add(newList.getId());
        boardRepository.save(board);

        return mapperUtils.toListResponse(newList);
    }
}
