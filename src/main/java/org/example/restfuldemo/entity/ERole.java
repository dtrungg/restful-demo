package org.example.restfuldemo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

import static org.example.restfuldemo.entity.EPermission.*;

@Getter
@AllArgsConstructor
public enum ERole {
    ADMIN(Set.of(READ_TASKS,
            READ_TASK,
            READ_TASK_USER,
            UPDATE_TASK,
            DELETE_TASK,
            CREATE_TASK,
            READ_USERS,
            READ_USER,
            UPDATE_USER,
            DELETE_USER,
            CREATE_USER
    )),
    USER(Set.of(READ_TASKS, READ_USER, UPDATE_USER, DELETE_USER, CREATE_USER));
    private final Set<EPermission> permissions;
}
