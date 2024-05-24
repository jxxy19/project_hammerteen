package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.BbsFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BbsFileRepository extends JpaRepository<BbsFileEntity, Integer> {

    List<BbsFileEntity> findAllByBbsIdx(int bbsIdx);
}
