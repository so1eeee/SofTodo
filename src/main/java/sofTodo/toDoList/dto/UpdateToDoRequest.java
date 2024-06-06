package sofTodo.toDoList.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateToDoRequest {
    private String content;
    private String date;
    private String color;
    private boolean isChecked;
}