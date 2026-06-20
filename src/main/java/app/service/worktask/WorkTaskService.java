package app.service.worktask;

import app.mapper.worktask.WorkTaskMapper;
import app.model.dto.worktask.WorkTaskDto;
import app.model.entity.user.User;
import app.model.entity.worktask.WorkTask;
import app.repository.user.UserRepository;
import app.repository.worktask.WorkTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WorkTaskService {

    private final WorkTaskRepository workTaskRepository;
    private final UserRepository userRepository;

    @Autowired
    public WorkTaskService(WorkTaskRepository workTaskRepository, UserRepository userRepository) {
        this.workTaskRepository = workTaskRepository;
        this.userRepository = userRepository;
    }

    public List<WorkTaskDto> listAll() {
        return workTaskRepository.findAll()
                .stream()
                .map(WorkTaskMapper::toDto)
                .collect(Collectors.toList());
    }

    public WorkTaskDto getById(UUID id) {
        WorkTask t = workTaskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkTask not found: " + id));
        return WorkTaskMapper.toDto(t);
    }

    public WorkTaskDto create(WorkTaskDto dto) {
        User assigned = null;
        if (dto.getAssignedToId() != null) {
            assigned = userRepository.findById(dto.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("User not found: " + dto.getAssignedToId()));
        }

        WorkTask task = WorkTask.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .assignedTo(assigned)
                .priority(dto.getPriority())
                .status(dto.getStatus() != null ? dto.getStatus() : app.model.entity.worktask.TaskStatus.OPEN)
                .build();

        WorkTask saved = workTaskRepository.save(task);
        return WorkTaskMapper.toDto(saved);
    }

    public WorkTaskDto update(UUID id, WorkTaskDto dto) {
        WorkTask existing = workTaskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkTask not found: " + id));

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setPriority(dto.getPriority());
        existing.setStatus(dto.getStatus());

        if (dto.getAssignedToId() != null) {
            User assigned = userRepository.findById(dto.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("User not found: " + dto.getAssignedToId()));
            existing.setAssignedTo(assigned);
        }

        WorkTask saved = workTaskRepository.save(existing);
        return WorkTaskMapper.toDto(saved);
    }

    public void delete(UUID id) {
        workTaskRepository.deleteById(id);
    }
}
