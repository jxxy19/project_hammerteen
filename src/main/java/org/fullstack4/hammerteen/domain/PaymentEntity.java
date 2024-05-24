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
@Table(name="hamt_payment")
public class PaymentEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int paymentIdx;

    @Column(nullable = false)
    private int orderIdx;

    @Column(nullable=false)
    private int paymentAmount;

    @Column(length = 20, nullable = false)
    private String userId;

    @Column(length = 10, nullable = false)
    private String userName;

    @Column(length = 50, nullable = false)
    private String userPhoneNumber;

    @Column(length = 50, nullable = false)
    private String userEmail;

    @Column(length = 50, nullable = false)
    private String paymentMethod;

    @Column(length = 50, nullable = false)
    private String paymentCompany;

    @Column(length = 100, nullable = false)
    private String paymentMerchantId;

    @Column(length = 100, nullable = false)
    private String paymentPgId;

    @Column(length = 500, nullable = false)
    private String receiptUrl;

    @Column(length = 10, nullable = false)
    @Builder.Default
    private String paymentStatus="Y";


}

