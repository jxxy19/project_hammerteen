package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.LectureEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<LectureEntity, Integer> {
    Page<LectureEntity> findAllByCategoryIdxStartingWithAndTeacherNameContainsOrTitleContainsOrderByLectureIdxDesc(Pageable pageable, String categoryIdx, String teacherName, String title);

}
