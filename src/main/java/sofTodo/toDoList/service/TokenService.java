package sofTodo.toDoList.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sofTodo.toDoList.config.jwt.TokenProvider;
import sofTodo.toDoList.domain.User;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        Optional<User> userOptional = userService.findById(userId);

        // Optional을 사용하여 User 객체를 얻음
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return tokenProvider.generateToken(user, Duration.ofHours(2));
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
}