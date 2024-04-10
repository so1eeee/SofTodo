package sofTodo.toDoList.service;

import org.springframework.ui.Model;

public interface MemberService {
    void addMember(String username, String password, String nickname);
    void findAll(Model model);
}
