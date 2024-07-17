package com.todo.repository;

import com.todo.entity.ToDoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToDoRepository extends JpaRepository<ToDoEntity, Long> {

    Optional<ToDoEntity> getByTitle(String title);


}
