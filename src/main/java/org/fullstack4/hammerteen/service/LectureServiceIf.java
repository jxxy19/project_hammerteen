package org.fullstack4.hammerteen.service;


import org.fullstack4.hammerteen.dto.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;

public interface LectureServiceIf {
    LPageResponseDTO<LectureDTO> list(LPageRequestDTO lpageRequestDTO);
    int regist(LectureDTO lectureDTO);
    LectureDTO registThumbnailImg(LectureDTO lectureDTO, MultipartHttpServletRequest files);
    void modifyThumbnailImg(LectureDTO lectureDTO, MultipartHttpServletRequest files);
    LectureDTO registThumbnailVideo(LectureDTO lectureDTO, MultipartHttpServletRequest files);
    void modifyThumbnailVideo(LectureDTO lectureDTO, MultipartHttpServletRequest files);
    void deleteThumbnailImg(int lectureIdx);
    void deleteThumbnailVideo(int lectureIdx);
    LectureDTO view(LectureDTO lectureDTO);
    void modify(LectureDTO lectureDTO);
    void delete(int lectureIdx);

    void registLectureDetail(LectureDetailDTO lectureDetailDTO);
    public void registLectureDetailVideo(LectureDetailDTO lectureDetailDTO, MultipartHttpServletRequest files,String videoParam);
    void modifyLectureDetail(LectureDetailDTO lectureDetailDTO);
    void modifyLectureDetailVideo(LectureDetailDTO lectureDetailDTO, MultipartHttpServletRequest files,String videoParam);
    void deleteLectureDetail(int lectureDetailIdx);
    void deleteLectureDetailAll(int lectureIdx);
    List<LectureDetailDTO> listLectureDetail(int lectureIdx);
    void registLectureReply(LectureReplyDTO lectureReplyDTO);
    void modifyLectureReply(LectureReplyDTO lectureReplyDTO);
    void deleteLectureReply(int lectureReplyIdx);
    void deleteThumbnailDetailFile(int lectureDetailIdx);
    void deleteLectureReplyAll(int lectureIdx);
    LPageResponseDTO<LectureReplyDTO> listLectureReply(LPageRequestDTO lpageRequestDTO,int lectureIdx);
    int countCategory(String categoryIdx);
    LectureReplyDTO viewReply(LectureReplyDTO lectureReplyDTO);

    // 지현추가 : 기존 구매여부 확인용
    int checkOrder(String userId, int lectureIdx);

    // 지현추가 : 선생님 통계 조회용
    Map<String, Object> getStatics(int teacherIdx);

    // 지현작업 : 찜 관련
    int registGood(LectureGoodDTO lectureGoodDTO);
    void deleteGood(int goodsIdx);
    List<LectureGoodDTO> listGood(String userId);
    int countList(String userId);

}
