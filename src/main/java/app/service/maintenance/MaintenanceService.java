package app.service.maintenance;

import app.mapper.maintenance.MaintenanceMapper;
import app.model.dto.maintenance.MaintenanceDto;
import app.model.entity.maintenance.MaintenanceRequest;
import app.model.entity.maintenance.MaintenanceStatus;
import app.model.entity.equipment.Equipment;
import app.model.entity.user.User;
import app.repository.maintenance.MaintenanceRepository;
import app.repository.equipment.EquipmentRepository;
import app.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;

    @Autowired
    public MaintenanceService(MaintenanceRepository maintenanceRepository,
                              EquipmentRepository equipmentRepository,
                              UserRepository userRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.equipmentRepository = equipmentRepository;
        this.userRepository = userRepository;
    }

    public List<MaintenanceDto> listAll() {
        return maintenanceRepository.findAll()
                .stream()
                .map(MaintenanceMapper::toDto)
                .collect(Collectors.toList());
    }

    public MaintenanceDto getById(UUID id) {
        MaintenanceRequest r = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance request not found: " + id));
        return MaintenanceMapper.toDto(r);
    }

    public MaintenanceDto create(MaintenanceDto dto) {
        Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found: " + dto.getEquipmentId()));

        User reporter = userRepository.findById(dto.getReporterId())
                .orElseThrow(() -> new RuntimeException("User not found: " + dto.getReporterId()));

        MaintenanceRequest req = MaintenanceRequest.builder()
                .equipment(equipment)
                .reporter(reporter)
                .description(dto.getDescription())
                .urgency(dto.getUrgency())
                .status(dto.getStatus() != null ? dto.getStatus() : MaintenanceStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .build();

        MaintenanceRequest saved = maintenanceRepository.save(req);
        return MaintenanceMapper.toDto(saved);
    }

    public MaintenanceDto resolve(UUID id) {
        MaintenanceRequest existing = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance request not found: " + id));

        existing.setStatus(MaintenanceStatus.RESOLVED);
        maintenanceRepository.save(existing);
        return MaintenanceMapper.toDto(existing);
    }

    public void delete(UUID id) {
        maintenanceRepository.deleteById(id);
    }
}
