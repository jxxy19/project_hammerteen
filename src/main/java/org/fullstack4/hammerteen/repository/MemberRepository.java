package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.MemberEntity;
import org.fullstack4.hammerteen.domain.TeacherEntity;
import org.fullstack4.hammerteen.dto.MemberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
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

    // 지현추가 : 선생님 리스트 조회용
    List<MemberEntity> findMemberEntityByUserIdLikeOrNameLikeAndRoleEquals(String userId, String name, String role);
    MemberEntity findAllByMemberIdxAndRole(int memberIdx, String role);

   TeacherEntity findByName(String name);
}
