package org.fullstack4.hammerteen.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="hamt_lecture_qna")
public class LectureQnaEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private int lectureQnaIdx;

    @Column(unique=true, nullable=false)
    private int lectureIdx;

    @Column(nullable=false)
    private String questionTitle;

    @Column(nullable=false)
    private String questionContent;

    @Column(nullable=false)
    private String userId;

    @Column(nullable=true)
    private String answerTitle;

    @Column(nullable=true)
    private String answerContent;

    @Column(nullable=true)
    private String answerYn;


    @Column(nullable=true)
    private LocalDateTime answerRegDate;

    public void modify(String questionTitle,String questionContent ,String answerTitle, String answerContent){
        this.questionTitle=questionTitle;
        this.questionContent=questionContent;
        this.answerTitle=answerTitle;
        this.answerContent=answerContent;
        super.setModify_date(LocalDateTime.now());
    }

}


