package org.fullstack4.hammerteen.service;


import org.fullstack4.hammerteen.dto.MemberDTO;
import org.fullstack4.hammerteen.dto.PageRequestDTO;
import org.fullstack4.hammerteen.dto.PageResponseDTO;
import org.fullstack4.hammerteen.dto.TeacherDTO;

import java.lang.reflect.Member;
import java.util.List;

public interface MemberServiceIf {

    int regist(MemberDTO memberDTO);

    MemberDTO view(String userId);

    MemberDTO modify(MemberDTO memberDTO);

    int delete(String userId);

    //아이디중복체크
    Boolean idCheck(String user_id);

    //이메일중복체크
    Boolean emailCheck(String user_id);

    PageResponseDTO<MemberDTO> list(PageRequestDTO pageRequestDTO);

    // 지현 추가 : 선생님 리스트 조회용(통계에서)
    List<MemberDTO> teacherList (String userIdOrName);
    MemberDTO getMemberByIdx(int memberIdx);

    MemberDTO Detailview(String userId);

    MemberDTO detailModify(MemberDTO memberDTO);

    //선생님 페이지 리스트
    PageResponseDTO<TeacherDTO> teacherMemberList (PageRequestDTO pageRequestDTO);

    //카테고리별 선생님 인원
    Long countTeachersByCategory(String categoryName);

    PageResponseDTO<TeacherDTO> teacherMemberListByCategory(PageRequestDTO pageRequestDTO, String categoryName);
}
