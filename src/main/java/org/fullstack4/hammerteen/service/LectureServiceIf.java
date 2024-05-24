package org.fullstack4.hammerteen.service;


import org.fullstack4.hammerteen.dto.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

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
    void delete(LectureDTO lectureDTO);

    void registLectureDetail(LectureDetailDTO lectureDetailDTO);
    void modifyLectureDetail(LectureDetailDTO lectureDetailDTO);
    void deleteLectureDetail(LectureDetailDTO lectureDetailDTO);
    void deleteLectureDetail(int lectureIdx);
    List<LectureDetailDTO> listLectureDetail(LPageRequestDTO lPageRequestDTO, int lectureIdx);
    void registLectureReply(LectureReplyDTO lectureReplyDTO);
    void modifyLectureReply(LectureReplyDTO lectureReplyDTO);
    void deleteLectureReply(LectureReplyDTO lectureReplyDTO);
    void deleteLectureReplyAll(int lectureIdx);
    LPageResponseDTO<LectureReplyDTO> listLectureReply(LPageRequestDTO lPageRequestDTO,int lectureIdx);
    int countCategory(String categoryName);

}
