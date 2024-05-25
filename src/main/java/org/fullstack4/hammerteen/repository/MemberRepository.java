package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

    Optional<MemberEntity> findByUserId(String userId);
    int deleteByUserId(String userId);

    //아이디 중복검사
    boolean existsByUserId(String UserId);

    //이메일 중복검사
    boolean existsByEmail(String email);

    //페이징 검색
    Page<MemberEntity> findAllByUserIdContainsOrNameContainsOrderByMemberIdxDesc(Pageable pageable , String userId , String name);

    Page<MemberEntity> findAllByOrderByMemberIdxDesc(Pageable pageable);
}
