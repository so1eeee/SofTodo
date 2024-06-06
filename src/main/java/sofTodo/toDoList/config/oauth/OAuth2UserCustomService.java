package sofTodo.toDoList.config.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import sofTodo.toDoList.domain.User;
import sofTodo.toDoList.repository.UserRepository;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        User user = saveOrUpdate(oAuth2User);
        return new CustomOAuth2User(oAuth2User, user);
    }

    private User saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String username = (String) attributes.get("email");

        User user = userRepository.findByUsername(username)
                .map(entity -> {
                    // 필요한 경우 사용자 정보를 업데이트
                    entity.setUsername(username);
                    return userRepository.save(entity);
                })
                .orElseGet(() -> {
                    // 새로운 사용자라면 새로 생성
                    User newUser = User.builder()
                            .username(username)
                            .nickname(null)  // 초기에는 null로 설정
                            .build();
                    return userRepository.save(newUser);
                });

        return user;
    }
}
