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
public class LecturePlayedDTO {
    private int playedIdx;
    private int lectureIdx;
    private int lectureDetailIdx;
    private int percentage;
    private String userId;

    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
}
