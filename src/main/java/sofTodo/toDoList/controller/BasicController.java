package sofTodo.toDoList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sofTodo.toDoList.dto.AddUserRequest;

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

    // 회원가입할때 DTO 전달 -> 폼과 모델 객체를 바인딩하여 사용자가 입력한 데이터를 서버로 전송할 때 편리하게 처리하기 위함
    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("addUserRequest", new AddUserRequest());
        return "/signup";
    }

    @GetMapping("/aboutRank")
    public String aboutRank(){
        return "/aboutRank";
    }
}