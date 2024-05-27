package org.fullstack4.hammerteen.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="hamt_my_lecture")
public class MyLectureEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int myLectureIdx;
    @Column(nullable=false)
    private int lectureIdx;
    @Column(length = 20, nullable = false)
    private String userId;
    @Builder.Default
    @Column(length = 2, nullable = false)
    private String status="Y";
}

