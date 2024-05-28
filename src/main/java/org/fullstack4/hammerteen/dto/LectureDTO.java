package org.fullstack4.hammerteen.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LectureDTO {
    private int lectureIdx;
    private String CategoryIdx;
    private String CategoryName;
    private int teacherIdx;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private int price;
    private String thumbnailVideoDirectory;
    private String thumbnailVideoFile;
    private String thumbnailImgDirectory;
    private String thumbnailImgFile;
    private String lectureRecommendTag;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;

    //지현 추가 강의 선생님이름 조회용 컬럼
    private String teacherName;

    private String percentageString;
    private String percentage;
}
