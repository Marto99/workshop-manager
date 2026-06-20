package app.model.dto.maintenance;

import app.model.entity.maintenance.MaintenanceStatus;
import app.model.entity.maintenance.MaintenanceUrgency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class MaintenanceDto {
    private UUID id;

    @NotNull(message = "Оборудването трябва да бъде избрано")
    private UUID equipmentId;
    private String equipmentName;
    private UUID reporterId;
    private String reporterName;

    @NotBlank(message = "Описанието не може да бъде празно")
    private String description;

    @NotNull(message = "Моля изберете спешност")
    private MaintenanceUrgency urgency;
    private MaintenanceStatus status;
    private LocalDateTime createdAt;
}
