package com.todo.controller;

import com.todo.dto.ToDoRequestDto;
import com.todo.dto.ToDoResponseDto;
import com.todo.entity.ToDoEntity;
import com.todo.service.IToDoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
@Slf4j
public class ToDoController {

    private final IToDoService toDoService;

    @PostMapping
    public ResponseEntity<ToDoResponseDto> createToDo(@RequestBody ToDoRequestDto toDoRequestDto){
        ToDoResponseDto toDoResponseDto = toDoService.createToDo(toDoRequestDto);
        URI location = URI.create(String.format("api/v1/todos/%s", toDoResponseDto.id()));
        log.info("created on: {}", location);
        return ResponseEntity.created(location).body(toDoResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDoResponseDto> getToDoById(@PathVariable Long id){
        return ResponseEntity.ok(toDoService.getToDoById(id));
    }

    @GetMapping("/{title}")
    public ResponseEntity<ToDoResponseDto> getToDoByTitle(@PathVariable String title){
        return ResponseEntity.ok(toDoService.getToDoByTitle(title));
    }

//    @GetMapping
//    public ResponseEntity<List<ToDoResponseDto>> getAllToDos(){
//        return ResponseEntity.ok(toDoService.getAllToDos());
//    }

    @GetMapping
    public ResponseEntity<Page<ToDoResponseDto>> getAllToDosPage(@RequestParam(required = false) ToDoEntity.Status status,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(toDoService.getAllToDosPage(status, pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ToDoResponseDto>> searchToDos(
            @RequestParam String keyword,
            @RequestParam(required = false) ToDoEntity.Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ToDoResponseDto> searchResults = toDoService.searchToDos(keyword, status, pageable);
        return ResponseEntity.ok(searchResults);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDoResponseDto> updateToDo(@PathVariable Long id, @RequestBody ToDoRequestDto toDoRequestDto){
        return ResponseEntity.ok(toDoService.updateToDo(id, toDoRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToDo(@PathVariable Long id){
        toDoService.deleteToDo(id);
        return ResponseEntity.noContent().build();
    }


}
