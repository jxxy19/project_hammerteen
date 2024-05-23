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


    private int lecturePrice;

    @NotNull
    private int lectureIdx;

    @NotEmpty
    private String lectureName;

    @NotEmpty
    private String userId;

    @NotEmpty
    private String userPhoneNumber;

    @NotEmpty
    private String userName;

    @NotEmpty
    private String userEmail;

    @NotEmpty
    private String paymentMethod;

    @NotEmpty
    private String paymentCompany;


    private int paymentAmount;

    @NotEmpty
    private String paymentNumber;

    @NotEmpty
    private String paymentStatus;


    private LocalDateTime paymentDate;


    private LocalDateTime refundDate;
}
