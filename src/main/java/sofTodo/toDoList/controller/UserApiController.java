package sofTodo.toDoList.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sofTodo.toDoList.dto.AddUserRequest;
import sofTodo.toDoList.repository.UserRepository;
import sofTodo.toDoList.service.UserService;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/user")
    public String signup(AddUserRequest request) {
        userService.save(request);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        userService.logout(request, response);
        return "redirect:/login";
    }

    @GetMapping("/search")
    String doSearch(@RequestParam String searchUser, Model model){
        userService.doSearch(searchUser, model);
        return "memberlist";
    }
}