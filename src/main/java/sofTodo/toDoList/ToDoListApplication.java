package sofTodo.toDoList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sofTodo.toDoList.controller.TodoController;
import sofTodo.toDoList.domain.Todo;
import sofTodo.toDoList.dto.CreateTodoRequestDto;
import sofTodo.toDoList.repsoitory.TodoRepository;
import sofTodo.toDoList.service.TodoService;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ToDoListApplication {
	public static void main(String[] args) {
		System.out.println("Branch develop");
		System.out.println("Branch develop");
		System.out.println("Branch develop");
		System.out.println("Branch develop");
		SpringApplication.run(ToDoListApplication.class, args);

		CreateTodoRequestDto todo1 = new CreateTodoRequestDto(1L, "오영록의 Todo");
		CreateTodoRequestDto todo2 = new CreateTodoRequestDto(2L, "이재빈의 Todo");
		CreateTodoRequestDto todo3 = new CreateTodoRequestDto(3L, "정솔의 Todo");

		List<Todo> todoList = new ArrayList<Todo>();

		TodoRepository todoRepo = new TodoRepository(todoList);

		TodoService todoSer = new TodoService(todoRepo);

		TodoController todoCon = new TodoController(todoSer);

		todoCon.readTodo();
		todoCon.createTodo(todo1);
		todoCon.createTodo(todo2);
		todoCon.createTodo(todo3);
		todoCon.deleteTodo(2L);
		todoCon.readTodo();

	}
}