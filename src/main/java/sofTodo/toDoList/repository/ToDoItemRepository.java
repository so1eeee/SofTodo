package sofTodo.toDoList.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sofTodo.toDoList.domain.ToDoItem;

import java.util.List;

public interface ToDoItemRepository extends JpaRepository<ToDoItem, Long> {
    List<ToDoItem> findByUserId(Long userId);
}