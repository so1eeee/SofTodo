package sofTodo.toDoList.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Todo {
    private final Long id;
    private final String content;

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
