package org.fullstack4.hammerteen.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.fullstack4.hammerteen.domain.MemberEntity;
import org.fullstack4.hammerteen.dto.MemberDTO;
import org.fullstack4.hammerteen.dto.PageRequestDTO;
import org.fullstack4.hammerteen.dto.PageResponseDTO;
import org.fullstack4.hammerteen.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        member.modify(memberDTO.getPwd(), memberDTO.getEmail(),memberDTO.getPhoneNumber(),memberDTO.getAddr1(),memberDTO.getAddr2(),memberDTO.getZipCode(),memberDTO.getUserState(),memberDTO.getFileName(),memberDTO.getDirectory());

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

    @Override
    public Boolean emailCheck(String email) {
        boolean result = memberRepository.existsByEmail(email);

        return result;
    }

    @Override
    public PageResponseDTO<MemberDTO> list(PageRequestDTO pageRequestDTO) {
        PageRequest pageable = pageRequestDTO.getPageable();
        Page<MemberEntity> result = null;
        String search_word = pageRequestDTO.getSearch_word();

       if(pageRequestDTO.getSearch_word()!=null && !pageRequestDTO.getSearch_word().isEmpty()) {
           result = memberRepository.findAllByUserIdContainsOrNameContainsOrderByMemberIdxDesc(pageable,search_word ,search_word);
        }
        else{
            result = memberRepository.findAllByOrderByMemberIdxDesc(pageable);
        }
        List<MemberDTO> dtoList = result.stream()
                .map(board->modelMapper.map(board,MemberDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<MemberDTO>withAll().pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList).total_count((int) result.getTotalElements()).build();
    }

    // 지현 추가 : 선생님 리스트 조회용(통계에서)
    @Override
    public List<MemberDTO> teacherList(String userIdOrName) {
        List<MemberEntity> memberEntityList = memberRepository.findMemberEntityByUserIdLikeOrNameLikeAndRoleEquals("%"+userIdOrName+"%", "%"+userIdOrName+"%", "Y");
        List<MemberDTO> memberDTOList = memberEntityList.stream().map(vo -> modelMapper.map(vo, MemberDTO.class)).collect(Collectors.toList());
        return memberDTOList;
    }
}
