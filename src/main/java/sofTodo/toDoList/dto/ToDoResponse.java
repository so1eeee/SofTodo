package sofTodo.toDoList.dto;

import lombok.Getter;
import sofTodo.toDoList.domain.ToDoItem;

@Getter
public class ToDoResponse {
    private final String content;

    public ToDoResponse(ToDoItem toDoItem) {
        this.content = toDoItem.getContent();
    }
}