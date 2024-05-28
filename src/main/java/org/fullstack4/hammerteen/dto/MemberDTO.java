package org.fullstack4.hammerteen.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {
    private int memberIdx;
    @NotEmpty
    private String userId;
    private String pwd;
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private String birthday;
    @NotEmpty
    private String addr1;
    private String addr2;
    @NotEmpty
    private String zipCode;


    private String directory;
    private String fileName;
    private String temFileName;

    @NotEmpty
    private String userState;

    private LocalDateTime regDate;

    private LocalDateTime leaveDate;

    private LocalDateTime modifyDate;

    //회원권한
    @Builder.Default
    private String role="user";
}
