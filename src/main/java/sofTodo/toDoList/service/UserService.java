package sofTodo.toDoList.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sofTodo.toDoList.domain.User;
import sofTodo.toDoList.dto.AddUserRequest;
import sofTodo.toDoList.repository.UserRepository;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(AddUserRequest dto) {
        userRepository.save(User.builder()
                .nickname(dto.getNickname())
                .username(dto.getUsername())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .slug(generateSlug(dto.getNickname()))
                .build());
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findBySlug(String slug) {
        return userRepository.findBySlug(slug);
    }

    public String generateSlug(String nickname) {
        String slug = Normalizer.normalize(nickname, Normalizer.Form.NFD);
        slug = slug.replaceAll("\\p{InCombiningDiacriticalMarks}", "");
        slug = slug.toLowerCase(Locale.ENGLISH);
        slug = slug.replaceAll("[^a-z0-9]+", "-");
        slug = slug.replaceAll("-$", "");
        return slug;
    }

    public List<User> getTop5UsersByMissionSuccessCount() {
        return userRepository.findTop5ByOrderByMissionSuccessCountDesc();
    }
}