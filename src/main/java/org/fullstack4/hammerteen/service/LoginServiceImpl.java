package org.fullstack4.hammerteen.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.fullstack4.hammerteen.domain.MemberEntity;
import org.fullstack4.hammerteen.dto.LoginDTO;
import org.fullstack4.hammerteen.dto.MemberDTO;
import org.fullstack4.hammerteen.repository.LoginRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginServiceIf{
    private final ModelMapper modelMapper;
    private final LoginRepository loginRepository;

    @Override
    public MemberDTO login_info(LoginDTO loginDTO) {
        Optional<MemberEntity> result = loginRepository.findByUserId(loginDTO.getUserId());
        MemberEntity memberEntity = result.orElse(null);
        if(memberEntity!=null && memberEntity.getPwd().equals(loginDTO.getPwd())) {
            MemberDTO memberDTO = modelMapper.map(memberEntity, MemberDTO.class);
            return memberDTO;
        }
        return null;
    }
}
