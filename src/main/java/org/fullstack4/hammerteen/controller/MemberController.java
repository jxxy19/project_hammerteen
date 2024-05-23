package org.fullstack4.hammerteen.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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


    public Map<String, String> setPageType(String menu2) {
        Map<String,String> pageType = new HashMap<>();
        pageType.put("menu1", "마이페이지");
        pageType.put("menu2", menu2);
        return pageType;
    }

    @GetMapping("/mypage")
    public void mypageGet(Model model) {
        log.info("마이페이지");
        model.addAttribute("pageType", setPageType("회원정보수정"));
    }

    @GetMapping("/writeList")
    public void myListGet(Model model) {log.info("작성글확인");
        model.addAttribute("pageType", setPageType("작성글 확인"));}

}
