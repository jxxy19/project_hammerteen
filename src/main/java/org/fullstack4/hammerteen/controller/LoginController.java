package org.fullstack4.hammerteen.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping(value="/login")
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/login")
    public void loginGet () {
        log.info("로그인 겟");
    }
    @PostMapping("/login")
    public void loginPost() {

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
}
