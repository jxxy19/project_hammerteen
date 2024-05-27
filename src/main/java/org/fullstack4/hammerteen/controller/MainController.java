package org.fullstack4.hammerteen.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.dto.*;
import org.fullstack4.hammerteen.service.BbsServiceIf;
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
@RequestMapping(value="/main")
@RequiredArgsConstructor
public class MainController {
    private final MemberServiceIf memberServiceIf;
    private final BbsServiceIf bbsServiceIf;
    @GetMapping("/main")
    public void mainGET(Model model, PageRequestDTO pageRequestDTO){

        PageResponseDTO<TeacherDTO> teacherDTO = memberServiceIf.teacherMemberList(pageRequestDTO);
        PageResponseDTO<BbsDTO> hotBoardDTO = bbsServiceIf.hotboardList(pageRequestDTO);
        System.out.println("메인 : " + hotBoardDTO);

        model.addAttribute("teacherDTO", teacherDTO);
        model.addAttribute("hotBoardDTO", hotBoardDTO);

    }
}
