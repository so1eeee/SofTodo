package sofTodo.toDoList.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sofTodo.toDoList.domain.RefreshToken;
import sofTodo.toDoList.repository.RefreshTokenRepository;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }
}