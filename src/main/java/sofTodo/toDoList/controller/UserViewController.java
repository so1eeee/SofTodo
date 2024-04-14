package sofTodo.toDoList.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my-page")
    public String mypage(Authentication auth) {
        return "mypage";
    }

    @GetMapping("/admin")
    public String adminP(){return "admin";}
}
