package org.fullstack4.hammerteen.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fullstack4.hammerteen.util.CommonUtil;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaticDTO {
    private LectureDTO lectureDTO;
    private int studentCnt;
    private int revenue;
    private String revenueToString;

    public void setRevenueToString() {
        this.revenueToString = CommonUtil.comma(String.valueOf(revenue));
    }

}
