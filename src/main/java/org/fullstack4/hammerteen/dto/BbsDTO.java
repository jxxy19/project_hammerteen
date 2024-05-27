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
public class BbsDTO {
    private int bbsIdx;

    @NotEmpty
    private String userId;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private int price;
    @NotEmpty
    private String category1;
    private String category2;
    private int readCnt;
    private int replyCnt;
    private String location;


    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
}
