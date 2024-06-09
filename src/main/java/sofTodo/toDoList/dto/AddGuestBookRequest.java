package sofTodo.toDoList.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sofTodo.toDoList.domain.GuestBook;
import sofTodo.toDoList.domain.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddGuestBookRequest {
    private String content;
    private String date;

    public GuestBook toEntity(User ownUser,User guestUser){
        return GuestBook.builder()
                .ownUser(ownUser)
                .guestUser(guestUser)
                .content(content)
                .date(date)
                .build();
    }
}
