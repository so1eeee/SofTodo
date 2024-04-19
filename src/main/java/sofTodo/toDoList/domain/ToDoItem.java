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
    public Long id;

    @Column(name ="content", nullable = false,updatable = false)
    private String content;

    @Builder
    public ToDoItem(String content) {
        this.content = content;
    }
}