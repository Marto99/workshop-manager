package app.repository.worktask;

import app.model.entity.worktask.WorkTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkTaskRepository extends JpaRepository<WorkTask, UUID> {
}
