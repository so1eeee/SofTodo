package sofTodo.toDoList.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddUserRequest {
    private String nickname;
    private String username;
    private String password;
    private String slug;
}