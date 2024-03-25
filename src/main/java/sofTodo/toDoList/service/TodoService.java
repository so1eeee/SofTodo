package sofTodo.toDoList.service;

import org.springframework.stereotype.Service;
import sofTodo.toDoList.domain.Todo;
import sofTodo.toDoList.dto.ReadTodoDto;
import sofTodo.toDoList.dto.CreateTodoRequestDto;
import sofTodo.toDoList.dto.CreateTodoResponseDto;
import sofTodo.toDoList.repsoitory.TodoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<ReadTodoDto> findAllTodo() {
        return todoRepository.findAllTodo().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CreateTodoResponseDto createTodo(CreateTodoRequestDto request) {
        Todo todo = new Todo(request.getId(), request.getContent());
        Todo savedTodo = todoRepository.save(todo);
        CreateTodoResponseDto response = new CreateTodoResponseDto(savedTodo.getId(), savedTodo.getContent());
        return response;
    }

    public void deleteTodo(Long id) {
        todoRepository.delete(id);
    }

    public void updateTodo(Long id, String content) {
        todoRepository.update(id, content);
    }
    private ReadTodoDto convertToDto(Todo todo) {
        return new ReadTodoDto(todo.getId(), todo.getContent());
    }

}
