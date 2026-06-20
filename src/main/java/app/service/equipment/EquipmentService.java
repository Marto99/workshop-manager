package app.service.equipment;

import app.mapper.equipment.EquipmentMapper;
import app.model.dto.equipment.EquipmentDto;
import app.model.entity.equipment.Equipment;
import app.repository.equipment.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public List<EquipmentDto> listAll() {
        return equipmentRepository.findAll()
                .stream()
                .map(EquipmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public EquipmentDto getById(UUID id) {
        Equipment eq = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment not found: " + id));
        return EquipmentMapper.toDto(eq);
    }

    public EquipmentDto create(EquipmentDto dto) {
        Equipment entity = EquipmentMapper.toEntity(dto);
        Equipment saved = equipmentRepository.save(entity);
        return EquipmentMapper.toDto(saved);
    }

    public EquipmentDto update(UUID id, EquipmentDto dto) {
        Equipment existing = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment not found: " + id));

        // copy updatable fields
        existing.setName(dto.getName());
        existing.setCategory(dto.getCategory());
        existing.setLocation(dto.getLocation());
        existing.setStatus(dto.getStatus());
        existing.setHourlyRate(dto.getHourlyRate());

        Equipment saved = equipmentRepository.save(existing);
        return EquipmentMapper.toDto(saved);
    }

    public void delete(UUID id) {
        equipmentRepository.deleteById(id);
    }
}