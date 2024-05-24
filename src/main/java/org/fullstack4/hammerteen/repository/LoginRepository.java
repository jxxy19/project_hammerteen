package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<MemberEntity, Integer> {

    Optional<MemberEntity> findByUserId(String userId);
}
