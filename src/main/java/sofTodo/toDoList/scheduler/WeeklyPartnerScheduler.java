package sofTodo.toDoList.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sofTodo.toDoList.domain.User;
import sofTodo.toDoList.repository.UserRepository;

import java.util.List;
import java.util.Random;

@Component
public class WeeklyPartnerScheduler {

    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 36 21 * * SUN") // 매주 일요일 9시 37분에 실행
    public void assignWeeklyPartners() {
        List<User> users = userRepository.findAll();
        Random random = new Random();

        for (User user : users) {
            User partner;
            do {
                partner = users.get(random.nextInt(users.size()));
            } while (partner.getId().equals(user.getId())); // 본인을 파트너로 선택하지 않음

            user.setWeeklyPartner(partner);
            userRepository.save(user);
        }
    }
}
