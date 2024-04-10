package sofTodo.toDoList.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sofTodo.toDoList.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/join")
    public String register() {return "join.html";}

    @PostMapping("/addmember")
    public String addMember(String username, String password, String nickname) {
        memberService.addMember(username, password, nickname);
        return "redirect:/memberlist";
    }

    @GetMapping("/memberlist")
    String list(Model model) {
        memberService.findAll(model);
        return "memberlist";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}