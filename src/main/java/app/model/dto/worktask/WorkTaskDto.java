package app.model.dto.worktask;

import app.model.entity.worktask.TaskPriority;
import app.model.entity.worktask.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class WorkTaskDto {
    private UUID id;
    private String title;
    private String description;
    private UUID assignedToId;
    private String assignedToName;
    private TaskPriority priority;
    private TaskStatus status;
}
