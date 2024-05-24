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
@Table(name="hamt_qna")
public class QnaEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int qnaIdx;

    @Column(length = 50, nullable = false)
    private String questionTitle;

    @Column(length = 2000, nullable = false)
    private String questionContent;


    @Column(length = 20)
    private String answerTitle;

    @Column(length = 2000)
    private String answerContent;

    @Column(length = 5)
    private String answerYn;

    @Column
    private LocalDateTime answerRegDate;

    @Column(length = 20, nullable = false)
    private String userId;

    public void modify(String questionTitle , String questionContent ,String answerTitle ,String answerContent){

        this.questionTitle=questionTitle;
        this.questionContent=questionContent;
        this.answerTitle=answerTitle;
        this.answerContent=answerContent;

    }
}
