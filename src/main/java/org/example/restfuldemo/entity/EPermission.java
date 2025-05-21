package org.example.restfuldemo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

import static org.springframework.http.HttpMethod.*;

@Getter
@AllArgsConstructor
public enum EPermission {
    // Task permissions
    READ_TASKS(GET, "/api/v1/tasks", "read tasks"),
    READ_TASK(GET, "/api/v1/tasks/**", "read task"),
    READ_TASK_USER(GET, "/api/v1/tasks/user/**", "read task by user id"),
    UPDATE_TASK(PUT, "/api/v1/tasks/**", "update task"),
    CREATE_TASK(POST, "/api/v1/tasks", "create task"),
    DELETE_TASK(DELETE, "/api/v1/tasks/**", "delete task"),
    // User permissions
    READ_USERS(GET, "/api/v1/users", "read users"),
    READ_USER(GET, "/api/v1/users/**", "read user"),
    UPDATE_USER(PUT, "/api/v1/users/**", "update user"),
    CREATE_USER(POST, "/api/v1/users", "create user"),
    DELETE_USER(DELETE, "/api/v1/users/**", "delete user");

    private final HttpMethod method;
    private final String url;
    private final String description;
}
