package sofTodo.toDoList.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sofTodo.toDoList.domain.GuestBook;

import java.util.List;
import java.util.Optional;

public interface GuestBookRepository extends JpaRepository<GuestBook, Long> {
    List<GuestBook> findByOwnUserId(long ownId);
    Optional<GuestBook> findById(long id);
}
