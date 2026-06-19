package app.model.dto.user;

import app.model.entity.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterRequest {
    @Size(min = 2, message = "Name must be at least 2 characters.")
    private String fullName;
    @Email
    private String email;
    @NotBlank(message = "Role can't be empty.")
    private UserRole role;
    @Size(min = 8, message = "Password must be at least 8 characters.")
    private String password;
}
