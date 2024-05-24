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
@RequestMapping(value="/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private String menu1 = "마이페이지";

    @GetMapping("/mypage")
    public void mypageGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "회원정보 수정"));
    }

    @GetMapping("/writeList")
    public void myListGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "작성글 확인"));
    }
    @GetMapping("/cart")
    public void cartGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "장바구니 내역"));
    }

    @GetMapping("/memberList")
    public void memberListGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "회원관리"));
    }
    @GetMapping("/memberView")
    public void memberViewGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "회원관리"));
    }
    @GetMapping("/likeList")
    public void likeListGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "찜 내역"));
    }
}
