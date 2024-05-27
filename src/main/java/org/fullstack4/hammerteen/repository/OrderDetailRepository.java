package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Integer> {
        List<OrderDetailEntity> findAllByOrderIdx(int orderIdx);

        @Query(value = "SELECT count(orderDetailEntity) From OrderDetailEntity orderDetailEntity where orderDetailEntity.lectureIdx = :lectureIdx AND orderDetailEntity.orderIdx IN (select orderEntity.orderIdx from OrderEntity orderEntity where orderEntity.orderStatus = :status)")
        int countByLectureIdx(@Param("lectureIdx")int lectureIdx, @Param("status")String status);

        @Query(value = "SELECT sum(orderDetailEntity.price) From OrderDetailEntity orderDetailEntity where orderDetailEntity.lectureIdx = :lectureIdx AND orderDetailEntity.orderIdx IN (select orderEntity.orderIdx from OrderEntity orderEntity where orderEntity.orderStatus = :status)")
        int sumPriceByLectureIdx(@Param("lectureIdx")int lectureIdx, @Param("status")String status);

        @Query(value = "SELECT orderDetailEntity FROM OrderDetailEntity orderDetailEntity where orderDetailEntity.orderIdx in (select orderEntity.orderIdx from OrderEntity orderEntity where orderEntity.userId = :userId and orderEntity.orderStatus != '0')")
        List<OrderDetailEntity> findAllByUserId(@Param("userId")String userId);

        @Query(value = "SELECT count(orderdetailEntity) FROM OrderDetailEntity orderdetailEntity inner join OrderEntity orderEntity on orderdetailEntity.orderIdx = orderEntity.orderIdx where orderEntity.userId = :userId and orderdetailEntity.lectureIdx = :lectureIdx")
        int countAllByUserIdAndLectureIdx(@Param("userId")String userId, @Param("lectureIdx")int lectureIdx);

}
