package sofTodo.toDoList.dto;

import lombok.Getter;
import sofTodo.toDoList.domain.ToDoItem;

@Getter
public class ToDoResponse {
    private Long id;
    private String content;
    private String date;
    private String color;
    private boolean isRandomMission;  // 새로운 필드 추가

    public ToDoResponse(ToDoItem toDoItem) {
        this.id = toDoItem.getId();  // id 필드 추가
        this.content = toDoItem.getContent();
        this.date = toDoItem.getDate();
        this.color = toDoItem.getColor();
        this.isRandomMission = toDoItem.isRandomMission();  // 필드 초기화
    }
}