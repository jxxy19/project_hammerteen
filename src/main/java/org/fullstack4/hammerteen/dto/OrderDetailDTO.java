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
public class OrderDetailDTO {
    private int orderDetailIdx;
    private int orderIdx;
    private int lectureIdx;
    private String title;
    private String teacherName;
    private int price;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
}
