package sofTodo.toDoList.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {
    private String nickname;
    private String username;
    private String password;
}