package org.fullstack4.hammerteen.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.hammerteen.domain.LectureEntity;
import org.fullstack4.hammerteen.domain.OrderDetailEntity;
import org.fullstack4.hammerteen.domain.OrderEntity;
import org.fullstack4.hammerteen.domain.PaymentEntity;
import org.fullstack4.hammerteen.dto.LectureDTO;
import org.fullstack4.hammerteen.dto.OrderDTO;
import org.fullstack4.hammerteen.dto.OrderDetailDTO;
import org.fullstack4.hammerteen.dto.PaymentDTO;
import org.fullstack4.hammerteen.repository.LectureRepository;
import org.fullstack4.hammerteen.repository.OrderDetailRepository;
import org.fullstack4.hammerteen.repository.OrderRepository;
import org.fullstack4.hammerteen.repository.PaymentRepository;
import org.fullstack4.hammerteen.util.CommonUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentServiceIf{
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final LectureRepository lectureRepository;
    private final ModelMapper modelMapper;
    @Transactional
    @Override
    public int registPayment(String[] lectureIdxes, PaymentDTO paymentDTO) {

        int orderIdx = orderRepository.save(OrderEntity.builder()
                .totalAmount(paymentDTO.getPaymentAmount())
                .userId(paymentDTO.getUserId())
                .build()).getOrderIdx();
        List<LectureEntity> lectureEntityList = new ArrayList<>();
        for(String lectureIdx : lectureIdxes) {
            lectureEntityList = lectureRepository.findAllById(Arrays.stream(lectureIdxes).map(CommonUtil::parseInt).collect(Collectors.toList()));
        }
        for(LectureEntity lectureEntity : lectureEntityList) {
            orderDetailRepository.save(OrderDetailEntity.builder()
                    .lectureIdx(lectureEntity.getLectureIdx())
                            .orderIdx(orderIdx)
                            .price(lectureEntity.getPrice())
                            .teacherName(lectureEntity.getTeacherName())
                            .title(lectureEntity.getTitle())
                            .build());
        }
        paymentDTO.setOrderIdx(orderIdx);
        paymentDTO.setPaymentStatus("Y");
        int idx = paymentRepository.save(modelMapper.map(paymentDTO, PaymentEntity.class)).getPaymentIdx();
        return idx;
    }

    @Override
    public void deletePayment(int paymentIdx) {
        paymentRepository.deleteById(paymentIdx);
    }

    @Override
    public List<PaymentDTO> selectPayment(String userId) {
        List<PaymentDTO> paymentDTOList = null;
        List<PaymentEntity> paymentEntityList = paymentRepository.findAllByUserIdEquals(userId);
        if(paymentEntityList != null) {
            paymentDTOList = paymentEntityList.stream().map(vo ->modelMapper.map(vo, PaymentDTO.class)).collect(Collectors.toList());
            paymentDTOList.forEach(dto ->
                {
                    dto.setPaymentAmountToComma();
                    dto.setRegDateToString();
                    dto.setPaymentMethodConvert();
                    dto.setRegDateTimeToString();
                    dto.setOrderDTO(modelMapper.map(orderRepository.findById(dto.getOrderIdx()),OrderDTO.class));
                    List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
                    dto.setOrderDetailDTOList(
                            orderDetailRepository.findAllByOrderIdx(dto.getOrderIdx()).stream().map(vo->modelMapper.map(vo, OrderDetailDTO.class)).collect(Collectors.toList())
                    );
                    List<LectureDTO> letureDTOList = new ArrayList<>();
                    for(OrderDetailDTO orderDetailDTO:dto.getOrderDetailDTOList()) {
                        LectureDTO lectureDTO = modelMapper.map(lectureRepository.findById(orderDetailDTO.getLectureIdx()), LectureDTO.class);
                        letureDTOList.add(lectureDTO);
                    }
                    dto.setLectureDTOList(letureDTOList);
                }
            );
        }
        return paymentDTOList;
    }

    @Override
    public List<LectureDTO> selectLecture(String[] lectureIdxes) {
        List<LectureDTO> lectureDTOList = null;
        List<LectureEntity> lectureEntityList = lectureRepository.findAllById(Arrays.stream(lectureIdxes).map(CommonUtil::parseInt).collect(Collectors.toList()));
        if(lectureEntityList != null) {
            lectureDTOList = lectureEntityList.stream().map(vo->modelMapper.map(vo, LectureDTO.class)).collect(Collectors.toList());
        }
        return lectureDTOList;
    }
}
