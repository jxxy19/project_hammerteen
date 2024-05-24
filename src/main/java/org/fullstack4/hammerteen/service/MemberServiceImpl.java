package org.fullstack4.hammerteen.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.fullstack4.hammerteen.domain.MemberEntity;
import org.fullstack4.hammerteen.dto.MemberDTO;
import org.fullstack4.hammerteen.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberServiceIf{
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    @Override
    public int regist(MemberDTO memberDTO) {
        MemberEntity member = modelMapper.map(memberDTO, MemberEntity.class);

        int idx = memberRepository.save(member).getMemberIdx();


        return idx;
    }

    @Override
    public MemberDTO view(String userId) {
        Optional<MemberEntity> result = memberRepository.findByUserId(userId);
        MemberEntity member = result.orElse(null);
        MemberDTO memberDTO = modelMapper.map(member,MemberDTO.class);

        return memberDTO;
    }

    @Override
    public MemberDTO modify(MemberDTO memberDTO) {
        Optional<MemberEntity> result = memberRepository.findByUserId(memberDTO.getUserId());
        MemberEntity member = result.orElse(null);
        member.modify(memberDTO.getPwd(), memberDTO.getEmail(),memberDTO.getPhoneNumber(),memberDTO.getAddr1(),memberDTO.getAddr2(),memberDTO.getZipCode(),memberDTO.getUserState());

        memberRepository.save(member);
        MemberDTO memberUpdateDTO = modelMapper.map(member,MemberDTO.class);
        return memberUpdateDTO;


    }

    @Override
    public int delete(String userId) {
        Optional<MemberEntity> result = memberRepository.findByUserId(userId);
        MemberEntity member = result.orElse(null);
        member.delete("N");
        memberRepository.save(member);

        return 1;
    }

    @Override
    public Boolean idCheck(String userId) {
        boolean result = memberRepository.existsByUserId(userId);

        return result;
    }
}
