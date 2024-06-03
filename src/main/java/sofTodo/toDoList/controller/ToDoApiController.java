package sofTodo.toDoList.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.*;
import sofTodo.toDoList.domain.ToDoItem;
import sofTodo.toDoList.domain.User;
import sofTodo.toDoList.dto.AddToDoRequest;
import sofTodo.toDoList.dto.ToDoResponse;
import sofTodo.toDoList.dto.UpdateToDoRequest;
import sofTodo.toDoList.service.ToDoServiceImpl;
import sofTodo.toDoList.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ToDoApiController {
    private final ToDoServiceImpl toDoService;
    private final UserService userService;

    @PostMapping("/todo")
    public ResponseEntity<ToDoItem> addToDo(@RequestBody AddToDoRequest request, Authentication authentication) {
        Long userId = extractUserId(authentication);

        if (userId != null) {
            User user = userService.findById(userId);
            ToDoItem savedToDo = toDoService.saveToDo(request, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedToDo);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    private Long extractUserId(Authentication authentication) {
        if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
            String email = (String) oauthUser.getAttributes().get("email");
            User user = userService.findByUsername(email);
            return user != null ? user.getId() : null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.findByUsername(userDetails.getUsername());
            return user != null ? user.getId() : null;
        }
        return null;
    }

    @GetMapping("/todo")
    public ResponseEntity<List<ToDoResponse>> viewToDo(Authentication authentication) {
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
            List<ToDoResponse> todos = toDoService.findByUserId(userId)
                    .stream()
                    .map(ToDoResponse::new)
                    .toList();
            return ResponseEntity.ok().body(todos);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/todo/{userId}/{todoId}")
    public ResponseEntity<Void> deleteToDo(@PathVariable long userId, @PathVariable long todoId) {
        User user = userService.findById(userId);
        if (user != null) {
            ToDoItem toDoItem = toDoService.findById(todoId);
            if (toDoItem != null && toDoItem.getUser().getId().equals(userId)) {
                toDoService.delete(todoId);
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/todo/{userId}/{todoId}")
    public ResponseEntity<ToDoItem> updateToDo(@PathVariable long userId, @PathVariable long todoId, @RequestBody UpdateToDoRequest request) {
        User user = userService.findById(userId);
        if (user != null) {
            ToDoItem toDoItem = toDoService.findById(todoId);
            if (toDoItem != null && toDoItem.getUser().getId().equals(userId)) {
                ToDoItem updatedToDo = toDoService.update(todoId, request);
                return ResponseEntity.ok().body(updatedToDo);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
