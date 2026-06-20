package app.mapper.equipment;

import app.model.dto.equipment.EquipmentDto;
import app.model.entity.equipment.Equipment;

public class EquipmentMapper {

    public static EquipmentDto toDto(Equipment equipment) {
        if (equipment == null) return null;

        return EquipmentDto.builder()
                .id(equipment.getId())
                .name(equipment.getName())
                .category(equipment.getCategory())
                .location(equipment.getLocation())
                .status(equipment.getStatus())
                .hourlyRate(equipment.getHourlyRate())
                .build();
    }

    public static Equipment toEntity(EquipmentDto dto) {
        if (dto == null) return null;

        return Equipment.builder()
                .id(dto.getId())
                .name(dto.getName())
                .category(dto.getCategory())
                .location(dto.getLocation())
                .status(dto.getStatus())
                .hourlyRate(dto.getHourlyRate())
                .build();
    }
}
