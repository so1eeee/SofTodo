package sofTodo.toDoList.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sofTodo.toDoList.config.oauth.CustomOAuth2User;
import sofTodo.toDoList.domain.User;
import sofTodo.toDoList.repository.UserRepository;

import java.security.Principal;

@Controller
@RequestMapping("/nickname")
public class NicknameController {

    private final UserRepository userRepository;

    public NicknameController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String nicknameForm(Model model) {
        return "nickname";  // 닉네임 입력 폼 뷰 반환
    }

    @PostMapping
    public String saveNickname(@RequestParam String nickname, @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        User user = customOAuth2User.getUser();
        user.setNickname(nickname);
        userRepository.save(user);
        // CustomOAuth2User 객체의 닉네임 업데이트
        customOAuth2User.setNickname(nickname);
        return "redirect:/home";  // 닉네임 저장 후 홈으로 리디렉션
    }
}

