package org.fullstack4.hammerteen.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="hamt_lecture_played")
public class LecturePlayedEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private int playedIdx;
    @Column(nullable = false)
    private int lectureIdx;
    @Column(nullable = false)
    private int lectureDetailIdx;
    @Column(nullable = false)
    private int percentage;
    @Column(length = 20, nullable = false)
    private String userId;

    public void registNext(int lectureDetailIdx){
        this.lectureDetailIdx = lectureDetailIdx;
        this.percentage = 0;
    }
}
