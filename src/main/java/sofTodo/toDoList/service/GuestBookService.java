package sofTodo.toDoList.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sofTodo.toDoList.domain.GuestBook;
import sofTodo.toDoList.domain.User;
import sofTodo.toDoList.dto.AddGuestBookRequest;
import sofTodo.toDoList.dto.UpdateGuestBookRequest;
import sofTodo.toDoList.repository.GuestBookRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuestBookService {
    private final GuestBookRepository guestBookRepository;

    public GuestBook saveGuestBook(AddGuestBookRequest request, User user) {
        return guestBookRepository.save(request.toEntity(user));
    }

    public Optional<GuestBook> findById(long id) {
        return guestBookRepository.findById(id);
    }

    public List<GuestBook> findByUserId(Long userId) {
        return guestBookRepository.findByUserId(userId);
    }

    public void delete(long id) {
        guestBookRepository.deleteById(id);
    }

    @Transactional
    public GuestBook update(long id, UpdateGuestBookRequest request) {
        GuestBook guestBook = guestBookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        guestBook.update(request.getContent(),request.getDate());
        return guestBook;
    }
}