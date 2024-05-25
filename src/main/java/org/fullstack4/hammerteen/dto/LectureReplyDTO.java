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
public class LectureReplyDTO {
    private int lectureReplyIdx;
    @NotNull
    private int lectureIdx;
    @NotEmpty
    private String reviewContent;
    private int rating;
    @NotEmpty
    private String userId;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
}