package sofTodo.toDoList.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sofTodo.toDoList.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    List<User> findTop5ByOrderByMissionSuccessCountDesc();
    List<User> findAllByNicknameContains(String userNickName);
    Optional<User> findBySlug(String slug);

    //닉네임 중복 검사
    boolean existsByNickname(String nickname);

    //아이디 중복 검사
    boolean existsByUsername(String username);
}