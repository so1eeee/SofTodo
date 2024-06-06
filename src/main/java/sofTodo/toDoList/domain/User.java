package sofTodo.toDoList.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name="users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id" , updatable = false)
    private Long id;

    @Column(name="nickname", unique = true)
    private String nickname;

    @Column(name="username", unique = true)
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="missionSuccessCount")
    private Long missionSuccessCount = 0L;

    private String slug;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ToDoItem> todoItems;

    public User(Long id, String username, String password, String slug) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.slug = slug;
    }


    public User update(String nickname) {
        this.nickname = nickname;
        return this;
    }

    @Builder
    public User(String username, String nickname, String password, String slug) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.slug = slug;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}