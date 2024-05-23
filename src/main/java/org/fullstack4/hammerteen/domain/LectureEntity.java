package org.fullstack4.hammerteen.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="hamt_lecture")
public class LectureEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private int lectureIdx;

    @Column(unique=true, nullable=false) //강의목차
    private String categoryIdx;

    @Column(nullable=false)
    private String title;
    @Column(nullable=false)
    private int price;

    @Column(length=20,nullable=false)
    private String content;

    @Column(nullable=false)
    private LocalDateTime startDate;

    @Column(nullable=false)
    private LocalDateTime endDate;

    @Column(nullable=true)
    private String video;

    @Column(nullable=true)
    private String img;

    @Column(nullable=true) //강의 추천태그
    private String lectureRecommend;

    public void modify(String title, int price, String content, LocalDateTime start_date,LocalDateTime end_date, String video, String img){
        this.title=title;
        this.price=price;
        this.content=content;
        this.startDate=start_date;
        this.endDate=end_date;
        this.video = video;
        this.img =img;

    }


}

