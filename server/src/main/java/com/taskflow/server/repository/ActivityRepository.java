package com.taskflow.server.repository;

import com.taskflow.server.model.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {

    Page<Activity> findByBoardIdOrderByCreatedAtDesc(String boardId, Pageable pageable);

    Page<Activity> findByCardIdOrderByCreatedAtDesc(String cardId, Pageable pageable);

    Page<Activity> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);

    List<Activity> findByBoardIdAndCreatedAtAfterOrderByCreatedAtDesc(String boardId, LocalDateTime after);

    void deleteByBoardId(String boardId);

    void deleteByCardId(String cardId);
}
