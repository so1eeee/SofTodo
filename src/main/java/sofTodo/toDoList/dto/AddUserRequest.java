package sofTodo.toDoList.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddUserRequest {

    @NotBlank(message = "닉네임을 입력해 주세요.")
    @Size(min = 2, message = "닉네임은 최소 2글자 이상이어야 합니다.")
    private String nickname;

    @NotBlank(message = "아이디를 입력해 주세요.")
    @Size(min = 3, message = "아이디는 최소 3글자 이상이어야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Size(min = 3, message = "비밀번호는 최소 3글자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호를 다시 한번 입력해 주세요.")
    private String confirmPassword;

    private String slug;
}