package com.todo.mappper;

import com.todo.dto.ToDoResponseDto;
import com.todo.entity.ToDoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToDoMapper {

    ToDoResponseDto toDoToToDoResponseDto(ToDoEntity toDoEntity);

}
