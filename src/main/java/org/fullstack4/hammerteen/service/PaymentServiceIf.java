package org.fullstack4.hammerteen.service;


import org.fullstack4.hammerteen.dto.LectureDTO;
import org.fullstack4.hammerteen.dto.OrderDTO;
import org.fullstack4.hammerteen.dto.OrderDetailDTO;
import org.fullstack4.hammerteen.dto.PaymentDTO;

import java.util.List;

public interface PaymentServiceIf {
    int registPayment(String[] lectureIdxes, PaymentDTO paymentDTO);
    void deletePayment(int paymentIdx);
    List<PaymentDTO> selectPayment(String userId);
    List<LectureDTO> selectLecture(String[] lectureIdxes);
}
