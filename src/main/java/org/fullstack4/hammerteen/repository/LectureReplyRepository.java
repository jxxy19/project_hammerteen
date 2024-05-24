package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.LectureDetailEntity;
import org.fullstack4.hammerteen.domain.LectureReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureReplyRepository extends JpaRepository<LectureReplyEntity, Integer> {


}
