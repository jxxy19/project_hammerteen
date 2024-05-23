package org.fullstack4.hammerteen.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="hamt_lecture_notice")
public class LectureNoticeEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private int lectureNoticeIdx;

    @Column(unique=true, nullable=false)
    private int lectureIdx;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false)
    private String content;

    @Column(nullable=true)
    private int readCnt;


    public void modify(String title,String content){
        this.title=title;
        this.content=content;
        super.setModify_date(LocalDateTime.now());
    }


}

