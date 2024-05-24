package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.LectureDetailEntity;
import org.fullstack4.hammerteen.domain.LectureEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureDetailRepository extends JpaRepository<LectureDetailEntity, Integer> {

}
