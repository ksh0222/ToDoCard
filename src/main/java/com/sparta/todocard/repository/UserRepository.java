package com.sparta.todocard.repository;

import com.sparta.todocard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;;import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}