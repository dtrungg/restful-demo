package org.example.restfuldemo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.restfuldemo.constants.Constants;
import org.example.restfuldemo.dto.request.TaskRequest;
import org.example.restfuldemo.dto.response.task.TaskResponse;
import org.example.restfuldemo.entity.Task;
import org.example.restfuldemo.entity.User;
import org.example.restfuldemo.exception.ResourceNotFoundException;
import org.example.restfuldemo.mapper.TaskMapper;
import org.example.restfuldemo.repository.TaskRepository;
import org.example.restfuldemo.repository.UserRepository;
import org.example.restfuldemo.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the TaskService interface providing CRUD operations for tasks.
 * Handles business logic related to task management including creation, retrieval,
 * update, and deletion of tasks with proper error handling.
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    /**
     * Retrieves all tasks from the repository and converts them to response DTOs.
     *
     * @return List of TaskResponse objects representing all tasks in the system
     */
    @Override
    public List<TaskResponse> getAllTasks() {
        List<Task> list = taskRepository.findAll();
        List<TaskResponse> taskResponsesList = list.stream()
                .map(TaskMapper.mapper::mapToResponse)
                .collect(Collectors.toList());
        return taskResponsesList;
    }

    /**
     * Fetches a specific task by its ID.
     *
     * @param id The unique identifier of the task to retrieve
     * @return TaskResponse object representing the requested task
     * @throws ResourceNotFoundException if task with the given ID doesn't exist
     */
    @Override
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.TASK_NOT_FOUND));
        return TaskMapper.mapper.mapToResponse(task);
    }

    /**
     * Creates a new task based on the provided request data.
     * Validates user existence before task creation and handles database transactions.
     *
     * @param taskRequest DTO containing task creation data
     * @return TaskResponse representing the newly created task
     * @throws ResourceNotFoundException if specified user doesn't exist
     */
    @Override
    @Transactional
    public TaskResponse createTask(TaskRequest taskRequest) {
        Task task = TaskMapper.mapper.mapToEntity(taskRequest);
        User user = userRepository.findById(taskRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(Constants.USER_NOT_FOUND));
        task.setUser(user);

        // save to database
        Task newTask = taskRepository.save(task);
        return TaskMapper.mapper.mapToResponse(newTask);
    }

    /**
     * Updates an existing task with new data.
     * Validates both task and user existence before performing update.
     *
     * @param taskRequest DTO containing updated task data
     * @param id          Unique identifier of the task to update
     * @return TaskResponse representing the updated task
     * @throws ResourceNotFoundException if task or user doesn't exist
     */
    @Override
    @Transactional
    public TaskResponse updateTask(TaskRequest taskRequest, Long id) {
        User user = userRepository.findById(taskRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(Constants.USER_NOT_FOUND));
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.TASK_NOT_FOUND));
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskRequest.getStatus());
        task.setDueDate(taskRequest.getDueDate());
        task.setUser(user);
        // save to database
        Task updatedTask = taskRepository.save(task);
        return TaskMapper.mapper.mapToResponse(updatedTask);
    }

    /**
     * Deletes a task by its unique identifier.
     *
     * @param id The unique identifier of the task to delete
     * @throws ResourceNotFoundException if task with the given ID doesn't exist
     */
    @Override
    @Transactional
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.TASK_NOT_FOUND));
        taskRepository.delete(task);
    }

    /**
     * Retrieves all tasks associated with a specific user.
     *
     * @param id Unique identifier of the user whose tasks to retrieve
     * @return List of TaskResponse objects for the specified user's tasks
     * @throws ResourceNotFoundException if user doesn't exist
     */
    @Override
    public List<TaskResponse> getTasksByUserId(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.USER_NOT_FOUND));
        List<Task> task = taskRepository.findByUserId(user.getId());
        List<TaskResponse> taskResponseList = task.stream().map(TaskMapper.mapper::mapToResponse).toList();
        return taskResponseList;
    }
}
