package sofTodo.toDoList.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sofTodo.toDoList.domain.RandomMission;
import sofTodo.toDoList.domain.User;
import sofTodo.toDoList.dto.AddToDoRequest;
import sofTodo.toDoList.service.RandomMissionService;
import sofTodo.toDoList.service.ToDoServiceImpl;
import sofTodo.toDoList.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Component
public class RandomMissionScheduler {

    private final RandomMissionService randomMissionService;
    private final UserService userService;
    private final ToDoServiceImpl toDoService;

    public RandomMissionScheduler(RandomMissionService randomMissionService, UserService userService, ToDoServiceImpl toDoService) {
        this.randomMissionService = randomMissionService;
        this.userService = userService;
        this.toDoService = toDoService;
    }

    @Scheduled(cron = "0 47 17 * * *")
    public void addRandomMissionsToUsers() {
        List<RandomMission> missions = randomMissionService.findAll();
        List<User> users = userService.findAll();

        if (!missions.isEmpty() && !users.isEmpty()) {
            Random random = new Random();

            for (User user : users) {
                RandomMission randomMission = missions.get(random.nextInt(missions.size()));
                AddToDoRequest request = new AddToDoRequest(
                        randomMission.getMission(),
                        LocalDate.now().toString(),
                        "#FEDD40", // 원하는 색상 설정
                        true // 랜덤 미션 플래그 설정
                );
                toDoService.saveToDo(request, user);
            }
        }
    }
}
