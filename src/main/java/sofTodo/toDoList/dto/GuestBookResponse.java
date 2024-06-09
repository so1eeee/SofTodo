package sofTodo.toDoList.dto;

import lombok.Getter;
import sofTodo.toDoList.domain.GuestBook;

@Getter
public class GuestBookResponse {
    private Long id;
    private String content;
    private String date;

    public GuestBookResponse(GuestBook guestBook) {
        this.id = guestBook.getId();  // id 필드 추가
        this.content = guestBook.getContent();
        this.date = guestBook.getDate();
    }
}