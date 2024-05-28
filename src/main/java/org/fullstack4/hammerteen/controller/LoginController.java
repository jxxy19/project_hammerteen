package org.fullstack4.hammerteen.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.dto.LoginDTO;
import org.fullstack4.hammerteen.dto.MemberDTO;
import org.fullstack4.hammerteen.service.LoginServiceIf;
import org.fullstack4.hammerteen.util.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@Controller
@RequestMapping(value="/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginServiceIf loginServiceIf;

    @GetMapping("/login")
    public void loginGet (HttpServletRequest req, Model model) {
        model.addAttribute("menu", "로그인");
        String acc_url = req.getHeader("referer");
        model.addAttribute("acc_url", acc_url);
    }
    @PostMapping("/login")
    public String loginPost(LoginDTO loginDTO,
                          @RequestParam(name="acc_url", defaultValue = "/", required = false) String acc_url,
                          HttpServletRequest req,
                          Model model,
                          HttpServletResponse response,
                          RedirectAttributes redirectAttributes) {
        HttpSession session = req.getSession();
        /*loginDTO.setPwd(commonUtil.encryptPwd(loginDTO.getPwd())); */
        String save_id = "";

        MemberDTO LoginMemberDTO = loginServiceIf.login_info(loginDTO);

        if(LoginMemberDTO !=null && LoginMemberDTO.getUserId() != null ) {

            if(LoginMemberDTO.getUserState().equals("N")){

                redirectAttributes.addFlashAttribute("errorAlert","없는 회원입니다.");
                return "redirect:/login/login";
            }
            //아이디 저장
            if(loginDTO.getSave_id()!=null) {
                save_id=loginDTO.getUserId();
                CookieUtil.addCookie(response,"save_id",save_id,60*60*24);
                CookieUtil.addCookie(response,"save_id_flag","checked",60*60*24);

            }

            if (loginDTO.getSave_id() == null) {
                CookieUtil.deleteCookie(response, "save_id");
                CookieUtil.deleteCookie(response, "save_id_flag");
            }


            //세션저장
            session.setAttribute("memberDTO",LoginMemberDTO);

            if (req.getServletPath().equals("/login")) {
                return "redirect:/";
            }

            if( acc_url.contains("/login/login")) {
                return "redirect:/";
            }
            return "redirect:" + acc_url;
        }

        System.out.println("로그인실패");
        redirectAttributes.addFlashAttribute("errors","사용자 정보가 일치하지 않습니다.");

        return "redirect:/login/login";

    }
    @GetMapping("/join")
    public void joinGet() {
        log.info("회원가입 겟");
    }
    @PostMapping("/join")
    public void joinPost() {
        log.info("회원가입 겟");
    }
    @GetMapping("/resetPwd")
    public void resetPwdGet() {
        log.info("resetPwd get");
    }
    @PostMapping("/resetPwd")
    public void resetPwdPost() {
        log.info("resetPwd post");
    }
    @GetMapping("/forgotPwd")
    public void forgotPwdGet() {
        log.info("forgotPwd get");
    }
    @PostMapping("/forgotPwd")
    public void forgotPwdPost() {
        log.info("forgotPwd post");
    }

    @GetMapping("/logout")
    public String logoutGet(HttpServletRequest req, Model model,HttpServletResponse resp){
        HttpSession session = req.getSession(false);
        if(session!=null) {
            session.invalidate();
        }

        //저장 아이디 쿠키 값 삭제
        String save_id_value= CookieUtil.getCookieValue(req,"save_id");
        if(save_id_value != null) {
            CookieUtil.deleteCookie(resp, save_id_value);
        }




        return "redirect:/";
    }
}
