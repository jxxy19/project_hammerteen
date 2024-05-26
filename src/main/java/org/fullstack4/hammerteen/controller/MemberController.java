package org.fullstack4.hammerteen.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.fullstack4.hammerteen.dto.MemberDTO;

import org.fullstack4.hammerteen.service.MemberServiceIf;
import org.fullstack4.hammerteen.util.CommonFileUtil;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping(value="/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberServiceIf memberServiceIf;
    private final CommonFileUtil commonFileUtil;

    /* @GetMapping("/view")
     public void view(){
     }*/
    @GetMapping("/regist")
    public void registGET(Model model) {
        model.addAttribute("menu", "회원가입");
    }

    @PostMapping("/regist")
    public String registPOST(@Valid MemberDTO memberDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {


        System.out.println("MemberDTO : " +   memberDTO);


        /* memberDTO.setPassword(commonUtil.encryptPwd(memberDTO.getPassword()));*/
        if (bindingResult.hasErrors()) {

            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("memberDTO", memberDTO);
            return "/member/regist";
        }
        int result = memberServiceIf.regist(memberDTO);
        System.out.println("result : " + result) ;

        if (result > 0) {
            redirectAttributes.addFlashAttribute("info", "alert(`가입이 완료되었습니다.`);");
            return "redirect:/login/login";
        } else {
            return "/member/regist";
        }
    }

    @GetMapping("/complete")
    public void completeGET(Model model) {
        model.addAttribute("menu", "회원가입 완료");
    }

    //아이디 중복체크
    @GetMapping("/duplecheck")
    @ResponseBody
    public ResponseEntity<?> duplecheckGET(@RequestParam("userId") String userId, HttpServletRequest request, HttpServletResponse response,
                                           Model model) {

        Boolean result = false;
        result = memberServiceIf.idCheck(userId);

        //아이디 중복으로 있으면 (true)
        if (result) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", false);
            resp.put("message", "중복된 아이디입니다.");

            return ResponseEntity.ok().body(resp);

        } else {
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", true);


            return ResponseEntity.ok().body(resp);

        }

    }

    //이메일 중복체크

    @GetMapping("/emailduplecheck")
    @ResponseBody
    public ResponseEntity<?> emailduplecheckGET(@RequestParam("email") String email, HttpServletRequest request, HttpServletResponse response,
                                           Model model) {
        System.out.println("여기로 들어와야함 중복");
        Boolean result = false;
        result = memberServiceIf.emailCheck(email);

        //아이디 중복으로 있으면 (true)
        if (result) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", false);
            resp.put("message", "중복된 이메일입니다.");

            return ResponseEntity.ok().body(resp);

        } else {
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", true);


            return ResponseEntity.ok().body(resp);

        }

    }
    /*@GetMapping("/modify")
    public void modifyGET(){
    }*/


   /* @PostMapping("/mypage")
    public String modifyPOST(@Valid MemberDTO memberDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request, MultipartHttpServletRequest file) {

        System.out.println("files" + file);
        List<String> filenames = null;
        String realPath ="D:\\java\\hammer\\src\\main\\resources\\static\\upload";

        filenames = commonFileUtil.fileuploads(file,realPath);
        System.out.println("filenames"+filenames);

        memberDTO.setFileName(filenames.get(0));
        memberDTO.setDirectory(realPath);


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("info", "alert(`회원 정보 수정 실패 올바른 값을 입력해 주세요.`);");
            return "redirect:/member/mypage";
        }

        MemberDTO modifyDTO = memberServiceIf.modify(memberDTO);
        System.out.println("modifyDTO22"+modifyDTO);

        request.getSession().setAttribute("memberDTO", modifyDTO);
        redirectAttributes.addFlashAttribute("info", "alert(`회원 정보 수정 성공`);");
        return "redirect:/member/mypage";
    }*/


    @GetMapping("/modify")
    public void viewGET(@RequestParam(name = "userId", defaultValue = "") String userId, Model model) {
        model.addAttribute("menu", "내 정보 수정");
//
//        MemberDTO memberDTO = memberServiceIf.view(userId);
//        model.addAttribute("memberDTO",memberDTO);
    }

    @GetMapping("/delete")
    public String deletePOST(HttpServletRequest request,@RequestParam(name="userId",defaultValue = "") String userId) {


        int result = memberServiceIf.delete(userId);
        if (result > 0) {
            request.getSession().invalidate();
            return "redirect:/mypage/memberList";
        } else {
            return "/mypage/memberView?userId" +userId;
        }
    }

    //지현 추가 : 선생님 조회용
    @RequestMapping(value = "/teacherList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String teacherList(@RequestParam(name = "userIdOrName", defaultValue = "") String userIdOrName) {
        log.info("========================================== teacherList 시작");
        log.info("userIdOrName : {}", userIdOrName);
        JSONArray jsonArray = new JSONArray();
        if(!userIdOrName.isEmpty()) {
            List<MemberDTO> memberDTOList = memberServiceIf.teacherList(userIdOrName);
            log.info("memberDTOList : {}", memberDTOList);
            for(MemberDTO dto : memberDTOList) {
                log.info("dto : {}", dto);
                Map<String, String> resultMap = new HashMap<>();
                resultMap.put("\"userId\"", "\""+dto.getUserId()+"\"");
                resultMap.put("\"name\"", "\""+dto.getUserId()+"\"");
                log.info("resultMap : {}", resultMap);
                jsonArray.put(resultMap);
            }
            log.info("jsonArray : {}", jsonArray);
        }
        log.info("========================================== teacherList 종료");
        return jsonArray.toString();
    }


}