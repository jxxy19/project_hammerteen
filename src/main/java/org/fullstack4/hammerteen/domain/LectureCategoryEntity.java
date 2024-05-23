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
@Table(name="hamt_lecture_category")
public class LectureCategoryEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int lectureCategoryIdx;

    @Column(length = 20)
    private String lectureCategoryName;

    @Column(nullable = false)
    private int lectureIdx;



}


