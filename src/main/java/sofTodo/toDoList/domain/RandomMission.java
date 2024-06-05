package sofTodo.toDoList.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="random_mission")
@Getter
@Setter
@NoArgsConstructor
public class RandomMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mission;

    public RandomMission(String mission) {
        this.mission = mission;
    }
}