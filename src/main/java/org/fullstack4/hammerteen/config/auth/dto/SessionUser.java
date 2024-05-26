package org.fullstack4.hammerteen.config.auth.dto;

import lombok.Getter;
import org.fullstack4.hammerteen.domain.Role;
import org.fullstack4.hammerteen.domain.User;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private static final long serialVersionUID = 1L; // serialVersionUID 추가

    // SessionUser는 인증된 사용자 정보만 필요하므로 아래 필드만 선언한다.
    private String name;
    private String email;
    private String picture;
    private String userId;
    private Role role;
    private String phoneNumber;
    private String birthday;
    private String addr1;
    private String addr2;
    private String zipCode;
    private String userState;
    private String directory;
    private String fileName;





    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.userId = user.getUserId();
        this.role =user.getRole();
        this.phoneNumber =user.getPhoneNumber();
        this.birthday =user.getBirthday();
        this.addr1 =user.getAddr1();
        this.addr2 =user.getAddr2();
        this.zipCode =user.getZipCode();
        this.userState =user.getUserState();
        this.directory =user.getDirectory();
        this.fileName =user.getFileName();

    }
}