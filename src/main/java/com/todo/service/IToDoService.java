package com.todo.service;

import com.todo.dto.ToDoRequestDto;
import com.todo.dto.ToDoResponseDto;
import com.todo.entity.ToDoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IToDoService {

    ToDoResponseDto createToDo(ToDoRequestDto toDoRequestDto);
    ToDoResponseDto getToDoById(Long id);
    ToDoResponseDto getToDoByTitle(String title);
    List<ToDoResponseDto> getAllToDos();
    Page<ToDoResponseDto> getAllToDosPage(ToDoEntity.Status status, Pageable pageable);
    Page<ToDoResponseDto> searchToDos(String keyword, ToDoEntity.Status status, Pageable pageable);
    ToDoResponseDto updateToDo(Long id, ToDoRequestDto toDoRequestDto);
    void deleteToDo(Long id);

}
