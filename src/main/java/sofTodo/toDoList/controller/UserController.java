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
    @PostMapping("/{slug}/increment-mission-success")
    public ResponseEntity<Void> incrementMissionSuccessCount(@PathVariable String slug) {
        User user = userService.findBySlug(slug).orElseThrow(() -> new IllegalArgumentException("User not found"));
        System.out.println("Increment mission success for user " + user.getId()); // 로그 추가
        userService.incrementMissionSuccessCount(user.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{slug}/decrement-mission-success")
    public ResponseEntity<Void> decrementMissionSuccessCount(@PathVariable String slug) {
        User user = userService.findBySlug(slug).orElseThrow(() -> new IllegalArgumentException("User not found"));
        System.out.println("Decrement mission success for user " + user.getId()); // 로그 추가
        userService.decrementMissionSuccessCount(user.getId());
        return ResponseEntity.ok().build();
    }
}
