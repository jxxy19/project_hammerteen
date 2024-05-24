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
@Table(name="hamt_lecture")
public class LectureDetailEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private int lectureDetailIdx;
    @Column(nullable=false)
    private int lectureIdx;
    @Column(nullable=false)
    private String title;
    @Column(length=500,nullable=false)
    private String content;
    @Column(nullable=true, length = 100)
    private String videoDirectory;
    @Column(nullable=true, length = 50)
    private String videoFile;

    public void modify(String title, String content){
        this.title=title;
        this.content=content;
    }

    public void modifyVideo(String videoDirectory, String videoFile){
        this.videoDirectory=videoDirectory;
        this.videoFile=videoFile;
    }


}

