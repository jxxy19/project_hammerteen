package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.LectureDetailEntity;
import org.fullstack4.hammerteen.domain.LectureRecommendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LectureRecommendRepository extends JpaRepository<LectureRecommendEntity, Integer> {

}
