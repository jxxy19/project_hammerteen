package org.fullstack4.hammerteen.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.dto.BbsDTO;
import org.fullstack4.hammerteen.dto.BbsReplyDTO;
import org.fullstack4.hammerteen.dto.MemberDTO;
import org.fullstack4.hammerteen.service.BbsReplyServiceIf;
import org.fullstack4.hammerteen.service.BbsServiceIf;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UrlPathHelper;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/board")
public class BoardReplyController {
    private final BbsServiceIf bbsServiceIf;
    private final BbsReplyServiceIf bbsReplyServiceIf;

    @PostMapping("/registr")
    public void ReplyRPost(BbsReplyDTO bbsReplyDTO, HttpServletRequest req,
                             @RequestParam(name = "category1") String category1,
                             @RequestParam(name = "bbsIdx") int bbsIdx
                             ){
        HttpSession session = req.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        bbsReplyDTO.setUserId(memberDTO.getUserId());

        int result = bbsReplyServiceIf.regist(bbsReplyDTO);
        BbsDTO bbsDTO = bbsServiceIf.view(bbsIdx);

        String categoryEng = "";
        if(category1.equals("자유게시판")) {
            categoryEng = "board";
        }
        if(category1.equals("자료게시판")) {
            categoryEng = "data";
        }
        if(category1.equals("후기게시판")) {
            categoryEng = "review";
        }
        if(category1.equals("교육정보게시판")) {
            categoryEng = "eduInfo";
        }
        if(category1.equals("공지사항")) {
            categoryEng = "notice";
        }
        if(category1.equals("QnA게시판")) {
            categoryEng = "qna";
        }
        if ( result <0 ) {
            System.out.println("dd여기 들어와지냐?전주연이다");
         /*   return "redirect:/board/view?category1=" + categoryEng + "&bbsIdx=" + bbsDTO.getBbsIdx();*/

        }

        }

}
