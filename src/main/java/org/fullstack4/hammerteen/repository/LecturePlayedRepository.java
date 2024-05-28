package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.LectureGoodEntity;
import org.fullstack4.hammerteen.domain.LecturePlayedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LecturePlayedRepository extends JpaRepository<LecturePlayedEntity, Integer> {
    Optional<Integer> countAllByPercentageGreaterThanEqualAndUserId(int percentage, String userId);
    Optional<LecturePlayedEntity> findAllByPercentageLessThanEqualAndUserIdAndLectureIdx(int percentage,String userId,int lectureIdx);
}
