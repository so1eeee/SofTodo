package sofTodo.toDoList.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sofTodo.toDoList.domain.RandomMission;
import sofTodo.toDoList.repository.RandomMissionRepository;

import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(RandomMissionRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                List<RandomMission> missions = List.of(
                        new RandomMission("길에 있는 쓰레기 줍기"),
                        new RandomMission("일회용품 사용하지 않기"),
                        new RandomMission("자전거 타기"),
                        new RandomMission("대중교통 이용하기"),
                        new RandomMission("불필요한 전기 끄기"),
                        new RandomMission("지역사회 봉사활동 참여하기"),
                        new RandomMission("재활용하기"),
                        new RandomMission("식물 심기"),
                        new RandomMission("물 절약하기"),
                        new RandomMission("로컬 마켓 이용하기"),
                        new RandomMission("일회용 컵 대신 텀블러 사용하기"),
                        new RandomMission("채식 한 끼 먹기"),
                        new RandomMission("전자제품 플러그 뽑아두기"),
                        new RandomMission("종이 낭비 줄이기"),
                        new RandomMission("친환경 제품 구매하기"),
                        new RandomMission("에너지 절약하기"),
                        new RandomMission("중고 물품 나누기"),
                        new RandomMission("페트병 라벨 제거하기"),
                        new RandomMission("환경 보호 관련 다큐멘터리 시청하기"),
                        new RandomMission("주변 사람들에게 환경 보호 알리기")
                );

                repository.saveAll(missions);
            }
        };
    }
}