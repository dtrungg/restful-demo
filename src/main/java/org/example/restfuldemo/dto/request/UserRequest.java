package org.example.restfuldemo.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequest {
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String userName;

    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 25, message = "Password must be between 6 and 25 characters")
    private String passWord;

    @JsonIgnore
    private List<TaskRequest> tasks;
}
