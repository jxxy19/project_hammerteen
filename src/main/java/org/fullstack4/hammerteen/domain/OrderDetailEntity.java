package org.fullstack4.hammerteen.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="hamt_order_detail")
public class OrderDetailEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int orderDetailIdx;
    @Column(nullable = false)
    private int orderIdx;
    @Column(nullable=false)
    private int lectureIdx;
    @Column(nullable=false, length = 50)
    private String title;
    @Column(nullable=false, length = 20)
    private String teacherName;
    @Column(nullable=false)
    private int price;
}

