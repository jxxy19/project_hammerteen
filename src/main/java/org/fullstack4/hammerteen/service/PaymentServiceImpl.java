package org.fullstack4.hammerteen.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.parser.JSONParser;
import org.fullstack4.hammerteen.domain.LectureEntity;
import org.fullstack4.hammerteen.domain.OrderDetailEntity;
import org.fullstack4.hammerteen.domain.OrderEntity;
import org.fullstack4.hammerteen.domain.PaymentEntity;
import org.fullstack4.hammerteen.dto.*;
import org.fullstack4.hammerteen.repository.*;
import org.fullstack4.hammerteen.util.CommonUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentServiceIf{
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final LectureRepository lectureRepository;
    private final MemberRepository memberRepository;
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
                            .teacherIdx(lectureEntity.getTeacherIdx())
                            .title(lectureEntity.getTitle())
                            .build());
        }
        paymentDTO.setOrderIdx(orderIdx);
        paymentDTO.setPaymentStatus("Y");
        int idx = paymentRepository.save(modelMapper.map(paymentDTO, PaymentEntity.class)).getPaymentIdx();
        return idx;
    }

    @Override
    public int confirmPayment(int paymentIdx) {
        PaymentEntity paymentEntity = paymentRepository.findAllByPaymentIdx(paymentIdx);
        OrderEntity orderEntity = orderRepository.findAllByOrderIdx(paymentEntity.getOrderIdx());
        orderEntity.setOrderStatus("2");
        int idx = orderRepository.save(orderEntity).getOrderIdx();
        return idx;
    }

    @Transactional
    @Override
    public int refundPayment(int paymentIdx) {
        int result = 0;
        PaymentEntity paymentEntity = paymentRepository.findAllByPaymentIdx(paymentIdx);
        OrderEntity orderEntity = orderRepository.findAllByOrderIdx(paymentEntity.getOrderIdx());

        String IMPORT_TOKEN_URL = "https://api.iamport.kr/users/getToken";
        String IMPORT_CANCEL_URL = "https://api.iamport.kr/payments/cancel";
        String KEY = "5873585476476853";
        String SECRET = "jxJRxaKCjLyjoHNGH4iK3POOiknyw7VVLi7aGc4pfCT8yAP4Xjha8SN8a1q2fOEjNd3rb7ZPy6vkbYcD";
        String mid = paymentEntity.getPaymentMerchantId();

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("imp_key", KEY);
        paramMap.put("imp_secret", SECRET);

        Map<String, String> paramMap2 = new HashMap<>();
        paramMap2.put("merchant_uid", mid);

        String responseBodyForToken = CommonUtil.postConnection(IMPORT_TOKEN_URL, paramMap);
        String access_token = CommonUtil.getAccessToken(responseBodyForToken);

        String responseBodyForRefund = CommonUtil.postConnection(IMPORT_CANCEL_URL, access_token, paramMap2);
        String response = CommonUtil.getResponse(responseBodyForRefund);

        log.info("paymentEntity : {}", paymentEntity);
        log.info("orderEntity : {}", orderEntity);
        log.info("responseBodyForToken : {}", responseBodyForToken);
        log.info("access_token : {}", access_token);
        log.info("responseBodyForRefund : {}", responseBodyForRefund);
        log.info("response : {}", response);

        if(response.equals("null")){
            log.info("환불 실패");
        } else {
            log.info("DB처리 로직 시작");
            paymentEntity.setPaymentStatus("N");
            orderEntity.setOrderStatus("0");
            int oIdx = orderRepository.save(orderEntity).getOrderIdx();
            int pIdx = paymentRepository.save(paymentEntity).getPaymentIdx();
            if(oIdx > 0 && pIdx > 0) {
                result = 1;
            }
        }
        return result;
    }

    @Override
    public List<PaymentDTO> selectPayment(String userId) {
        List<PaymentDTO> paymentDTOList = null;
        List<PaymentEntity> paymentEntityList = paymentRepository.findAllByUserIdEqualsOrderByPaymentIdxDesc(userId);
        if(paymentEntityList != null) {
            paymentDTOList = paymentEntityList.stream().map(vo ->modelMapper.map(vo, PaymentDTO.class)).collect(Collectors.toList());
            paymentDTOList.forEach(dto ->
                {
                    dto.setPaymentAmountToComma();
                    dto.setRegDateToString();
                    dto.setPaymentMethodConvert();
                    dto.setRegDateTimeToString();
                    dto.setOrderDTO(modelMapper.map(orderRepository.findById(dto.getOrderIdx()),OrderDTO.class));
                    dto.setOrderDTOOrderStatus();
                    List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
                    dto.setOrderDetailDTOList(
                            orderDetailRepository.findAllByOrderIdx(dto.getOrderIdx()).stream().map(vo->modelMapper.map(vo, OrderDetailDTO.class)).collect(Collectors.toList())
                    );
                    List<LectureDTO> letureDTOList = new ArrayList<>();
                    for(OrderDetailDTO orderDetailDTO:dto.getOrderDetailDTOList()) {
                        LectureDTO lectureDTO = modelMapper.map(lectureRepository.findById(orderDetailDTO.getLectureIdx()), LectureDTO.class);
                        MemberDTO memberDTO = modelMapper.map(memberRepository.findById(lectureDTO.getTeacherIdx()), MemberDTO.class);
                        lectureDTO.setTeacherName(memberDTO.getName());
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
            lectureDTOList.forEach(dto -> {
                MemberDTO memberDTO = modelMapper.map(memberRepository.findById(dto.getTeacherIdx()), MemberDTO.class);
                dto.setTeacherName(memberDTO.getName());
            });
        }
        return lectureDTOList;
    }
}
