package org.example.restfuldemo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.restfuldemo.entity.Role;

@Getter
public class UserRequest {
    @NotBlank(message = "user name is required")
    @Size(min = 6, max = 50, message = "user name size must be between 6 and 50")
    private String userName;

    @NotBlank(message = "password is required")
    @Size(min = 6, max = 25, message = "password size must be between 6 and 25")
    private String passWord;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^\\d{11}$", message = "Invalid phone number format")
    private String phoneNumber;

    private Role role;
}
