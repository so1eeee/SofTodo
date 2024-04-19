package sofTodo.toDoList.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sofTodo.toDoList.domain.ToDoItem;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter // 이게 AddToDoRequest에 왜 있어야할까
public class AddToDoRequest {
    private String content;

    public ToDoItem toEntity(){
        return ToDoItem.builder()
                .content(content)
                .build();
    }
}