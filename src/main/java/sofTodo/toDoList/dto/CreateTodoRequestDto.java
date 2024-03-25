package sofTodo.toDoList.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateTodoRequestDto {
    private final Long id;
    private final String content;
}
