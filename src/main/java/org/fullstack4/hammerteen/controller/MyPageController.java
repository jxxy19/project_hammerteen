package org.fullstack4.hammerteen.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.dto.MemberDTO;
import org.fullstack4.hammerteen.dto.PageRequestDTO;
import org.fullstack4.hammerteen.dto.PageResponseDTO;
import org.fullstack4.hammerteen.dto.PaymentDTO;
import org.fullstack4.hammerteen.service.MemberServiceIf;
import org.fullstack4.hammerteen.service.PaymentServiceIf;
import org.fullstack4.hammerteen.util.CommonFileUtil;
import org.fullstack4.hammerteen.util.CommonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Log4j2
@Controller
@RequestMapping(value="/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final PaymentServiceIf paymentServiceIf;
    private final MemberServiceIf memberServiceIf;
    private final CommonFileUtil commonFileUtil;
    private String menu1 = "마이페이지";

    @GetMapping("/mypage")
    public void mypageGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "회원정보 수정"));
    }

    @PostMapping("/mypage")
    public String mypagePost(@Valid MemberDTO memberDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request, MultipartHttpServletRequest file) {

        List<String> filenames = null;
        String realPath ="D:\\java\\hammer\\src\\main\\resources\\static\\upload";

        filenames = commonFileUtil.fileuploads(file,realPath);


        if(filenames != null) {
            memberDTO.setFileName(filenames.get(0));
            memberDTO.setDirectory(realPath);
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("info", "alert(`회원 정보 수정 실패 올바른 값을 입력해 주세요.`);");
            return "redirect:/mypage/mypage";
        }

        MemberDTO modifyDTO = memberServiceIf.modify(memberDTO);


        request.getSession().setAttribute("memberDTO", modifyDTO);

        return "redirect:/mypage/mypage";
    }

    //회원탈퇴
    @GetMapping("/delete")
    public String deletePOST(HttpServletRequest request){
        HttpSession session = request.getSession();
        MemberDTO dto = (MemberDTO)session.getAttribute("memberDTO");
        String userId = dto.getUserId();
        int result = memberServiceIf.delete(userId);
        if(result > 0 ){
            request.getSession().invalidate();
            return "redirect:/login/logout";
        }
        else{
            return "/mypage/mypage";
        }
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
    public void memberListGet(PageRequestDTO pageRequestDTO, Model model,
                              HttpServletRequest request
    ) {

        PageResponseDTO<MemberDTO> pageResponseDTO = memberServiceIf.list(pageRequestDTO);


        model.addAttribute("pageResponseDTO" , pageResponseDTO);
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "회원관리"));
    }
    @GetMapping("/memberView")
    public void memberViewGet(MemberDTO memberDTO, Model model) {
        System.out.println("memberVIew로 들어와야함" + memberDTO);


        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "회원관리"));
    }
    @GetMapping("/likeList")
    public void likeListGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "찜 내역"));
    }
    @GetMapping("/payList")
    public String payListGet(Model model,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        if(memberDTO != null) {
            String userId = memberDTO.getUserId();
            List<PaymentDTO> paymentDTOList = paymentServiceIf.selectPayment(userId);
            model.addAttribute("paymentDTOList", paymentDTOList);
            model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "결제 내역"));
            return "/mypage/payList";
        } else {
            redirectAttributes.addFlashAttribute("info", "로그인 정보 없음");
            return "redirect:/";
        }
    }
}
