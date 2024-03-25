package sofTodo.toDoList.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReadTodoDto {
    private final Long id;
    private final String content;
}
