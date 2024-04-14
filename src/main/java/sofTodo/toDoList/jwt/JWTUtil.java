package sofTodo.toDoList.jwt;

import java.util.Date;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Component
public class JWTUtil { //jwt 생성검증 발급하는곳
    // 0.12.3 버전으로 했는데 제일 최근꺼임
    // 덜 최근으로 많있느느건 0.11.5
    private final SecretKey secretKey;

    private JWTUtil(@Value("${spring.jwt.secretKey}")String secret){
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    } // 시크립 키를 기반으로 암호키 만들어냄
    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    } // 토큰을 받아 데이터랑 확인 -> 우리키랑 확인 -> Claim을 확인하고 특정한 데이터를 가져옴

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    } // role 가져옴

    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    } //만료되었는지

    public String createJwt(String username, String role, Long expiredMs) {
        // 이름, 롤, 토큰의 유효기간
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey) //암호화 진행
                .compact();
    }
}
