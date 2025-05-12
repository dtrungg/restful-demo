package org.example.restfuldemo.dto.response.user;

import lombok.*;
import org.example.restfuldemo.dto.request.TaskRequest;
import org.example.restfuldemo.dto.response.task.TaskResponse;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String userName;
    private List<TaskRequest> tasks;
}
