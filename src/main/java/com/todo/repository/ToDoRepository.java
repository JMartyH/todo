package com.todo.repository;

import com.todo.entity.ToDoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToDoRepository extends JpaRepository<ToDoEntity, Long>{

    Optional<ToDoEntity> getByTitle(String title);

    @Query("SELECT t FROM ToDoEntity t " +
            "ORDER BY CASE " +
            "   WHEN t.status = 'PENDING' THEN 1 " +
            "   WHEN t.status = 'COMPLETED' THEN 2 " +
            "   WHEN t.status = 'CANCELLED' THEN 3 " +
            "   ELSE 4 " +
            "END," +                  // End of CASE for status
            " t.title ASC")            // Sort by title in ascending order
    Page<ToDoEntity> findAllSorted(Pageable pageable);

    Page<ToDoEntity> findByStatus(ToDoEntity.Status status, Pageable pageable);

    @Query("SELECT t FROM ToDoEntity t " +
            "WHERE (LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:status IS NULL OR t.status = :status)")
    Page<ToDoEntity> searchToDos(@Param("keyword") String keyword,
                                 @Param("status") ToDoEntity.Status status,
                                 Pageable pageable);
}
