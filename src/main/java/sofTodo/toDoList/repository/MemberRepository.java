package sofTodo.toDoList.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sofTodo.toDoList.domain.Member;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
