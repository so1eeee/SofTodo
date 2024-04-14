package sofTodo.toDoList.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sofTodo.toDoList.dto.CustomUserDetails;

import java.util.Collection;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    // 검증담당
    private final JWTUtil jwtUtil;
    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil){
        this.authenticationManager = authenticationManager;
        this.jwtUtil =jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        // 클라이언트 요청에서 중간에 빼감

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password ,null);

        return authenticationManager.authenticate(authToken);
        // 던져주면 알아서 검증 진행
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal(); // 특정유저를 가져올수있다.
        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        //iterator 을 이용해 하나씩 보면서 가져옴
        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(username, role, 60*60*10L); //유효기간
        response.addHeader("Authorization", "Bearer " + token); // 이렇게 앞에 붙이는게 규정임


    } //로그인 성공시 -> jwt 발행하면됨
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed){
        response.setStatus(401);
        //로그인 실패시 401 응답
    }


}
