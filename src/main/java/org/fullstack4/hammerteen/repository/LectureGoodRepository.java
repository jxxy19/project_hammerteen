package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.LectureGoodEntity;
import org.fullstack4.hammerteen.domain.LectureReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureGoodRepository extends JpaRepository<LectureGoodEntity, Integer> {



}
