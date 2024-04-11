package sofTodo.toDoList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserViewController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 우선 로그인 성공하면 todolist View 로 return 처리 해줬습니다.
    @PostMapping("/login")
    public String loginSuccess(){
        return "todolist";
    }


    // 로그인 실패하면 실패화면으로 보내줌
    @GetMapping("/loginfail")
    public String loginFail(){
        return "loginfail";
    }




    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }
}
