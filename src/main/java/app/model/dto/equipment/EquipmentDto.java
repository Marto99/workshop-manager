package app.model.dto.equipment;

import app.model.entity.equipment.EquipmentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class EquipmentDto {
    private UUID id;
    private String name;
    private String category;
    private String location;
    private EquipmentStatus status;
    private BigDecimal hourlyRate;
}
