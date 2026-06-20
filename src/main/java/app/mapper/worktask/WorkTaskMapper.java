package app.mapper.worktask;

import app.model.dto.worktask.WorkTaskDto;
import app.model.entity.user.User;
import app.model.entity.worktask.WorkTask;

public class WorkTaskMapper {

    public static WorkTaskDto toDto(WorkTask task) {
        if (task == null) return null;

        WorkTaskDto.WorkTaskDtoBuilder builder = WorkTaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .status(task.getStatus());

        if (task.getAssignedTo() != null) {
            builder.assignedToId(task.getAssignedTo().getId())
                   .assignedToName(task.getAssignedTo().getFullName());
        }

        return builder.build();
    }

    public static WorkTask toEntity(WorkTaskDto dto) {
        if (dto == null) return null;

        User assigned = dto.getAssignedToId() != null ? User.builder().id(dto.getAssignedToId()).build() : null;

        return WorkTask.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .assignedTo(assigned)
                .priority(dto.getPriority())
                .status(dto.getStatus())
                .build();
    }
}
