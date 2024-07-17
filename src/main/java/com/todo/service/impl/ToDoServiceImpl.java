package com.todo.service.impl;

import com.todo.dto.ToDoRequestDto;
import com.todo.dto.ToDoResponseDto;
import com.todo.entity.ToDoEntity;
import com.todo.mappper.ToDoMapper;
import com.todo.repository.ToDoRepository;
import com.todo.service.IToDoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ToDoServiceImpl implements IToDoService {

    private final ToDoRepository toDoRepository;
    private final ToDoMapper toDoMapper;

    @Override
    public ToDoResponseDto createToDo(ToDoRequestDto toDoRequestDto) {
        ToDoEntity toDoEntity = new ToDoEntity();
        toDoEntity.setTitle(toDoRequestDto.title());
        toDoEntity.setDescription(toDoRequestDto.description());
        toDoEntity.setCreationDate(LocalDateTime.now());
        toDoEntity.setDueDate(toDoRequestDto.dueDate());
        toDoEntity.setStatus(toDoRequestDto.status());
        log.info("Attempting to save: {}", toDoRequestDto.title());

        toDoRepository.save(toDoEntity);
        log.info("Successfully saved: {}", toDoRequestDto.title());
        return toDoMapper.toDoToToDoResponseDto(toDoEntity);
    }

    @Override
    public ToDoResponseDto getToDoById(Long id) {

        ToDoEntity toDoEntity = toDoRepository.findById(id).orElseThrow(() -> new RuntimeException("id")); // Change exception
        log.info("Fetched todo: {}", toDoEntity.getId());
        return toDoMapper.toDoToToDoResponseDto(toDoEntity);
    }

    @Override
    public ToDoResponseDto getToDoByTitle(String title){
        ToDoEntity toDoEntity = toDoRepository.getByTitle(title).orElseThrow(() -> new RuntimeException("title"));
        log.info("Fetched todo: {}", toDoEntity.getTitle());
        return toDoMapper.toDoToToDoResponseDto(toDoEntity);
    }

    @Override
    public List<ToDoResponseDto> getAllToDos() {
        List<ToDoEntity> toDos = toDoRepository.findAll();
        log.info("Fetching all todos");
        return toDos.stream()
                .map(toDoEntity -> toDoMapper.toDoToToDoResponseDto(toDoEntity))
                .toList();
    }

    @Override
    public ToDoResponseDto updateToDo(Long id, ToDoRequestDto toDoRequestDto) {

        ToDoEntity toDoEntity = toDoRepository.findById(id).orElseThrow(() -> new RuntimeException("asd")); // Change exception
        log.info("Fetched todo: {}", toDoEntity.getId());
        toDoEntity.setTitle(toDoRequestDto.title());
        toDoEntity.setDescription(toDoRequestDto.description());
        toDoEntity.setDueDate(toDoRequestDto.dueDate()); // TODO: Could cause error if user input is incorrect
        toDoEntity.setStatus(toDoRequestDto.status());
        log.info("Attempting to save: {}", toDoRequestDto.title());

        toDoRepository.save(toDoEntity);
        log.info("Successfully saved: {}", toDoRequestDto.title());
        return toDoMapper.toDoToToDoResponseDto(toDoEntity);
    }

    @Override
    public void deleteToDo(Long id) {
        ToDoEntity toDoEntity = toDoRepository.findById(id).orElseThrow(() -> new RuntimeException("asd")); // Change exception
        log.info("Deleting: {}", toDoEntity.getId());
        toDoRepository.deleteById(id);
    }
}