//package sofTodo.toDoList.config;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import sofTodo.toDoList.config.jwt.TokenProvider;
//import sofTodo.toDoList.service.UserService;
//
//import java.io.IOException;
//import java.time.Duration;
//
//@Component
//@RequiredArgsConstructor
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//    private final UserService userService;
//    private final TokenProvider tokenProvider;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        String username = authentication.getName();
//        userService.findByUsername(username).ifPresent(user -> {
//            try {
//                String token = tokenProvider.generateToken(user, Duration.ofHours(2)); // Duration 추가
//                response.sendRedirect("/home/" + user.getSlug() + "?token=" + token);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//    }
//}

package sofTodo.toDoList.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import sofTodo.toDoList.config.jwt.TokenProvider;
import sofTodo.toDoList.service.UserService;
import sofTodo.toDoList.domain.User;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = extractUsername(authentication);

        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            try {
                String token = tokenProvider.generateToken(user, Duration.ofHours(2)); // Duration 추가
                response.sendRedirect("/home/" + user.getSlug() + "?token=" + token);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            response.sendRedirect("/login?error=user_not_found");
        }
    }

    private String extractUsername(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof DefaultOAuth2User) {
            DefaultOAuth2User oauthUser = (DefaultOAuth2User) principal;
            return (String) oauthUser.getAttributes().get("email"); // Assuming the email is the username
        } else if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            return userDetails.getUsername();
        } else {
            return principal.toString();
        }
    }
}