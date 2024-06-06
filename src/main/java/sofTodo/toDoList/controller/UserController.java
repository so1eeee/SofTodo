package sofTodo.toDoList.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sofTodo.toDoList.domain.User;
import sofTodo.toDoList.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        Long userId = null;

        if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
            String email = (String) oauthUser.getAttributes().get("email");
            userId = userService.findByUsername(email).map(User::getId).orElse(null);
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            userId = userService.findByUsername(userDetails.getUsername()).map(User::getId).orElse(null);
        }

        if (userId != null) {
            User user = userService.findById(userId).orElse(null);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.badRequest().build();
        }


    }
}
