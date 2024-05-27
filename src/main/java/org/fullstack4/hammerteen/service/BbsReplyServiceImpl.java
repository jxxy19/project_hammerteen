package org.fullstack4.hammerteen.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.domain.BbsReplyEntity;
import org.fullstack4.hammerteen.dto.BbsReplyDTO;
import org.fullstack4.hammerteen.repository.BbsReplyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor

public class BbsReplyServiceImpl implements BbsReplyServiceIf{
    private final BbsReplyRepository bbsReplyRepository;
    private final ModelMapper modelMapper;
    @Override
    public int regist(BbsReplyDTO bbsReplyDTO) {
        BbsReplyEntity bbsReply = modelMapper.map(bbsReplyDTO, BbsReplyEntity.class);
        int result = bbsReplyRepository.save(bbsReply).getBbsIdx();
        return 0;
    }
}
