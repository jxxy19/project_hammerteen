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
@RequestMapping(value="/lecture")
@RequiredArgsConstructor
public class LectureController {
    private String menu1 = "강의";
    @GetMapping("/list")
    public void listGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, this.menu1));
    }
    @GetMapping("/view")
    public void viewGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, this.menu1));
    }
    @GetMapping("/regist")
    public void registGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, this.menu1));
    }
    @GetMapping("/modify")
    public void modifyGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, this.menu1));
    }
}
