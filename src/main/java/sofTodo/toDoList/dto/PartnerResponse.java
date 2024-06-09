package sofTodo.toDoList.dto;

import lombok.Data;
import sofTodo.toDoList.domain.User;

@Data
public class PartnerResponse {
    private String nickname;
    private String slug;

    public PartnerResponse() {
        this.nickname = "";
        this.slug = "";
    }

    public PartnerResponse(User user) {
        this.nickname = user.getNickname();
        this.slug = user.getSlug();
    }
}
