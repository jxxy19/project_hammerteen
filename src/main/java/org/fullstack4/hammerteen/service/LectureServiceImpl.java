package org.fullstack4.hammerteen.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.domain.LectureDetailEntity;
import org.fullstack4.hammerteen.domain.LectureEntity;
import org.fullstack4.hammerteen.domain.LectureGoodEntity;
import org.fullstack4.hammerteen.domain.LectureReplyEntity;
import org.fullstack4.hammerteen.dto.*;
import org.fullstack4.hammerteen.repository.LectureDetailRepository;
import org.fullstack4.hammerteen.repository.LectureGoodRepository;
import org.fullstack4.hammerteen.repository.LectureReplyRepository;
import org.fullstack4.hammerteen.repository.LectureRepository;
import org.fullstack4.hammerteen.util.CommonFileUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
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
    private final LectureGoodRepository lectureGoodRepository;


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
    public void delete(int lectureIdx) {
        lectureRepository.deleteById(lectureIdx);
    }
    @Override
    public LPageResponseDTO<LectureDTO> list(LPageRequestDTO lpageRequestDTO) {
        PageRequest pageable = lpageRequestDTO.getPageable();
        Page<LectureEntity> result = null;
        String categoryIdx = lpageRequestDTO.getCategoryIdx();
        String search_word = lpageRequestDTO.getSearch_word();
        result = lectureRepository.findAllByCategoryIdxStartingWithAndTeacherNameContainsOrTitleContainsOrderByLectureIdxDesc(
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
        Optional<LectureEntity> result = lectureRepository.findById(lectureDTO.getLectureIdx());
        LectureEntity lectureEntity =result.orElse(null);
        String saveDirectory = "D:\\java4\\hammerteen\\src\\main\\resources\\static\\upload";
        List<String> filenames = null;
        filenames = commonFileUtil.thumbnailUploadImg(files,saveDirectory);
        if (lectureEntity != null) {
            if(filenames!=null) {
                if(lectureEntity.getThumbnailImgDirectory()!=null) {
                    commonFileUtil.fileDelite(lectureEntity.getThumbnailImgDirectory(), lectureEntity.getThumbnailImgFile());
                }
                for (String filename : filenames) {
                    lectureEntity.modifyImg(saveDirectory,filename);
                }
            }
            lectureRepository.save(lectureEntity);
        }
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
        Optional<LectureEntity> result = lectureRepository.findById(lectureIdx);
        LectureEntity lectureEntity =result.orElse(null);
        if (lectureEntity != null && lectureEntity.getThumbnailImgFile()!=null) {
            commonFileUtil.fileDelite(lectureEntity.getThumbnailImgDirectory(), lectureEntity.getThumbnailImgFile());
        }
    }

    @Override
    public void deleteThumbnailVideo(int lectureIdx) {
        Optional<LectureEntity> result = lectureRepository.findById(lectureIdx);
        LectureEntity lectureEntity =result.orElse(null);
        if (lectureEntity != null && lectureEntity.getThumbnailVideoFile()!=null) {
            commonFileUtil.fileDelite(lectureEntity.getThumbnailVideoDirectory(), lectureEntity.getThumbnailVideoFile());
        }
    }


    @Override
    public void registLectureDetail(LectureDetailDTO lectureDetailDTO) {
        LectureDetailEntity lectureDetailEntity = modelMapper.map(lectureDetailDTO, LectureDetailEntity.class);
        lectureDetailRepository.save(lectureDetailEntity);
    }
    @Override
    public void registLectureDetailVideo(LectureDetailDTO lectureDetailDTO, MultipartHttpServletRequest files,String videoParam) {
        String saveDirectory = "D:\\java4\\hammerteen\\src\\main\\resources\\static\\upload";
        List<String> filenames = null;
        filenames = commonFileUtil.uploadVideo(files,saveDirectory,videoParam);
        if(filenames!=null) {
            for (String filename : filenames) {
                lectureDetailDTO.setVideoDirectory(saveDirectory);
                lectureDetailDTO.setVideoFile(filename);
            }
        }
    }

    @Override
    public void modifyLectureDetail(LectureDetailDTO lectureDetailDTO) {
        Optional<LectureDetailEntity> result = lectureDetailRepository.findById(lectureDetailDTO.getLectureDetailIdx());
        LectureDetailEntity lectureDetailEntity =result.orElse(null);
        if (lectureDetailEntity != null) {
            lectureDetailEntity.modify(lectureDetailDTO.getTitle(),lectureDetailDTO.getContent());
            lectureDetailRepository.save(lectureDetailEntity);
        }
    }

    @Override
    public void modifyLectureDetailVideo(LectureDetailDTO lectureDetailDTO, MultipartHttpServletRequest files,String videoParam) {
        Optional<LectureDetailEntity> result = lectureDetailRepository.findById(lectureDetailDTO.getLectureDetailIdx());
        LectureDetailEntity lectureDetailEntity =result.orElse(null);
        String saveDirectory = "D:\\java4\\hammerteen\\src\\main\\resources\\static\\upload";
        List<String> filenames = null;
        filenames = commonFileUtil.uploadVideo(files,saveDirectory,videoParam);
        if (lectureDetailEntity != null) {
            if(filenames!=null) {
                if(lectureDetailEntity.getVideoDirectory()!=null) {
                    commonFileUtil.fileDelite(lectureDetailEntity.getVideoDirectory(), lectureDetailEntity.getVideoFile());
                }
                for (String filename : filenames) {
                    lectureDetailEntity.modifyVideo(saveDirectory,filename);
                }
            }
            lectureDetailRepository.save(lectureDetailEntity);
        }
    }

    @Override
    public void deleteLectureDetail(int lectureDetailIdx) {
        Optional<LectureDetailEntity> result = lectureDetailRepository.findById(lectureDetailIdx);
        LectureDetailEntity lectureDetailEntity =result.orElse(null);
        if (lectureDetailEntity != null) {
            if(lectureDetailEntity.getVideoDirectory()!=null) {
                commonFileUtil.fileDelite(lectureDetailEntity.getVideoDirectory(), lectureDetailEntity.getVideoFile());
            }
        }
        lectureDetailRepository.deleteById(lectureDetailIdx);
    }

    @Override
    public void deleteLectureDetailAll(int lectureIdx) {
        List<LectureDetailEntity> lectureEntityList = lectureDetailRepository.findAllByLectureIdx(lectureIdx);
        for(LectureDetailEntity lectureDetailEntity : lectureEntityList){
            if(lectureDetailEntity.getVideoDirectory()!=null){
                commonFileUtil.fileDelite(lectureDetailEntity.getVideoDirectory(), lectureDetailEntity.getVideoFile());
            }
        }
        lectureDetailRepository.deleteAllByLectureIdx(lectureIdx);
    }

    @Override
    public List<LectureDetailDTO> listLectureDetail(int lectureIdx) {
        List<LectureDetailEntity> lectureEntityList = lectureDetailRepository.findAllByLectureIdx(lectureIdx);
        List<LectureDetailDTO> dtoList = lectureEntityList.stream()
                .map(board->modelMapper.map(board,LectureDetailDTO.class))
                .collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public void registLectureReply(LectureReplyDTO lectureReplyDTO) {
        LectureReplyEntity lectureReplyEntity = modelMapper.map(lectureReplyDTO,LectureReplyEntity.class);
        lectureReplyRepository.save(lectureReplyEntity);
    }

    @Override
    public void modifyLectureReply(LectureReplyDTO lectureReplyDTO) {

    }

    @Override
    public void deleteLectureReply(int lectureReplyIdx) {

    }

    @Override
    public void deleteLectureReplyAll(int lectureIdx) {

    }

    @Override
    public LPageResponseDTO<LectureReplyDTO> listLectureReply(LPageRequestDTO lpageRequestDTO, int lectureIdx) {
        PageRequest pageable = lpageRequestDTO.getPageable();
        Page<LectureReplyEntity> result = null;
        result = lectureReplyRepository.findAllByLectureIdxOrderByLectureReplyIdxDesc(pageable,lectureIdx);
        List<LectureReplyDTO> dtoList = result.stream()
                .map(board->modelMapper.map(board,LectureReplyDTO.class))
                .collect(Collectors.toList());
        return LPageResponseDTO.<LectureReplyDTO>withAll().lpageRequestDTO(lpageRequestDTO)
                .dtoList(dtoList).total_count((int) result.getTotalElements()).build();
    }

    @Override
    public int countCategory(String categoryIdx) {
        lectureRepository.countByCategoryIdx(categoryIdx);
        if(lectureRepository.countByCategoryIdx(categoryIdx)!=0) {
            return lectureRepository.countByCategoryIdx(categoryIdx);
        }
        else{
            return 0;
        }
    }

    @Override
    public void registGood(LectureGoodDTO lectureGoodDTO) {
        LectureGoodEntity board = modelMapper.map(lectureGoodDTO, LectureGoodEntity.class);
        lectureGoodRepository.save(board);
    }
    @Override
    public void deleteGood(LectureGoodDTO lectureGoodDTO) {
        lectureGoodRepository.deleteById(lectureGoodDTO.getGoodIdx());
    }

    @Override
    public List<LectureGoodDTO> listGood(String userId) {
        List<LectureGoodEntity> lectureGoodEntityList = lectureGoodRepository.findAllByUserId(userId);
        List<LectureGoodDTO> lectureGoodDTOList = lectureGoodEntityList.stream().map(board->modelMapper.map(board,LectureGoodDTO.class)).collect(Collectors.toList());
        return lectureGoodDTOList;
    }
    @Override
    public LectureGoodDTO viewGood(LectureGoodDTO lectureGoodDTO) {
        LectureGoodEntity bbsGoodEntity = lectureGoodRepository.findByLectureIdxAndUserId(lectureGoodDTO.getLectureIdx(),lectureGoodDTO.getUserId());
        if(bbsGoodEntity!=null) {
            return modelMapper.map(bbsGoodEntity, LectureGoodDTO.class);
        }
        else {
            return null;
        }
    }
}
