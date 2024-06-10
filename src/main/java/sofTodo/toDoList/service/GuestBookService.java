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

    public GuestBook saveGuestBook(AddGuestBookRequest request, User own_user ,User guest_user) {
        return guestBookRepository.save(request.toEntity(own_user,guest_user));
    }

    public Optional<GuestBook> findById(long id) {
        return guestBookRepository.findById(id);
    }

    public List<GuestBook> findByOwnUserId(Long ownUserId) { // 주인찾기
        return guestBookRepository.findByOwnUserId(ownUserId);
    }

    public void delete(long id) {
        guestBookRepository.deleteById(id);
    }

    @Transactional
    public GuestBook update(long id, UpdateGuestBookRequest request) {
        Optional<GuestBook> optionalGuestBook = guestBookRepository.findById(id);
        if(optionalGuestBook.isPresent()) {
            GuestBook guestBook = optionalGuestBook.get();
            guestBook.update(request.getContent(), request.getDate());
            return guestBook;
        }
        else return null;
    }

    public void deleteByOwnUserId(Long ownUserId) {
        guestBookRepository.deleteByOwnUserId(ownUserId);
    }
}