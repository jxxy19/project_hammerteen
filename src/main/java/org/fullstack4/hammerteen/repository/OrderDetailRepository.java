package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Integer> {
        List<OrderDetailEntity> findAllByOrderIdx(int orderIdx);
}
