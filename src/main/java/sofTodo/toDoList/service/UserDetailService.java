package sofTodo.toDoList.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sofTodo.toDoList.domain.User;
import sofTodo.toDoList.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(username));
    }
//    @Override
//    public User loadUserByUsername(String username) throws UsernameNotFoundException {
//        var user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("그런 아이디 없음"));
//
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("일반유저"));
//
//        return new User(user.getUsername(), user.getPassword(), authorities);
//    }

}