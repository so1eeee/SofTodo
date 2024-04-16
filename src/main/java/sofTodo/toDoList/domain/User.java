package sofTodo.toDoList.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name="users", indexes = @Index(columnList = "username", name= "작명"))
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

    @Builder
    public User(String username, String password, String auth){
        this.username = username;
        this.password = password;
    }
}