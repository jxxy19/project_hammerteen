package org.fullstack4.hammerteen.service;


import org.fullstack4.hammerteen.dto.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface BbsServiceIf {
    int regist(BbsDTO bbsDTO);
    BbsDTO view(int bbsIdx);
    PageResponseDTO<BbsDTO> list(PageRequestDTO pageRequestDTO);
    void modify(BbsDTO bbsDTO);
    void delete(int bbsIdx);
    PageResponseDTO<BbsDTO> myList(PageRequestDTO pageRequestDTO, String userId);
    void updateReadCnt (int bbsIdx);

    void registFile(BbsFileDTO bbsFileDTO, MultipartHttpServletRequest files);


    List<BbsFileDTO> listFile( int bbsIdx);

    void deleteFile(BbsFileDTO bbsFileDTO);
    PageResponseDTO<BbsDTO> hotboardList(PageRequestDTO pageRequestDTO);
//    강의 제목리스트
    List<LectureDTO> lectureList(LectureDTO lectureDTO);
//    강의 별 리스트
//    공지사항
    PageResponseDTO<BbsDTO> lectureNotice(PageRequestDTO pageRequestDTO, String lectureTitle);

}
