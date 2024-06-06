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

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ToDoApiController {
    private final ToDoServiceImpl toDoService;
    private final UserService userService;

    @PostMapping("/todo")
    public ResponseEntity<ToDoItem> addToDo(@RequestBody AddToDoRequest request, Authentication authentication) {
        Long userId = extractUserId(authentication);

        if (userId != null) {
            Optional<User> userOptional = userService.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                ToDoItem savedToDo = toDoService.saveToDo(request, user);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedToDo);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
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

    @GetMapping("/todo")
    public ResponseEntity<List<ToDoResponse>> viewToDo(Authentication authentication) {
        Long userId = extractUserId(authentication);

        if (userId != null) {
            List<ToDoResponse> todos = toDoService.findByUserId(userId)
                    .stream()
                    .map(ToDoResponse::new)
                    .toList();
            return ResponseEntity.ok().body(todos);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping("/todo/{slug}")
    public ResponseEntity<List<ToDoResponse>> getTodosBySlug(@PathVariable String slug) {
        Optional<User> userOptional = userService.findBySlug(slug);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<ToDoResponse> todos = toDoService.findByUserId(user.getId())
                    .stream()
                    .map(ToDoResponse::new)
                    .toList();
            return ResponseEntity.ok().body(todos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @DeleteMapping("/todo/{todoId}")
    public ResponseEntity<Void> deleteToDo(@PathVariable long todoId, Authentication authentication) {
        Long userId = extractUserId(authentication);
        if (userId != null) {
            Optional<ToDoItem> toDoItemOptional = toDoService.findById(todoId);
            if (toDoItemOptional.isPresent()) {
                ToDoItem toDoItem = toDoItemOptional.get();
                if (toDoItem.getUser().getId().equals(userId)) {
                    toDoService.delete(todoId);
                    return ResponseEntity.ok().build();
                }
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/todo/{todoId}")
    public ResponseEntity<ToDoItem> updateToDo(@PathVariable long todoId, @RequestBody UpdateToDoRequest request, Authentication authentication) {
        Long userId = extractUserId(authentication);
        if (userId != null) {
            Optional<ToDoItem> toDoItemOptional = toDoService.findById(todoId);
            if (toDoItemOptional.isPresent()) {
                ToDoItem toDoItem = toDoItemOptional.get();
                if (toDoItem.getUser().getId().equals(userId)) {
                    ToDoItem updatedToDo = toDoService.update(todoId, request);
                    return ResponseEntity.ok().body(updatedToDo);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}