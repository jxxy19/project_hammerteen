package org.fullstack4.hammerteen.service;


import org.fullstack4.hammerteen.dto.MemberDTO;
import org.fullstack4.hammerteen.dto.PageRequestDTO;
import org.fullstack4.hammerteen.dto.PageResponseDTO;

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

    MemberDTO Detailview(String userId);

    MemberDTO detailModify(MemberDTO memberDTO);
}
