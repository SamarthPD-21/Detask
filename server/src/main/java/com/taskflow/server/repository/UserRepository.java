package com.taskflow.server.repository;

import com.taskflow.server.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmailOrUsername(String email, String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByVerificationToken(String token);

    Optional<User> findByPasswordResetToken(String token);

    List<User> findByIdIn(List<String> ids);

    List<User> findByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCase(String username, String fullName);
}
