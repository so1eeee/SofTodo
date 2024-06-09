package sofTodo.toDoList.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sofTodo.toDoList.domain.User;
import sofTodo.toDoList.dto.AddUserRequest;
import sofTodo.toDoList.repository.UserRepository;
import sofTodo.toDoList.service.UserService;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;
    private final UserRepository userRepository;

    // 유효성 검사 기능 추가
    @PostMapping("/user")
    public String signup(@Valid AddUserRequest request, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("addUserRequest", request);
            return "signup";
        }

        // 비밀번호 확인
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.addUserRequest", "비밀번호가 일치하지 않습니다.");
            model.addAttribute("addUserRequest", request);
            return "signup";
        }

        // 닉네임, 아이디 중복검사 예외처리
        try {
            userService.save(request);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("닉네임")) {
                result.rejectValue("nickname", "error.addUserRequest", e.getMessage());
            } else if (e.getMessage().contains("아이디")) {
                result.rejectValue("username", "error.addUserRequest", e.getMessage());
            }
            model.addAttribute("addUserRequest", request);
            return "signup";
        }

        return "redirect:/login";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.findByUsername(userDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
            model.addAttribute("user", user);
            return "edit";
        }
        return "redirect:/";
    }

    @PostMapping("/user/update-nickname")
    public String updateNickname(@RequestParam("nickname") String nickname, Model model, Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            try {
                userService.updateNickname(username, nickname);

                // 사용자 정보를 세션에 저장하고 업데이트
                User updatedUser = userService.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        updatedUser, null, updatedUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                return "redirect:/my-page";
            } catch (IllegalArgumentException e) {
                model.addAttribute("nicknameError", e.getMessage());
                User user = userService.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
                model.addAttribute("user", user);
                return "edit";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/user/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/user/login";
    }

    @GetMapping("/search")
    public String memberSearch(@RequestParam(required = false) String userNickName, Model model) {
        if (userNickName == null || userNickName.isEmpty()) {
            return "memberlist";
        }

        var result = userRepository.findAllByNicknameContains(userNickName);
        model.addAttribute("users", result);

        return "memberlist";
    }
}