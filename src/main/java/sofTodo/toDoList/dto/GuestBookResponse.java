package sofTodo.toDoList.dto;

import lombok.Getter;
import sofTodo.toDoList.domain.GuestBook;

@Getter
public class GuestBookResponse {
    private Long id;
    private String content;
    private String date;
    private Long userId; // guestUser의 ID
    private String nickname;

    public GuestBookResponse(GuestBook guestBook) {
        this.id = guestBook.getId();  // id 필드 추가
        this.content = guestBook.getContent();
        this.date = guestBook.getDate();
        this.userId = guestBook.getGuestUser().getId(); // guestUser의 ID 설정
        this.nickname = guestBook.getGuestUser().getNickname();
    }
}