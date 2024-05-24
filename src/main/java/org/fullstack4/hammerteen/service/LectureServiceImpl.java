package org.fullstack4.hammerteen.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.domain.LectureEntity;
import org.fullstack4.hammerteen.dto.*;
import org.fullstack4.hammerteen.repository.LectureDetailRepository;
import org.fullstack4.hammerteen.repository.LectureReplyRepository;
import org.fullstack4.hammerteen.repository.LectureRepository;
import org.fullstack4.hammerteen.util.CommonFileUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class LectureServiceImpl implements LectureServiceIf{
    private final ModelMapper modelMapper;
    private final CommonFileUtil commonFileUtil;
    private final LectureRepository lectureRepository;
    private final LectureDetailRepository lectureDetailRepository;
    private final LectureReplyRepository lectureReplyRepository;

    @Override
    public int regist(LectureDTO lectureDTO) {
        LectureEntity lectureEntity = modelMapper.map(lectureDTO,LectureEntity.class);
        return lectureRepository.save(lectureEntity).getLectureIdx();
    }

    @Override
    public LectureDTO view(LectureDTO lectureDTO) {
        Optional<LectureEntity> result = lectureRepository.findById(lectureDTO.getLectureIdx());
        LectureEntity lectureEntity =result.orElse(null);
        LectureDTO resultDTO = modelMapper.map(lectureEntity, LectureDTO.class);
        resultDTO.setThumbnailImgDirectory(resultDTO.getThumbnailImgDirectory()!=null?resultDTO.getThumbnailImgDirectory().replace("D:\\java4\\hammerteen\\src\\main\\resources\\static\\upload","/upload"):"/assets/img");
        resultDTO.setThumbnailImgFile(resultDTO.getThumbnailImgFile()==null?"default.png":resultDTO.getThumbnailImgFile());
        resultDTO.setThumbnailVideoDirectory(resultDTO.getThumbnailVideoDirectory()!=null?resultDTO.getThumbnailVideoDirectory().replace("D:\\java4\\hammerteen\\src\\main\\resources\\static\\upload","/upload"):"/assets/video");
        resultDTO.setThumbnailVideoFile(resultDTO.getThumbnailVideoFile()==null?"default.mp4":resultDTO.getThumbnailVideoFile());
        return resultDTO;
    }

    @Override
    public void modify(LectureDTO lectureDTO) {
        Optional<LectureEntity> result = lectureRepository.findById(lectureDTO.getLectureIdx());
        LectureEntity lectureEntity =result.orElse(null);
        if (lectureEntity != null) {
            lectureEntity.modify(lectureDTO.getTitle(),lectureDTO.getContent(),lectureDTO.getPrice(),lectureDTO.getStartDate(),lectureDTO.getEndDate());
            lectureRepository.save(lectureEntity);
        }

    }

    @Override
    public void delete(LectureDTO lectureDTO) {
        lectureRepository.deleteById(lectureDTO.getLectureIdx());
    }
    @Override
    public LPageResponseDTO<LectureDTO> list(LPageRequestDTO lpageRequestDTO) {
        PageRequest pageable = lpageRequestDTO.getPageable();
        Page<LectureEntity> result = null;
        String categoryIdx = lpageRequestDTO.getCategoryIdx();
        String search_word = lpageRequestDTO.getSearch_word();
        result = lectureRepository.findAllByCategoryIdxStartingWithAndTeacherNameContainsOrTitleContainsOrderByOrderByLectureIdxDesc(
                pageable, categoryIdx, search_word, search_word);
        List<LectureDTO> dtoList = result.stream()
                .map(board->modelMapper.map(board,LectureDTO.class))
                .collect(Collectors.toList());
        dtoList.stream().forEach(e -> e.setThumbnailImgDirectory(e.getThumbnailImgDirectory()!=null?e.getThumbnailImgDirectory().replace("D:\\java4\\hammerteen\\src\\main\\resources\\static\\upload","/upload"):"/assets/img"));
        dtoList.stream().forEach(e -> e.setThumbnailImgFile(e.getThumbnailImgFile()==null?"default.png":e.getThumbnailImgFile()));
        return LPageResponseDTO.<LectureDTO>withAll().lpageRequestDTO(lpageRequestDTO)
                .dtoList(dtoList).total_count((int) result.getTotalElements()).build();
    }

    @Override
    public LectureDTO registThumbnailImg(LectureDTO lectureDTO, MultipartHttpServletRequest files) {
        String saveDirectory = "D:\\java4\\hammerteen\\src\\main\\resources\\static\\upload";
        List<String> filenames = null;
        filenames = commonFileUtil.thumbnailUploadImg(files,saveDirectory);
        if(filenames!=null) {
            for (String filename : filenames) {
                lectureDTO.setThumbnailImgDirectory(saveDirectory);
                lectureDTO.setThumbnailImgFile(filename);
            }
        }
        return lectureDTO;
    }

    @Override
    public void modifyThumbnailImg(LectureDTO lectureDTO, MultipartHttpServletRequest files) {

    }

    @Override
    public LectureDTO registThumbnailVideo(LectureDTO lectureDTO, MultipartHttpServletRequest files) {
        String saveDirectory = "D:\\java4\\hammerteen\\src\\main\\resources\\static\\upload";
        List<String> filenames = null;
        filenames = commonFileUtil.thumbnailUploadImg(files,saveDirectory);
        if(filenames!=null) {
            for (String filename : filenames) {
                lectureDTO.setThumbnailVideoDirectory(saveDirectory);
                lectureDTO.setThumbnailVideoFile(filename);
            }
        }
        return lectureDTO;
    }

    @Override
    public void modifyThumbnailVideo(LectureDTO lectureDTO, MultipartHttpServletRequest files) {
        Optional<LectureEntity> result = lectureRepository.findById(lectureDTO.getLectureIdx());
        LectureEntity lectureEntity =result.orElse(null);
        String saveDirectory = "D:\\java4\\hammerteen\\src\\main\\resources\\static\\upload";
        List<String> filenames = null;
        filenames = commonFileUtil.thumbnailUploadVideo(files,saveDirectory);
        if (lectureEntity != null) {
            if(filenames!=null) {
                if(lectureEntity.getThumbnailVideoDirectory()!=null) {
                    commonFileUtil.fileDelite(lectureEntity.getThumbnailVideoDirectory(), lectureEntity.getThumbnailVideoFile());
                }
                for (String filename : filenames) {
                    lectureEntity.modifyVideo(saveDirectory,filename);
                }
            }
            lectureRepository.save(lectureEntity);
        }
    }

    @Override
    public void deleteThumbnailImg(int lectureIdx) {

    }

    @Override
    public void deleteThumbnailVideo(int lectureIdx) {

    }


    @Override
    public void registLectureDetail(LectureDetailDTO lectureDetailDTO) {

    }

    @Override
    public void modifyLectureDetail(LectureDetailDTO lectureDetailDTO) {

    }

    @Override
    public void deleteLectureDetail(LectureDetailDTO lectureDetailDTO) {

    }

    @Override
    public void deleteLectureDetail(int lectureIdx) {

    }

    @Override
    public List<LectureDetailDTO> listLectureDetail(LPageRequestDTO lPageRequestDTO, int lectureIdx) {
        return null;
    }

    @Override
    public void registLectureReply(LectureReplyDTO lectureReplyDTO) {

    }

    @Override
    public void modifyLectureReply(LectureReplyDTO lectureReplyDTO) {

    }

    @Override
    public void deleteLectureReply(LectureReplyDTO lectureReplyDTO) {

    }

    @Override
    public void deleteLectureReplyAll(int lectureIdx) {

    }

    @Override
    public LPageResponseDTO<LectureReplyDTO> listLectureReply(LPageRequestDTO lPageRequestDTO, int lectureIdx) {
        return null;
    }

    @Override
    public int countCategory(String categoryName) {
        return 0;
    }
}
