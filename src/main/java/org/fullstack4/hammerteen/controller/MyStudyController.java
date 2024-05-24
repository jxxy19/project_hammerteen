package org.fullstack4.hammerteen.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.util.CommonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping(value="/mystudy")
@RequiredArgsConstructor
public class MyStudyController {
    private String menu1 = "나의 학습방";
    @GetMapping("/myLectureList")
    public void myLectureListGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "나의 강의실"));
    }
    @GetMapping("/myReportCardList")
    public void myReportCardListGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "성적표"));
    }
    @GetMapping("/myStudyPlan")
    public void myStudyPlanGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "학습계획표"));
    }
}
