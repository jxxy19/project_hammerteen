package org.fullstack4.hammerteen.service;


import org.fullstack4.hammerteen.dto.BbsDTO;
import org.fullstack4.hammerteen.dto.BbsFileDTO;
import org.fullstack4.hammerteen.dto.PageRequestDTO;
import org.fullstack4.hammerteen.dto.PageResponseDTO;
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
}
