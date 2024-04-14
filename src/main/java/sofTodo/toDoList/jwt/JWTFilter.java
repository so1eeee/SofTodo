package sofTodo.toDoList.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import sofTodo.toDoList.domain.User;
import sofTodo.toDoList.dto.CustomUserDetails;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {
    //토큰 검증 필터를 만든다. 근데 세션으로 하는데 Stateless라 한번하고 사라짐

    private final JWTUtil jwtUtil;
    public JWTFilter(JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //request에서 헤더를 가져옴
        String authorization = request.getHeader("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer")){
            System.out.println("token null");
            filterChain.doFilter(request, response);
            //필터에서 들어온걸 다음으로 넘김
            // 뽑아온건데 그게 빈거나 규칙을 만족안하면 종료해야한다.
            return ;
        }
        String token = authorization.split(" ")[1];
        // 토큰에서 앞부분 제거하고 뒤에만 가져감

        if(jwtUtil.isExpired(token)){
            System.out.println("token expired");
            filterChain.doFilter(request, response);
            return;
        } // 소멸시간까지 확인한다

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        User user = new User(username,"temppassword",role);

        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
        // 세션이 생성되서 특정한 곳에서 접근 가능

        filterChain.doFilter(request,response);


    }


}
