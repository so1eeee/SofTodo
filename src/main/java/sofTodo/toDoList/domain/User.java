package sofTodo.toDoList.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name="users")
@NoArgsConstructor
@Getter
@Entity
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id" , updatable = false)
    private Long id;

    @Column(name="username", nullable = false, unique = true)
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="auth", nullable =false)
    private String auth;

    @Builder
    public User(String username, String password, String auth){
        this.username = username;
        this.password = password;
        this.auth = auth;
    }
}
