package app.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginRequest {
    @Email
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters.")
    private String password;
}
