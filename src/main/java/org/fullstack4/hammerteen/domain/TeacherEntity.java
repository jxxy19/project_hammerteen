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
@Table(name="hamt_teacher")
public class TeacherEntity extends BaseEntity {

    @Id // 필수
    @Column(unique=true, nullable=false)
    private int teacherIdx;

    @Column(length=20,nullable=false)
    private String categoryName;
    @Column(length=100,nullable=false)
    private String education;
    @Column(length=100,nullable=false)
    private String writing;
    @Column(length=300,nullable=true)
    private String profile;
    @Column(length=300,nullable=false)
    private String name;
    @Column(length=300,nullable=false)
    private String userId;




    public void modify(String categoryName,String education,String writing,String profile,String name){

        this.categoryName=categoryName;
        this.education=education;
        this.writing=writing;
        this.profile=profile;
        this.name=name;


    }



}

