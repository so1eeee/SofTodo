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
        // 닉네임 중복 체크
        if (userRepository.existsByNickname(dto.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        //아이디 중복 체크
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 비밀번호 암호화 및 사용자 저장
        userRepository.save(User.builder()
                .nickname(dto.getNickname())
                .username(dto.getUsername())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .slug(generateSlug(dto.getNickname()))
                .build());
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

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> getTop5UsersByMissionSuccessCount() {
        return userRepository.findTop5ByOrderByMissionSuccessCountDesc();
    }

    public void incrementMissionSuccessCount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setMissionSuccessCount(user.getMissionSuccessCount() + 1);
        userRepository.save(user);
    }

    public void decrementMissionSuccessCount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setMissionSuccessCount(user.getMissionSuccessCount() - 1);
        userRepository.save(user);
    }
}

