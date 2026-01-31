package com.taskflow.server.repository;

import com.taskflow.server.model.Card;
import com.taskflow.server.model.Priority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CardRepository extends MongoRepository<Card, String> {

    List<Card> findByListIdAndArchivedFalseOrderByPositionAsc(String listId);

    List<Card> findByBoardIdAndArchivedFalse(String boardId);

    List<Card> findByBoardIdAndArchivedTrue(String boardId);

    Page<Card> findByBoardIdAndArchivedFalse(String boardId, Pageable pageable);

    @Query("{ 'assigneeIds': ?0, 'archived': false }")
    Page<Card> findByAssigneeId(String userId, Pageable pageable);

    @Query("{ 'assigneeIds': ?0, 'archived': false, 'completed': false }")
    List<Card> findActiveCardsByAssigneeId(String userId);

    @Query("{ 'dueDate': { '$lte': ?0 }, 'completed': false, 'archived': false }")
    List<Card> findOverdueCards(LocalDateTime date);

    @Query("{ 'dueDate': { '$gte': ?0, '$lte': ?1 }, 'completed': false, 'archived': false }")
    List<Card> findCardsDueBetween(LocalDateTime start, LocalDateTime end);

    long countByBoardIdAndArchivedFalse(String boardId);

    long countByBoardIdAndCompletedTrueAndArchivedFalse(String boardId);

    long countByListIdAndArchivedFalse(String listId);

    void deleteByBoardId(String boardId);

    void deleteByListId(String listId);

    // Complex queries for analytics
    @Query("{ 'boardId': { '$in': ?0 }, 'completed': true, 'completedAt': { '$gte': ?1 } }")
    List<Card> findCompletedCardsSince(List<String> boardIds, LocalDateTime since);

    @Query("{ 'boardId': { '$in': ?0 }, 'createdAt': { '$gte': ?1 } }")
    List<Card> findCardsCreatedSince(List<String> boardIds, LocalDateTime since);

    @Query("{ 'boardId': { '$in': ?0 }, 'archived': false }")
    List<Card> findAllByBoardIds(List<String> boardIds);

    // Filter queries
    @Query("{ 'boardId': ?0, 'archived': false, 'priority': ?1 }")
    List<Card> findByBoardIdAndPriority(String boardId, Priority priority);

    @Query("{ 'boardId': ?0, 'archived': false, 'labelIds': ?1 }")
    List<Card> findByBoardIdAndLabelId(String boardId, String labelId);

    @Query("{ 'boardId': ?0, 'archived': false, 'assigneeIds': ?1 }")
    List<Card> findByBoardIdAndAssigneeId(String boardId, String assigneeId);

    // Search
    @Query("{ 'boardId': ?0, 'archived': false, '$or': [ { 'title': { '$regex': ?1, '$options': 'i' } }, { 'description': { '$regex': ?1, '$options': 'i' } } ] }")
    List<Card> searchInBoard(String boardId, String query);

    @Query("{ '$or': [ { 'title': { '$regex': ?0, '$options': 'i' } }, { 'description': { '$regex': ?0, '$options': 'i' } } ], 'boardId': { '$in': ?1 }, 'archived': false }")
    Page<Card> searchCards(String query, List<String> boardIds, Pageable pageable);
}
