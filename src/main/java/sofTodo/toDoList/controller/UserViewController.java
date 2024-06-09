package sofTodo.toDoList.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import sofTodo.toDoList.domain.User;
import sofTodo.toDoList.service.UserService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserViewController {

    private final UserService userService;

    //로그인을 한 유저가 "/" URL로 접속을 하면 /home으로 redirect 해줌! 아니라면? -> hello(첫 시작 페이지) 페이지로..
    @GetMapping("/")
    public ModelAndView homepage(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            return new ModelAndView("hello");
        }

        Long userId = extractUserId(authentication);
        if (userId != null) {
            Optional<User> userOptional = userService.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String slug = user.getSlug();
                if (slug == null) {
                    // 슬러그가 null인 경우 처리
                    System.out.println("User slug is null");
                    return new ModelAndView("hello");
                }
                return new ModelAndView("redirect:/home/" + slug);
            } else {
                System.out.println("User not found with ID: " + userId);
            }
        } else {
            System.out.println("User ID is null");
        }
        return new ModelAndView("hello");
    }

    private Long extractUserId(Authentication authentication) {
        if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
            String email = (String) oauthUser.getAttributes().get("email");
            Optional<User> userOptional = userService.findByUsername(email);
            return userOptional.map(User::getId).orElse(null);
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<User> userOptional = userService.findByUsername(userDetails.getUsername());
            return userOptional.map(User::getId).orElse(null);
        }
        return null;
    }

    @GetMapping("/ranking")
    public String ranking(Model model) {
        model.addAttribute("topUsers", userService.getTop5UsersByMissionSuccessCount());
        return "ranking";
    }

    @GetMapping("/home/{slug}")
    public String home(@PathVariable String slug, Model model) {
        Optional<User> userOptional = userService.findBySlug(slug);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("user", user);
            if (user.getWeeklyPartner() != null) {
                model.addAttribute("partner", user.getWeeklyPartner());
            }
            return "home";
        } else {
            return "error"; // 슬러그에 해당하는 사용자를 찾을 수 없을 때 반환할 페이지
        }
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return ResponseEntity.ok(user);
    }
}
