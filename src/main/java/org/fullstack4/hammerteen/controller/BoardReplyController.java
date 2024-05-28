package org.fullstack4.hammerteen.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.dto.BbsDTO;
import org.fullstack4.hammerteen.dto.BbsReplyDTO;
import org.fullstack4.hammerteen.dto.MemberDTO;
import org.fullstack4.hammerteen.service.BbsReplyServiceIf;
import org.fullstack4.hammerteen.service.BbsServiceIf;
import org.fullstack4.hammerteen.util.CommonUtil;
import org.hibernate.sql.exec.spi.StandardEntityInstanceResolver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/board")
public class BoardReplyController {
    private final BbsServiceIf bbsServiceIf;
    private final BbsReplyServiceIf bbsReplyServiceIf;

    @PostMapping("/replyRegist")
    public String ReplyRPost(BbsReplyDTO bbsReplyDTO, HttpServletRequest req,
                             @RequestParam(name = "category1") String category1,
                             @RequestParam(name = "bbsIdx") int bbsIdx,
                             RedirectAttributes redirectAttributes
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
        else if(category1.equals("자료게시판")) {
            categoryEng = "data";
        }
        else if(category1.equals("후기게시판")) {
            categoryEng = "review";
        }
        else if(category1.equals("교육정보게시판")) {
            categoryEng = "eduInfo";
        }
        else if(category1.equals("공지사항")) {
            categoryEng = "notice";
        }
        else if(category1.equals("QnA게시판")) {
            categoryEng = "qna";
        }
        redirectAttributes.addAttribute("category1", categoryEng);
        redirectAttributes.addAttribute("bbsIdx", bbsReplyDTO.getBbsIdx());
        if ( result > 0 ) {
             return "redirect:/board/view";
        }
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/replyDelete", method = RequestMethod.POST, produces = "application/text;charset=UTF-8")
    public String replyDelete(@RequestParam(name="replyIdx") int replyIdx,
                           @RequestParam(name="bbsIdx") int bbsIdx,
                           @RequestParam(name="userId") String frmUserId,
                           RedirectAttributes redirectAttributes,
                              HttpSession session) {
        log.info("replyIdx : "+replyIdx);
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        String userId = memberDTO.getUserId();
        if(userId.equals(frmUserId)) {
             redirectAttributes.addAttribute("bbsIdx", bbsIdx);
            bbsReplyServiceIf.replyDelete(replyIdx);
            return "1";
        }
        else {
            return "0";
        }
    }
    @ResponseBody
    @RequestMapping(value = "/replyModify", method = RequestMethod.POST, produces = "application/text;charset=UTF-8")
    public String replyModify(BbsReplyDTO bbsReplyDTO, HttpServletRequest req,
                              @RequestParam(name="content") String content,
                              @RequestParam(name="replyIdx") int replyIdx,
                              @RequestParam(name="category") String category1,
                              RedirectAttributes redirectAttributes) {
        log.info("replyIdx : "+replyIdx );
        log.info(bbsReplyDTO);
        bbsReplyDTO.setContent(content);
        log.info("바ㅏ뀐 값은 ??? : " + bbsReplyDTO);
        HttpSession session = req.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        String userId = memberDTO.getUserId();
        String categoryEng = "";
        if(category1.equals("자유게시판")) {
            categoryEng = "board";
        }
        else if(category1.equals("자료게시판")) {
            categoryEng = "data";
        }
        else if(category1.equals("후기게시판")) {
            categoryEng = "review";
        }
        else if(category1.equals("교육정보게시판")) {
            categoryEng = "eduInfo";
        }
        else if(category1.equals("공지사항")) {
            categoryEng = "notice";
        }
        else if(category1.equals("QnA게시판")) {
            categoryEng = "qna";
        }
        if(bbsReplyDTO != null && userId.equals(bbsReplyDTO.getUserId())) {
            redirectAttributes.addAttribute("category1", categoryEng);
            redirectAttributes.addAttribute("bbsIdx", bbsReplyDTO.getBbsIdx());
            bbsReplyServiceIf.replyModify(bbsReplyDTO);
            return bbsReplyDTO.getContent();
        }
        else{
            return "";
        }
    }


}
