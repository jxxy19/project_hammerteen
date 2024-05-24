package org.fullstack4.hammerteen.service;


import org.fullstack4.hammerteen.dto.LoginDTO;
import org.fullstack4.hammerteen.dto.MemberDTO;

public interface LoginServiceIf {
    MemberDTO login_info(LoginDTO loginDTO);
}
