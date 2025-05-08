package org.example.restfuldemo.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TaskDto {
    private Long id;
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "PENDING|IN_PROGRESS|COMPLETED", message = "Invalid status")
    private String status;

    @NotNull(message = "Due date is required")
    @FutureOrPresent(message = "Due date must be in the present or future")
    private LocalDateTime dueDate;

    @NotNull(message = "Person ID is required")
    private PersonDto person;
}
