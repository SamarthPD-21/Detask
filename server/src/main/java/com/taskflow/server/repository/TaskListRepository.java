package com.taskflow.server.repository;

import com.taskflow.server.model.TaskList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskListRepository extends MongoRepository<TaskList, String> {

    List<TaskList> findByBoardIdAndArchivedFalseOrderByPositionAsc(String boardId);

    List<TaskList> findByBoardIdOrderByPositionAsc(String boardId);

    List<TaskList> findByBoardIdAndArchivedTrue(String boardId);

    int countByBoardIdAndArchivedFalse(String boardId);

    void deleteByBoardId(String boardId);
}
