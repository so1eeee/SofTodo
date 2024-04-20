package sofTodo.toDoList.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "todoitem")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ToDoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name ="content", nullable = false)
    private String content;

    @Builder
    public ToDoItem(String content) {
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}