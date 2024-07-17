package com.todo.dto;

import com.todo.entity.ToDoEntity;

import java.time.LocalDateTime;

public record ToDoRequestDto(
    String title,
    String description,
    ToDoEntity.Status status,
    LocalDateTime dueDate
) {
}
