package org.fullstack4.hammerteen.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="hamt_member")
public class MemberEntity extends BaseEntity {

    @Id // 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)

    private int memberIdx;
    @Column(length=20,nullable=false)
    private String userId;
    @Column(length=100,nullable=false)
    private String pwd;
    @Column(length=10,nullable=false)
    private String name;
    @Column(length=30,nullable=false)
    private String email;
    @Column(length=20,nullable = false)
    private String phoneNumber;
    @Column(length =20 ,nullable=false)
    private String birthday;
    @Column(length =50 ,nullable=false)  //도로명 주소
    private String addr1;
    @Column(length =30 ,nullable=false) // 상세주소
    private String addr2;
    @Column(length =10 ,nullable=false) // 우편번호
    private String zipCode;
    @Column(length =10 ,nullable=false) //회원상태
    private String userState;
    @LastModifiedDate
    @Column(name="leave_date",insertable = false ,updatable = false,nullable = true) //탈퇴일자
    private LocalDateTime leaveDate;
    @Column(length = 10,nullable = true)
    private String role;

    @Column(length = 1000,nullable = true)
    private String directory;
    @Column(length = 1000,nullable = true)
    private String fileName;

    public void modify(String pwd,String email,String phone_number,String addr1,String addr2,String zip_code,String user_state,String fileName, String directory){

        this.pwd=pwd;
        this.email=email;
        this.phoneNumber=phone_number;
        this.addr1=addr1;
        this.addr2=addr2;
        this.zipCode=zip_code;
        this.userState=user_state;
        this.fileName=fileName;
        this.directory=directory;

    }

    public void DetailModify(String pwd,String email,String phone_number,String addr1,String addr2,String zip_code,String user_state,String fileName, String directory ,String role){

        this.pwd=pwd;
        this.email=email;
        this.phoneNumber=phone_number;
        this.addr1=addr1;
        this.addr2=addr2;
        this.zipCode=zip_code;
        this.userState=user_state;
        this.fileName=fileName;
        this.directory=directory;
        this.role=role;

    }


    public void delete(String user_state){


        this.userState=user_state;

    }

}

