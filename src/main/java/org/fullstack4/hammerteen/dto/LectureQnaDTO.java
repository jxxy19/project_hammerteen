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
public class LectureQnaDTO {
    private int lectureQnaIdx;

    @NotNull
    private int lectureIdx;

    @NotEmpty
    private String questionTitle;

    @NotEmpty
    private String questionContent;

    @NotEmpty
    private LocalDateTime RegDate;

    @Column(nullable=false)
    private String userId;

    @Column(nullable=true)
    private String answerTitle;

    @Column(nullable=true)
    private String answerContent;

    @Builder.Default
    private String answerYn ="N";

    @Column(nullable=true)
    private LocalDateTime answerRegDate;


    private LocalDateTime modifyDate;
}
