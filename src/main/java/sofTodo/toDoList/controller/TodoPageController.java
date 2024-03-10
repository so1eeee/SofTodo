package sofTodo.toDoList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TodoPageController {
    @GetMapping("/")
    public String getTodoListView() {
        return "home";
    }
}
