package sofTodo.toDoList.dto;

import lombok.Getter;
import lombok.Setter;
import sofTodo.toDoList.domain.ToDoItem;

@Getter
@Setter
public class ToDoItemDTO extends ToDoItem {
    private Long id;
    private String content;
    private String date;
    private String color;
    private Boolean isRandomMission;
}
