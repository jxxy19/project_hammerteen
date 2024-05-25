package org.fullstack4.hammerteen.service;


import org.fullstack4.hammerteen.dto.LectureDTO;
import org.fullstack4.hammerteen.dto.OrderDTO;
import org.fullstack4.hammerteen.dto.OrderDetailDTO;
import org.fullstack4.hammerteen.dto.PaymentDTO;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.util.List;

public interface PaymentServiceIf {
    int registPayment(String[] lectureIdxes, PaymentDTO paymentDTO);
    int confirmPayment(int paymentIdx);
    int refundPayment(int paymentIdx);
    List<PaymentDTO> selectPayment(String userId);
    List<LectureDTO> selectLecture(String[] lectureIdxes);
}
