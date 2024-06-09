package sofTodo.toDoList.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import sofTodo.toDoList.domain.User;

@Data
@AllArgsConstructor
public class PartnerResponse {
    private String slug;
    private String nickname;

    public PartnerResponse(User partner) {
        this.slug = partner.getSlug();
        this.nickname = partner.getNickname();
    }
}
