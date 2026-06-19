package app.model.dto.user;

import app.model.entity.user.UserRole;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserDto {
    private UUID id;
    private String fullName;
    private String email;
    private UserRole role;
}
