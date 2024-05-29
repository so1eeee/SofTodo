package sofTodo.toDoList.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NicknameController {

    @GetMapping("/set-nickname")
    public String setNickname(@RequestParam(value = "nickname", required = false) String nickname, HttpSession session) {
        if (nickname != null && !nickname.isEmpty()) {
            System.out.println("Setting nickname in session: " + nickname);
            session.setAttribute("nickname", nickname);
        } else {
            System.out.println("No nickname found in request");
        }
        return "redirect:/oauth2/authorization/google";
    }
}
