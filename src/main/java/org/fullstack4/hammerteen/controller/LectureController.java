package org.fullstack4.hammerteen.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.dto.MemberDTO;
import org.fullstack4.hammerteen.dto.StaticDTO;
import org.fullstack4.hammerteen.service.LectureServiceIf;
import org.fullstack4.hammerteen.service.MemberServiceIf;
import org.fullstack4.hammerteen.util.CommonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping(value="/lecture")
@RequiredArgsConstructor
public class LectureController {
    private final MemberServiceIf memberServiceIf;
    private final LectureServiceIf lectureServiceIf;

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
    @GetMapping("/teacherList")
    public void teacherListGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType("선생님", "선생님"));
    }
    @GetMapping("/saleStatic")
    public String teacherSaleStaticGet(Model model,
                                     @RequestParam(name = "teacherIdx",defaultValue = "0")String teacherIdx,
                                     RedirectAttributes redirectAttributes) {
        if(teacherIdx.equals("0")) {
            redirectAttributes.addFlashAttribute("info", "없는 선생님 입니다.");
            return "redirect:/lecture/teacherList";
        }
        MemberDTO memberDTO = memberServiceIf.getMemberByIdx(CommonUtil.parseInt(teacherIdx));
        if(memberDTO != null) {
            Map<String, Object> resultMap = lectureServiceIf.getStatics(CommonUtil.parseInt(teacherIdx));
            List<StaticDTO> staticDTOList =  resultMap.get("staticDTOList") != null ? (List<StaticDTO>) resultMap.get("staticDTOList") : null;
            String staticDTOListJSON = (String) resultMap.get("staticDTOListJSON");
            model.addAttribute("memberDTO", memberDTO);
            model.addAttribute("staticDTOList", staticDTOList);
            model.addAttribute("staticDTOListJSON", staticDTOListJSON);
            model.addAttribute("pageType", CommonUtil.setPageType("선생님", "매출통계"));
        } else {
            redirectAttributes.addFlashAttribute("info", "없는 선생님 입니다.");
            return "redirect:/lecture/teacherList";
        }
        return "/lecture/saleStatic";
    }
}
