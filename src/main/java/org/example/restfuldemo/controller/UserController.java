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
import org.example.restfuldemo.dto.request.UserRequest;
import org.example.restfuldemo.dto.response.ResponseData;
import org.example.restfuldemo.dto.response.ResponseUtil;
import org.example.restfuldemo.dto.response.user.UserResponse;
import org.example.restfuldemo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "User Management", description = "APIs for managing users")
@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "User created successfully",
            content = @Content(schema = @Schema(implementation = ResponseData.class))), @ApiResponse(
            responseCode = "400", description = "Bad Request", content = @Content)})
    @PostMapping
    public ResponseEntity<ResponseData<UserResponse>> createUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseUtil.success(userService.createUser(userRequest),
                        Constants.USER_CREATE_SUCCESSFUL_MESSAGE
                ));
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all users")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Users retrieved successfully",
            content = @Content(schema = @Schema(implementation = ResponseData.class))), @ApiResponse(
            responseCode = "404", description = "No users found", content = @Content)})
    @GetMapping
    public ResponseEntity<ResponseData<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtil.success(userService.getAllUsers(), Constants.USER_LIST_SUCCESSFUL_MESSAGE));
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a user by their unique ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User retrieved successfully",
            content = @Content(schema = @Schema(implementation = ResponseData.class))), @ApiResponse(
            responseCode = "404", description = "User not found", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<UserResponse>> getUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtil.success(userService.getUserById(id), Constants.USER_SUCCESSFUL_MESSAGE));
    }

    @Operation(summary = "Update user", description = "Updates a user's details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User updated successfully"), @ApiResponse(
            responseCode = "404", description = "User not found", content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        userService.updateUser(id, userRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtil.success(Constants.USER_UPDATE_SUCCESSFUL_MESSAGE));
    }

    @Operation(summary = "Delete user", description = "Deletes a user by their unique ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "User deleted successfully"), @ApiResponse(
            responseCode = "404", description = "User not found", content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ResponseUtil.success(Constants.USER_DELETE_SUCCESSFUL_MESSAGE));
    }
}