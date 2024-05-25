package org.fullstack4.hammerteen.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fullstack4.hammerteen.util.CommonUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {
    private int paymentIdx;
    private int orderIdx;
    private int paymentAmount;
    private String paymentAmountToComma;
    private String userId;
    private String userPhoneNumber;
    private String userName;
    private String userEmail;
    private String paymentMethod;
    private String paymentMethodConvert;
    private String paymentCompany;
    private String paymentMerchantId;
    private String paymentPgId;
    private String receiptUrl;
    private String paymentStatus;
    private LocalDateTime regDate;
    private String regDateToString;
    private String regDateTimeToString;
    private LocalDateTime modifyDate;

    private OrderDTO orderDTO;
    private List<OrderDetailDTO> orderDetailDTOList;
    private List<LectureDTO> lectureDTOList;

    public void setPaymentAmountToComma() {
        this.paymentAmountToComma = CommonUtil.comma(String.valueOf(paymentAmount));
    }
    public void setRegDateToString() {
        this.regDateToString = this.regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    public void setRegDateTimeToString() {
        this.regDateTimeToString = this.regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    public void setPaymentMethodConvert() {
        switch (this.paymentMethod) {
            case "card" :
                this.paymentMethodConvert = "카드";
                break;
            case "trans" :
                this.paymentMethodConvert = "실시간 계좌이체";
                break;
            case "vbank" :
                this.paymentMethodConvert = "가상계좌";
                break;
            case "phone" :
                this.paymentMethodConvert = "휴대폰 소액결제";
                break;
            case "cultureland" :
                this.paymentMethodConvert = "컬쳐랜드 상품권 (구.문화상품권)";
                break;
            case "smartculture" :
                this.paymentMethodConvert = "스마트문상 (게임 문화 상품권)";
                break;
            case "happymoney" :
                this.paymentMethodConvert = "해피머니";
                break;
            case "booknlife" :
                this.paymentMethodConvert = "도서 문화 상품권";
                break;
            case "culturegift" :
                this.paymentMethodConvert = "문화상품권";
                break;
            case "samsung" :
                this.paymentMethodConvert = "삼성페이";
                break;
            case "kakaopay" :
                this.paymentMethodConvert = "카카오페이";
                break;
            case "naverpay" :
                this.paymentMethodConvert = "네이버페이";
                break;
            case "payco" :
                this.paymentMethodConvert = "페이코";
                break;
            case "lpay" :
                this.paymentMethodConvert = "L페이";
                break;
            case "ssgpay" :
                this.paymentMethodConvert = "SSG페이";
                break;
            case "tosspay" :
                this.paymentMethodConvert = "토스페이";
                break;
            case "applepay" :
                this.paymentMethodConvert = "애플페이";
                break;
            case "pinpay" :
                this.paymentMethodConvert = "핀페이";
                break;
            case "skpay" :
                this.paymentMethodConvert = "11Pay (구.SKPay)";
                break;
            case "wechat" :
                this.paymentMethodConvert = "위쳇페이";
                break;
            case "alipay" :
                this.paymentMethodConvert = "알리페이";
                break;
            case "unionpay" :
                this.paymentMethodConvert = "유니온페이";
                break;
            case "tenpay" :
                this.paymentMethodConvert = "텐페이";
                break;
            case "paysbuy" :
                this.paymentMethodConvert = "페이스바이";
                break;
            case "econtext" :
                this.paymentMethodConvert = "편의점 결제";
                break;
            case "molpay" :
                this.paymentMethodConvert = "MOL페이";
                break;
            case "point" :
                this.paymentMethodConvert = "베네피아 포인트 등 포인트 결제";
                break;
            case "paypal" :
                this.paymentMethodConvert = "페이팔";
                break;
            case "toss_brandpay" :
                this.paymentMethodConvert = "토스페이먼츠 브랜드페이";
                break;
            case "naverpay_card" :
                this.paymentMethodConvert = "네이버페이 - 카드)";
                break;
            case "naverpay_point" :
                this.paymentMethodConvert = "네이버페이 - 포인트";
                break;
            default:
                this.paymentMethodConvert = "기타";
        }
    }
}
