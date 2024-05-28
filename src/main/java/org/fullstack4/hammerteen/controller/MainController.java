package org.fullstack4.hammerteen.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.domain.LectureReplyEntity;
import org.fullstack4.hammerteen.dto.*;
import org.fullstack4.hammerteen.service.BbsServiceIf;
import org.fullstack4.hammerteen.service.LectureServiceIf;
import org.fullstack4.hammerteen.service.LoginServiceIf;
import org.fullstack4.hammerteen.service.MemberServiceIf;
import org.fullstack4.hammerteen.util.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class MainController {
    private final MemberServiceIf memberServiceIf;
    private final BbsServiceIf bbsServiceIf;
    private final LectureServiceIf lectureServiceIf;
    @GetMapping("/")
    public String mainGET(Model model, PageRequestDTO pageRequestDTO){

        //선생님이 궁금해요
        PageResponseDTO<TeacherDTO> teacherDTO = memberServiceIf.teacherMemberList(pageRequestDTO);
        //커뮤니티
        PageResponseDTO<BbsDTO> hotBoardDTO = bbsServiceIf.hotboardList(pageRequestDTO);

        //추천태그이름
        List<LectureRecommendDTO> recommendName= new ArrayList<>();
        recommendName = lectureServiceIf.recommendNameList();

        //가장인기있는강의
        List<LectureDTO> popularLectureList = lectureServiceIf.popularLecutreList();
        PageResponseDTO<LectureDTO> recommendLectureDTO = null;

        //강의후기(메인페이지)
        List<LectureReplyDTO> lectureReplyList= new ArrayList<>();
        lectureReplyList = lectureServiceIf.lectureReplyList();


        if(pageRequestDTO.getLectureRecommendTag() == null) {
            pageRequestDTO.setLectureRecommendTag("1");
        }

            recommendLectureDTO  = lectureServiceIf.recommendList(pageRequestDTO);




        model.addAttribute("recommendLectureDTO", recommendLectureDTO);
        model.addAttribute("lectureReplyList", lectureReplyList);
        model.addAttribute("recommendName", recommendName);
        model.addAttribute("popularLectureList", popularLectureList);
        model.addAttribute("teacherDTO", teacherDTO);
        model.addAttribute("hotBoardDTO", hotBoardDTO);
        return "index";
    }
}
