package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.MemberEntity;
import org.fullstack4.hammerteen.domain.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {
    public List<PaymentEntity> findAllByUserIdEqualsOrderByPaymentIdxDesc(String userId);
    public PaymentEntity findAllByPaymentIdx(int paymentIdx);

}
