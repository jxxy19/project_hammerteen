package org.fullstack4.hammerteen.controller;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.dto.*;
import org.fullstack4.hammerteen.service.LectureServiceIf;
import org.fullstack4.hammerteen.service.MemberServiceIf;
import org.fullstack4.hammerteen.util.CommonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UrlPathHelper;

import java.util.ArrayList;
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
    public void listGet(Model model, LPageRequestDTO lpageRequestDTO,
                        HttpServletRequest request) {
        LPageResponseDTO<LectureDTO> pageResponseDTO = lectureServiceIf.list(lpageRequestDTO);
        List<Integer> categoryLists = new ArrayList<>();
        int categoryTotalCount = 0;
        String categoryCodeList[] = {"100000","200000","300000","400000","500000","600000","700000","800000","900000"};
        for(String category : categoryCodeList){
            int count = lectureServiceIf.countCategory(category);
            categoryLists.add(count);
            categoryTotalCount += count;
        }
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, this.menu1));
        model.addAttribute("pageResponseDTO" , pageResponseDTO);
        model.addAttribute("categoryTotalCount" , categoryTotalCount);
        model.addAttribute("categoryLists" , categoryLists);
    }
    @GetMapping("/view")
    public void viewGet(Model model, LectureDTO lectureDTO,LPageRequestDTO lpageRequestDTO) {
        LectureDTO resultDTO = lectureServiceIf.view(lectureDTO);
        LPageResponseDTO<LectureReplyDTO> lectureReplyDTOList = lectureServiceIf.listLectureReply(lpageRequestDTO, lectureDTO.getLectureIdx());
        List<LectureDetailDTO> lectureDetailDTOList = lectureServiceIf.listLectureDetail(lectureDTO.getLectureIdx());
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, this.menu1));
        model.addAttribute("lectureDTO", resultDTO);
        model.addAttribute("lectureReplyDTOList", lectureReplyDTOList);
        model.addAttribute("lectureDetailDTOList", lectureDetailDTOList);
    }
    @GetMapping("/regist")
    public String registGet(Model model, HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
//        if(memberDTO.getRole().equals("user")){
//            return "redirect:/";
//        }
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, this.menu1));
        return "/lecture/regist";
    }
    @Transactional
    @PostMapping("/regist")
    public String registPost(LectureDTO lectureDTO, MultipartHttpServletRequest files, HttpServletRequest request){
//        HttpSession session = request.getSession();
//        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
//        if(memberDTO.getRole().equals("user")){
//            return "redirect:/";
//        }
//        lectureDTO.setTeacherIdx(memberDTO.getMemberIdx());
//        lectureDTO.setTeacherName(memberDTO.getUserId());
        if(files!=null) {
            lectureDTO = lectureServiceIf.registThumbnailImg(lectureDTO,files);
            lectureDTO = lectureServiceIf.registThumbnailVideo(lectureDTO,files);
        }
        int resultidx = lectureServiceIf.regist(lectureDTO);
        return "redirect:/lecture/view?lectureIdx="+resultidx;
    }

    @GetMapping("/modify")
    public String modifyGet(Model model, LectureDTO lectureDTO, HttpServletRequest request) {
        LectureDTO viewDTO = lectureServiceIf.view(lectureDTO);
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute( "memberDTO");
        if(!(memberDTO.getMemberIdx()==(viewDTO.getLectureIdx())) && memberDTO.getRole().equals("user")){
            return "redirect:/";
        }
        model.addAttribute("lectureDTO",viewDTO);
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, this.menu1));

        return "/lecture/modify";
    }


    @Transactional
    @PostMapping("/modify")
    public String modifyPost(LectureDTO lectureDTO, MultipartHttpServletRequest files, HttpServletRequest request){
//        HttpSession session = request.getSession();
//        MemberDTO memberDTO = (MemberDTO) session.getAttribute( "memberDTO");
//        if(memberDTO.getRole().equals("user")){
//            return "redirect:/";
//        }
        if(files!=null) {
            lectureServiceIf.modifyThumbnailImg(lectureDTO,files);
            lectureServiceIf.modifyThumbnailVideo(lectureDTO,files);
        }
        return "redirect:/lecture/view?lectureIdx="+lectureDTO.getLectureIdx();
    }


    @Transactional
    @GetMapping("/delete")
    public String delete(LectureDTO lectureDTO, HttpServletRequest request){
//        LectureDTO viewDTO = lectureServiceIf.view(lectureDTO);
//        HttpSession session = request.getSession();
//        MemberDTO memberDTO = (MemberDTO) session.getAttribute( "memberDTO");
//        if(!memberDTO.getUserId().equals(viewDTO.getTeacherName()) && memberDTO.getRole().equals("user")){
//            return "redirect:/";
//        }
        lectureServiceIf.deleteLectureDetailAll(lectureDTO.getLectureIdx());
        lectureServiceIf.deleteLectureReplyAll(lectureDTO.getLectureIdx());
        lectureServiceIf.deleteThumbnailImg(lectureDTO.getLectureIdx());
        lectureServiceIf.deleteThumbnailVideo(lectureDTO.getLectureIdx());
        lectureServiceIf.delete(lectureDTO.getLectureIdx());
        return "redirect:/lecture/list";
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
