package sofTodo.toDoList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToDoListApplication {
	public static void main(String[] args) {
		System.out.println("Main Branch");
		SpringApplication.run(ToDoListApplication.class, args);
	}
}