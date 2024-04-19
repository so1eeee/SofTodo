//package sofTodo.toDoList.service;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.stereotype.Service;
//import org.springframework.ui.Model;
//import sofTodo.toDoList.domain.User;
//import sofTodo.toDoList.dto.AddUserRequest;
//import sofTodo.toDoList.repository.UserRepository;
//
//import java.util.Optional;
//
//@RequiredArgsConstructor
//@Service
//public class UserService {
//    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    public Long save(AddUserRequest dto) {
//        return userRepository.save(User.builder()
//                .username(dto.getUsername())
//                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
//                .build()).getId();
//    }
//
//    public void logout(HttpServletRequest request, HttpServletResponse response) {
//        new SecurityContextLogoutHandler().logout(request, response,
//                SecurityContextHolder.getContext().getAuthentication()
//        );
//    }
//
//    public void doSearch(String searchUser, Model model){
//        Optional<User> result = userRepository.findByUsername(searchUser);
//        result.ifPresent(user -> model.addAttribute("user", user));
//    }
//
//}