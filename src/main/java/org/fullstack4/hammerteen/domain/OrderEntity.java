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
@Table(name="hamt_order")
public class OrderEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int orderIdx;
    @Column(length = 20, nullable = false)
    private String userId;
    @Column(length = 5, nullable = false)
    @Builder.Default
    private String orderStatus="1";
    @Column(nullable=false)
    private int totalAmount;
}

