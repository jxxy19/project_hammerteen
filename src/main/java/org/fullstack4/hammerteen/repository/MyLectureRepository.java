package org.fullstack4.hammerteen.repository;

import org.fullstack4.hammerteen.domain.MyLectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyLectureRepository extends JpaRepository<MyLectureEntity, Integer> {
    List<MyLectureEntity> findAllByLectureIdxAndStatus(int lectureIdx, String status);
    List<MyLectureEntity> findAllByUserIdAndStatus(String userId, String status);
    MyLectureEntity findAllByUserIdAndLectureIdxAndStatus(String userId, int lectureIdx, String status);


}
