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

@Tag(name = Constants.TASK_MANAGEMENT_TAG_NAME, description = Constants.TASK_MANAGEMENT_TAG_DESCRIPTION)
@RestController
@RequestMapping("${api.prefix}/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @Operation(summary = Constants.GET_ALL_TASKS_SUMMARY, description = Constants.GET_ALL_TASKS_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.HTTP_OK, description = Constants.TASK_SUCCESSFUL_MESSAGE,
                    content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = Constants.HTTP_BAD_REQUEST, description = Constants.NO_TASKS_FOUND_ERROR,
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<ResponseData<List<TaskResponse>>> getAllTasks() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtil.success(taskService.getAllTasks(), Constants.TASK_LIST_SUCCESSFUL_MESSAGE));
    }

    @Operation(summary = Constants.GET_TASK_BY_ID_SUMMARY, description = Constants.GET_TASK_BY_ID_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.HTTP_OK, description = Constants.TASK_SUCCESSFUL_MESSAGE,
                    content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = Constants.HTTP_BAD_REQUEST, description = Constants.TASK_NOT_FOUND_ERROR,
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<TaskResponse>> getTaskById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtil.success(taskService.getTaskById(id), Constants.TASK_SUCCESSFUL_MESSAGE));
    }

    @Operation(summary = Constants.CREATE_TASK_SUMMARY, description = Constants.CREATE_TASK_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.HTTP_CREATED, description = Constants.TASK_CREATE_SUCCESSFUL_MESSAGE,
                    content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = Constants.HTTP_BAD_REQUEST, description = Constants.BAD_REQUEST_ERROR,
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<ResponseData<TaskResponse>> createTask(@Valid @RequestBody TaskRequest taskRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseUtil.success(taskService.createTask(taskRequest),
                        Constants.TASK_CREATE_SUCCESSFUL_MESSAGE
                ));
    }

    @Operation(summary = Constants.UPDATE_TASK_SUMMARY, description = Constants.UPDATE_TASK_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.HTTP_OK, description = Constants.TASK_UPDATE_SUCCESSFUL_MESSAGE,
                    content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = Constants.HTTP_BAD_REQUEST, description = Constants.TASK_NOT_FOUND_ERROR,
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<TaskResponse>> updateTask(
            @Valid @RequestBody TaskRequest taskRequest,
            @PathVariable Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtil.success(taskService.updateTask(taskRequest, id),
                        Constants.TASK_UPDATE_SUCCESSFUL_MESSAGE
                ));
    }

    @Operation(summary = Constants.DELETE_TASK_SUMMARY, description = Constants.DELETE_TASK_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.HTTP_NO_CONTENT,
                    description = Constants.TASK_DELETE_SUCCESSFUL_MESSAGE,
                    content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = Constants.HTTP_BAD_REQUEST, description = Constants.TASK_NOT_FOUND_ERROR,
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = Constants.GET_TASKS_BY_USER_ID_SUMMARY,
            description = Constants.GET_TASKS_BY_USER_ID_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.HTTP_OK, description = Constants.TASK_SUCCESSFUL_MESSAGE,
                    content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = Constants.HTTP_BAD_REQUEST, description = Constants.NO_TASKS_FOUND_ERROR,
                    content = @Content)
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<ResponseData<List<TaskResponse>>> getTasksByUserId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtil.success(taskService.getTasksByUserId(id), Constants.TASK_LIST_SUCCESSFUL_MESSAGE));
    }
}