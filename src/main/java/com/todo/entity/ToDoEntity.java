package com.todo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.generator.values.internal.GeneratedValuesHelper;

import java.time.LocalDateTime;

@Entity
@Table(name = "todos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToDoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String title;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime dueDate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public ToDoEntity(String title, String description, LocalDateTime creationDate, LocalDateTime dueDate, Status status) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    // Enum for status
    public enum Status {
        PENDING,
        COMPLETED,
        CANCELLED
    }


}
