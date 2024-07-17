package com.todo.service;

import com.todo.dto.ToDoRequestDto;
import com.todo.dto.ToDoResponseDto;

import java.util.List;

public interface IToDoService {

    ToDoResponseDto createToDo(ToDoRequestDto toDoRequestDto);
    ToDoResponseDto getToDoById(Long id);
    ToDoResponseDto getToDoByTitle(String title);
    List<ToDoResponseDto> getAllToDos();
    ToDoResponseDto updateToDo(Long id, ToDoRequestDto toDoRequestDto);
    void deleteToDo(Long id);

}
