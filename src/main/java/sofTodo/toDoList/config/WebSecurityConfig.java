package sofTodo.toDoList.config;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import sofTodo.toDoList.jwt.JWTFilter;
import sofTodo.toDoList.jwt.JWTUtil;
import sofTodo.toDoList.jwt.LoginFilter;
import sofTodo.toDoList.service.UserDetailService;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
public class WebSecurityConfig {
//    private final UserDetailService userService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

    public WebSecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        //CORS 설정해줘야된다. 프로트랑 백이랑 연결시키려는 느낌 토큰때문에 중요함
        http.cors((cors)-> cors
                .configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000")); // 허용할 주소
                        configuration.setAllowedMethods(Collections.singletonList("*")); // 모든 메소드 허용
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*")); // 허용할 헤더
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList("Authorization")); // 사용자에게 jwt 보낼 헤더 허용

                        return configuration;
                    }
                }));



        http.csrf((auth) -> auth.disable());
        // csrf disable
        // 세션방식에서는 방어해주지만 jwt 방식은 stateless 상태여서

        http.formLogin((auth)-> auth.disable());
        // form disable

        http.httpBasic((auth) -> auth.disable());
        //http basic 방식

        http.authorizeHttpRequests((auth) ->
                auth.requestMatchers(
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/signup"),
                                new AntPathRequestMatcher("/user")
                        ).permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN") // admin은 다 가질수 잇도록
                        .anyRequest().authenticated()
        );

        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration),jwtUtil), UsernamePasswordAuthenticationFilter.class);
        // 필터를 대체
        http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //STATELESS 가 제일 중요

        http.logout(logout -> logout.logoutUrl("/logout"));
        return http.build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) throws Exception {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userService);
//        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
//        return new ProviderManager(authProvider);
//    } // 이거말고
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}