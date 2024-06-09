package sofTodo.toDoList.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import sofTodo.toDoList.domain.ToDoItem;

import java.util.List;

public interface ToDoItemRepository extends JpaRepository<ToDoItem, Long> {
    List<ToDoItem> findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ToDoItem t WHERE t.user.id = :userId")
    void deleteByUserId(Long userId);
}