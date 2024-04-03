package sofTodo.toDoList.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sofTodo.toDoList.domain.ToDoItem;
import sofTodo.toDoList.repository.ToDoItemRepository;
import sofTodo.toDoList.service.ToDoServiceImpl;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoItemRepository toDoItemRepository;
    private final ToDoServiceImpl toDoService;

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
    String addPost(String content) {
        toDoService.addToDoItem(content);
        return "redirect:/todolist";
    }
    @GetMapping("/edit/{id}")
    String edit(Model model, @PathVariable Long id) {
        Optional<ToDoItem> result = toDoItemRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("data", result.get());
            return "edit";
        } else {
            return "redirect:/todolist";
        }
    }

    @PostMapping("/edit")
    String editItem(String content, Long id) {
        toDoService.editToDoItem(content,id);
        return "redirect:/todolist";
    }
}