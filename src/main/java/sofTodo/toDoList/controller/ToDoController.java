package sofTodo.toDoList.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sofTodo.toDoList.service.ToDoServiceImpl;

@Controller
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoServiceImpl toDoService;

    @GetMapping("/todolist")
    String list(Model model) {
        toDoService.findAll(model);
        return "todolist";
    }

    @GetMapping("/write")
    String write() {
        return "write";
    }

    @PostMapping("/add")
    String addPost(String content) {
        toDoService.addToDoItem(content);
        return "redirect:/todolist";
    }

    @GetMapping("/edit/{id}")
    String edit(Model model, @PathVariable Long id) {
        boolean isLoaded = toDoService.editToDoItem(model, id);
        return isLoaded ? "edit" : "redirect:/todolist";
    }

    @PostMapping("/edit")
    String editItem(String content, Long id) {
        toDoService.editToDoItem(content,id);
        return "redirect:/todolist";
    }
}