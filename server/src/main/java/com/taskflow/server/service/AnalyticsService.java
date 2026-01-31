package com.taskflow.server.service;

import com.taskflow.server.dto.response.AnalyticsResponse;
import com.taskflow.server.model.Board;
import com.taskflow.server.model.Card;
import com.taskflow.server.model.Priority;
import com.taskflow.server.repository.BoardRepository;
import com.taskflow.server.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final BoardRepository boardRepository;
    private final CardRepository cardRepository;

    @Cacheable(value = "analytics", key = "#userId")
    public AnalyticsResponse getUserAnalytics(String userId) {
        // Get all boards for user
        List<Board> boards = boardRepository.findAllByOwnerIdOrMemberIdsContainingAndArchivedFalse(userId);
        List<String> boardIds = boards.stream().map(Board::getId).collect(Collectors.toList());

        if (boardIds.isEmpty()) {
            return AnalyticsResponse.builder()
                    .totalBoards(0)
                    .totalCards(0)
                    .completedCards(0)
                    .overdueTasks(0)
                    .tasksCompletedThisWeek(0)
                    .tasksCreatedThisWeek(0)
                    .completionRate(0)
                    .cardsByPriority(new HashMap<>())
                    .cardsByLabel(new HashMap<>())
                    .tasksCompletedByDay(new HashMap<>())
                    .tasksByMember(new HashMap<>())
                    .build();
        }

        // Get all cards
        List<Card> allCards = cardRepository.findAllByBoardIds(boardIds);

        // Calculate statistics
        long totalCards = allCards.size();
        long completedCards = allCards.stream().filter(Card::isCompleted).count();
        long overdueTasks = allCards.stream()
                .filter(c -> !c.isCompleted() && c.getDueDate() != null && c.getDueDate().isBefore(LocalDateTime.now()))
                .count();

        // This week's stats
        LocalDateTime weekStart = LocalDateTime.now().minusDays(7);
        List<Card> completedThisWeek = cardRepository.findCompletedCardsSince(boardIds, weekStart);
        List<Card> createdThisWeek = cardRepository.findCardsCreatedSince(boardIds, weekStart);

        // Completion rate
        double completionRate = totalCards > 0 ? (completedCards * 100.0) / totalCards : 0;

        // Cards by priority
        Map<String, Long> cardsByPriority = allCards.stream()
                .filter(c -> c.getPriority() != null)
                .collect(Collectors.groupingBy(
                        c -> c.getPriority().name(),
                        Collectors.counting()
                ));

        // Cards by label
        Map<String, Long> cardsByLabel = new HashMap<>();
        for (Card card : allCards) {
            if (card.getLabelIds() != null) {
                for (String labelId : card.getLabelIds()) {
                    cardsByLabel.merge(labelId, 1L, Long::sum);
                }
            }
        }

        // Tasks completed by day (last 7 days)
        Map<String, Long> tasksCompletedByDay = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE");
        for (int i = 6; i >= 0; i--) {
            LocalDateTime day = LocalDateTime.now().minusDays(i);
            String dayName = day.format(formatter);
            long count = completedThisWeek.stream()
                    .filter(c -> c.getCompletedAt() != null &&
                            c.getCompletedAt().toLocalDate().equals(day.toLocalDate()))
                    .count();
            tasksCompletedByDay.put(dayName, count);
        }

        // Tasks by member
        Map<String, Long> tasksByMember = new HashMap<>();
        for (Card card : allCards) {
            if (card.getAssigneeIds() != null) {
                for (String assigneeId : card.getAssigneeIds()) {
                    tasksByMember.merge(assigneeId, 1L, Long::sum);
                }
            }
        }

        return AnalyticsResponse.builder()
                .totalBoards(boards.size())
                .totalCards(totalCards)
                .completedCards(completedCards)
                .overdueTasks(overdueTasks)
                .tasksCompletedThisWeek(completedThisWeek.size())
                .tasksCreatedThisWeek(createdThisWeek.size())
                .completionRate(Math.round(completionRate * 100.0) / 100.0)
                .cardsByPriority(cardsByPriority)
                .cardsByLabel(cardsByLabel)
                .tasksCompletedByDay(tasksCompletedByDay)
                .tasksByMember(tasksByMember)
                .build();
    }

    public AnalyticsResponse getBoardAnalytics(String boardId, String userId) {
        List<Card> cards = cardRepository.findByBoardIdAndArchivedFalse(boardId);

        long totalCards = cards.size();
        long completedCards = cards.stream().filter(Card::isCompleted).count();
        long overdueTasks = cards.stream()
                .filter(c -> !c.isCompleted() && c.getDueDate() != null && c.getDueDate().isBefore(LocalDateTime.now()))
                .count();

        LocalDateTime weekStart = LocalDateTime.now().minusDays(7);
        long tasksCompletedThisWeek = cards.stream()
                .filter(c -> c.isCompleted() && c.getCompletedAt() != null && c.getCompletedAt().isAfter(weekStart))
                .count();
        long tasksCreatedThisWeek = cards.stream()
                .filter(c -> c.getCreatedAt() != null && c.getCreatedAt().isAfter(weekStart))
                .count();

        double completionRate = totalCards > 0 ? (completedCards * 100.0) / totalCards : 0;

        Map<String, Long> cardsByPriority = cards.stream()
                .filter(c -> c.getPriority() != null)
                .collect(Collectors.groupingBy(
                        c -> c.getPriority().name(),
                        Collectors.counting()
                ));

        Map<String, Long> cardsByLabel = new HashMap<>();
        for (Card card : cards) {
            if (card.getLabelIds() != null) {
                for (String labelId : card.getLabelIds()) {
                    cardsByLabel.merge(labelId, 1L, Long::sum);
                }
            }
        }

        Map<String, Long> tasksByMember = new HashMap<>();
        for (Card card : cards) {
            if (card.getAssigneeIds() != null) {
                for (String assigneeId : card.getAssigneeIds()) {
                    tasksByMember.merge(assigneeId, 1L, Long::sum);
                }
            }
        }

        return AnalyticsResponse.builder()
                .totalBoards(1)
                .totalCards(totalCards)
                .completedCards(completedCards)
                .overdueTasks(overdueTasks)
                .tasksCompletedThisWeek(tasksCompletedThisWeek)
                .tasksCreatedThisWeek(tasksCreatedThisWeek)
                .completionRate(Math.round(completionRate * 100.0) / 100.0)
                .cardsByPriority(cardsByPriority)
                .cardsByLabel(cardsByLabel)
                .tasksByMember(tasksByMember)
                .build();
    }
}
