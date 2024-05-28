package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.LectureDetailEntity;
import org.fullstack4.hammerteen.domain.LectureEntity;
import org.fullstack4.hammerteen.domain.LectureReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

import java.util.List;


public interface LectureReplyRepository extends JpaRepository<LectureReplyEntity, Integer> {

    Page<LectureReplyEntity> findAllByLectureIdxOrderByLectureReplyIdxDesc(Pageable pageable, int lectureIdx);

    LectureReplyEntity findByUserIdAndLectureIdx(String userId, int lectureIdx);

    Page<LectureReplyEntity> findAllByUserIdOrderByLectureReplyIdxDesc(Pageable pageable, String userId);


    Optional<Integer> countByLectureIdx(int lectureIdx);
    @Query(value = "select sum(n.rating) from LectureReplyEntity n where n.lectureIdx=:lectureIdx")
    Optional<Integer> sumRating(int lectureIdx);


    //메인페이지 강의후기
   /* @Query(value = "SELECT * FROM hamt_lecture_reply ORDER BY regdate DESC LIMIT 6", nativeQuery = true)
    List<LectureReplyEntity> findTop6ByRegDateDesc();
*/


}
