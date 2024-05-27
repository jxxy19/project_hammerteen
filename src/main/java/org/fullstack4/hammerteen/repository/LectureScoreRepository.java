package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.LectureReplyEntity;
import org.fullstack4.hammerteen.domain.LectureScoreEntity;
import org.fullstack4.hammerteen.dto.LectureScoreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureScoreRepository extends JpaRepository<LectureScoreEntity, Integer> {
    List<LectureScoreEntity> findAllByUserId(String userId);
    LectureScoreEntity findAllByLectureIdxAndUserId(int lectureIdx, String userId);
}
