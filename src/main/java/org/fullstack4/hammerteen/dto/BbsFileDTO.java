package org.fullstack4.hammerteen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BbsFileDTO {
    private int bbsFileIdx;
    private int bbsIdx;
    private String directory;
    private String fileName;
    private String userId;
    private LocalDateTime reg_date;
    private LocalDateTime modifyDate;
}
