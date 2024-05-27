package org.fullstack4.hammerteen.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="hamt_lecture_score")
public class LectureScoreEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int scoreIdx;

    @Column(nullable = false)
    private int lectureIdx;

    @Column(nullable = false)
    private int lectureScore;

    @Column(nullable = false)
    private String userId;
}


