package sofTodo.toDoList.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

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

    @Column(name ="date", nullable = false)
    private String date;

    @Column(name ="color", nullable = false)
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore()
    private User user;

    @Builder
    public ToDoItem(User user,String content,String date, String color) {
        this.user = user;
        this.content = content;
        this.date = date;
        this.color = color;
    }

    public void update(String content,String date, String color) {
        this.content = content;
        this.date = date;
        this.color = color;
    }
}