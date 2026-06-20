package app.model.dto.user;

import app.model.entity.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterRequest {
    @Size(min = 2, message = "Името трябва да е поне 2 символа.")
    private String fullName;

    @Email(message = "Невалиден имейл адрес")
    private String email;

    @NotNull(message = "Ролята не може да бъде празна")
    private UserRole role;

    @Size(min = 8, message = "Паролата трябва да има поне 8 символа.")
    private String password;
}
