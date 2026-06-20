package app.mapper.maintenance;

import app.model.dto.maintenance.MaintenanceDto;
import app.model.entity.maintenance.MaintenanceRequest;
import app.model.entity.equipment.Equipment;
import app.model.entity.user.User;

public class MaintenanceMapper {

    public static MaintenanceDto toDto(MaintenanceRequest r) {
        if (r == null) return null;

        return MaintenanceDto.builder()
                .id(r.getId())
                .equipmentId(r.getEquipment() != null ? r.getEquipment().getId() : null)
                .equipmentName(r.getEquipment() != null ? r.getEquipment().getName() : null)
                .reporterId(r.getReporter() != null ? r.getReporter().getId() : null)
                .reporterName(r.getReporter() != null ? r.getReporter().getFullName() : null)
                .description(r.getDescription())
                .urgency(r.getUrgency())
                .status(r.getStatus())
                .createdAt(r.getCreatedAt())
                .build();
    }

    public static MaintenanceRequest toEntity(MaintenanceDto dto) {
        if (dto == null) return null;

        Equipment equipment = dto.getEquipmentId() != null ? Equipment.builder().id(dto.getEquipmentId()).build() : null;
        User reporter = dto.getReporterId() != null ? User.builder().id(dto.getReporterId()).build() : null;

        return MaintenanceRequest.builder()
                .id(dto.getId())
                .equipment(equipment)
                .reporter(reporter)
                .description(dto.getDescription())
                .urgency(dto.getUrgency())
                .status(dto.getStatus())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
