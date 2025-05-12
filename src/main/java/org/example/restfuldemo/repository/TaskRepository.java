package org.example.restfuldemo.repository;

import org.example.restfuldemo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for Task domain objects.
 * Provides CRUD operations and custom query methods for task management.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
    /**
     * Retrieves all tasks assigned to a specific user.
     *
     * @param userId The unique identifier of the user whose tasks to retrieve
     * @return List of Task objects assigned to the specified user (never null, may be empty)
     */
    List<Task> findByUserId(Long userId);
}
