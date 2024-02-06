package com.sparta.todocard.repository;

import com.sparta.todocard.entity.ToDoCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface 	PostRepository extends JpaRepository<ToDoCard, Long> {
    List<ToDoCard> findAllByOrderByModifiedAtDesc();
    Optional<ToDoCard> findByUsername(String username);
}