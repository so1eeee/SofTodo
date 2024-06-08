package sofTodo.toDoList.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sofTodo.toDoList.domain.User;
import sofTodo.toDoList.dto.GuestBookResponse;
import sofTodo.toDoList.service.GuestBookService;

import sofTodo.toDoList.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class GuestBookApiController {
    private final GuestBookService guestBookService;
    private final UserService userService;

    @GetMapping("/guestBook/{slug}")
    public ResponseEntity<List<GuestBookResponse>> getGuestBookBySlug(@PathVariable String slug) {
        Optional<User> userOptional = userService.findBySlug(slug);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<GuestBookResponse> guestBooks = guestBookService.findByUserId(user.getId())
                    .stream()
                    .map(GuestBookResponse::new)
                    .toList();
            return ResponseEntity.ok().body(guestBooks);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
