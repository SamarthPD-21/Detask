package com.taskflow.server.service;

import com.taskflow.server.dto.request.BoardRequest;
import com.taskflow.server.dto.request.LabelRequest;
import com.taskflow.server.dto.response.BoardDetailResponse;
import com.taskflow.server.dto.response.BoardResponse;
import com.taskflow.server.dto.response.ListResponse;
import com.taskflow.server.dto.response.PageResponse;
import com.taskflow.server.exception.ForbiddenException;
import com.taskflow.server.exception.ResourceNotFoundException;
import com.taskflow.server.model.*;
import com.taskflow.server.repository.BoardRepository;
import com.taskflow.server.repository.CardRepository;
import com.taskflow.server.repository.TaskListRepository;
import com.taskflow.server.repository.UserRepository;
import com.taskflow.server.util.MapperUtils;
import com.taskflow.server.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BoardService {

    private static final Logger log = LoggerFactory.getLogger(BoardService.class);

    private final BoardRepository boardRepository;
    private final TaskListRepository listRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final ActivityService activityService;
    private final MapperUtils mapperUtils;

    public BoardService(BoardRepository boardRepository, TaskListRepository listRepository,
                        CardRepository cardRepository, UserRepository userRepository,
                        ActivityService activityService, MapperUtils mapperUtils) {
        this.boardRepository = boardRepository;
        this.listRepository = listRepository;
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.activityService = activityService;
        this.mapperUtils = mapperUtils;
    }

    @Transactional
    public BoardResponse createBoard(BoardRequest request, String userId) {
        Board board = Board.builder()
                .name(request.getName())
                .description(request.getDescription())
                .backgroundColor(request.getBackgroundColor() != null ? request.getBackgroundColor() : "#0079BF")
                .backgroundImage(request.getBackgroundImage())
                .ownerId(userId)
                .memberIds(new HashSet<>())
                .listOrder(new ArrayList<>())
                .visibility(request.getVisibility() != null ? request.getVisibility() : Visibility.PRIVATE)
                .labels(createDefaultLabels())
                .createdAt(LocalDateTime.now())
                .build();

        board = boardRepository.save(board);
        log.info("Board created: {} by user: {}", board.getId(), userId);

        activityService.logActivity(board.getId(), null, null, ActivityType.BOARD_CREATED,
                "created this board");

        return mapperUtils.toBoardResponseWithMembers(board);
    }

    private Set<Label> createDefaultLabels() {
        Set<Label> labels = new HashSet<>();
        labels.add(Label.builder().id(UUID.randomUUID().toString()).name("").color("#61BD4F").build());
        labels.add(Label.builder().id(UUID.randomUUID().toString()).name("").color("#F2D600").build());
        labels.add(Label.builder().id(UUID.randomUUID().toString()).name("").color("#FF9F1A").build());
        labels.add(Label.builder().id(UUID.randomUUID().toString()).name("").color("#EB5A46").build());
        labels.add(Label.builder().id(UUID.randomUUID().toString()).name("").color("#C377E0").build());
        labels.add(Label.builder().id(UUID.randomUUID().toString()).name("").color("#0079BF").build());
        return labels;
    }

    @Cacheable(value = "boards", key = "#boardId")
    public BoardResponse getBoardById(String boardId, String userId) {
        Board board = findBoardAndCheckAccess(boardId, userId);
        BoardResponse response = mapperUtils.toBoardResponseWithMembers(board);

        // Add card counts
        long totalCards = cardRepository.countByBoardIdAndArchivedFalse(boardId);
        long completedCards = cardRepository.countByBoardIdAndCompletedTrueAndArchivedFalse(boardId);
        response.setTotalCards((int) totalCards);
        response.setCompletedCards((int) completedCards);

        return response;
    }

    public BoardDetailResponse getBoardDetail(String boardId, String userId) {
        Board board = findBoardAndCheckAccess(boardId, userId);

        // Get all lists with cards
        List<TaskList> lists = listRepository.findByBoardIdAndArchivedFalseOrderByPositionAsc(boardId);
        List<ListResponse> listResponses = new ArrayList<>();

        for (TaskList list : lists) {
            List<Card> cards = cardRepository.findByListIdAndArchivedFalseOrderByPositionAsc(list.getId());
            listResponses.add(mapperUtils.toListResponseWithCards(list, cards, board.getLabels()));
        }

        // Get members
        List<User> members = new ArrayList<>();
        if (board.getMemberIds() != null && !board.getMemberIds().isEmpty()) {
            members = userRepository.findByIdIn(new ArrayList<>(board.getMemberIds()));
        }

        // Get owner
        User owner = userRepository.findById(board.getOwnerId()).orElse(null);

        // Card counts
        long totalCards = cardRepository.countByBoardIdAndArchivedFalse(boardId);
        long completedCards = cardRepository.countByBoardIdAndCompletedTrueAndArchivedFalse(boardId);

        return BoardDetailResponse.builder()
                .id(board.getId())
                .name(board.getName())
                .description(board.getDescription())
                .backgroundColor(board.getBackgroundColor())
                .backgroundImage(board.getBackgroundImage())
                .ownerId(board.getOwnerId())
                .owner(mapperUtils.toUserResponse(owner))
                .members(members.stream().map(mapperUtils::toUserResponse).collect(Collectors.toList()))
                .visibility(board.getVisibility())
                .labels(board.getLabels())
                .lists(listResponses)
                .totalCards((int) totalCards)
                .completedCards((int) completedCards)
                .build();
    }

    public PageResponse<BoardResponse> getUserBoards(String userId, int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Board> boardPage = boardRepository.findByOwnerIdOrMemberIdsContainingAndArchivedFalse(userId, pageable);

        List<BoardResponse> boards = boardPage.getContent().stream()
                .map(board -> {
                    BoardResponse response = mapperUtils.toBoardResponseWithMembers(board);
                    long totalCards = cardRepository.countByBoardIdAndArchivedFalse(board.getId());
                    long completedCards = cardRepository.countByBoardIdAndCompletedTrueAndArchivedFalse(board.getId());
                    response.setTotalCards((int) totalCards);
                    response.setCompletedCards((int) completedCards);
                    return response;
                })
                .collect(Collectors.toList());

        return PageResponse.<BoardResponse>builder()
                .content(boards)
                .page(page)
                .size(size)
                .totalElements(boardPage.getTotalElements())
                .totalPages(boardPage.getTotalPages())
                .first(boardPage.isFirst())
                .last(boardPage.isLast())
                .build();
    }

    @Transactional
    @CacheEvict(value = "boards", key = "#boardId")
    public BoardResponse updateBoard(String boardId, BoardRequest request, String userId) {
        Board board = findBoardAndCheckAccess(boardId, userId);

        if (request.getName() != null) {
            board.setName(request.getName());
        }
        if (request.getDescription() != null) {
            board.setDescription(request.getDescription());
        }
        if (request.getBackgroundColor() != null) {
            board.setBackgroundColor(request.getBackgroundColor());
        }
        if (request.getBackgroundImage() != null) {
            board.setBackgroundImage(request.getBackgroundImage());
        }
        if (request.getVisibility() != null) {
            board.setVisibility(request.getVisibility());
        }

        board.setUpdatedAt(LocalDateTime.now());
        board = boardRepository.save(board);
        log.info("Board updated: {}", boardId);

        activityService.logActivity(boardId, null, null, ActivityType.BOARD_UPDATED,
                "updated this board");

        return mapperUtils.toBoardResponseWithMembers(board);
    }

    @Transactional
    @CacheEvict(value = "boards", key = "#boardId")
    public void deleteBoard(String boardId, String userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Board", "id", boardId));

        if (!board.getOwnerId().equals(userId)) {
            throw new ForbiddenException("Only board owner can delete the board");
        }

        // Delete all related data
        cardRepository.deleteByBoardId(boardId);
        listRepository.deleteByBoardId(boardId);
        boardRepository.delete(board);

        log.info("Board deleted: {}", boardId);
    }

    @Transactional
    @CacheEvict(value = "boards", key = "#boardId")
    public BoardResponse archiveBoard(String boardId, String userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Board", "id", boardId));

        if (!board.getOwnerId().equals(userId)) {
            throw new ForbiddenException("Only board owner can archive the board");
        }

        board.setArchived(true);
        board.setUpdatedAt(LocalDateTime.now());
        board = boardRepository.save(board);

        activityService.logActivity(boardId, null, null, ActivityType.BOARD_ARCHIVED,
                "archived this board");

        return mapperUtils.toBoardResponseWithMembers(board);
    }

    @Transactional
    @CacheEvict(value = "boards", key = "#boardId")
    public BoardResponse addMember(String boardId, String email, String userId) {
        Board board = findBoardAndCheckAccess(boardId, userId);

        User newMember = userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        if (board.getOwnerId().equals(newMember.getId())) {
            throw new ForbiddenException("Owner is already a member of the board");
        }

        if (board.getMemberIds().contains(newMember.getId())) {
            throw new ForbiddenException("User is already a member of this board");
        }

        board.getMemberIds().add(newMember.getId());
        board.setUpdatedAt(LocalDateTime.now());
        board = boardRepository.save(board);

        activityService.logActivity(boardId, null, null, ActivityType.BOARD_MEMBER_ADDED,
                "added " + newMember.getFullName() + " to this board");

        return mapperUtils.toBoardResponseWithMembers(board);
    }

    @Transactional
    @CacheEvict(value = "boards", key = "#boardId")
    public BoardResponse removeMember(String boardId, String memberId, String userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Board", "id", boardId));

        if (!board.getOwnerId().equals(userId)) {
            throw new ForbiddenException("Only board owner can remove members");
        }

        if (!board.getMemberIds().contains(memberId)) {
            throw new ResourceNotFoundException("Member not found in this board");
        }

        board.getMemberIds().remove(memberId);
        board.setUpdatedAt(LocalDateTime.now());
        board = boardRepository.save(board);

        User removedMember = userRepository.findById(memberId).orElse(null);
        String memberName = removedMember != null ? removedMember.getFullName() : "a member";

        activityService.logActivity(boardId, null, null, ActivityType.BOARD_MEMBER_REMOVED,
                "removed " + memberName + " from this board");

        return mapperUtils.toBoardResponseWithMembers(board);
    }

    @Transactional
    @CacheEvict(value = "boards", key = "#boardId")
    public BoardResponse addLabel(String boardId, LabelRequest request, String userId) {
        Board board = findBoardAndCheckAccess(boardId, userId);

        Label label = Label.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .color(request.getColor())
                .build();

        board.getLabels().add(label);
        board.setUpdatedAt(LocalDateTime.now());
        board = boardRepository.save(board);

        return mapperUtils.toBoardResponseWithMembers(board);
    }

    @Transactional
    @CacheEvict(value = "boards", key = "#boardId")
    public BoardResponse updateLabel(String boardId, String labelId, LabelRequest request, String userId) {
        Board board = findBoardAndCheckAccess(boardId, userId);

        Label label = board.getLabels().stream()
                .filter(l -> l.getId().equals(labelId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Label", "id", labelId));

        label.setName(request.getName());
        label.setColor(request.getColor());

        board.setUpdatedAt(LocalDateTime.now());
        board = boardRepository.save(board);

        return mapperUtils.toBoardResponseWithMembers(board);
    }

    @Transactional
    @CacheEvict(value = "boards", key = "#boardId")
    public BoardResponse deleteLabel(String boardId, String labelId, String userId) {
        Board board = findBoardAndCheckAccess(boardId, userId);

        board.getLabels().removeIf(l -> l.getId().equals(labelId));
        board.setUpdatedAt(LocalDateTime.now());
        board = boardRepository.save(board);

        // Remove label from all cards
        List<Card> cards = cardRepository.findByBoardIdAndArchivedFalse(boardId);
        for (Card card : cards) {
            if (card.getLabelIds() != null && card.getLabelIds().contains(labelId)) {
                card.getLabelIds().remove(labelId);
                cardRepository.save(card);
            }
        }

        return mapperUtils.toBoardResponseWithMembers(board);
    }

    public PageResponse<BoardResponse> searchBoards(String query, String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boardPage = boardRepository.searchByNameAndUser(query, userId, pageable);

        List<BoardResponse> boards = boardPage.getContent().stream()
                .map(mapperUtils::toBoardResponseWithMembers)
                .collect(Collectors.toList());

        return PageResponse.<BoardResponse>builder()
                .content(boards)
                .page(page)
                .size(size)
                .totalElements(boardPage.getTotalElements())
                .totalPages(boardPage.getTotalPages())
                .first(boardPage.isFirst())
                .last(boardPage.isLast())
                .build();
    }

    // Helper method to find board and check access
    public Board findBoardAndCheckAccess(String boardId, String userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Board", "id", boardId));

        if (!board.getOwnerId().equals(userId) && !board.getMemberIds().contains(userId)) {
            if (board.getVisibility() != Visibility.PUBLIC) {
                throw new ForbiddenException("You don't have access to this board");
            }
        }

        return board;
    }
}
