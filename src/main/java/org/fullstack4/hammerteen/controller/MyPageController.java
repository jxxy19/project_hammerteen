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
import org.fullstack4.hammerteen.dto.*;
import org.fullstack4.hammerteen.service.*;
import org.fullstack4.hammerteen.util.CommonFileUtil;
import org.fullstack4.hammerteen.util.CommonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UrlPathHelper;

import java.util.Arrays;
import java.util.List;

@Log4j2
@Controller
@RequestMapping(value="/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final PaymentServiceIf paymentServiceIf;
    private final MemberServiceIf memberServiceIf;
    private final CommonFileUtil commonFileUtil;
    private final BbsServiceIf bbsServiceIf;
    private final CartServiceIf cartServiceIf;
    private final LectureServiceIf lectureServiceIf;
    private String menu1 = "마이페이지";

    @GetMapping("/mypage")
    public void mypageGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "회원정보 수정"));
    }

    @PostMapping("/mypage")
    public String mypagePost(@Valid MemberDTO memberDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request, MultipartHttpServletRequest file) {


        //핸드폰번호 합치기
        String[] phoneStr = memberDTO.getPhoneNumber().split(",");
        memberDTO.setPhoneNumber(phoneStr[0]+phoneStr[1]+phoneStr[2]);

        List<String> filenames = null;
        String realPath ="D:\\java4\\hammerteen\\src\\main\\resources\\static\\upload";
        if(!memberDTO.getTemFileName().isEmpty()){
            memberDTO.setFileName(memberDTO.getTemFileName());
            MemberDTO modifyDTO = memberServiceIf.detailModify(memberDTO);
            System.out.println("modifyDTO22"+modifyDTO);

            request.getSession().setAttribute("memberDTO", modifyDTO);

            return "redirect:/mypage/memberView?userId="+memberDTO.getUserId();


        }
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
    public void myListGet(Model model, PageRequestDTO pageRequestDTO,
                          HttpServletRequest req) {
        HttpSession session = req.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        String userId = memberDTO.getUserId();
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "작성글 확인"));
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String url = urlPathHelper.getOriginatingQueryString(req);
        if (url.contains("board")) {
            pageRequestDTO.setCategory1("자유게시판");
            PageResponseDTO<BbsDTO> pageResponseDTO = bbsServiceIf.myList(pageRequestDTO, userId);
            model.addAttribute("pageResponseDTO" , pageResponseDTO);
            model.addAttribute("category1", "board");

        }
        if (url.contains("qna")) {
            pageRequestDTO.setCategory1("QnA게시판");
            PageResponseDTO<BbsDTO> pageResponseDTO = bbsServiceIf.myList(pageRequestDTO, userId);
            model.addAttribute("pageResponseDTO" , pageResponseDTO);
            model.addAttribute("category1", "qna");
        }
        if (url.contains("notice")) {
            pageRequestDTO.setCategory1("공지사항");
            PageResponseDTO<BbsDTO> pageResponseDTO = bbsServiceIf.myList(pageRequestDTO, userId);
            model.addAttribute("pageResponseDTO" , pageResponseDTO);
            model.addAttribute("category1", "notice");
        }
        if (url.contains("review")) {
            pageRequestDTO.setCategory1("후기게시판");
            PageResponseDTO<BbsDTO> pageResponseDTO = bbsServiceIf.myList(pageRequestDTO, userId);
            model.addAttribute("pageResponseDTO" , pageResponseDTO);
            model.addAttribute("category1", "review");
        }
        if (url.contains("eduInfo")) {
            pageRequestDTO.setCategory1("교육정보게시판");
            PageResponseDTO<BbsDTO> pageResponseDTO = bbsServiceIf.myList(pageRequestDTO, userId);
            model.addAttribute("pageResponseDTO" , pageResponseDTO);
            model.addAttribute("category1", "eduInfo");
        }
        if (url.contains("data")) {
            pageRequestDTO.setCategory1("자료게시판");
            PageResponseDTO<BbsDTO> pageResponseDTO = bbsServiceIf.myList(pageRequestDTO, userId);
            model.addAttribute("pageResponseDTO" , pageResponseDTO);
            model.addAttribute("category1", "data");
        }

    }
    @GetMapping("/cart")
    public String cartGet(Model model,
                        HttpSession session,
                          RedirectAttributes redirectAttributes) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        if(memberDTO != null) {
            List<CartDTO> cartDTOList = cartServiceIf.myCartList(memberDTO.getUserId());
            int totalCnt = cartServiceIf.countList(memberDTO.getUserId());
            log.info("cartDTOList : {}", cartDTOList);
            model.addAttribute("totalCnt", totalCnt);
            model.addAttribute("cartDTOList",cartDTOList);
            model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "장바구니 내역"));
            return "/mypage/cart";
        } else {
            redirectAttributes.addFlashAttribute("info", "로그인 정보가 없습니다.");
            return "redirect:/";
        }
    }

    @PostMapping("/deleteCart")
    public String deleteCartPOST(Model model, RedirectAttributes redirectAttributes,
                                 @RequestParam(name="cartIdx", defaultValue = "") String cartIdx, HttpSession session) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        log.info("cartIdx : {}", cartIdx);
        if(memberDTO != null) {
            for(String idx : cartIdx.split(",")) {
                cartServiceIf.deleteCart(CommonUtil.parseInt(idx));
            }
            redirectAttributes.addFlashAttribute("info", "삭제가 완료되었습니다.");
            return "redirect:/mypage/cart";
        } else {
            redirectAttributes.addFlashAttribute("info", "로그인 정보가 없습니다.");
            return "redirect:/";
        }
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



        MemberDTO resultDTO = memberServiceIf.Detailview(memberDTO.getUserId());
        TeacherDTO teacherDTO = memberServiceIf.teacherView(memberDTO.getUserId());


        model.addAttribute("dto", resultDTO);
        model.addAttribute("teacherDTO", teacherDTO);

        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "회원관리"));
    }


    @PostMapping("/memberView")
    public String memberViewPost(@Valid MemberDTO memberDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request, MultipartHttpServletRequest file,

        @RequestParam(name="categoryName",defaultValue = "")String categoryName , @RequestParam(name="education",defaultValue = "") String education, @RequestParam(name="writing",defaultValue = "") String writing){
        //핸드폰번호 합치기
        String[] phoneStr = memberDTO.getPhoneNumber().split(",");
        memberDTO.setPhoneNumber(phoneStr[0]+phoneStr[1]+phoneStr[2]);
        TeacherDTO teacherDTO = new TeacherDTO();
        if(memberDTO.getRole().equals("teacher")){


            teacherDTO.setCategoryName(categoryName);
            teacherDTO.setEducation(education);
            teacherDTO.setWriting(writing);
            teacherDTO.setName(memberDTO.getName());
            teacherDTO.setUserId(memberDTO.getUserId());
        }



        List<String> filenames = null;
        String realPath ="D:\\java4\\hammerteen\\src\\main\\resources\\static\\upload";
        if(!memberDTO.getTemFileName().isEmpty()){
            memberDTO.setFileName(memberDTO.getTemFileName());
            MemberDTO modifyDTO = memberServiceIf.detailModify(memberDTO);
            if(memberDTO.getRole().equals("teacher")) {
                int result = memberServiceIf.registTeacher(teacherDTO, memberDTO.getUserId());
            }


            request.getSession().setAttribute("memberDTO", modifyDTO);

            return "redirect:/mypage/memberView?userId="+memberDTO.getUserId();


        }


        filenames = commonFileUtil.fileuploads(file,realPath);
     

        if(filenames != null) {
            if (memberDTO.getRole().equals("teacher")) {

                teacherDTO.setProfile(filenames.get(0));

            }
            else {

                memberDTO.setFileName(filenames.get(0));
                memberDTO.setDirectory(realPath);
            }
        }
        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/mypage/memberView?userId="+memberDTO.getUserId();
        }


        MemberDTO modifyDTO = memberServiceIf.detailModify(memberDTO);

        if(memberDTO.getRole().equals("teacher")) {
            int result = memberServiceIf.registTeacher(teacherDTO, memberDTO.getUserId());
        }


        request.getSession().setAttribute("memberDTO", modifyDTO);

        return "redirect:/mypage/memberView?userId="+memberDTO.getUserId();
    }




    @GetMapping("/likeList")
    public String likeListGet(Model model,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        if(memberDTO != null) {
            List<LectureGoodDTO> goodDTOList = lectureServiceIf.listGood(memberDTO.getUserId());
            int totalCnt = lectureServiceIf.countList(memberDTO.getUserId());
            log.info("goodDTOList : {}", goodDTOList);
            model.addAttribute("totalCnt", totalCnt);
            model.addAttribute("goodDTOList",goodDTOList);
            model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "찜 내역"));
            return "/mypage/likeList";
        } else {
            redirectAttributes.addFlashAttribute("info", "로그인 정보가 없습니다.");
            return "redirect:/";
        }
    }

    @PostMapping("/deleteGood")
    public String deleteLikePOST(Model model, RedirectAttributes redirectAttributes,
                                 @RequestParam(name="goodIdx", defaultValue = "") String goodIdx, HttpSession session) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        log.info("goodIdx : {}", goodIdx);
        if(memberDTO != null) {
            for(String idx : goodIdx.split(",")) {
                lectureServiceIf.deleteGood(CommonUtil.parseInt(idx));
            }
            redirectAttributes.addFlashAttribute("info", "삭제가 완료되었습니다.");
            return "redirect:/mypage/likeList";
        } else {
            redirectAttributes.addFlashAttribute("info", "로그인 정보가 없습니다.");
            return "redirect:/";
        }
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
