package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.MemberEntity;
import org.fullstack4.hammerteen.domain.TeacherEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Integer> {



    //선생님 페이지 리스트
    Page<TeacherEntity> findAllByNameContainsOrderByTeacherIdxDesc(PageRequest pageable, String searchWord);

    Page<TeacherEntity> findAllByOrderByTeacherIdxDesc(PageRequest pageable);

    Long countByCategoryName(String categoryName);

    //카테고리별 선생님 리스트
    Page<TeacherEntity> findAllByCategoryNameOrderByTeacherIdxDesc(Pageable pageable, String categoryName);

    //선생님 존재 여부
    Optional<TeacherEntity> findByName(String name);

    Optional<TeacherEntity> findByUserId(String userId);
}
