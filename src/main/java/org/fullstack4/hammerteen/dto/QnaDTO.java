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
public class QnaDTO {

    private int qnaIdx;


    @NotEmpty
    private String questionTitle;

    @NotEmpty
    private String questionContent;

    @NotEmpty
    private String answerTitle;

    @NotEmpty
    private String answerContent;

    @Builder.Default
    private String answerYn ="N";


    private LocalDateTime answerRegDate;

    @NotEmpty
    private String userId;

    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
}
