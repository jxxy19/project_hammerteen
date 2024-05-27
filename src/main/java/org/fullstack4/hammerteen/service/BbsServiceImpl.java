package org.fullstack4.hammerteen.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.domain.BbsEntity;
import org.fullstack4.hammerteen.dto.BbsDTO;
import org.fullstack4.hammerteen.dto.PageRequestDTO;
import org.fullstack4.hammerteen.dto.PageResponseDTO;
import org.fullstack4.hammerteen.repository.BbsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class BbsServiceImpl implements BbsServiceIf{
    private final ModelMapper modelMapper;
    private final BbsRepository bbsRepository;
//    게시글 등록
    @Override
    public int regist(BbsDTO bbsDTO) {
        BbsEntity bbsEntity = modelMapper.map(bbsDTO, BbsEntity.class);
        return bbsRepository.save(bbsEntity).getBbsIdx();
    }
//    게시글 상세
    @Override
    public BbsDTO view(BbsDTO bbsDTO) {
        Optional<BbsEntity> result = bbsRepository.findById(bbsDTO.getBbsIdx());
        BbsEntity board = result.orElse(null);
        BbsDTO resultbbsDTO = modelMapper.map(board, BbsDTO.class);

        return resultbbsDTO;
    }
//    게시글 리스트
    @Override
    public PageResponseDTO<BbsDTO> list(PageRequestDTO pageRequestDTO) {
        PageRequest pageable = pageRequestDTO.getPageable();
        Page<BbsEntity> result = null;
        String category = pageRequestDTO.getCategory1();
        String category2 = pageRequestDTO.getCategory2();
        String search_word = pageRequestDTO.getSearch_word();
        if(pageRequestDTO.getSearch_word()!=null && !pageRequestDTO.getSearch_word().isEmpty()) {
            result = bbsRepository.findAllByCategory1AndTitleContainsOrContentContainsAndCategory1ContainsOrUserIdContainsAndCategory1ContainsOrderByBbsIdxDesc(
                    pageable, category, search_word,search_word,category,search_word,category
            );
        }
        else{
            result = bbsRepository.findAllByCategory1OrderByBbsIdxDesc(pageable, pageRequestDTO.getCategory1());
        }
        List<BbsDTO> dtoList = result.stream()
                .map(board->modelMapper.map(board,BbsDTO.class))
                .collect(Collectors.toList());
        return PageResponseDTO.<BbsDTO>withAll().pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList).total_count((int) result.getTotalElements()).build();
    }
//게시글 수정
    @Override
    public void modify(BbsDTO bbsDTO) {
        Optional<BbsEntity> result = bbsRepository.findById(bbsDTO.getBbsIdx());
        BbsEntity bbsEntity =result.orElse(null);
        if (bbsEntity != null) {
            bbsEntity.modify(bbsDTO.getTitle(),bbsDTO.getContent(),bbsDTO.getCategory1());
            bbsRepository.save(bbsEntity);
        }
    }
//    게시글 삭제
    @Override
    public void delete(BbsDTO bbsDTO) {
        bbsRepository.deleteById(bbsDTO.getBbsIdx());
    }
//    내가 작성한 게시글 리스트
    @Override
    public PageResponseDTO<BbsDTO> myList(PageRequestDTO pageRequestDTO, String userId) {
        PageRequest pageable = pageRequestDTO.getPageable();
        Page<BbsEntity> result = null;
        String category = pageRequestDTO.getCategory1();
        String category2 = pageRequestDTO.getCategory2();
        result = bbsRepository.findAllByCategory1AndUserIdOrderByBbsIdxDesc(pageable, pageRequestDTO.getCategory1(), userId);

        log.info("result : " + result);
        List<BbsDTO> dtoList = result.stream()
                .map(board->modelMapper.map(board,BbsDTO.class))
                .collect(Collectors.toList());
        log.info("dtoList : "+ dtoList);
        return PageResponseDTO.<BbsDTO>withAll().pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList).total_count((int) result.getTotalElements()).build();
    }
//조회수 체크
    @Transactional
    @Override
    public void updateReadCnt(int bbsIdx) {
        bbsRepository.updateReadCnt(bbsIdx);
    }


}
