package sofTodo.toDoList.service;

import org.springframework.stereotype.Service;
import sofTodo.toDoList.domain.RandomMission;
import sofTodo.toDoList.repository.RandomMissionRepository;

import java.util.List;

@Service
public class RandomMissionService {
    private final RandomMissionRepository randomMissionRepository;

    public RandomMissionService(RandomMissionRepository randomMissionRepository) {
        this.randomMissionRepository = randomMissionRepository;
    }

    public List<RandomMission> findAll() {
        return randomMissionRepository.findAll();
    }

}
