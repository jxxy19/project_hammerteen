package org.fullstack4.hammerteen.config.auth.dto;

import lombok.Getter;
import org.fullstack4.hammerteen.domain.User;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    // SessionUser는 인증된 사용자 정보만 필요하므로 아래 필드만 선언한다.
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}