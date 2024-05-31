package sofTodo.toDoList.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sofTodo.toDoList.domain.ToDoItem;
import sofTodo.toDoList.dto.ToDoViewResponse;
import sofTodo.toDoList.repository.UserRepository;
import sofTodo.toDoList.service.ToDoServiceImpl;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ToDoViewController {
    private final ToDoServiceImpl toDoService;
    private final UserRepository userRepository;

    @GetMapping("/todolist")
    public String getToDo(Model model, Authentication auth) {
        Long userId = toDoService.extractUserId(auth);

        List<ToDoViewResponse> todos = toDoService.findByUserId(userId).stream()
                .map(ToDoViewResponse::new)
                .toList();
        model.addAttribute("todos", todos);
        return "todolist";
    }

    @GetMapping("/new-todo")
    public String newToDo(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("todo", new ToDoViewResponse());
        }else{
            ToDoItem toDoItem = toDoService.findById(id);
            model.addAttribute("todo", new ToDoViewResponse(toDoItem));
        }
        return "newTodo";
    }
}