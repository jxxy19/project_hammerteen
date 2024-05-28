package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.BbsEntity;
import org.fullstack4.hammerteen.domain.LectureReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BbsRepository extends JpaRepository<BbsEntity, Integer> {
    Page<BbsEntity> findAllByCategory1OrderByBbsIdxDesc(Pageable pageable, String category1);
    Page<BbsEntity> findAllByCategory1AndUserIdOrderByBbsIdxDesc(Pageable pageable, String category1, String userId);
    Page<BbsEntity> findAllByCategory1AndTitleContainsOrContentContainsAndCategory1ContainsOrUserIdContainsAndCategory1ContainsOrderByBbsIdxDesc(
            Pageable pageable,String category1, String title, String content,String category2, String userId, String category3);
    @Modifying
    @Query(value = "update BbsEntity b set b.readCnt=b.readCnt+1 where b.bbsIdx=:bbsIdx")
    void updateReadCnt(@Param("bbsIdx") int bbsIdx);

    Page<BbsEntity> findAllByOrderByReadCntDesc(PageRequest pageable);
}
