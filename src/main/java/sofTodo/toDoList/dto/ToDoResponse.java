package sofTodo.toDoList.dto;

import lombok.Getter;
import sofTodo.toDoList.domain.ToDoItem;

@Getter
public class ToDoResponse {
    private final String content;
    private final String date;
    private final String color;

    public ToDoResponse(ToDoItem toDoItem) {
        this.content = toDoItem.getContent();
        this.date = toDoItem.getDate();
        this.color = toDoItem.getColor();
    }
}