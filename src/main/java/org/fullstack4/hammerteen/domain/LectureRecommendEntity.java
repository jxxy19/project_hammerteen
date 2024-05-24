package org.fullstack4.hammerteen.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="hamt_lecture_recommend")
public class LectureRecommendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int recommendIdx;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(nullable = false)
    private int lectureIdx;
}
