package sofTodo.toDoList.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.*;
import sofTodo.toDoList.domain.User;
import sofTodo.toDoList.repository.UserRepository;
import sofTodo.toDoList.service.GuestBookService;
import sofTodo.toDoList.service.UserService;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final NicknameController nicknameController;
    private final GuestBookService guestBookService;

    @GetMapping("/user")
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

    @DeleteMapping("/user")
    public ResponseEntity<Void> deleteUser(Authentication authentication) {
        Long userId = extractUserId(authentication);

        if (userId != null) {
            guestBookService.deleteByOwnUserId(userId); // 먼저 관련된 GuestBook 레코드를 삭제
            // 삭제하기 전에 해당 사용자가 파트너로 설정된 모든 사용자들의 파트너 관계를 해제
            userService.nullifyPartnersForUser(userId);
            userService.deleteUserById(userId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

    //닉네임 중복 검사
    @GetMapping("/api/check-nickname")
    public ResponseEntity<Boolean> checkNickname(@RequestParam String nickname) {
        boolean exists = userRepository.existsByNickname(nickname);
        return ResponseEntity.ok(exists);
    }

    // 아이디 중복 검사
    @GetMapping("/api/check-username")
    public ResponseEntity<Boolean> checkUsername(@RequestParam String username) {
        boolean exists = userRepository.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/user/{slug}/increment-mission-success")
    public ResponseEntity<Void> incrementMissionSuccessCount(@PathVariable String slug) {
        User user = userService.findBySlug(slug).orElseThrow(() -> new IllegalArgumentException("User not found"));
        System.out.println("Increment mission success for user " + user.getId()); // 로그 추가
        userService.incrementMissionSuccessCount(user.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/{slug}/decrement-mission-success")
    public ResponseEntity<Void> decrementMissionSuccessCount(@PathVariable String slug) {
        User user = userService.findBySlug(slug).orElseThrow(() -> new IllegalArgumentException("User not found"));
        System.out.println("Decrement mission success for user " + user.getId()); // 로그 추가
        userService.decrementMissionSuccessCount(user.getId());
        return ResponseEntity.ok().build();
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