package org.fullstack4.hammerteen.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.dto.*;
import org.fullstack4.hammerteen.service.BbsServiceIf;
import org.fullstack4.hammerteen.util.CommonUtil;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.UrlPathHelper;

import java.util.List;

@Log4j2
@Controller
@RequestMapping(value="/board")
@RequiredArgsConstructor
public class BoardController {
    private final BbsServiceIf bbsServiceIf;
    private String menuB = "자유게시판";
    private String menuQ = "QNA(1:1)";
    private String menuR = "후기게시판";
    private String menuE = "교육정보게시판";
    private String menuD = "자료게시판";
    private String menuN = "공지사항";
    @GetMapping("/list")
    public String listGet(Model model, HttpServletRequest req,
                          PageRequestDTO pageRequestDTO) {
        HttpSession session = req.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String url = urlPathHelper.getOriginatingQueryString(req);
        if ( memberDTO != null ) {
            model.addAttribute("userId", memberDTO.getUserId());
        }
        if (url.contains("board")) {
            pageRequestDTO.setCategory1("자유게시판");
            model.addAttribute("pageType", CommonUtil.setPageType(menuB, "글목록"));
            PageResponseDTO<BbsDTO> pageResponseDTO = bbsServiceIf.list(pageRequestDTO);
            model.addAttribute("pageResponseDTO" , pageResponseDTO);
            model.addAttribute("category1", "board");

        }
        if (url.contains("qna")) {
            pageRequestDTO.setCategory1("QnA게시판");
            model.addAttribute("pageType", CommonUtil.setPageType(menuQ, "글목록"));
            PageResponseDTO<BbsDTO> pageResponseDTO = bbsServiceIf.list(pageRequestDTO);
            model.addAttribute("pageResponseDTO" , pageResponseDTO);
            model.addAttribute("category1", "qna");
        }
        if (url.contains("notice")) {
            pageRequestDTO.setCategory1("공지사항");
            model.addAttribute("pageType", CommonUtil.setPageType(menuN, "글목록"));
            PageResponseDTO<BbsDTO> pageResponseDTO = bbsServiceIf.list(pageRequestDTO);
            model.addAttribute("pageResponseDTO" , pageResponseDTO);
            model.addAttribute("category1", "notice");
        }
        if (url.contains("review")) {
            pageRequestDTO.setCategory1("후기게시판");
            model.addAttribute("pageType", CommonUtil.setPageType(menuR, "글목록"));
            PageResponseDTO<BbsDTO> pageResponseDTO = bbsServiceIf.list(pageRequestDTO);
            model.addAttribute("pageResponseDTO" , pageResponseDTO);
            model.addAttribute("category1", "review");
        }
        if (url.contains("eduInfo")) {
            pageRequestDTO.setCategory1("교육정보게시판");
            model.addAttribute("pageType", CommonUtil.setPageType(menuE, "글목록"));
            PageResponseDTO<BbsDTO> pageResponseDTO = bbsServiceIf.list(pageRequestDTO);
            model.addAttribute("pageResponseDTO" , pageResponseDTO);
            model.addAttribute("category1", "eduInfo");
        }
        if (url.contains("data")) {
            pageRequestDTO.setCategory1("자료게시판");
            model.addAttribute("pageType", CommonUtil.setPageType(menuD, "글목록"));
            PageResponseDTO<BbsDTO> pageResponseDTO = bbsServiceIf.list(pageRequestDTO);
            model.addAttribute("pageResponseDTO" , pageResponseDTO);
            model.addAttribute("category1", "data");
        }
        return null;
    }
    @GetMapping("/regist")
    public void registGet(Model model, HttpServletRequest req) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String url = urlPathHelper.getOriginatingQueryString(req);
        if(url.contains("board")) {
            model.addAttribute("pageType", CommonUtil.setPageType(menuB, "글 작성"));
        }
        if(url.contains("qna")) {
            model.addAttribute("pageType", CommonUtil.setPageType(menuQ, "글 작성"));
        }
        if (url.contains("notice")) {
            model.addAttribute("pageType", CommonUtil.setPageType(menuN, "글 작성"));
        }
        if(url.contains("review")) {
            model.addAttribute("pageType", CommonUtil.setPageType(menuR, "글 작성"));
        }
        if(url.contains("eduInfo")) {
            model.addAttribute("pageType", CommonUtil.setPageType(menuE, "글 작성"));
        }
        if(url.contains("data")) {
            model.addAttribute("pageType", CommonUtil.setPageType(menuD, "글 작성"));
        }

    }
    @PostMapping("/regist")
    public String registPost(BbsDTO bbsDTO, MultipartHttpServletRequest files, HttpServletRequest req){
        HttpSession session = req.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        bbsDTO.setUserId(memberDTO.getUserId());
        int resultidx = bbsServiceIf.regist(bbsDTO);
        String categoryEng = "";
        if(bbsDTO.getCategory1().equals("자유게시판")) {
            categoryEng = "board";
        }
        if(bbsDTO.getCategory1().equals("자료게시판")) {
            categoryEng = "data";
        }
        if(bbsDTO.getCategory1().equals("후기게시판")) {
            categoryEng = "review";
        }
        if(bbsDTO.getCategory1().equals("교육정보게시판")) {
            categoryEng = "eduInfo";
        }
        if(bbsDTO.getCategory1().equals("공지사항")) {
            categoryEng = "notice";
        }
        if(bbsDTO.getCategory1().equals("QnA게시판")) {
            categoryEng = "qna";
        }
        return "redirect:/board/view?category1="+categoryEng+"&bbsIdx="+resultidx;
    }
    @GetMapping("/view")
    public void viewGet(BbsDTO bbsDTO, Model model, HttpServletRequest req) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String url = urlPathHelper.getOriginatingQueryString(req);
        bbsServiceIf.updateReadCnt(bbsDTO.getBbsIdx());
        bbsDTO.setRead_cnt(bbsDTO.getRead_cnt());
        BbsDTO resultbbsDTO = bbsServiceIf.view(bbsDTO);
        model.addAttribute("bbsDTO",resultbbsDTO);
        if (url.contains("board")) {
            model.addAttribute("category1", "board");
            model.addAttribute("pageType", CommonUtil.setPageType(menuB, "글 상세"));
        }
        if (url.contains("qna")) {
            model.addAttribute("category1", "qna");
            model.addAttribute("pageType", CommonUtil.setPageType(menuQ, "글 상세"));
        }
        if (url.contains("review")) {
            model.addAttribute("category1", "review");
            model.addAttribute("pageType", CommonUtil.setPageType(menuR, "글 상세"));
        }
        if (url.contains("eduInfo")) {
            model.addAttribute("category1", "eduInfo");
            model.addAttribute("pageType", CommonUtil.setPageType(menuE, "글 상세"));
        }
        if (url.contains("data")) {
            model.addAttribute("category1", "data");
            model.addAttribute("pageType", CommonUtil.setPageType(menuD, "글 상세"));
        }
    }
    @GetMapping("/modify")
    public String modifyGet(BbsDTO bbsDTO, PageRequestDTO pageRequestDTO,
                          Model model, HttpServletRequest req){
        BbsDTO resultbbsDTO = bbsServiceIf.view(bbsDTO);
        HttpSession session = req.getSession();
        model.addAttribute("bbsDTO",resultbbsDTO);
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String url = urlPathHelper.getOriginatingQueryString(req);
        if (url.contains("board")) {
            model.addAttribute("pageType", CommonUtil.setPageType(menuB, "글 수정"));
        }
        if (url.contains("qna")) {
            model.addAttribute("pageType", CommonUtil.setPageType(menuQ, "글 수정"));
        }
        if (url.contains("review")) {
            model.addAttribute("pageType", CommonUtil.setPageType(menuR, "글 수정"));
        }
        if (url.contains("eduInfo")) {
            model.addAttribute("pageType", CommonUtil.setPageType(menuE, "글 수정"));
        }
        if (url.contains("data")) {
            model.addAttribute("pageType", CommonUtil.setPageType(menuD, "글 수정"));
        }
        return null;
    }
    @PostMapping("/modify")
    public String modifyPost(BbsDTO bbsDTO, HttpServletRequest request){
        HttpSession session = request.getSession();
        bbsServiceIf.modify(bbsDTO);
        String categoryEng = "";
        if(bbsDTO.getCategory1().equals("자유게시판")) {
            categoryEng = "board";
        }
        if(bbsDTO.getCategory1().equals("자료게시판")) {
            categoryEng = "data";
        }
        if(bbsDTO.getCategory1().equals("후기게시판")) {
            categoryEng = "review";
        }
        if(bbsDTO.getCategory1().equals("교육정보게시판")) {
            categoryEng = "eduInfo";
        }
        if(bbsDTO.getCategory1().equals("공지사항")) {
            categoryEng = "notice";
        }
        if(bbsDTO.getCategory1().equals("QnA게시판")) {
            categoryEng = "qna";
        }
        return "redirect:/board/view?category1="+categoryEng+"&bbsIdx="+ bbsDTO.getBbsIdx();
    }
    @GetMapping("/delete")
    public String delete(BbsDTO bbsDTO, HttpServletRequest req) {
        BbsDTO view = bbsServiceIf.view(bbsDTO);
        HttpSession session = req.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        String categoryEng = "";
        if (view.getCategory1().equals("자유게시판")) {
            categoryEng = "board";
        }
        if (view.getCategory1().equals("자료게시판")) {
            categoryEng = "data";
        }
        if (view.getCategory1().equals("후기게시판")) {
            categoryEng = "review";
        }
        if (view.getCategory1().equals("교육정보게시판")) {
            categoryEng = "eduInfo";
        }
        if (view.getCategory1().equals("공지사항")) {
            categoryEng = "notice";
        }
        if (view.getCategory1().equals("QnA게시판")) {
            categoryEng = "qna";
        }
        if (memberDTO != null) {
            if (!memberDTO.getUserId().equals(view.getUserId()) && memberDTO.getRole().equals("user")) {
                return "redirect:/";
            } else {
                bbsServiceIf.delete(bbsDTO);
                return "redirect:/board/list?category1=" + categoryEng;
            }
        }
        if (memberDTO == null) {
            return "redirect:/board/view?category1=" + categoryEng + "&bbsIdx=" + bbsDTO.getBbsIdx();
        }
        return null;
    }
}
