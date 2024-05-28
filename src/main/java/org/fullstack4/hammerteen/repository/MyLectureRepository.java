package org.fullstack4.hammerteen.repository;

import org.fullstack4.hammerteen.domain.MyLectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MyLectureRepository extends JpaRepository<MyLectureEntity, Integer> {
    List<MyLectureEntity> findAllByLectureIdxAndStatus(int lectureIdx, String status);
    List<MyLectureEntity> findAllByUserIdAndStatus(String userId, String status);
    MyLectureEntity findAllByUserIdAndLectureIdxAndStatus(String userId, int lectureIdx, String status);


    //가장인기있는 강의 (메인)
    @Query(value = "SELECT lecture_idx FROM hamt_my_lecture WHERE status = 'Y' GROUP BY lecture_idx ORDER BY COUNT(lecture_idx) DESC LIMIT 6", nativeQuery = true)
    List<Integer> findTop6PopularLectures();


}
