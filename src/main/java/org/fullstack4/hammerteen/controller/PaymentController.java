package org.fullstack4.hammerteen.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.util.CommonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping(value="/payment")
@RequiredArgsConstructor
public class PaymentController {
    private String menu1 = "결제";
    @GetMapping("/payment")
    public void paymentGet(Model model) {
        model.addAttribute("pageType", CommonUtil.setPageType(this.menu1, "결제"));
    }
}
