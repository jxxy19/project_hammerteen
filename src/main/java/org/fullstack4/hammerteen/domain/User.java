package org.fullstack4.hammerteen.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Id // 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;

    @Column
    private String picture;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }
    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }



    public String getRoleKey() {
        return this.role.getKey();
    }

}
