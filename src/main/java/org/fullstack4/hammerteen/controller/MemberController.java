package org.fullstack4.hammerteen.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.util.CommonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping(value="/member")
@RequiredArgsConstructor
public class MemberController {
    private String menu1 = "마이페이지";

    @GetMapping("/mypage")
    public void mypageGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "회원정보수정"));
    }

    @GetMapping("/writeList")
    public void myListGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "작성글 확인"));
    }
}
