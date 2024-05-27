package org.fullstack4.hammerteen.repository;

import org.fullstack4.hammerteen.domain.MyLectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyLectureRepository extends JpaRepository<MyLectureEntity, Integer> {
    List<MyLectureEntity> findAllByLectureIdx(int lectureIdx);
    List<MyLectureEntity> findAllByUserId(String userId);
    MyLectureEntity findAllByUserIdAndLectureIdx(String userId, int lectureIdx);

}
