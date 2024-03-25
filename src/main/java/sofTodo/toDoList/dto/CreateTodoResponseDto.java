package sofTodo.toDoList.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateTodoResponseDto {
    private final Long id;
    private final String content;

    @Override
    public String toString() {
        return "CreateTodoResponseDto{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
