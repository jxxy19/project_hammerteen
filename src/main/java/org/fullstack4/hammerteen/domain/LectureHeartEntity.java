package org.fullstack4.hammerteen.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="hamt_lecture_heart")
public class LectureHeartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int heartIdx;

    @Column(nullable = false)
    private int lectureIdx;

    @Column(length = 20, nullable = false)
    private String userId;
}
