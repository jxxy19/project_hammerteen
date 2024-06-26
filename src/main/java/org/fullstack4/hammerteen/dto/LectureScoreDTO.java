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
public class LectureScoreDTO {
    private int scoreIdx;

    @NotNull
    private int lectureIdx;


    private int lectureScore;

    @NotEmpty
    private String userId;

    private LocalDateTime regDate;
    private LocalDateTime modifyDate;

    // 데이터 조회용
    private LectureDTO lectureDTO;
}
