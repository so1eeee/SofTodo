package sofTodo.toDoList.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class NicknameProcessingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().equals("/oauth2/authorization/google") && request.getMethod().equals("GET")) {
            String nickname = request.getParameter("nickname");
            if (nickname != null && !nickname.isEmpty()) {
                request.getSession().setAttribute("nickname", nickname);
            } else {
                System.out.println("No nickname found in request");
            }
        }
        filterChain.doFilter(request, response);
    }
}
