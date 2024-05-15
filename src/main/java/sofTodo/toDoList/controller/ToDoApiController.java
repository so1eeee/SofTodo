package sofTodo.toDoList.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sofTodo.toDoList.domain.ToDoItem;
import sofTodo.toDoList.dto.AddToDoRequest;
import sofTodo.toDoList.dto.ToDoResponse;
import sofTodo.toDoList.dto.UpdateToDoRequest;
import sofTodo.toDoList.service.ToDoServiceImpl;

import java.util.List;

@RestController//HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
@RequiredArgsConstructor
public class ToDoApiController {
    private final ToDoServiceImpl toDoService;

    @PostMapping("/todo")
    public ResponseEntity<ToDoItem> addToDo(@RequestBody AddToDoRequest request) { //@RequestBody 는 HTTP를 요청할 때 응답에 해당하는 값을 @RequestBody가 붙은 대상 객체에 매핑한다.
        ToDoItem savedToDo = toDoService.saveToDo(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedToDo);
    }

    @GetMapping("/todo")
    public ResponseEntity<List<ToDoResponse>> findAllToDo() {
        List<ToDoResponse> todos = toDoService.findAll()
                .stream()
                .map(ToDoResponse::new)
                .toList();
        return ResponseEntity.ok()
                .body(todos);
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<ToDoResponse> findToDo(@PathVariable long id) {
        ToDoItem toDoItem = toDoService.findById(id);

        return ResponseEntity.ok()
                .body(new ToDoResponse(toDoItem));
    }


    @DeleteMapping("/todo/{id}")
    public ResponseEntity<Void> deleteToDo(@PathVariable long id) {
        toDoService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/todo/{id}")
    public ResponseEntity<ToDoItem> updateToDo(@PathVariable long id, @RequestBody UpdateToDoRequest request) {
        ToDoItem updatedToDo = toDoService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedToDo);
    }
}