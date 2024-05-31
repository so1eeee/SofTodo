package sofTodo.toDoList.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import sofTodo.toDoList.service.UserService;

@Controller
public class BasicController {
    private final UserService userService;

    public BasicController(UserService service) {
        userService = service;
    }

    //로그인을 한 유저가 "/" URL로 접속을 하면 /home으로 redirect 해줌! 아니라면? -> hello(첫 시작 페이지) 페이지로..
    @GetMapping("/")
    public ModelAndView homepage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            return new ModelAndView("redirect:/home");
        }
        return new ModelAndView("hello");
    }
    @GetMapping("/ranking")
    public String ranking(Model model) {
        model.addAttribute("topUsers", userService.getTop5UsersByMissionSuccessCount());
        return "ranking";
    }
    @GetMapping("/my-page")
    public String mypage(){
        return "mypage";
    }
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }
}