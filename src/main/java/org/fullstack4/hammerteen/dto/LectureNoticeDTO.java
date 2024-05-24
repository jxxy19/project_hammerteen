package org.fullstack4.hammerteen.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LectureNoticeDTO {
    private int lectureNoticeIdx;

    @NotNull
    private int lectureIdx;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;


    private int readCnt;

    private LocalDateTime regDate;
    private LocalDateTime modifyDate;

}
