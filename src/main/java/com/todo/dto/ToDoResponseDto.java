package com.todo.dto;

import com.todo.entity.ToDoEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ToDoResponseDto (
        Long id,
        String title,
        String description,
        LocalDateTime creationDate,
        LocalDateTime dueDate,
        ToDoEntity.Status status
) implements Serializable {
}
