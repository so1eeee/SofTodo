package sofTodo.toDoList.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sofTodo.toDoList.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}
