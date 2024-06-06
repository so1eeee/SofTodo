package sofTodo.toDoList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BasicController {

    //사이트 기본 홈페이지(로그인 안했을시)
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/signup")
    public String signup(){
        return "/signup";
    }

    @GetMapping("/my-page")
    public String mypage() {
        return "mypage";
    }
}