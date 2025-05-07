package org.example.restfuldemo.repository;

import org.example.restfuldemo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
