package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.BbsReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BbsReplyRepository extends JpaRepository<BbsReplyEntity, Integer> {
    List<BbsReplyEntity> findAllByBbsIdxOrderByReplyIdxDesc(int bbsIdx);
}
