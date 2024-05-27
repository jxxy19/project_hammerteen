package org.fullstack4.hammerteen.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.domain.BbsEntity;
import org.fullstack4.hammerteen.domain.BbsReplyEntity;
import org.fullstack4.hammerteen.dto.BbsDTO;
import org.fullstack4.hammerteen.dto.BbsReplyDTO;
import org.fullstack4.hammerteen.repository.BbsReplyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return result;
    }

    @Override
    public List<BbsReplyDTO> replyList(BbsReplyDTO bbsReplyDTO) {
        List<BbsReplyEntity> list = bbsReplyRepository.listOfReply(bbsReplyDTO.getBbsIdx());
        List<BbsReplyDTO> rList = list.stream()
                .map(board->modelMapper.map(board, BbsReplyDTO.class))
                .collect(Collectors.toList());
        return rList;
    }

    @Override
    public void replyDelete(int replyIdx) {
        bbsReplyRepository.deleteById(replyIdx);
    }

    @Override
    public void replyModify(BbsReplyDTO bbsReplyDTO) {
        Optional<BbsReplyEntity> result = bbsReplyRepository.findById(bbsReplyDTO.getReplyIdx());
        BbsReplyEntity bbsReplyEntity =result.orElse(null);
        if (bbsReplyEntity != null) {
            bbsReplyEntity.modify(bbsReplyDTO.getContent());
            bbsReplyRepository.save(bbsReplyEntity);
        }
    }

}
