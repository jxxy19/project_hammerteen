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
    private String lecture_idx;
    @NotEmpty
    private String title;
    @NotEmpty
    private int price;
    @NotEmpty
    private String category_idx;
    @NotEmpty
    private String content;
    @NotEmpty
    private LocalDateTime start_date;
    @NotEmpty
    private LocalDateTime end_date;
    private String video;
    private String img;
    private String lecture_recommend;

    private LocalDateTime regDate;
    private LocalDateTime modifyDate;


}
