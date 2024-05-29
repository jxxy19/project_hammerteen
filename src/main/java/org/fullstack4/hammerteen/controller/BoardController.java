package org.fullstack4.hammerteen.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.dto.*;
import org.fullstack4.hammerteen.service.BbsReplyServiceIf;
import org.fullstack4.hammerteen.service.BbsServiceIf;
import org.fullstack4.hammerteen.util.CommonFileUtil;
import org.fullstack4.hammerteen.util.CommonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UrlPathHelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

@Log4j2
@Controller
@RequestMapping(value="/board")
@RequiredArgsConstructor
public class BoardController {
    private final BbsServiceIf bbsServiceIf;
    private final BbsReplyServiceIf bbsReplyServiceIf;
    private final CommonFileUtil commonFileUtil;
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
            System.out.println("pageResponseDTO : + "+pageResponseDTO);
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
            log.info(CommonUtil.setPageType(menuN, "글목록"));
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
        LectureDTO lectureDTO = new LectureDTO();
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String url = urlPathHelper.getOriginatingQueryString(req);
        List<LectureDTO> lecList = bbsServiceIf.lectureList(lectureDTO);
        model.addAttribute("lecList",lecList);
        log.info(lecList);
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
    public String registPost(@Valid BbsDTO bbsDTO, BindingResult bindingResult, MultipartHttpServletRequest files, HttpServletRequest req,
                             RedirectAttributes redirectAttributes) {
        HttpSession session = req.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        bbsDTO.setUserId(memberDTO.getUserId());

        String categoryEng = "";
        if(bbsDTO.getCategory1().equals("자유게시판")) {
            categoryEng = "board";
        }
        else if(bbsDTO.getCategory1().equals("자료게시판")) {
            categoryEng = "data";
        }
        else if(bbsDTO.getCategory1().equals("후기게시판")) {
            categoryEng = "review";
        }
        else if(bbsDTO.getCategory1().equals("교육정보게시판")) {
            categoryEng = "eduInfo";
        }
        else if(bbsDTO.getCategory1().equals("공지사항")) {
            categoryEng = "notice";
        }
        else if(bbsDTO.getCategory1().equals("QnA게시판")) {
            categoryEng = "qna";
        }
        if(bindingResult.hasErrors()) {
            log.info(bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors()); //휘발성속성
            redirectAttributes.addFlashAttribute("dto", bbsDTO);
            log.info("오류 여기");
            return "redirect:/board/regist?category1="+categoryEng;
        }
        int resultidx = bbsServiceIf.regist(bbsDTO);
        if(files!=null) {
            BbsFileDTO bbsFileDTO = BbsFileDTO.builder().bbsIdx(resultidx).userId(bbsDTO.getUserId()).build();
            bbsServiceIf.registFile(bbsFileDTO, files);
        }
        if( resultidx > 0 ) {
            return "redirect:/board/list?category1="+categoryEng;
        } else {
            return "redirect:/board/list?category1="+categoryEng;
        }
    }
    @GetMapping("/view")
    public String viewGet(@RequestParam(name="bbsIdx") int bbsIdx,
                        BbsDTO bbsDTO, Model model, HttpServletRequest req, BbsReplyDTO bbsReplyDTO) {
        HttpSession session = req.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        String userId = memberDTO.getUserId();
        BbsDTO resultbbsDTO = bbsServiceIf.view(bbsIdx);
//        log.info(userId.equals(resultbbsDTO.getUserId()));
//        log.info(memberDTO.getRole());
        if((!(userId.equals(resultbbsDTO.getUserId())) && resultbbsDTO.getCategory1().equals("QnA게시판") && (memberDTO.getRole().equals("user")))) {
            return "redirect:/board/list?category1=qna";
        }
        else {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String url = urlPathHelper.getOriginatingQueryString(req);
        List<BbsFileDTO> bbsFileDTOList = bbsServiceIf.listFile(bbsDTO.getBbsIdx());
        bbsServiceIf.updateReadCnt(bbsIdx);
        List<BbsReplyDTO> replyDTO = bbsReplyServiceIf.replyList(bbsReplyDTO);
        model.addAttribute("replyDTO", replyDTO);
        model.addAttribute("bbsDTO",resultbbsDTO);
        model.addAttribute("bbsFileDTOList",bbsFileDTOList);
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
        return null;
    }
    }
    @GetMapping("/modify")
    public String modifyGet(BbsDTO bbsDTO, PageRequestDTO pageRequestDTO,
                          Model model, HttpServletRequest req){
        BbsDTO resultbbsDTO = bbsServiceIf.view(bbsDTO.getBbsIdx());
        List<BbsFileDTO> bbsFileDTOList = bbsServiceIf.listFile(bbsDTO.getBbsIdx());
        HttpSession session = req.getSession();
        model.addAttribute("bbsDTO",resultbbsDTO);
        model.addAttribute("bbsFileDTOList",bbsFileDTOList);
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
    public String modifyPost(@Valid BbsDTO bbsDTO, BindingResult bindingResult, HttpServletRequest req,MultipartHttpServletRequest files,
                             RedirectAttributes redirectAttributes){

        String categoryEng = "";
        if(bbsDTO.getCategory1().equals("자유게시판")) {
            categoryEng = "board";
        }
        else if(bbsDTO.getCategory1().equals("자료게시판")) {
            categoryEng = "data";
        }
        else if(bbsDTO.getCategory1().equals("후기게시판")) {
            categoryEng = "review";
        }
        else if(bbsDTO.getCategory1().equals("교육정보게시판")) {
            categoryEng = "eduInfo";
        }
        else if(bbsDTO.getCategory1().equals("공지사항")) {
            categoryEng = "notice";
        }
        else if(bbsDTO.getCategory1().equals("QnA게시판")) {
            categoryEng = "qna";
        }
        if(bindingResult.hasErrors()) {
            log.info(bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors()); //휘발성속성
            redirectAttributes.addFlashAttribute("dto", bbsDTO);
            log.info("오류 여기");
            return "redirect:/board/modify?category1="+categoryEng+"&bbsIdx="+ bbsDTO.getBbsIdx();
        }
        bbsServiceIf.modify(bbsDTO);
        if(files!=null) {
            BbsFileDTO bbsFileDTO = BbsFileDTO.builder().bbsIdx(bbsDTO.getBbsIdx()).userId(bbsDTO.getUserId()).build();
            bbsServiceIf.registFile(bbsFileDTO, files);
        }
        return "redirect:/board/view?category1="+categoryEng+"&bbsIdx="+ bbsDTO.getBbsIdx();
    }
    @PostMapping("/delete")
    public String delete(@RequestParam(name="bbsIdx") int bbsIdx, BbsDTO bbsDTO, HttpServletRequest req) {
        BbsDTO view = bbsServiceIf.view(bbsIdx);
        log.info(view);
        HttpSession session = req.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        String categoryEng = "";
        if(view.getCategory1().equals("자유게시판")) {
            categoryEng = "board";
        }
        else if(view.getCategory1().equals("자료게시판")) {
            categoryEng = "data";
        }
        else if(view.getCategory1().equals("후기게시판")) {
            categoryEng = "review";
        }
        else if(view.getCategory1().equals("교육정보게시판")) {
            categoryEng = "eduInfo";
        }
        else if(view.getCategory1().equals("공지사항")) {
            categoryEng = "notice";
        }
        else if(view.getCategory1().equals("QnA게시판")) {
            categoryEng = "qna";
        }
        if (memberDTO != null) {
                bbsServiceIf.delete(bbsIdx);
                return "redirect:/board/list?category1="+categoryEng;
            }

        if (memberDTO == null) {
            return "redirect:/board/view?category1=" + categoryEng + "&bbsIdx=" + bbsDTO.getBbsIdx();
        }
        return null;
    }

    @GetMapping("/downloadfile")
    public void downloadFile(BbsFileDTO bbsFileDTO, HttpServletResponse response, HttpServletRequest request){
        String saveDirectory = "D:\\java4\\springboot\\hammerteen\\src\\main\\resources\\static\\upload";
        commonFileUtil.fileDownload(saveDirectory,bbsFileDTO.getFileName(),response,request);
    }
    @ResponseBody
    @GetMapping("/deletefile")
    public HttpStatus deleteFile(BbsFileDTO bbsFileDTO){
        System.out.println("여기로 들어옴? ㅏ일삭제임");
        bbsServiceIf.deleteFile(bbsFileDTO);

        return HttpStatus.OK;
    }
}
