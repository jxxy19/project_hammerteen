package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.LectureGoodEntity;
import org.fullstack4.hammerteen.domain.LectureReplyEntity;
import org.fullstack4.hammerteen.dto.LectureGoodDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureGoodRepository extends JpaRepository<LectureGoodEntity, Integer> {
    List<LectureGoodEntity> findAllByUserId(String userId);
    LectureGoodEntity findByLectureIdxAndUserId(int lectureId, String userId);
    int countAllByUserId(String userId);
}
