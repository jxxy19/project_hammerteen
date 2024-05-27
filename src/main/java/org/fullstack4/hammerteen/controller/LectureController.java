package org.fullstack4.hammerteen.controller;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.dto.*;
import org.fullstack4.hammerteen.service.CartServiceIf;
import org.fullstack4.hammerteen.service.LectureServiceIf;
import org.fullstack4.hammerteen.service.MemberServiceIf;
import org.fullstack4.hammerteen.util.CommonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UrlPathHelper;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


@Log4j2
@Controller
@RequestMapping(value="/lecture")
@RequiredArgsConstructor
public class LectureController {
    private final MemberServiceIf memberServiceIf;
    private final LectureServiceIf lectureServiceIf;
    private final CartServiceIf cartServiceIf;

    private String menu1 = "강의";
    @GetMapping("/list")
    public void listGet(Model model, LPageRequestDTO lpageRequestDTO,
                        HttpServletRequest request) {
        LPageResponseDTO<LectureDTO> pageResponseDTO = lectureServiceIf.list(lpageRequestDTO);
        List<Integer> categoryLists = new ArrayList<>();
        int categoryTotalCount = 0;
        String categoryCodeList[] = {"100000","200000","300000","400000","500000","600000","700000","800000","900000"};
        String categoryCodeListName[] = {"국어","수학","영어","한국사","사회","과학","직업","제2외국어","일반/진로/교양"};
        for(String category : categoryCodeList){
            int count = lectureServiceIf.countCategory(category);
            categoryLists.add(count);
            categoryTotalCount += count;
        }
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, this.menu1));
        model.addAttribute("pageResponseDTO" , pageResponseDTO);
        model.addAttribute("categoryTotalCount" , categoryTotalCount);
        model.addAttribute("categoryLists" , categoryLists);
        for(LectureDTO lectureDTO : pageResponseDTO.getDtoList()){
            for(int i =0; i<categoryCodeListName.length; i++) {
                if (lectureDTO.getCategoryIdx().equals(categoryCodeList[i])){
                    lectureDTO.setCategoryName(categoryCodeListName[i]);
                }
            }
        }
        model.addAttribute("categoryCodeListName" , categoryCodeListName);
    }
    @GetMapping("/view")
    public void viewGet(Model model, LectureDTO lectureDTO,LPageRequestDTO lpageRequestDTO,HttpSession session) {
        LectureDTO resultDTO = lectureServiceIf.view(lectureDTO);
        LPageResponseDTO<LectureReplyDTO> lectureReplyDTOList = lectureServiceIf.listLectureReply(lpageRequestDTO, lectureDTO.getLectureIdx());
        List<LectureDetailDTO> lectureDetailDTOList = lectureServiceIf.listLectureDetail(lectureDTO.getLectureIdx());
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, this.menu1));
        String categoryCodeList[] = {"100000","200000","300000","400000","500000","600000","700000","800000","900000"};
        String categoryCodeListName[] = {"국어","수학","영어","한국사","사회","과학","직업","제2외국어","일반/진로/교양"};
        for(int i =0; i<categoryCodeListName.length; i++) {
            if (resultDTO.getCategoryIdx().equals(categoryCodeList[i])){
                resultDTO.setCategoryName(categoryCodeListName[i]);
            }
        }
        String userId=null;
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        if(memberDTO!=null){
            userId= memberDTO.getUserId();
        }
        String videoName = "";
        LectureReplyDTO lectureReplyDTO = lectureServiceIf.viewReply(LectureReplyDTO.builder().userId("test").lectureIdx(lectureDTO.getLectureIdx()).build());
        if(resultDTO.getThumbnailVideoFile()!=null) {
            videoName = URLEncoder.encode(resultDTO.getThumbnailVideoFile(), StandardCharsets.UTF_8);
        }
        lectureDTO.setThumbnailVideoFile(videoName);
        model.addAttribute("lectureDTO", resultDTO);
        model.addAttribute("lectureReplyDTO", lectureReplyDTO);
        model.addAttribute("lectureReplyDTOList", lectureReplyDTOList);
        model.addAttribute("lectureDetailDTOList", lectureDetailDTOList);

    }
    @GetMapping("/regist")
    public String registGet(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        if(memberDTO.getRole().equals("user")){
            return "redirect:/";
        }
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, this.menu1));
        return "/lecture/regist";
    }
    @Transactional
    @PostMapping("/regist")
    public String registPost(LectureDTO lectureDTO, LectureDetailDTO lectureDetailDTO, MultipartHttpServletRequest files, HttpServletRequest request){
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        if(memberDTO.getRole().equals("user")){
            return "redirect:/";
        }
        lectureDTO.setTeacherIdx(memberDTO.getMemberIdx());
        lectureDTO.setTeacherName(memberDTO.getUserId());
        if(files!=null) {
            lectureDTO = lectureServiceIf.registThumbnailImg(lectureDTO,files);
            lectureDTO = lectureServiceIf.registThumbnailVideo(lectureDTO,files);

        }
        int resultidx = lectureServiceIf.regist(lectureDTO);
        List<LectureDetailDTO> lectureDetailList = new ArrayList<>();
        String[] titleList = lectureDetailDTO.getDetailTitle().split(",");
        String[] lengthList = lectureDetailDTO.getVideoLength().split(",");

        for(int i =0; i<titleList.length;i++){
            LectureDetailDTO tempDTO = LectureDetailDTO.builder().detailTitle(titleList[i]).videoLength(lengthList[i]).lectureIdx(resultidx).build();
            lectureDetailList.add(tempDTO);
        }

        for(int i = 0; files.getFile("video"+(i+1))!=null; i++) {
            lectureServiceIf.registLectureDetailVideo(lectureDetailList.get(i), files, "video"+(i+1));
            lectureDetailList.get(i).setLectureIdx(resultidx);
            lectureServiceIf.registLectureDetail(lectureDetailList.get(i));
        }
        return "redirect:/lecture/view?lectureIdx="+resultidx;
    }

    @GetMapping("/modify")
    public String modifyGet(Model model, LectureDTO lectureDTO, HttpServletRequest request) {
        LectureDTO viewDTO = lectureServiceIf.view(lectureDTO);
        List<LectureDetailDTO> lectureDetailDTOList = lectureServiceIf.listLectureDetail(lectureDTO.getLectureIdx());
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute( "memberDTO");
        if(!(memberDTO.getMemberIdx()==(viewDTO.getTeacherIdx()))){
            return "redirect:/";
        }
        model.addAttribute("lectureDTO",viewDTO);
        model.addAttribute("lectureDetailDTOList",lectureDetailDTOList);
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, this.menu1));

        return "/lecture/modify";
    }


    @Transactional
    @PostMapping("/modify")
    public String modifyPost(LectureDTO lectureDTO,LectureDetailDTO lectureDetailDTO, MultipartHttpServletRequest files, HttpServletRequest request){
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute( "memberDTO");
        if(!(memberDTO.getMemberIdx()==(lectureDTO.getTeacherIdx()))){
            return "redirect:/";
        }
        if(files!=null) {
            lectureServiceIf.modifyThumbnailImg(lectureDTO,files);
            lectureServiceIf.modifyThumbnailVideo(lectureDTO,files);
        }
        log.info(lectureDetailDTO+ "lectureDetailDTO");
        if(lectureDetailDTO.getDetailTitle()!=null&& lectureDetailDTO.getDetailTitle().length()>0) {
            int resultidx = lectureDTO.getLectureIdx();
            lectureServiceIf.deleteLectureDetailAll(resultidx);
            List<LectureDetailDTO> lectureDetailList = new ArrayList<>();
            String[] titleList = lectureDetailDTO.getDetailTitle().split(",");
            String[] lengthList = lectureDetailDTO.getVideoLength().split(",");
            String[] lectureDetailIdxList = String.valueOf(lectureDetailDTO.getLectureDetailIdx()).split(",");
            for (int i = 0; i < titleList.length; i++) {
                if(lectureDetailIdxList[i]!=null){
                    LectureDetailDTO lectureDetailDTO1 = LectureDetailDTO.builder().lectureDetailIdx(Integer.parseInt(lectureDetailIdxList[i])).build();
                    LectureDetailDTO resultDTO = lectureServiceIf.view(lectureDetailDTO1);
                    lectureServiceIf.modifyLectureDetail(resultDTO);

                }else {
                    LectureDetailDTO tempDTO = LectureDetailDTO.builder().detailTitle(titleList[i]).videoLength(lengthList[i]).lectureIdx(resultidx)
                            .build();
                    lectureDetailList.add(tempDTO);
                }
            }
            log.info("lectureDetailList "+lectureDetailList);
            for (int i = 0; files.getFile("video" + (i + 1)) != null; i++) {
                lectureServiceIf.registLectureDetailVideo(lectureDetailList.get(i), files, "video" + (i + 1));
                lectureDetailList.get(i).setLectureIdx(resultidx);
                lectureServiceIf.registLectureDetail(lectureDetailList.get(i));
            }
        }
        lectureServiceIf.modify(lectureDTO);
        return "redirect:/lecture/view?lectureIdx="+lectureDTO.getLectureIdx();
    }


    @Transactional
    @GetMapping("/delete")
    public String delete(LectureDTO lectureDTO, HttpServletRequest request){
        LectureDTO viewDTO = lectureServiceIf.view(lectureDTO);
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute( "memberDTO");
        if(!(memberDTO.getMemberIdx()==(viewDTO.getTeacherIdx()))){
            return "redirect:/";
        }
        lectureServiceIf.deleteLectureDetailAll(lectureDTO.getLectureIdx());
        lectureServiceIf.deleteLectureReplyAll(lectureDTO.getLectureIdx());
        lectureServiceIf.deleteThumbnailImg(lectureDTO.getLectureIdx());
        lectureServiceIf.deleteThumbnailVideo(lectureDTO.getLectureIdx());
        lectureServiceIf.delete(lectureDTO.getLectureIdx());
        return "redirect:/lecture/list";
    }

    @Transactional
    @ResponseBody
    @PostMapping("/deletethumbnailimg")
    public HttpStatus deleteThumbnailImg(LectureDTO lectureDTO, HttpServletRequest request){
        LectureDTO viewDTO = lectureServiceIf.view(lectureDTO);
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute( "memberDTO");
        if(!(memberDTO.getMemberIdx()==(viewDTO.getTeacherIdx()))){
            return HttpStatus.FORBIDDEN;
        }
        lectureServiceIf.deleteThumbnailImg(lectureDTO.getLectureIdx());
        return HttpStatus.OK;
    }
    @Transactional
    @ResponseBody
    @PostMapping("/deletethumbnailvideo")
    public HttpStatus deleteThumbnailVideo(LectureDTO lectureDTO, HttpServletRequest request){
        LectureDTO viewDTO = lectureServiceIf.view(lectureDTO);
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute( "memberDTO");
        if(!(memberDTO.getMemberIdx()==(viewDTO.getTeacherIdx()))){
            return HttpStatus.FORBIDDEN;
        }
        lectureServiceIf.deleteThumbnailVideo(lectureDTO.getLectureIdx());
        return HttpStatus.OK;
    }
    @Transactional
    @ResponseBody
    @PostMapping("/deletelecturedetailfile")
    public HttpStatus deleteLectureDetailFile(LectureDetailDTO lectureDetailDTO,int teacherIdx, HttpServletRequest request){
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute( "memberDTO");
        if(!(memberDTO.getMemberIdx()==(teacherIdx))){
            return HttpStatus.FORBIDDEN;
        }
        lectureServiceIf.deleteThumbnailDetailFile(lectureDetailDTO.getLectureDetailIdx());
        return HttpStatus.OK;
    }
    @Transactional
    @ResponseBody
    @PostMapping("/deletelecturedetail")
    public HttpStatus deleteLectureDetail(LectureDetailDTO lectureDetailDTO,int teacherIdx, HttpServletRequest request){
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute( "memberDTO");
        if(!(memberDTO.getMemberIdx()==(teacherIdx))){
            return HttpStatus.FORBIDDEN;
        }
        lectureServiceIf.deleteLectureDetail(lectureDetailDTO.getLectureDetailIdx());
        return HttpStatus.OK;
    }

    @PostMapping("/registreply")
    public String registReply(LectureReplyDTO lectureReplyDTO, HttpSession session){
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        if(memberDTO==null){
            return "redirect:/";
        }
        lectureReplyDTO.setUserId(memberDTO.getUserId());
        lectureServiceIf.registLectureReply(lectureReplyDTO);

        return "redirect:/lecture/view?lectureIdx="+lectureReplyDTO.getLectureIdx();
    }

    // 장바구니 담기
    @GetMapping("/addCart")
    public String addCart(@RequestParam(name="lectureIdx", defaultValue = "")String lectureIdx,
                          HttpSession session,
                          RedirectAttributes redirectAttributes){
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
        if(memberDTO != null) {
            // 장바구니 확인
            List<CartDTO> myCartList = cartServiceIf.myCartList(memberDTO.getUserId());
            int already = 0;
            for(CartDTO cartDTO : myCartList) {
                if(cartDTO.getLectureIdx() == CommonUtil.parseInt(lectureIdx)) {
                    redirectAttributes.addFlashAttribute("info", "이미 담긴 강의 입니다.");
                    already++;
                    break;
                }
            }
            // 결제 내역 확인
            List<OrderDetailDTO> myOrderList = cartServiceIf.myOrderList(memberDTO.getUserId());
            for(OrderDetailDTO orderDetailDTO : myOrderList) {
                if(orderDetailDTO.getLectureIdx() == CommonUtil.parseInt(lectureIdx)) {
                    redirectAttributes.addFlashAttribute("info", "이미 구매한 강의 입니다.");
                    already++;
                    break;
                }
            }
            if(already == 0) {
                CartDTO cartDTO = CartDTO.builder()
                        .lectureIdx(CommonUtil.parseInt(lectureIdx))
                        .userId(memberDTO.getUserId())
                        .build();
                int result = cartServiceIf.addCart(cartDTO);
                if(result > 0) {
                    redirectAttributes.addFlashAttribute("info", "장바구니 추가 성공");
                    return "redirect:/mypage/cart";
                } else {
                    redirectAttributes.addFlashAttribute("info", "장바구니 추가 실패");
                    return "redirect:/lecture/list";
                }
            } else {
                return "redirect:/lecture/list";
            }
        } else {
            redirectAttributes.addFlashAttribute("info", "로그인 정보 없음");
            return "redirect:/";
        }
    }



    // 선생님 페이지 리스트
    @GetMapping("/teacherList")
    public void teacherListGet(PageRequestDTO pageRequestDTO, Model model,
                               HttpServletRequest request,@RequestParam(value = "categoryName", required = false) String categoryName) {
        PageResponseDTO<TeacherDTO> pageResponseDTO = memberServiceIf.teacherMemberList(pageRequestDTO);

        // categoryName이 null이 아니라면, 특정 카테고리에 대한 선생님 리스트를 가져옵니다.
        if (categoryName != null && !categoryName.isEmpty()) {
            pageResponseDTO = memberServiceIf.teacherMemberListByCategory(pageRequestDTO, categoryName);
        } else {
            // categoryName이 null이거나 비어있다면, 모든 선생님 리스트를 가져옵니다.
            pageResponseDTO = memberServiceIf.teacherMemberList(pageRequestDTO);
        }

        model.addAttribute("pageResponseDTO" , pageResponseDTO);




        //선생님 별 인원수
        model.addAttribute("koreanCount", memberServiceIf.countTeachersByCategory("국어"));
        model.addAttribute("mathCount", memberServiceIf.countTeachersByCategory("수학"));
        model.addAttribute("englishCount", memberServiceIf.countTeachersByCategory("영어"));
        model.addAttribute("historyCount", memberServiceIf.countTeachersByCategory("한국사"));
        model.addAttribute("socialCount", memberServiceIf.countTeachersByCategory("사회"));
        model.addAttribute("scienceCount", memberServiceIf.countTeachersByCategory("과학"));
        model.addAttribute("jobCount", memberServiceIf.countTeachersByCategory("직업"));
        model.addAttribute("languageCount", memberServiceIf.countTeachersByCategory("제2외국어"));
        model.addAttribute("etcCount", memberServiceIf.countTeachersByCategory("일반/진로/교양"));

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
