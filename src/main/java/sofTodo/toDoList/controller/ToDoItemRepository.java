package sofTodo.toDoList.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import sofTodo.toDoList.domain.ToDoItem;

public interface ToDoItemRepository extends JpaRepository<ToDoItem, Long> {

}
