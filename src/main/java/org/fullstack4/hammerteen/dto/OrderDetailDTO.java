package org.fullstack4.hammerteen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDTO {
    private int orderDetailIdx;
    private int orderIdx;
    private int lectureIdx;
    private String title;
    private int teacherIdx;
    private int price;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;

    //성적 조회용
    private LectureScoreDTO lectureScoreDTO;
    private MemberDTO memberDTO;
}
