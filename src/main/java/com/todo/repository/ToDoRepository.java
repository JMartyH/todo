package com.todo.repository;

import com.todo.entity.ToDoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToDoRepository extends JpaRepository<ToDoEntity, Long>, PagingAndSortingRepository<ToDoEntity, Long> {

    Optional<ToDoEntity> getByTitle(String title);

    Page<ToDoEntity> findByStatus(ToDoEntity.Status status, Pageable pageable);


}
