package org.fullstack4.hammerteen.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.dto.LectureDTO;
import org.fullstack4.hammerteen.dto.MemberDTO;
import org.fullstack4.hammerteen.dto.PaymentDTO;
import org.fullstack4.hammerteen.service.PaymentServiceIf;
import org.fullstack4.hammerteen.util.CommonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Log4j2
@Controller
@RequestMapping(value="/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentServiceIf paymentServiceIf;
    private String menu1 = "결제";
    @GetMapping("/payment")
    public String paymentGet(Model model,
                           HttpSession session,
                           String lectureIdx,
                           RedirectAttributes redirectAttributes) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        if(memberDTO != null) {
            List<LectureDTO> lectureDTOList = paymentServiceIf.selectLecture(lectureIdx.split(","));
            int totalPrice = 0;
            for(LectureDTO lectureDTO : lectureDTOList) {
                totalPrice += lectureDTO.getPrice();
            }
            log.info("lectureDTOList : {}", lectureDTOList);
            model.addAttribute("totalPrice", CommonUtil.comma(String.valueOf(totalPrice)));
            model.addAttribute("lectureDTOList", lectureDTOList);
            model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "결제"));
            return "/payment/payment";
        } else {
            redirectAttributes.addFlashAttribute("info", "로그인 정보 없음");
            return "redirect:/";
        }
    }
    @PostMapping("/payment")
    public String paymentPost(PaymentDTO paymentDTO,
                              BindingResult bindingResult,
                              String lectureIdxes,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        if(memberDTO != null) {
            if(bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("info","결제 정보가 부족합니다.");
            }
            if(!memberDTO.getUserId().equals(paymentDTO.getUserId()) || !memberDTO.getName().equals(paymentDTO.getUserName())){
                log.info("결제정보와 로그인 정보 불일치");
                log.info("memberDTO : {}", memberDTO);
                log.info("paymentDTO : {}", paymentDTO);
            }
            log.info("paymentDTO : {}", paymentDTO);
            log.info("lectureIdxes : {}", lectureIdxes);
            int idx = paymentServiceIf.registPayment(lectureIdxes.split(","),paymentDTO);
            if(idx > 0) {
                redirectAttributes.addFlashAttribute("info","결제가 완료되었습니다.");
                return "redirect:/mypage/payList";
            } else {
                redirectAttributes.addFlashAttribute("info","결제가 실패하였습니다.");
            }
            return "redirect:/payment/payment";
        } else {
            redirectAttributes.addFlashAttribute("info", "로그인 정보 없음");
            return "redirect:/";
        }
    }
}
