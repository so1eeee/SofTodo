package sofTodo.toDoList.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.*;
import sofTodo.toDoList.domain.GuestBook;
import sofTodo.toDoList.domain.User;
import sofTodo.toDoList.dto.AddGuestBookRequest;
import sofTodo.toDoList.dto.GuestBookResponse;
import sofTodo.toDoList.dto.UpdateGuestBookRequest;
import sofTodo.toDoList.service.GuestBookService;
import sofTodo.toDoList.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://your-frontend-url")
public class GuestBookApiController {
    private final GuestBookService guestBookService;
    private final UserService userService;

    @GetMapping("/guestBook/{slug}")
    public ResponseEntity<List<GuestBookResponse>> getGuestBookBySlug(@PathVariable String slug) {
        Optional<User> userOptional = userService.findBySlug(slug);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<GuestBookResponse> guestBooks = guestBookService.findByOwnUserId(user.getId())
                    .stream()
                    .map(GuestBookResponse::new)
                    .toList();
            return ResponseEntity.ok().body(guestBooks);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PostMapping("/guestBook/{ownSlug}") // 등록
    public ResponseEntity<GuestBook> addGuestBook(@PathVariable String ownSlug, @RequestBody AddGuestBookRequest request, Authentication authentication) {
        Long userId = extractUserId(authentication); // 입력하는 사람 id
        if (userId != null) {
            Optional<User> ownUserOptional = userService.findBySlug(ownSlug); // 페이지 주인
            Optional<User> guestUserOptional = userService.findById(userId); // 입력하는 사람

            if (guestUserOptional.isPresent() && ownUserOptional.isPresent()) {
                User ownuser = ownUserOptional.get();
                User guestuser = guestUserOptional.get();

                GuestBook savedGuestBook = guestBookService.saveGuestBook(request, ownuser, guestuser);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedGuestBook);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PutMapping("/guestBook/{guestbookId}") //수정
    public ResponseEntity<GuestBook> updateGuestBook(@PathVariable long guestbookId, @RequestBody UpdateGuestBookRequest request, Authentication authentication) {
        Long userId = extractUserId(authentication); // 입력하는 사람
        if (userId != null) {
            Optional<GuestBook> guestBookOptional = guestBookService.findById(guestbookId);
            if (guestBookOptional.isPresent()) {
                GuestBook guestBook = guestBookOptional.get();
                if (guestBook.getGuestUser().getId().equals(userId)){
                    GuestBook updatedGuestBook = guestBookService.update(guestbookId, request);
                    return ResponseEntity.ok().body(updatedGuestBook);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @DeleteMapping("/guestBook/{guestbookId}")
    public ResponseEntity<Void> deleteGuestBook(@PathVariable long guestbookId, Authentication authentication) {
        Long userId = extractUserId(authentication); // 쓰는 사람
        if (userId != null) {
            Optional<GuestBook> guestBookOptional = guestBookService.findById(guestbookId);
            if (guestBookOptional.isPresent()) {
                GuestBook guestBook = guestBookOptional.get();
                if (guestBook.getGuestUser().getId().equals(userId)) {
                    guestBookService.delete(guestbookId);
                    return ResponseEntity.ok().build();
                }
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    private Long extractUserId(Authentication authentication) {
        if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
            String email = (String) oauthUser.getAttributes().get("email");
            return userService.findByUsername(email)
                    .map(User::getId)
                    .orElse(null);
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userService.findByUsername(userDetails.getUsername())
                    .map(User::getId)
                    .orElse(null);
        }
        return null;
    }
}
