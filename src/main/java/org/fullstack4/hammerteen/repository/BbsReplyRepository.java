package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.BbsReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BbsReplyRepository extends JpaRepository<BbsReplyEntity, Integer> {
    List<BbsReplyEntity> findAllByBbsIdxOrderByReplyIdxDesc(int bbsIdx);
    @Query("select r from BbsReplyEntity r where r.bbsIdx = :bbsIdx")
    Page<BbsReplyEntity> listOfReply(int bbsIdx, Pageable pageable);
}
