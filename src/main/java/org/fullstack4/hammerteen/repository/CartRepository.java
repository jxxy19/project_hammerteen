package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.CartEntity;
import org.fullstack4.hammerteen.domain.LectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, Integer> {
    List<CartEntity> findAllByUserId(String userId);
    int countAllByUserId(String userId);
    void deleteAllByLectureIdxAndUserId(int lectureIdx, String userId);
}
