package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.LectureEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<LectureEntity, Integer> {
    Page<LectureEntity> findAllByCategoryIdxStartingWithAndTeacherIdxContainsOrTitleContainsOrderByLectureIdxDesc(Pageable pageable, String categoryIdx, String teacherName, String title);

    int countByCategoryIdx(String categoryIdx);


    // 지현추가 : 각 선생님 별 강으 조회용
    List<LectureEntity> findAllByTeacherIdx(int teacherIdx);
}
