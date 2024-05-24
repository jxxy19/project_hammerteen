package org.fullstack4.hammerteen.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="hamt_category")
public class CategoryEntity {

    @Id
    @Column(unique = true, nullable = false, length = 10)
    private String categoryIdx;
    @Column(length = 20, nullable = false)
    private String categoryName;

}


