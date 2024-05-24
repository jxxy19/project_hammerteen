package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {


}
