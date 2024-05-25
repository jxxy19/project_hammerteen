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
public class OrderDTO {
    private int orderIdx;
    private String userId;
    private String orderStatus;
    private String orderStatusToConvert;
    private int totalAmount;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;

    public void setOrderStatusToConvert() {
        switch(this.orderStatus){
            case "1" :
                this.orderStatusToConvert = "결제완료";
                break;
            case "2" :
                this.orderStatusToConvert = "구매확정";
                break;
            case "0" :
                this.orderStatusToConvert = "환불";
                break;
            default: this.orderStatusToConvert = "오류";
        }
    }
}
