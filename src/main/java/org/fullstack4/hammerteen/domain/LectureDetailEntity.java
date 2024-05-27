package org.fullstack4.hammerteen.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="hamt_lecture_Detail")
public class LectureDetailEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private int lectureDetailIdx;
    @Column(nullable=false)
    private int lectureIdx;
    @Column(nullable = false)
    private int videoLength;
    @Column(nullable=false)
    private String detailTitle;
    @Column(nullable=true, length = 200)
    private String videoDirectory;
    @Column(nullable=true, length = 200)
    private String videoFile;

    public void modify(String title, int videoLength, String videoDirectory, String videoFile){
        this.detailTitle=title;
        this.videoLength = videoLength;
        this.videoDirectory = videoDirectory;
        this.videoFile = videoFile;
    }

    public void modifyVideo(String videoDirectory, String videoFile){
        this.videoDirectory=videoDirectory;
        this.videoFile=videoFile;
    }


}

