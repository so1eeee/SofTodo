package sofTodo.toDoList.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sofTodo.toDoList.dto.CreateTodoRequestDto;
import sofTodo.toDoList.dto.CreateTodoResponseDto;
import sofTodo.toDoList.dto.ReadTodoDto;
import sofTodo.toDoList.service.TodoService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService)
    {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<List<ReadTodoDto>> readTodo() {

        List<ReadTodoDto> allTodoDto = todoService.findAllTodo();

        return ResponseEntity.ok().body(allTodoDto);
    }

    @PostMapping
    public ResponseEntity<CreateTodoResponseDto> createTodo(@RequestBody CreateTodoRequestDto request) {
        CreateTodoResponseDto response = todoService.createTodo(request);
        return ResponseEntity.created(URI.create("/todo/" + response.getId())).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTodo(@PathVariable Long id, @RequestBody String request) {
        todoService.updateTodo(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {

        todoService.deleteTodo(id);

        return ResponseEntity.noContent().build();
    }



}
