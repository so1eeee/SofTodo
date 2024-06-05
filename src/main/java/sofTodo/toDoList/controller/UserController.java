package sofTodo.toDoList.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.*;
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
            User user = userService.findByUsername(email);
            userId = user.getId();
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.findByUsername(userDetails.getUsername());
            userId = user.getId();
        }

        if (userId != null) {
            User user = userService.findById(userId);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/{userId}/mission-success")
    public ResponseEntity<Void> incrementMissionSuccess(@PathVariable Long userId) {
        userService.incrementMissionSuccessCount(userId);
        return ResponseEntity.ok().build();
    }
}
