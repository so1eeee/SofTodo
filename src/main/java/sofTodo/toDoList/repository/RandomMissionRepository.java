package sofTodo.toDoList.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sofTodo.toDoList.domain.RandomMission;

public interface RandomMissionRepository extends JpaRepository<RandomMission, Long> {
}
