package org.fullstack4.hammerteen.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="hamt_bbs_category")
public class BbsCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int bbsCategoryIdx;

    @Column(nullable = false)
    private int bbsIdx;

    @Column(length = 5, nullable = false)
    private String bbsCategoryName;



}


