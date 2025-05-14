package org.example.restfuldemo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    @NotBlank(message = "title is required")
    @Size(min = 3, max = 100, message = "title size must be between 3 and 100 characters")
    private String title;

    @NotNull(message = "description is required")
    @Size(min = 0, max = 500, message = "description size must be between 0 and 500 characters")
    private String description;

    @NotBlank(message = "status is required")
    @Pattern(regexp = "PENDING|IN_PROGRESS|COMPLETED", message = "status must be one of PENDING, IN_PROGRESS, COMPLETED")
    private String status;

    @NotNull(message = "dueDate is required")
    @FutureOrPresent(message = "dueDate must be today or in the future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy") // Uncomment if needed
    private LocalDate dueDate;

    @NotNull(message = "userId is required")
    private Long userId;
}
