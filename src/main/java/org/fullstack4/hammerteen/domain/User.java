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

    @Column
    @Builder.Default
    private String userId = "socialTest";

    @Column
    @Builder.Default
    private String phoneNumber="01066664444";
    @Column
    @Builder.Default
    private String birthday="1998-12-24";
    @Column
    @Builder.Default
    private String addr1="서울시 노원구";
    @Column
    @Builder.Default
    private String addr2="2층";
    @Column
    @Builder.Default
    private String zipCode="01665";
    @Column
    @Builder.Default
    private String userState="Y";

    @Column
    @Builder.Default
    private String directory="";
    @Column
    @Builder.Default
    private String fileName="26e87dcaprofile.png";


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    public User(String name, String email, String picture, Role role,String UserId,String phoneNumber,String birthday,String addr1,String addr2,String zipCode,String userState,String directory,String fileName) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.userId =  UserId;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.zipCode = zipCode;
        this.userState = userState;
        this.directory = directory;
        this.fileName = fileName;


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
