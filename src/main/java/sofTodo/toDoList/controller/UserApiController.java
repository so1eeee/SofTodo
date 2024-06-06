package sofTodo.toDoList.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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

    @GetMapping("/user/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/user/login";
    }

    @GetMapping("/search")
    public String memberSearch(@RequestParam(required = false) String userNickName, Model model) {

        if (userNickName == null || userNickName.isEmpty()) {
            return "memberlist";
        }

        var result = userRepository.findAllByNicknameContains(userNickName);
        model.addAttribute("users", result);

        return "memberlist";
    }
}