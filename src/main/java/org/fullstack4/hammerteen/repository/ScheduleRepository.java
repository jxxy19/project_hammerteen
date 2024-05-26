package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.MemberEntity;
import org.fullstack4.hammerteen.domain.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Integer> {
    List<ScheduleEntity> findAllByUserId(String userId);
    @Query("SELECT s FROM ScheduleEntity s WHERE s.userId = :userId AND(YEAR(s.startDate) = :year OR YEAR(s.endDate) = :year) AND (MONTH(s.startDate) = :month OR MONTH(s.endDate) = :month)")
    List<ScheduleEntity> findAllByUserIdAndStartDateMonthOrEndDateMonth(@Param("userId") String userId, @Param("year") int year, @Param("month") int month);
}
