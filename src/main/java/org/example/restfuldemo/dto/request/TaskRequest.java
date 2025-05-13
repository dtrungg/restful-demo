package org.example.restfuldemo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.restfuldemo.constants.Constants;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    @NotBlank(message = Constants.TITLE_REQUIRED)
    @Size(min = 3, max = 100, message = Constants.TITLE_SIZE)
    private String title;

    @NotBlank(message = Constants.DESCRIPTION_REQUIRED)
    @Size(min = 10, max = 500, message = Constants.DESCRIPTION_SIZE)
    private String description;

    @NotBlank(message = Constants.STATUS_REQUIRED)
    @Pattern(regexp = "PENDING|IN_PROGRESS|COMPLETED", message = Constants.INVALID_STATUS)
    private String status;

    @NotNull(message = Constants.DUE_DATE_REQUIRED)
    @FutureOrPresent(message = Constants.DUE_DATE_FUTURE_OR_PRESENT)
    // @JsonFormat(pattern="yyyy-MM-dd") // Uncomment if needed
    private LocalDate dueDate;

    @NotNull(message = Constants.USER_ID_REQUIRED)
    private Long userId;
}
