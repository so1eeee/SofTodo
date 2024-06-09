package sofTodo.toDoList.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sofTodo.toDoList.domain.User;
import sofTodo.toDoList.service.UserService;
import sofTodo.toDoList.dto.PartnerResponse;

@RequiredArgsConstructor
@RestController
public class PartnerController {
    private final UserService userService;

    @GetMapping("/partner/{slug}")
    public ResponseEntity<PartnerResponse> getPartner(@PathVariable String slug, Authentication authentication) {
        Long userId = extractUserId(authentication);

        if (userId != null) {
            User user = userService.findBySlug(slug).orElse(null);
            if (user != null && user.getWeeklyPartner() != null) {
                return ResponseEntity.ok(new PartnerResponse(user.getWeeklyPartner()));
            }
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.badRequest().build();
    }

    private Long extractUserId(Authentication authentication) {
        if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
            String email = (String) oauthUser.getAttributes().get("email");
            return userService.findByUsername(email).map(User::getId).orElse(null);
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userService.findByUsername(userDetails.getUsername()).map(User::getId).orElse(null);
        }
        return null;
    }
}
