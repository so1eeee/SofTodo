package sofTodo.toDoList.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sofTodo.toDoList.domain.User;
import sofTodo.toDoList.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class WeeklyPartnerScheduler {

    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 24 14 * * MON") // 매주 월요일 2시 38분에 실행
    public void assignWeeklyPartners() {
        List<User> users = userRepository.findAll();
        Collections.shuffle(users); // 사용자 목록을 무작위로 섞음

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            User partner = users.get((i + 1) % users.size()); // 다음 사용자를 파트너로 지정, 마지막 사용자는 첫 번째 사용자와 짝지음

            user.setWeeklyPartner(partner);
            userRepository.save(user);
        }
    }
}
