package app.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginRequest {
    @Email(message = "Невалиден имейл адрес")
    @NotBlank(message = "Имейлът не може да бъде празен")
    private String email;

    @Size(min = 8, message = "Паролата трябва да има поне 8 символа.")
    private String password;
}
