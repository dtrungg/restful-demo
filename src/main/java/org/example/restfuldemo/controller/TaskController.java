package org.example.restfuldemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.restfuldemo.constants.Constants;
import org.example.restfuldemo.dto.request.TaskRequest;
import org.example.restfuldemo.dto.response.ResponseData;
import org.example.restfuldemo.dto.response.ResponseUtil;
import org.example.restfuldemo.dto.response.task.TaskResponse;
import org.example.restfuldemo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Task Management", description = "APIs for managing tasks")
@RestController
@RequestMapping("${api.prefix}/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @Operation(summary = "Get all tasks", description = "Retrieves a list of all tasks")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Tasks retrieved successfully",
            content = @Content(schema = @Schema(implementation = ResponseData.class))), @ApiResponse(
            responseCode = "404", description = "No tasks found", content = @Content)})
    @GetMapping
    public ResponseEntity<ResponseData<List<TaskResponse>>> getAllTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseUtil.success(taskService.getAllTasks(),
                Constants.TASK_LIST_SUCCESSFUL_MESSAGE
        ));
    }

    @Operation(summary = "Get task by ID", description = "Retrieves a task by its unique ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Task retrieved successfully",
            content = @Content(schema = @Schema(implementation = ResponseData.class))), @ApiResponse(
            responseCode = "404", description = "Task not found", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<TaskResponse>> getTaskById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseUtil.success(taskService.getTaskById(id),
                Constants.TASK_SUCCESSFUL_MESSAGE
        ));
    }

    @Operation(summary = "Create a new task", description = "Creates a new task with the provided details")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Task created successfully",
            content = @Content(schema = @Schema(implementation = ResponseData.class))), @ApiResponse(
            responseCode = "400", description = "Bad Request", content = @Content)})
    @PostMapping
    public ResponseEntity<ResponseData<TaskResponse>> createTask(@Valid @RequestBody TaskRequest taskRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseUtil.success(taskService.createTask(taskRequest),
                Constants.TASK_CREATE_SUCCESSFUL_MESSAGE
        ));
    }

    @Operation(summary = "Update task", description = "Updates a task's details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Task updated successfully",
            content = @Content(schema = @Schema(implementation = ResponseData.class))), @ApiResponse(
            responseCode = "404", description = "Task not found", content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<TaskResponse>> updateTask(
            @Valid @RequestBody TaskRequest taskRequest,
            @PathVariable Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseUtil.success(taskService.updateTask(taskRequest, id),
                Constants.TASK_UPDATE_SUCCESSFUL_MESSAGE
        ));
    }

    @Operation(summary = "Delete task", description = "Deletes a task by its unique ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Task deleted successfully",
            content = @Content(schema = @Schema(implementation = ResponseData.class))), @ApiResponse(
            responseCode = "404", description = "Task not found", content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<Void>> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ResponseUtil.success(Constants.TASK_DELETE_SUCCESSFUL_MESSAGE));
    }

    @Operation(summary = "Get tasks by user ID", description = "Retrieves tasks assigned to a user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Tasks retrieved successfully",
            content = @Content(schema = @Schema(implementation = ResponseData.class))), @ApiResponse(
            responseCode = "404", description = "No tasks found", content = @Content)})
    @GetMapping("/user/{id}")
    public ResponseEntity<ResponseData<List<TaskResponse>>> getTasksByUserId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseUtil.success(taskService.getTasksByUserId(id),
                Constants.TASK_LIST_SUCCESSFUL_MESSAGE
        ));
    }
}