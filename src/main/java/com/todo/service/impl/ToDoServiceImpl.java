package com.todo.service.impl;

import com.todo.dto.ToDoRequestDto;
import com.todo.dto.ToDoResponseDto;
import com.todo.entity.ToDoEntity;
import com.todo.mappper.ToDoMapper;
import com.todo.repository.ToDoRepository;
import com.todo.service.IToDoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ToDoServiceImpl implements IToDoService {

    private final ToDoRepository toDoRepository;
    private final ToDoMapper toDoMapper;

    @CacheEvict(value = "todos", allEntries = true)
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
    @Cacheable(value = "todos", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #status")
    @Override
    public Page<ToDoResponseDto> getAllToDosPage(ToDoEntity.Status status, Pageable pageable){
        Page<ToDoEntity> todos;
        if (status == null) {
            todos = toDoRepository.findAll(pageable);
        } else {
            todos = toDoRepository.findByStatus(status, pageable);
        }

        return todos
                .map(toDoEntity -> toDoMapper.toDoToToDoResponseDto(toDoEntity));
    }

    @Override
    public Page<ToDoResponseDto> searchToDos(String keyword, ToDoEntity.Status status, Pageable pageable) {
        Page<ToDoEntity> toDoEntities = toDoRepository.searchToDos(keyword, status, pageable);
        return toDoEntities.map(toDoEntity -> toDoMapper.toDoToToDoResponseDto(toDoEntity));
    }

    //TODO: Might also add cache invalidation here
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

    //TODO: Might also add cache invalidation here
    @Override
    public void deleteToDo(Long id) {
        ToDoEntity toDoEntity = toDoRepository.findById(id).orElseThrow(() -> new RuntimeException("asd")); // Change exception
        log.info("Deleting: {}", toDoEntity.getId());
        toDoRepository.deleteById(id);
    }
}
