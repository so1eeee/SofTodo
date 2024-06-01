package sofTodo.toDoList.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sofTodo.toDoList.domain.ToDoItem;
import sofTodo.toDoList.domain.User;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
public class AddToDoRequest {
    private String content;
    private String date;
    private String color;

    public ToDoItem toEntity(User user){
        return ToDoItem.builder()
                .user(user)
                .content(content)
                .date(date)
                .color(color)
                .build();
    }
}