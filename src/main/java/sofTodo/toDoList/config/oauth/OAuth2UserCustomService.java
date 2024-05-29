package sofTodo.toDoList.config.oauth;

import jakarta.servlet.http.HttpSession;
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
    private final HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        User user = saveOrUpdate(oAuth2User);
        return new CustomOAuth2User(oAuth2User, user);
    }

    private User saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String username = (String) attributes.get("email");
        String nickname = (String) session.getAttribute("nickname");

        User user = userRepository.findByUsername(username)
                .map(entity -> {
                    entity.update(nickname);
                    return entity;
                })
                .orElse(User.builder()
                        .username(username)
                        .nickname(nickname)
                        .build());

        return userRepository.save(user);
    }
}
