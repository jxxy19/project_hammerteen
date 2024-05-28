package org.fullstack4.hammerteen.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.domain.*;
import org.fullstack4.hammerteen.dto.*;
import org.fullstack4.hammerteen.repository.*;
import org.fullstack4.hammerteen.util.CommonFileUtil;
import org.fullstack4.hammerteen.util.CommonUtil;
import org.modelmapper.ModelMapper;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.*;
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
    private final LectureRecommendRepository lectureRecommendRepository;

    //지현추가 : 통계용
    private final OrderDetailRepository orderDetailRepository;
    //지현추가 : 성적관리용
    private final LectureScoreRepository lectureScoreRepository;
    private final MyLectureRepository myLectureRepository;


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
            lectureEntity.modify(lectureDTO.getTitle(),lectureDTO.getContent(),lectureDTO.getPrice(),lectureDTO.getCategoryIdx());
            lectureRepository.save(lectureEntity);
        }

    }

    @Override
    public void delete(int lectureIdx) {
        lectureRepository.deleteById(lectureIdx);
    }

    @Override
    public LectureDetailDTO view(LectureDetailDTO lectureDetailDTO) {
        Optional<LectureDetailEntity> result = lectureDetailRepository.findById(lectureDetailDTO.getLectureDetailIdx());
        LectureDetailEntity lectureDetailEntity = result.orElse(null);
        LectureDetailDTO resultDTO = modelMapper.map(lectureDetailEntity, LectureDetailDTO.class);

        return resultDTO;
    }

    @Override
    public LPageResponseDTO<LectureDTO> list(LPageRequestDTO lpageRequestDTO) {
        PageRequest pageable = lpageRequestDTO.getPageable("lectureIdx");
        Page<LectureEntity> result = null;
        String categoryIdx = lpageRequestDTO.getCategoryIdx();
        String search_word = lpageRequestDTO.getSearch_word();
        if(lpageRequestDTO.getSearch_word()!=null && !lpageRequestDTO.getSearch_word().isEmpty()) {
            result = lectureRepository.findAllByCategoryIdxStartingWithAndTeacherNameContainsOrTitleContainsOrderByLectureIdxDesc(
                    pageable, categoryIdx, search_word, search_word);
        }
        else{
            result = lectureRepository.findAll(pageable);
        }
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
                lectureRepository.save(lectureEntity);
            }
        }
    }

    @Override
    public LectureDTO registThumbnailVideo(LectureDTO lectureDTO, MultipartHttpServletRequest files) {
        String saveDirectory = "D:\\java4\\hammerteen\\src\\main\\resources\\static\\upload";
        List<String> filenames = null;
        filenames = commonFileUtil.thumbnailUploadVideo(files,saveDirectory);
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
                lectureRepository.save(lectureEntity);
            }
        }
    }

    @Override
    public void deleteThumbnailImg(int lectureIdx) {
        Optional<LectureEntity> result = lectureRepository.findById(lectureIdx);
        LectureEntity lectureEntity =result.orElse(null);
        if (lectureEntity != null && lectureEntity.getThumbnailImgFile()!=null) {
            commonFileUtil.fileDelite(lectureEntity.getThumbnailImgDirectory(), lectureEntity.getThumbnailImgFile());
        }
        if(lectureEntity !=null) {
            lectureEntity.modifyImg(null,null);
            lectureRepository.save(lectureEntity);
        }
    }

    @Override
    public void deleteThumbnailVideo(int lectureIdx) {
        Optional<LectureEntity> result = lectureRepository.findById(lectureIdx);
        LectureEntity lectureEntity =result.orElse(null);
        if (lectureEntity != null && lectureEntity.getThumbnailVideoFile()!=null) {
            commonFileUtil.fileDelite(lectureEntity.getThumbnailVideoDirectory(), lectureEntity.getThumbnailVideoFile());
        }
        if(lectureEntity !=null) {
            lectureEntity.modifyVideo(null,null);
            lectureRepository.save(lectureEntity);
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
            lectureDetailEntity.modify(lectureDetailDTO.getDetailTitle(),Integer.parseInt(lectureDetailDTO.getVideoLength()),lectureDetailDTO.getVideoDirectory(),lectureDetailDTO.getVideoFile());
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
        log.info("result " + result);
        if (lectureDetailEntity != null) {
            if(lectureDetailEntity.getVideoDirectory()!=null) {
                commonFileUtil.fileDelite(lectureDetailEntity.getVideoDirectory(), lectureDetailEntity.getVideoFile());
            }
        }
        lectureDetailRepository.deleteById(lectureDetailIdx);
    }
    @Override
    public void deleteThumbnailDetailFile(int lectureDetailIdx) {
        Optional<LectureDetailEntity> result = lectureDetailRepository.findById(lectureDetailIdx);
        LectureDetailEntity lectureDetailEntity =result.orElse(null);
        log.info("result2" + result);
        if (lectureDetailEntity != null && lectureDetailEntity.getVideoDirectory()!=null) {
            commonFileUtil.fileDelite(lectureDetailEntity.getVideoDirectory(), lectureDetailEntity.getVideoFile());
        }
        if(lectureDetailEntity !=null) {
            lectureDetailEntity.modifyVideo(null,null);
            lectureDetailRepository.save(lectureDetailEntity);
        }
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
    public LectureReplyDTO viewReply(LectureReplyDTO lectureReplyDTO) {
        LectureReplyEntity lectureReplyEntity = lectureReplyRepository.findByUserIdAndLectureIdx(lectureReplyDTO.getUserId(),lectureReplyDTO.getLectureIdx());
        if(lectureReplyEntity!=null) {
            LectureReplyDTO ResultDTO = modelMapper.map(lectureReplyEntity, LectureReplyDTO.class);
            return ResultDTO;
        }
        return null;
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

    // 지현추가 : 기존 구매 여부 확인용
    @Override
    public int checkOrder(String userId, int lectureIdx) {
        return orderDetailRepository.countAllByUserIdAndLectureIdx(userId, lectureIdx);
    }

    // 지현추가 : 통계용
    @Override
    public Map<String, Object> getStatics(int teacherIdx) {
        Map<String, Object> resultMap = new HashMap<>();
        List<StaticDTO> staticDTOList = new ArrayList<>();
        List<LectureEntity> lectureEntityList = lectureRepository.findAllByTeacherIdx(teacherIdx);
        lectureEntityList.forEach(vo -> {
            LectureDTO lectureDTO = modelMapper.map(vo, LectureDTO.class);
            StaticDTO staticDTO = StaticDTO.builder()
                    .lectureDTO(lectureDTO)
                    .studentCnt(orderDetailRepository.countByLectureIdx(lectureDTO.getLectureIdx(),"1"))
                    .revenue(orderDetailRepository.sumPriceByLectureIdx(lectureDTO.getLectureIdx(),"1"))
                    .build();
            staticDTO.setRevenueToString();
            staticDTOList.add(staticDTO);
        });
        JSONArray staticDTOListJSON = new JSONArray();
        for(StaticDTO dto : staticDTOList) {
            Map<String, String> lectureMap = new HashMap<>();
            lectureMap.put("\"lectureTitle\"", "\""+dto.getLectureDTO().getTitle()+"\"");
            lectureMap.put("\"studentCnt\"", "\""+dto.getStudentCnt()+"\"");
            lectureMap.put("\"revenue\"", "\""+dto.getRevenue()+"\"");
            staticDTOListJSON.put(lectureMap);
        }
        resultMap.put("staticDTOList", staticDTOList);
        resultMap.put("staticDTOListJSON", staticDTOListJSON.toString());
        return resultMap;
    }

    // 지현작업 : 찜 관련
    @Override
    public int registGood(LectureGoodDTO lectureGoodDTO) {
        LectureGoodEntity board = modelMapper.map(lectureGoodDTO, LectureGoodEntity.class);
        int idx = lectureGoodRepository.save(board).getGoodIdx();
        return idx;
    }

    @Transactional
    @Override
    public void deleteGood(int goodsIdx) {
        lectureGoodRepository.deleteById(goodsIdx);
    }

    @Override
    public List<LectureGoodDTO> listGood(String userId) {
        List<LectureGoodEntity> lectureGoodEntityList = lectureGoodRepository.findAllByUserId(userId);
        List<LectureGoodDTO> lectureGoodDTOList = null;
        if(lectureGoodEntityList != null) {
            lectureGoodDTOList = lectureGoodEntityList.stream().map(board->modelMapper.map(board,LectureGoodDTO.class)).collect(Collectors.toList());
            lectureGoodDTOList.forEach(dto -> {
                dto.setLectureDTO(modelMapper.map(lectureRepository.findById(dto.getLectureIdx()), LectureDTO.class));
            });
        }
        return lectureGoodDTOList;
    }

    @Override
    public int countList(String userId) {
        return lectureGoodRepository.countAllByUserId(userId);
    }

    // 성적관리용
    @Override
    public List<MyLectureDTO> myLectureList(String userId) {
        List<MyLectureEntity> myLectureEntityList = myLectureRepository.findAllByUserIdAndStatus(userId, "Y");
        List<MyLectureDTO> myLectureDTOList = null;
        if(myLectureEntityList != null) {
            myLectureDTOList = myLectureEntityList.stream()
                    .map(vo-> modelMapper.map(vo, MyLectureDTO.class))
                    .toList();
        }
        return  myLectureDTOList;
    }

    @Override
    public List<LectureScoreDTO> myScoreList(String userId) {
        List<LectureScoreEntity> lectureScoreEntityList = lectureScoreRepository.findAllByUserId(userId);
        List<LectureScoreDTO> lectureScoreDTOList = null;
        if(lectureScoreEntityList != null) {
            lectureScoreDTOList = lectureScoreEntityList.stream().map(vo -> modelMapper.map(vo, LectureScoreDTO.class)).collect(Collectors.toList());
        }
        return lectureScoreDTOList;
    }

    @Override
    public LectureScoreDTO myScore(String userId, int lectureIdx) {
        LectureScoreEntity lectureScoreEntity = lectureScoreRepository.findAllByLectureIdxAndUserId(lectureIdx, userId);
        LectureScoreDTO lectureScoreDTO = null;
        if(lectureScoreEntity != null) {
            lectureScoreDTO = modelMapper.map(lectureScoreEntity, LectureScoreDTO.class);
        }
        return lectureScoreDTO;
    }

    @Override
    public List<LectureDTO> lectureListForTeacher(int teacherIdx) {
        List<LectureEntity> lectureEntityList = lectureRepository.findAllByTeacherIdx(teacherIdx);
        List<LectureDTO> lectureDTOList = null;
        if(lectureEntityList != null) {
            lectureDTOList = lectureEntityList.stream().map(vo -> modelMapper.map(vo, LectureDTO.class)).collect(Collectors.toList());
        }
        return lectureDTOList;
    }

    @Override
    public List<LectureDTO> lectureListForAdmin() {
        List<LectureEntity> lectureEntityList = lectureRepository.findAll();
        List<LectureDTO> lectureDTOList = null;
        if(lectureEntityList != null) {
            lectureDTOList = lectureEntityList.stream().map(vo -> modelMapper.map(vo, LectureDTO.class)).collect(Collectors.toList());
        }
        return lectureDTOList;
    }

    @Override
    public List<MyLectureDTO> studentList(int lectureIdx) {
        List<MyLectureEntity> myLectureEntityList = myLectureRepository.findAllByLectureIdxAndStatus(lectureIdx, "Y");
        List<MyLectureDTO> myLectureDTOList = null;
        if(myLectureEntityList != null) {
            myLectureDTOList = myLectureEntityList.stream()
                    .map(vo -> modelMapper.map(vo, MyLectureDTO.class))
                    .toList();
        }
        return myLectureDTOList;
    }

    @Override
    public int saveLectureScore(LectureScoreDTO lectureScoreDTO) {
        return lectureScoreRepository.save(modelMapper.map(lectureScoreDTO, LectureScoreEntity.class)).getScoreIdx();
    }

    @Override
    public LectureDTO selectLectureDTOByIdx(int lectureIdx) {
        LectureEntity lectureEntity = lectureRepository.findAllByLectureIdx(lectureIdx);
        LectureDTO lectureDTO = null;
        if(lectureEntity != null) {
            lectureDTO = modelMapper.map(lectureEntity, LectureDTO.class);
        }
        return lectureDTO;
    }
//
//    @Override
//    public String getMemberId(int orderIdx) {
//        return orderDetailRepository.findUserId(orderIdx);
//    }
//
//    @Override
//    public LectureScoreDTO getLectureScoreDTO(int lectureIdx, String userId) {
//        LectureScoreEntity lectureScoreEntity = lectureScoreRepository.findAllByLectureIdxAndUserId(lectureIdx, userId);
//        LectureScoreDTO lectureScoreDTO = null;
//        if(lectureScoreEntity != null) {
//            lectureScoreDTO = modelMapper.map(lectureScoreEntity, LectureScoreDTO.class);
//        }
//        return lectureScoreDTO;
//    }

    //메인페이지 추천강의
    @Override
    public PageResponseDTO<LectureDTO> recommendList(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage_size(6);
        PageRequest pageable = pageRequestDTO.getPageable();

        Page<LectureEntity> result = null;
        String recommendTag = pageRequestDTO.getLectureRecommendTag();


        result = lectureRepository.findAllByLectureRecommendTagContainsOrderByLectureIdxDesc(pageable,recommendTag );


        List<LectureDTO> dtoList = result.stream()
                .map(board->modelMapper.map(board,LectureDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<LectureDTO>withAll().pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList).total_count((int) result.getTotalElements()).build();
    }

    @Override
    public List<LectureRecommendDTO> recommendNameList() {
        List<LectureRecommendEntity> lectureEntityList = lectureRecommendRepository.findAll();
        List<LectureRecommendDTO> dtoList = lectureEntityList.stream()
                .map(board->modelMapper.map(board,LectureRecommendDTO.class))
                .collect(Collectors.toList());
        return dtoList;
    }
}
