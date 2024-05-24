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
    private String lectureCategoryIdx;
    private String teacherName;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private int price;
    private LocalDate startDate;
    private LocalDate endDate;
    private String thumbnailVideoDirectory;
    private String thumbnailVideoFile;
    private String thumbnailImgDirectory;
    private String thumbnailImgFile;
    private String lectureRecommendTag;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;


}
