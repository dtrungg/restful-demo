package org.example.restfuldemo.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    TASK_READ("task:read"),

    TASK_UPDATE("task:update"),

    TASK_CREATE("task:create"),

    TASK_DELETE("task:delete"),

    USER_READ("user:read"),

    USER_UPDATE("user:update"),

    USER_CREATE("user:create"),

    USER_DELETE("user:delete");

    private final String permission;

    public String getPermission() {
        return permission;
    }
}
