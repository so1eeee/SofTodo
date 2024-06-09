package sofTodo.toDoList.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sofTodo.toDoList.domain.GuestBook;
import sofTodo.toDoList.domain.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddGuestBookRequest {
    private String content;
    private String date;

    public GuestBook toEntity(User user){
        return GuestBook.builder()
                .user(user)
                .content(content)
                .date(date)
                .build();
    }
}
