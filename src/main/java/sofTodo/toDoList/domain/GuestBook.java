package sofTodo.toDoList.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

@Table(name = "guest_book")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class GuestBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name ="content", nullable = false)
    private String content;

    @Column(name ="date", nullable = false)
    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore()
    private User user;

    @Builder
    public GuestBook(User user,String content,String date) {
        this.user = user;
        this.content = content;
        this.date = date;
    }
    public void update(String content,String date) {
        this.content = content;
        this.date = date;
    }

}
