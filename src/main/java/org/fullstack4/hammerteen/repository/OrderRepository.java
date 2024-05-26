package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.OrderEntity;
import org.fullstack4.hammerteen.domain.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    public OrderEntity findAllByOrderIdx(int orderIdx);

}
