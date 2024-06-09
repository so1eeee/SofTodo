package sofTodo.toDoList.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sofTodo.toDoList.domain.GuestBook;

import java.util.List;

public interface GuestBookRepository extends JpaRepository<GuestBook, Long> {
    List<GuestBook> findByUserId(Long userId);
}
