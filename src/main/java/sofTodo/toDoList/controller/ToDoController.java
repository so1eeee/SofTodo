package sofTodo.toDoList.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import sofTodo.toDoList.domain.ToDoItem;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoItemRepository toDoItemRepository;


    @GetMapping("/todolist")
    String list(Model model) {
        List<ToDoItem> result = toDoItemRepository.findAll();

        model.addAttribute("todoitems", result);
        return "todolist";
    }

    @GetMapping("/write")
    String write() {
        return "write";
    }

    @PostMapping("/add")
    String addPost(@ModelAttribute ToDoItem toDoItem) {
        toDoItemRepository.save(toDoItem);
        return "redirect:/todolist";
    }

}