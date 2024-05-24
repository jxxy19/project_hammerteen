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
public class PaymentDTO {
    private int paymentIdx;
    private int orderIdx;
    private int paymentAmount;
    private String userId;
    private String userPhoneNumber;
    private String userName;
    private String userEmail;
    private String paymentMethod;
    private String paymentCompany;
    private String paymentMerchantId;
    private String paymentPgId;
    private String receiptUrl;
    private String paymentStatus;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
}
