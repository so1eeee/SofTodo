package sofTodo.toDoList.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import sofTodo.toDoList.domain.GuestBook;

import java.util.List;
import java.util.Optional;

public interface GuestBookRepository extends JpaRepository<GuestBook, Long> {
    List<GuestBook> findByOwnUserId(long ownId);
    Optional<GuestBook> findById(long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM GuestBook gb WHERE gb.guestUser.id = :userId")
    void deleteByGuestUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM GuestBook gb WHERE gb.ownUser.id = :ownUserId")
    void deleteByOwnUserId(Long ownUserId);
}
