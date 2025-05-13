package org.example.restfuldemo.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.restfuldemo.constants.Constants;

import java.util.List;

@Getter
@Setter
public class UserRequest {
    private Long id;

    @NotBlank(message = Constants.NAME_REQUIRED)
    @Size(min = 4, max = 50, message = Constants.NAME_SIZE)
    private String userName;

    @NotBlank(message = Constants.PASSWORD_REQUIRED)
    @Size(min = 4, max = 25, message = Constants.PASSWORD_SIZE)
    private String passWord;

    @JsonIgnore
    private List<TaskRequest> tasks;
}
