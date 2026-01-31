package com.taskflow.server.repository;

import com.taskflow.server.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends MongoRepository<Board, String> {

    Page<Board> findByOwnerIdAndArchivedFalse(String ownerId, Pageable pageable);

    @Query("{ '$or': [ { 'ownerId': ?0 }, { 'memberIds': ?0 } ], 'archived': false }")
    Page<Board> findByOwnerIdOrMemberIdsContainingAndArchivedFalse(String userId, Pageable pageable);

    @Query("{ '$or': [ { 'ownerId': ?0 }, { 'memberIds': ?0 } ], 'archived': false }")
    List<Board> findAllByOwnerIdOrMemberIdsContainingAndArchivedFalse(String userId);

    Page<Board> findByArchivedTrue(Pageable pageable);

    @Query("{ '$or': [ { 'ownerId': ?0 }, { 'memberIds': ?0 } ], 'archived': true }")
    Page<Board> findArchivedByOwnerIdOrMemberIds(String userId, Pageable pageable);

    @Query("{ 'name': { '$regex': ?0, '$options': 'i' }, '$or': [ { 'ownerId': ?1 }, { 'memberIds': ?1 } ] }")
    Page<Board> searchByNameAndUser(String name, String userId, Pageable pageable);

    long countByOwnerId(String ownerId);

    @Query(value = "{ '$or': [ { 'ownerId': ?0 }, { 'memberIds': ?0 } ] }", count = true)
    long countByOwnerIdOrMemberIds(String userId);
}
