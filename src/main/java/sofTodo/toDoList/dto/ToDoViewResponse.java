package sofTodo.toDoList.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import sofTodo.toDoList.domain.ToDoItem;

@NoArgsConstructor
@Getter
public class ToDoViewResponse {
    private  Long id;
    private  String content;
    private  String date;
    private  String color;

    public ToDoViewResponse(ToDoItem toDoItem) {
        this.id = toDoItem.getId();
        this.content = toDoItem.getContent();
        this.date = toDoItem.getDate();
        this.color = toDoItem.getColor();
    }
}
