package sofTodo.toDoList.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    @GetMapping("/join")
    public String register() {return "join.html";}

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }
}
