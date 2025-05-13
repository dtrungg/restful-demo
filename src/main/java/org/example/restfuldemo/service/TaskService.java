package org.example.restfuldemo.service;

import org.example.restfuldemo.dto.request.TaskRequest;
import org.example.restfuldemo.dto.response.task.TaskResponse;

import java.util.List;

public interface TaskService {
    /**
     * Retrieves all tasks from the repository and converts them to response DTOs.
     *
     * @return List of TaskResponse objects representing all tasks in the system
     */
    List<TaskResponse> getAllTasks();

    /**
     * Fetches a specific task by its ID.
     *
     * @param id The unique identifier of the task to retrieve
     * @return TaskResponse object representing the requested task
     */
    TaskResponse getTaskById(Long id);

    /**
     * Creates a new task based on the provided request data.
     * Validates user existence before task creation and handles database transactions.
     *
     * @param taskRequest DTO containing task creation data
     * @return TaskResponse representing the newly created task
     */
    TaskResponse createTask(TaskRequest taskRequest);

    /**
     * Updates an existing task with new data.
     * Validates both task and user existence before performing update.
     *
     * @param taskRequest DTO containing updated task data
     * @param id          Unique identifier of the task to update
     * @return TaskResponse representing the updated task
     */
    TaskResponse updateTask(TaskRequest taskRequest, Long id);

    /**
     * Deletes a task by its unique identifier.
     *
     * @param id The unique identifier of the task to delete
     */
    void deleteTask(Long id);

    /**
     * Retrieves all tasks associated with a specific user.
     *
     * @param id Unique identifier of the user whose tasks to retrieve
     * @return List of TaskResponse objects for the specified user's tasks
     */
    List<TaskResponse> getTasksByUserId(Long id);
}
