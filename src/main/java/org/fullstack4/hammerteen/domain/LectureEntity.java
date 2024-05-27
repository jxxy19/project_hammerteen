package org.fullstack4.hammerteen.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDate;
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
    @Column(nullable=false, length = 10)
    private String categoryIdx;
    @Column(nullable=false)
    private int teacherIdx;
    @Column(length=20,nullable=false)
    private String teacherName;
    @Column(nullable=false, length = 50)
    private String title;
    @Column(nullable=false, length = 500)
    private String content;
    @Column(nullable=false)
    private int price;
    @Column(nullable=true, length = 100)
    private String thumbnailVideoDirectory;
    @Column(nullable=true, length = 50)
    private String thumbnailVideoFile;
    @Column(nullable=true, length = 100)
    private String thumbnailImgDirectory;
    @Column(nullable=true, length = 50)
    private String thumbnailImgFile;
    @Column(nullable=true, length = 30)
    private String lectureRecommendTag;

    public void modify(String title, String content, int price){
        this.title=title;
        this.content=content;
        this.price=price;
    }

    public void modifyImg(String thumbnailImgDirectory, String thumbnailImgFile){
        this.thumbnailImgDirectory = thumbnailImgDirectory;
        this.thumbnailImgFile = thumbnailImgFile;
    }

    public void modifyVideo(String thumbnailVideoDirectory, String thumbnailVideoFile){
        this.thumbnailVideoDirectory = thumbnailVideoDirectory;
        this.thumbnailVideoFile = thumbnailVideoFile;
    }


}

