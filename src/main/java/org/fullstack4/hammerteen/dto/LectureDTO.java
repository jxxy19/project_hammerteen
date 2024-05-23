package org.fullstack4.hammerteen.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LectureDTO {

    @NotEmpty
    private String lectureIdx;
    @NotEmpty
    private String title;
    @NotEmpty
    private int price;
    @NotEmpty
    private String categoryIdx;
    @NotEmpty
    private String content;
    @NotEmpty
    private LocalDateTime startDate;
    @NotEmpty
    private LocalDateTime endDate;
    private String video;
    private String img;
    private String lectureRecommend;

    private LocalDateTime regDate;
    private LocalDateTime modifyDate;


}
