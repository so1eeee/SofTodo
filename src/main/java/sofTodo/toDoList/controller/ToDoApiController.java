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

@RestController//HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
@RequiredArgsConstructor
public class ToDoApiController {
    private final ToDoServiceImpl toDoService;
    private final UserService userService;

    @PostMapping("/todo")
    public ResponseEntity<ToDoItem> addToDo(@RequestBody AddToDoRequest request, Authentication authentication) {
        Long userId = null;

        if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
            String email = (String) oauthUser.getAttributes().get("email"); // OAuth2User에서 nickname 추출
            User user = userService.findByUsername(email);
            userId = user.getId();
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.findByUsername(userDetails.getUsername());
            userId = user.getId();
        }

        if (userId != null) {
            User user = userService.findById(userId);
            String nickname = user.getNickname();
            System.out.println("nickname = " + nickname);

            ToDoItem savedToDo = toDoService.saveToDo(request, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedToDo);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/todo")
    public ResponseEntity<List<ToDoResponse>> viewToDo(Authentication authentication) {
        Long userId = null;

        if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
            String email = (String) oauthUser.getAttributes().get("email"); // OAuth2User에서 nickname 추출
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
            return ResponseEntity.ok()
                    .body(todos);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

//    @GetMapping("/todo")
//    public ResponseEntity<List<ToDoResponse>> findAllToDo() {
//        List<ToDoResponse> todos = toDoService.findAll()
//                .stream()
//                .map(ToDoResponse::new)
//                .toList();
//        return ResponseEntity.ok()
//                .body(todos);
//    }

//    @GetMapping("/todo/{id}")
//    public ResponseEntity<ToDoResponse> findToDo(@PathVariable long id) {
//        ToDoItem toDoItem = toDoService.findById(id);
//
//        return ResponseEntity.ok()
//                .body(new ToDoResponse(toDoItem));
//    }
//
//
//    @DeleteMapping("/todo/{id}")
//    public ResponseEntity<Void> deleteToDo(@PathVariable long id) {
//        toDoService.delete(id);
//
//        return ResponseEntity.ok()
//                .build();
//    }
//
//    @PutMapping("/todo/{id}")
//    public ResponseEntity<ToDoItem> updateToDo(@PathVariable long id, @RequestBody UpdateToDoRequest request) {
//        ToDoItem updatedToDo = toDoService.update(id, request);
//
//        return ResponseEntity.ok()
//                .body(updatedToDo);
//    }
}