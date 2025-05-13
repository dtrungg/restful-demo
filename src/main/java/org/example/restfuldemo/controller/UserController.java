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


@Tag(name = Constants.USER_MANAGEMENT_TAG_NAME, description = Constants.USER_MANAGEMENT_TAG_DESCRIPTION)
@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = Constants.CREATE_USER_SUMMARY, description = Constants.CREATE_USER_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.HTTP_CREATED, description = Constants.USER_CREATE_SUCCESSFUL_MESSAGE,
                    content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = Constants.HTTP_BAD_REQUEST, description = Constants.BAD_REQUEST_ERROR,
                    content = @Content),
            @ApiResponse(responseCode = Constants.HTTP_CONFLICT, description = Constants.USER_EXIST, content = @Content)
    })
    @PostMapping
    public ResponseEntity<ResponseData<UserResponse>> createUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseUtil.success(userService.createUser(userRequest),
                        Constants.USER_CREATE_SUCCESSFUL_MESSAGE
                ));
    }

    @Operation(summary = Constants.GET_ALL_USERS_SUMMARY, description = Constants.GET_ALL_USERS_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.HTTP_OK, description = Constants.USER_SUCCESSFUL_MESSAGE,
                    content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = Constants.HTTP_BAD_REQUEST, description = Constants.NO_USERS_FOUND_ERROR,
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<ResponseData<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtil.success(userService.getAllUsers(), Constants.USER_LIST_SUCCESSFUL_MESSAGE));
    }

    @Operation(summary = Constants.GET_USER_BY_ID_SUMMARY, description = Constants.GET_USER_BY_ID_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.HTTP_OK, description = Constants.USER_SUCCESSFUL_MESSAGE,
                    content = @Content(schema = @Schema(implementation = ResponseData.class))),
            @ApiResponse(responseCode = Constants.HTTP_BAD_REQUEST, description = Constants.USER_NOT_FOUND_ERROR,
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<UserResponse>> getUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtil.success(userService.getUserById(id), Constants.USER_SUCCESSFUL_MESSAGE));
    }

    @Operation(summary = Constants.UPDATE_USER_SUMMARY, description = Constants.UPDATE_USER_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.HTTP_OK, description = Constants.USER_UPDATE_SUCCESSFUL_MESSAGE),
            @ApiResponse(responseCode = Constants.HTTP_BAD_REQUEST, description = Constants.USER_NOT_FOUND_ERROR,
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<UserResponse>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest userRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseUtil.success(userService.updateUser(id, userRequest),
                        Constants.USER_UPDATE_SUCCESSFUL_MESSAGE
                ));
    }

    @Operation(summary = Constants.DELETE_USER_SUMMARY, description = Constants.DELETE_USER_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.HTTP_NO_CONTENT,
                    description = Constants.USER_DELETE_SUCCESSFUL_MESSAGE),
            @ApiResponse(responseCode = Constants.HTTP_BAD_REQUEST, description = Constants.USER_NOT_FOUND_ERROR,
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}