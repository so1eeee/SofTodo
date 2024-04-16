package sofTodo.toDoList.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "todoitem")
@Entity
@Getter
@Setter
public class ToDoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String content;
    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}