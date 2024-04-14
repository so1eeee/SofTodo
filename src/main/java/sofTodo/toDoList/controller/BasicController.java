package sofTodo.toDoList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BasicController {
    @GetMapping("/")
    public String getToDoListMainView() {return "home";}

    @GetMapping("/ranking")
    public String ranking(){
        return "ranking";
    }
    @GetMapping("/my-page")
    public String mypage(){
        return "mypage";
    }
}